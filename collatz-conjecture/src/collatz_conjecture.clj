(ns collatz-conjecture)

(defn collatz [n]
  {:pre [(pos-int? n)]}
  (let [step (fn [n]
               (if (even? n)
                 (quot n 2)
                 (inc (* n 3))))]
    (->> n
         (iterate step)
         (take-while #(not= 1 %))
         (count))))
