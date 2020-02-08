(ns matching-brackets)

(def openings
  {\) \(
   \] \[
   \} \{})

(def brackets
  (into #{} (apply concat openings)))

(defn- process-token [stack token]
  (let [opening? (not (boolean (openings token)))]
    (if opening?
      (conj stack token)
      (if (empty? stack)
        (reduced (conj stack token))
        (let [residue (take-while (complement #{(openings token)}) stack)
              residue (reduce process-token () residue)
              before (-> (drop-while #{(openings token)} stack)
                         (rest))]
          (apply concat residue before))))))

(defn valid? [s]
  (->>
   (filter brackets s)
   (reduce process-token ())
   #_(empty?)))

(comment
  (valid? "[({})")
  (valid? "(defn valid? [s]
             (map identity s))")
  (valid? "([(){[])")
  (valid? "([()[])]")
  (drop-while (complement #{\)}) '(\( \[ \] \( \) \)))
  (let [s "([(){[])"]
    (->> (filter brackets s)
         )))