(ns beer-song
  (:require clojure.string))

;; 99 bottles of beer on the wall, 99 bottles of beer.
;; Take one down and pass it around, 98 bottles of beer on the wall.

(defn bottles
  [num]
  (str (cond (> num 1)
             (str num " bottles")
             (= num 1)
             "1 bottle"
             (= num 0)
             "No more bottles")
       " of beer"))

(defn fetch
  [num]
  (if (= 0 num)
    "Go to the store and buy some more"
    (str "Take "
         (if (> num 1)
           "one"
           "it")
         " down and pass it around")))

(defn verse
  "Returns the nth verse of the song."
  [num]
  (str (bottles num)
       " on the wall, " 
       (clojure.string/lower-case (bottles num))
       ".\n"
       (fetch num)
       ", "
       (clojure.string/lower-case (bottles (if (= num 0) 99 (dec num) )))
       " on the wall.\n"))

(comment 
  (verse 2)
  (verse 1)
  (verse 0))

(defn sing
  "Given a start and an optional end, returns all verses in this interval. If
  end is not given, the whole song from start is sung."
  ([start]
   (sing start 0))
  ([start end]
   (->> (range start (dec end) -1)
        (map verse)
        (interpose "\n")
        (apply str))))

(comment
  (println (sing 3)))
