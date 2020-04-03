(ns hamming
  (:require [clojure.test :refer [is]]))

(defn distance
  {:test (fn []
           (is (= 7 (distance "GAGCCTACTAACGGGAT" "CATCGTAATGACGGCCT")))
           (is (nil? (distance "GAT" "GATACA"))))}
  [strand1 strand2]
  (when (= (count strand1) (count strand2))
    (->> (map #(= %1 %2) strand1 strand2)
         (filter false?)
         (count))))
