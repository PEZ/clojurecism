(ns anagram
  (:require [clojure.string :refer [lower-case]]))

(defn- anagram? [word prospect]
  (let [word (lower-case word)
        prospect (lower-case prospect)]
    (and
     (not= word prospect)
     (= (frequencies word) (frequencies prospect)))))

(defn anagrams-for [word prospect-list]
  (filter (partial anagram? word) prospect-list))

(comment
  (anagrams-for "listen" ["enlists" "google" "inlets" "banana"])
  ;; => ("inlets")
  )