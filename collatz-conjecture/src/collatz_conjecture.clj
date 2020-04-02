(ns collatz-conjecture)

(defn collatz [n]
  {:pre [(pos-int? n)]}
  (->> n
       (iterate #(if (even? %)
                   (/ % 2)
                   (+ (* % 3) 1)))
       (take-while #(not= 1 %))
       count))
