(ns say)

(defn- say-1-19 [n]
  (->> n
       (dec)
       (nth ["one" "two" "three" "four" "five"
             "six" "seven" "eight" "nine" "ten"
             "eleven" "twelve" "thriteen" "fourteen" "fiveteen"
             "sixteen" "seventeen" "eighteen" "nineteen"])))

(defn- say-20-90 [n]
  (as-> n $
    (quot $ 10)
    (- $ 2)
    (nth ["twenty" "thirty" "forty" "fifty"
          "sixty" "seventy" "eighty" "ninety"]
         $)))

(defn- say-1-99 [n]
  (cond
    (< n 20)           (say-1-19 n)
    (zero? (mod n 10)) (say-20-90 n)
    :else              (str (say-20-90 n) "-" (say-1-19 (mod n 10)))))

(defn- say-100-900 [n]
  (as-> n $
    (quot $ 100)
    (str (say-1-99 $) " hundred")))

(defn- say-1-999 [n]
  (cond 
    (< n 100)           (say-1-99 n)
    (zero? (mod n 100)) (say-100-900 n)
    :else               (str (say-100-900 n) " " (say-1-99 (mod n 100)))))

(defn- say-thousands-prefix [n div]
  (as-> n $
    (quot $ div)
    (str (say-1-999 $) " " ({1000 "thousand"
                             1000000 "million"
                             1000000000 "billion"} div))))

(defn- say-1000+ [n]
  (let [div (cond
              (< n 1000000)       1000
              (< n 1000000000)    1000000
              (< n 1000000000000) 1000000000)
        q (quot n div)
        q10 (* div q)
        n-1-q99 (- n q10)
        m (mod n div)]
    (cond 
      (zero? m)     (say-thousands-prefix n div)
      (< n 1000)    (say-1-999 n)
      (< n 1000000) (str (say-thousands-prefix n div) " " (say-1-999 n-1-q99))
      :else         (str (say-thousands-prefix n div) " " (say-1000+ n-1-q99)))))

(defn number [n]
  (if (or (< n 0) (> n 999999999999))
    (throw (.IllegalArgumentException n))
    (cond
      (zero? n)           "zero"
      (< n 20)            (say-1-19 n)
      (< n 1000)          (say-1-999 n)
      (< n 1000000000000) (say-1000+ n))))


(comment
  (number 89)
  (number 100)
  (number 999)
  (number 1000)
  (number 1999)
  (number 1000000000)
  (number 9002345)
  (number 99002345)
  (number 999002345)
  (number 987654321123))