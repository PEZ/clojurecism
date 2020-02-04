(ns armstrong-numbers)

(defn armstrong? [num] ;; <- arglist goes here
  (let [digits (count (str num))]
    (->> num
         str
         (map int)
         (map #(- % 48))
         (map #(apply * (repeat digits %)))
         (apply +)
         (= num))))

(comment
  (armstrong? 9)
  (armstrong? 10)
  (armstrong? 153)
  (armstrong? 154)
  (filter armstrong? (range 100000))
  (armstrong? 21897142587612075)
  ;; => true
  )