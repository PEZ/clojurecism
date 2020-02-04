(ns clock)

(defn clock-parts [t]
  {:h (mod (quot t 60) 24)
   :m (mod t 60)})

(defn clock->string [t]
  (let [{:keys [h m]} (clock-parts t)]
    (format "%02d:%02d" h m)))

(defn clock [h m]
  (let [m (if (< m 0) (- m 60) m)
        h (+ h (quot m 60))
        h (mod h 24)
        m (mod m 60)]
    (+ (* h 60) m)))

(defn add-time [t add-m]
  (let [{:keys [h m]} (clock-parts t)]
    (clock h (+ m add-m))))
