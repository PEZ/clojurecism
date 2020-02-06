(ns rna-transcription)

(def dna-strand->rna-strand
  {"G" "C"
   "C" "G"
   "T" "A"
   "A" "U"})

(defn to-rna [dna]
  {:pre (boolean (re-find #"^[GCTA]+$" dna))}
  (->> dna
       (map (comp dna-strand->rna-strand str))
       (apply str)))

(comment
  (to-rna "GATACA")
  ;; => "CUAUGU"
  (to-rna "GAP")
  ;; => Execution error (AssertionError) at rna-transcription/to-rna (rna_transcription.clj:9).
  ;;    Assert failed: (re-find #"^[GCTA]+$" dna)
)