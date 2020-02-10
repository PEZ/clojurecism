(ns rle-timing
  (:require [run-length-encoding :as rs]
            [rle-i2 :as i2]
            [criterium.core :refer [quick-bench]]))

(comment
  (quick-bench (rs/run-length-decode "12WB12W3B24WB"))
  (quick-bench (i2/run-length-decode "12WB12W3B24WB"))
  (def long-s (apply str (repeat 50 "12WB12W3B24WB")))
  (quick-bench (rs/run-length-decode long-s))
  (quick-bench (i2/run-length-decode long-s)))