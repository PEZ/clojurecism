(ns collatz-conjecture)

(defn- collatz-steps [num]
  (reductions (fn [n i]
                (cond
                  (= n 1)   (reduced i)
                  (even? n) (/ n 2)
                  :else     (+ (* n 3) 1)))
              num (range)))

(comment
  (collatz-steps 12)
  ;; => (12 6 3 10 5 16 8 4 2 1 9)
  )

(defn collatz [num]
  {:pre [(pos-int? num)]}
  (->> num
       collatz-steps
       last))
