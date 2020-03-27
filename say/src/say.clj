(ns say)

(def ^:private english
  {0 "zero"
   1 "one"
   2 "two"
   3 "three"
   4 "four"
   5 "five"
   6 "six"
   7 "seven"
   8 "eight"
   9 "nine"
   10 "ten"
   11 "eleven"
   12 "twelve"
   13 "thriteen"
   14 "fourteen"
   15 "fiveteen"
   16 "sixteen"
   17 "seventeen"
   18 "eighteen"
   19 "nineteen"
   20 "twenty"
   30 "thirty"
   40 "forty"
   50 "fifty"
   60 "sixty"
   70 "seventy"
   80 "eighty"
   90 "ninety"
   100 "hundred"
   1000 "thousand"
   1000000 "million"
   1000000000 "billion"})

(defn- chunk-divisor
  "Calculate the largest power-of-10 group/chunk of `n`"
  [n]
  (cond
    (zero? n)  0
    (< n 10)   1
    (< n 100)  10
    (< n 1000) 100
    :else      (as-> n $
                 (Math/log10 $)
                 (Math/floor $)
                 (repeat $ 10)
                 (partition 3 $)
                 (flatten $)
                 (apply * $))))

(defn number [n]
  (cond
    (< n 0)            (throw (.IllegalArgumentException "Numbers below zero are not allowed"))
    (> n 999999999999) (throw (.IllegalArgumentException "Numbers above the billions are not allowed"))
    :else              (let [chunk-div (chunk-divisor n)
                             chunk-head (quot n chunk-div)
                             chunk-tail (- n (* chunk-head chunk-div))]
                         (cond
                           (< n 20)         (english n)
                           (= 10 chunk-div) (str (english (* chunk-head 10)) "-" (english chunk-tail))
                           :else            (str (number chunk-head) " " (english chunk-div) " " (number chunk-tail))))))


(comment
  (chunk-divisor 401)
  (chunk-divisor 987654321123)
  (* (quot 987654321123 1000000000) 100000000000)
  (- 987654321123 900000000000)
  (chunk-divisor 41)
  (* (quot 41 10) 10)
  (- 41 (* 10 40))
  (mod 41 10)
  (number 0)
  (number 41)
  (number 400)
  (number 1100)
  (number 481)
  (number 11141)
  (number 111111)
  (number 987654321123)
  ;; => "nine hundred eighty-seven billion six hundred fifty-four million three hundred twenty-one thousand one hundred twenty-three"
  )