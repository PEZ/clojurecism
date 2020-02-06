(ns rna-transcription)

(defn to-rna [dna]
  (let [dna-strand->rna-strand 
        (fn [strand]
          (case strand
            \G "C"
            \C "G"
            \T "A"
            \A "U"
            (throw (AssertionError. "Illegal DNA strand"))))]
    (->> dna
         (map dna-strand->rna-strand)
         (apply str))))

(comment
  (to-rna "GATACA")
  ;; => "CUAUGU"
  (to-rna "GAP")
  ;; => Execution error (AssertionError) at rna-transcription/to-rna$dna-strand->rna-strand (rna_transcription.clj:6).
  ;;    Illegal DNA strand
)