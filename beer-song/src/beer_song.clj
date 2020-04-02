(ns beer-song
  (:require [clojure.pprint :refer [cl-format]]))

(defn bottles
  [num]
  (cl-format nil "~[no more bottles~;1 bottle~:;~:*~D bottles~] of beer" num))

(defn fetch
  [num]
  (cl-format nil "~[go to the store and buy some more~:;~:*take ~[0~;it~:;one~] down and pass it around~]" num))

(defn verse
  "The `num` verse of the song."
  [num]
  (cl-format nil
             "~@(~a~)~:* on the wall, ~a.\n~@(~a~), ~a on the wall.\n"
             (bottles num)
             (fetch num)
             (bottles (if (zero? num) 
                        99 
                        (dec num)))))

(comment
  (verse 2)
  ;; => "2 bottles of beer on the wall, 2 bottles of beer.\nTake one down and pass it around, 1 bottle of beer on the wall.\n"
  (verse 1)
  ;; => "1 bottle of beer on the wall, 1 bottle of beer.\nTake it down and pass it around, no more bottles of beer on the wall.\n"
  (verse 0)
  ;; => "No more bottles of beer on the wall, no more bottles of beer.\nGo to the store and buy some more, 99 bottles of beer on the wall.\n"
  )

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
