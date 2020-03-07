(ns isbn-bonus-tasks
  (:require [isbn-verifier :as isbn]
            [clojure.string :as string]))

(def weights-13 (mapcat identity (repeat 7 [1 3])))

(defn isbn-13? [s]
  (let [s (string/replace s #"-", "")]
    (if (re-find #"^\d{13}$" s)
      (= 0 (mod (isbn/isbn-sum s weights-13) 10))
      false)))

(defn isbn-10->isbn-13 [isbn-10]
  (let [isbn-10 (string/replace isbn-10 #"-" "")
        isbn-13 (str "978" (subs isbn-10 0 9))
        isbn-sum (isbn/isbn-sum isbn-13 weights-13)
        check-digit (- 10 (mod isbn-sum 10))
        check-digit (if (= check-digit 10) 0 check-digit)]
    (str isbn-13 check-digit)))

(comment
  (isbn-13? "978-0-306-40615-7")
  ;; => true
  (isbn-10->isbn-13 "3-598-21508-8")
  ;; => "9783598215087"
  (isbn-13? (isbn-10->isbn-13 "3-598-21508-8"))
  ;; => true
  )

(defn generate-isbn-10 []
  (let [random-9 (->> #(rand-int 10)
                      repeatedly
                      (take 9)
                      (apply str))
        check-weights (range 10 1 -1)
        isbn-sum (isbn/isbn-sum random-9 check-weights)
        check-digit (mod (- 11 (mod isbn-sum 11)) 11)]
    (str random-9 check-digit)))

(comment
  (generate-isbn-10)
  ;; => "6391696705"
  (isbn/isbn? "6391696705")
  ;; => true
  )  