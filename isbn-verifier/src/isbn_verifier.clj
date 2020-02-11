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

(defn isbn? [s]
  (let [s (clojure.string/replace s #"-", "")]
    (if (or (not= 10 (count s)) (re-find #"X." s))
      false
      (let [isbn-sum (->> s
                          (re-seq #"([0-9X])")
                          (mapcat last)
                          (map isbn-digit->val)
                          (map-indexed #(* (inc %1) %2))
                          (apply +))]
        (= 0 (mod isbn-sum 11))))))
