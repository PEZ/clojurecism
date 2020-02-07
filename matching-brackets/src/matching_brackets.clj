(ns matching-brackets)

(def openings
  {\( \)
   \[ \]
   \{ \}})

(def closings
  {\) \(
   \] \[
   \} \{})

(def brackets
  (-> #{} 
      (into (keys openings))
      (into (keys closings))))

(def start-book
  {\( 0
   \[ 0
   \{ 0})

(defn- tick [book char]
  (let [opening? (boolean (openings char))
        bracket (if opening? char (closings char))
        bracket-count ((if opening? inc dec) (book bracket))
        new-book (assoc book bracket bracket-count)]
    (if (neg? bracket-count)
      (reduced new-book)
      new-book)))

(defn valid? [s]
  (->>
   (filter brackets s)
   (reduce tick start-book)
   (vals)
   (apply +)
   (= 0)))

(comment
  (valid? "(defn valid? [s]
             (map identity s))")
  (let [s "([(){[])"]
    (->> (filter brackets s)
         (partition-by openings))))