(ns collatz-conjecture)

#_(defn collatz [num]
    {:pre [(pos-int? num)]}
    (reduce (fn [n i]
              (cond
                (= n 1)   (reduced i)
                (even? n) (/ n 2)
                :else     (+ (* n 3) 1)))
            num (range)))

(defn collatz [n]
    {:pre [(pos-int? n)]}
    (->> n
         (iterate #(if (even? %)
                     (/ % 2)
                     (+ (* % 3) 1)))
         (take-while #(not= 1 %))
         count))
