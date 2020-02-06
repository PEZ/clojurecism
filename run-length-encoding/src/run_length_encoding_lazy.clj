(ns run-length-encoding-lazy)

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (let [run-length (fn [coll]
                     (as-> (count coll) $
                       (if (> $ 1) (str $) "")))]
    (->> (partition-by identity plain-text)
         (map #(str (run-length %) (first %)))
         (apply str))))

(def alphabet
  (let [upper-range (range (int \A)
                           (inc (int \Z)))
        lower-range (range (int \a)
                           (inc (int \z)))]
    (-> #{}
        (into (map char upper-range))
        (into (map char lower-range))
        (conj \space))))

(defn- parse-run-sequence
  "Transform something like `((\\1 \\2) (\\A) (\\B) (\\3) (\\C) (\\D) (\\4) (\\E))`
   into `([12 \"A\"] [1 \"B\"] [3 \"C\"] [1 \"D\"] [4 \"E\"]])`
   The tricky part here is that run-lengths of 1 (like (\\B) above) are not
   prepended with any length. Se here we default to length 1 and use that if
   we parse up something within the alphabet without having parsed a length."
  [run-sequence]
  (loop [remaining run-sequence
         current-length 1
         result []]
    (if (seq remaining)
      (let [token (first remaining)
            first-char (first token)
            token-type (if (alphabet first-char) :alpha :num)
            this-length (if (= token-type :num) (Integer/parseInt (apply str token)) current-length)]
        (if (= token-type :alpha)
          (recur (rest remaining) 1 (conj result [this-length (str first-char)]))
          (recur (rest remaining) this-length result)))
      result)))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [encoded-text]
  (let [decode-char (fn [[head tail]]
                      (if (alphabet (first head))
                          [1 (str (first tail))]
                          [(Integer/parseInt (apply str head)) (apply str tail)]))]
    (->> (partition-by alphabet encoded-text)
         (partition 2 1)
         (filter #(alphabet (first (second %))))
         (map decode-char)
         (mapcat #(apply repeat %))
         (apply str))))

(comment
  (run-length-decode "XYZ")
  ;; => (((\X) (\Y)) ((\Y) (\Z)))

  (run-length-decode "12AB3CD4E")
  (run-length-encode "AAAAAAAAAAAABCCCDEEEE")
  (partition-by alphabet "12AB3CD4E")
  ;; => ((\1 \2) (\A) (\B) (\3) (\C) (\D) (\4) (\E))
  (->> (partition-by alphabet "12AB3CD4E")
       (parse-run-sequence))
  ;; => [[12 "A"] [1 "B"] [3 "C"] [1 "D"] [4 "E"]]
)