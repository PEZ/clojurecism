(ns phone-number
  (:require [clojure.string :as string]))

(defn number [num-string]
  (let [remove-non-digits #(string/replace % #"\D" "")
        remove-country-code #(if (> (count %) 10)
                               (string/replace % #"^1" "")
                               %)
        reject-invalid-numbers #(if-not (= (count %) 10)
                                  "0000000000"
                                  %)]
    (-> num-string
        (remove-non-digits)
        (remove-country-code)
        (reject-invalid-numbers))))

(defn area-code [num-string]
  (subs (number num-string) 0 3))

(defn pretty-print [num-string]
  (as-> num-string $
    (number  $)
    (format "(%s) %s-%s"
            (subs $ 0 3)
            (subs $ 3 6)
            (subs $ 6 10))))
