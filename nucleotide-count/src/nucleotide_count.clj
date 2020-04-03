(ns nucleotide-count)

(def ^:private starting-counts {\A 0 \C 0 \G 0 \T 0})

(defn nucleotide-counts [strand]
  (merge starting-counts (frequencies strand)))

(defn count-of-nucleotide-in-strand [nucleotide strand]
  {:pre [(get starting-counts nucleotide)]}
  (get (nucleotide-counts strand) nucleotide))
