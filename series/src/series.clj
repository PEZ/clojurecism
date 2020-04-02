(ns series)

(defn slices [string length]
  (if (> length 0)
    (map (partial apply str) (partition length 1 string))
    [""]))