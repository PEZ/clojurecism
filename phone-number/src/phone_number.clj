(ns phone-number
  (:require [clojure.string :as string]))

(defn number [num-string]
  (-> num-string
      (string/replace #"\D" "")
      (#(if (> (count %) 10)
          (string/replace % #"^1" "")
          %))
      (#(if-not (= (count %) 10)
          "0000000000"
          %))))

(defn area-code [num-string]
  (subs (number num-string) 0 3))

(defn pretty-print [num-string]
  (as-> num-string $
    (number  $)
    (format "(%s) %s-%s"
            (subs $ 0 3)
            (subs $ 3 6)
            (subs $ 6 10))))
