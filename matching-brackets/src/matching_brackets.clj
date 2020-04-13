(ns matching-brackets)

(def close->open
  {\) \(
   \] \[
   \} \{})

(def brackets
  (into #{} (apply concat close->open)))

(defn- scan [stack bracket]
  (let [opener (close->open bracket)]
    (cond (nil? opener)            (conj stack bracket)
          (= (peek stack) opener)  (pop stack)
          :else                    (reduced (conj stack bracket)))))

(defn valid? [s]
  (->> s
   (filter brackets)
   (reduce scan [])
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