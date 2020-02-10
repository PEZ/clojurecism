(ns run-length-encoding)

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (->> (partition-by identity plain-text)
       (mapcat (juxt count first))
       (remove #{1})
       (apply str)))

(def alphabet
  (let [upper (range (int \A)
                     (inc (int \Z)))
        lower (range (int \a)
                     (inc (int \z)))]
    (-> #{\space}
        (into (map char upper))
        (into (map char lower)))))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [encoded-text]
  (let [decode-char (fn [[head tail]]
                      (if (alphabet (first head))
                        [1 (str (first tail))]
                        [(Integer/parseInt (apply str head)) (apply str tail)]))]
    ;; "d" is for "dummy".
    ;; Prepending it at the start for `(partition 2 1)` to chew on
    (->> (partition-by alphabet (str "d" encoded-text))
         (partition 2 1)
         (filter #(alphabet (first (second %))))
         (map decode-char)
         (mapcat #(apply repeat %))
         (apply str))))

(comment
  (run-length-decode "12AB3CD4E")
  (run-length-encode "AAAAAAAAAAAABCCCDEEEE")
  (partition-by alphabet "12AB3CD4E")
  ;; => ((\1 \2) (\A) (\B) (\3) (\C) (\D) (\4) (\E))
  (->> (partition-by alphabet "12AB3CD4E")
       (partition 2 1)
       (filter #(alphabet (first (second %)))))
  ;; => (((\1 \2) (\A)) ((\A) (\B)) ((\3) (\C)) ((\C) (\D)) ((\4) (\E)))
)