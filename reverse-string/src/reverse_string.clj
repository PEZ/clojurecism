(ns reverse-string
  (:require clojure.string)
  (:refer-clojure :exclude [reverse]))

;; This worked, but is really slow
(defn reverse-slow [coll]
  (loop [remaining coll
         reversed []]
    (if-let [last-char (last remaining)]
      (recur (butlast remaining) (conj reversed last-char))
      reversed)))

;; conj-ing to a list, prepends the item
(defn reverse [coll]
  (reduce conj () coll))

(defn reverse-string [s]
  (clojure.string/join "" (reverse s)))

(comment
  (conj [\0] \1)
  ;; => [\0 \1]
  (reduce conj [] "foo")
  ;; => [\f \o \o]
  (conj '(\0) \1)
  ;; => (\1 \0)
  (reduce conj () "foo")
  ;; => (\o \o \f)
  (reverse-string "foo")
  ;; => "oof"
  (reverse-string "")
  ;; => ""
  )