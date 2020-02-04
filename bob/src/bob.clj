(ns bob
  (:require clojure.string))

(defn response-for
  ([]
   (response-for ""))
  ([s]
   (cond (re-find #"^\s*$" s)
         "Fine. Be that way!"
         
         (and (re-find #"\p{L}" s) (= (clojure.string/upper-case s) s))
         (if (re-find #"\?\s*$" s)
           "Calm down, I know what I'm doing!"
           "Whoa, chill out!")
         
         (re-find #"\?\s*$" s)
         "Sure."
         
         :else
         "Whatever.")))
