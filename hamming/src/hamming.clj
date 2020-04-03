(ns hamming
  (:require [clojure.test :refer [is]]))

(defn distance
  {:test (fn []
           (is (= 7 (distance "GAGCCTACTAACGGGAT" "CATCGTAATGACGGCCT")))
           (is (nil? (distance "GAT" "GATACA"))))}
  [s1 s2]
  (when (= (count s1) (count s2))
    (->> (map = s1 s2)
         (filter false?)
         (count))))
