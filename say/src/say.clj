(ns say)

(def ^:private primitives
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
  (when-not (<= 0 n 999999999999)
    (throw (.IllegalArgumentException "Number out of bounds")))
  (if (and (< n 100) (primitives n))
    (primitives n)
    (let [chunk-div (chunk-divisor n)
          chunk-head (quot n chunk-div)
          chunk-tail (- n (* chunk-head chunk-div))
          say-tail (fn [sep] (when-not (zero? chunk-tail) (str sep (number chunk-tail))))]
      (if (= chunk-div 10)
        (str (number (* chunk-head 10)) (say-tail "-"))
        (str (number chunk-head) " " (primitives chunk-div) (say-tail " "))))))

(comment
  (number 987654321123)
  ;; => "nine hundred eighty-seven billion six hundred fifty-four million three hundred twenty-one thousand one hundred twenty-three"
  )