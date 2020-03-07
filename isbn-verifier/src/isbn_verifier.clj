(ns isbn-verifier
  (:require [clojure.string :as string]))

(defn- isbn-digit->val [dc]
  (if (= \X dc) 
    10
    (Character/digit dc 10)))

(def weights-10 (range 1 11))

(defn isbn-sum [isbn weights]
  (->> isbn
       (re-seq #"([0-9X])")
       (mapcat last)
       (map isbn-digit->val)
       (map * weights)
       (apply +)))

(defn isbn? [s]
  (let [s (string/replace s #"-", "")]
    (if (or (not= 10 (count s)) (re-find #"X." s))
      false
      (zero? (mod (isbn-sum s weights-10) 11)))))
