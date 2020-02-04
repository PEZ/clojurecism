(ns bob
  (:require clojure.string))

(defn response-for
  [s]
  (let [nothing-to-say? #(re-find #"^\s*$" s)
        yelling? #(and (re-find #"\p{L}" s) (= (clojure.string/upper-case s) s))
        asking? #(re-find #"\?\s*$" s)
        asking-loud? #(and (yelling?) (asking?))]
    (cond (nothing-to-say?) "Fine. Be that way!"
          (asking-loud?)    "Calm down, I know what I'm doing!"
          (yelling?)        "Whoa, chill out!"
          (asking?)         "Sure."
          :else             "Whatever.")))
