(ns matching-brackets)

(def openings
  {\) \(
   \] \[
   \} \{})

(def brackets
  (into #{} (apply concat openings)))

(defn- scan [stack token]
  (let [opening? (not (boolean (openings token)))]
    (if opening?
      (conj stack token)
      (let [balanced? (= (first stack) (openings token))]
        (cond balanced?      (rest stack)
              (empty? stack) (reduced (conj stack token))
              :else          (reduced (conj stack token)))))))

(defn valid? [s]
  (->>
   (filter brackets s)
   (reduce scan ())
   (empty?)))

(comment
  (valid? "()")
  ;; => true
  (valid? "[(]")
  ;; => false
  (valid? "[(])")
  ;; => false
  (valid? "(defn valid? [s]
             (->>
              (filter brackets s)
              (reduce consume-bracket ())
              (empty?)))")
  ;; => true
  (valid? "foo([1, 2, bar(), {baz: gaz}, []])")
  ;; => true
  (valid? "foo([1, 2, bar(), {baz: gaz}, [])")
  ;; => false
  (valid? (slurp "src/matching_brackets.clj"))
  ;; => false
  ;;    (Because naïvité, quoted brackets not handled)
  (valid? (slurp "test/matching_brackets_test.clj"))
  ;; => false
  ;;    (This would be a great test for a real-world Clojure bracket matcher 😀)
  (valid? (slurp "project.clj"))
  ;; => true
  ;;    (phew!)
  )