(ns two-fer
  (:refer-clojure :exclude [name]))

(defn two-fer
  ([]
   (two-fer nil))
  ([name]
   (str "One for " (or name "you") ", one for me.")))

(comment
  (two-fer "Bob")
  ;; => "One for Bob, one for me."
  (two-fer)
  ;; => "One for you, one for me."
  (two-fer nil)
  ;; => "One for you, one for me."
  (two-fer "")
  ;; => "One for , one for me."
)
