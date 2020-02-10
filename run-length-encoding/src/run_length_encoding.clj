(ns run-length-encoding)

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (->> (partition-by identity plain-text)
       (mapcat (juxt count first))
       (remove #{1})
       (apply str)))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [encoded-text]
  (let [length-str->length (fn [s]
                             (-> (if (= "" s) "1" s) 
                                 (Integer/parseInt)))]
    (->> (re-seq #"(?i)(\d*)([a-z\s])" encoded-text)
         (map (juxt #(length-str->length (second %)) last))
         (mapcat #(apply repeat %))
         (apply str))))

(comment
  (run-length-decode "12AB3CD4E")
  ;; => "AAAAAAAAAAAABCCCDEEEE"
  (run-length-encode "AAAAAAAAAAAABCCCDEE§EE")
  ;; => "12AB3CD2E§2E"
)