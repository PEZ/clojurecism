(ns word-count
  (:require [clojure.string :as string]))

(defn word-count [s]
  (->> (string/lower-case s)
       (re-seq #"\w+")
       (group-by identity)
       (reduce-kv (fn [m k v] (assoc m k (count v))) {})))

(comment
  (word-count "Hello world! Beatiful world, hello.")
  ;; => {"beatiful" 1, "hello" 2, "world" 2}
  )