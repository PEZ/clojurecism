(ns nucleotide-count)

(def ^:private zero-nucleotides {\A 0 \C 0 \G 0 \T 0})

(def ^:private allowed-nucleotides (set (keys zero-nucleotides)))

(defn nucleotide-counts [strand]
  (merge zero-nucleotides (frequencies strand)))

(defn count-of-nucleotide-in-strand [nucleotide strand]
  (if-not (some #{nucleotide} allowed-nucleotides)
    (throw (IllegalArgumentException. (str "Nucleotide is garbage: " nucleotide)))
    ((nucleotide-counts strand) nucleotide)))
