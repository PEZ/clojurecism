(ns speak-number
  (:require 
   [say :refer [number]]
   [speak :refer [speak]]))

(defn speak-the-number [n]
  (-> n
      number
      speak))

(comment
  (speak-the-number 987654321123)
  )