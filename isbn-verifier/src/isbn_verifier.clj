(ns isbn-verifier
  (:require clojure.string))

(defn- isbn-digit->val
  [d]
  (case d
    \0 0
    \1 1
    \2 2
    \3 3
    \4 4
    \5 5
    \6 6
    \7 7
    \8 8
    \9 9
    \X 10
    (throw (AssertionError. (str "Can't cope with that digit: " d)))))

(def weights-10 (range 1 11))

(defn isbn-sum [isbn weights]
  (->> isbn
       (re-seq #"([0-9X])")
       (mapcat last)
       (map isbn-digit->val)
       (map #(* %1 %2) weights)
       (apply +)))

(defn isbn? [s]
  (let [s (clojure.string/replace s #"-", "")]
    (if (or (not= 10 (count s)) (re-find #"X." s))
      false
      (= 0 (mod (isbn-sum s weights-10) 11)))))
