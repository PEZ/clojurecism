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
  (let [->run-length (fn [[_ length-str character]]
                       [(Integer/parseInt (if (= "" length-str) "1" length-str)) character])]
    (->> (re-seq #"(?i)(\d*)([a-z\s])" encoded-text)
         (map ->run-length)
         (mapcat #(apply repeat %))
         (apply str))))

(comment
  (run-length-encode "AAAAAAAAAAAABCCCDEEEE")
  ;; => "12AB3CD4E"
  (run-length-decode "12AB3CD4E")
  ;; => "AAAAAAAAAAAABCCCDEEEE"
  (run-length-decode "2A,2b-D2E")
  ;; => "AAbbDEE"
  )