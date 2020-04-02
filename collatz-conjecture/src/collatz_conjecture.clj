(ns collatz-conjecture)

(defn collatz [num]
  (assert (pos-int? num))
  (loop [i 0
         n num]
    (if (= n 1)
      i
      (recur (inc i) (if (even? n)
                       (/ n 2)
                       (+ (* n 3) 1))))))

(comment
  (collatz 12)
  ;; => 9
  )