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
    (str (say-1-19 $) " hundred")))

(defn- say-1-999 [n]
  (cond 
    (< n 100)           (say-1-99 n)
    (zero? (mod n 100)) (say-100-900 n)
    :else               (str (say-100-900 n) " " (say-1-99 (mod n 100)))))

(defn- say-chunk [n div]
  (str (say-1-999 n) " " ({1000 "thousand"
                           1000000 "million"
                           1000000000 "billion"} div)))

(defn- chunk-divisor
  "Calculate the largest group/chunk of thousands for `n`"
  [n]
  (as-> n $
    (Math/log10 $)
    (Math/floor $)
    (repeat $ 10)
    (partition 3 $)
    (flatten $)
    (apply * $)))

(defn number [n]
  (cond
    (< n 0)            (throw (.IllegalArgumentException n))
    (> n 999999999999) (throw (.IllegalArgumentException n))
    (zero? n)          "zero"
    (< n 1000)         (say-1-999 n)
    :else              (let [chunk-div (chunk-divisor n)
                             chunk-head (quot n chunk-div)
                             chunk-tail (- n (* chunk-div chunk-head))]
                         (if (zero? (mod n chunk-div))
                           (say-chunk chunk-head chunk-div)
                           (str (say-chunk chunk-head chunk-div) " " (number chunk-tail))))))


(comment
  (number 987654321123)
  ;; => "nine hundred eighty-seven billion six hundred fifty-four million three hundred twenty-one thousand one hundred twenty-three"
)