(ns clock)

(defn clock->string [{:keys [h m]}]
  (format "%02d:%02d" h m))

(defn clock [h m]
  (let [minutes (if (< m 0) (- m 60) m)
        extra-hours (quot minutes 60)
        hours (+ h extra-hours)]
    {:h (mod hours 24)
     :m (mod minutes 60)}))

(defn add-time [{:keys [h m]} minutes]
  (clock h (+ m minutes)))
