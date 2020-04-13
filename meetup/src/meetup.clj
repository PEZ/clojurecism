(ns meetup
  (:import [java.time LocalDate]))

(def ^:private weekdays [:monday :tuesday :wednesday :thursday :friday :saturday :sunday])

(defn- build-month-days
  "Builds a vector of tuples, [weekday date], for all days in `month` of `year`."
  [year month]
  (->> (range 31)
       (map #(.. (LocalDate/of year month 1) (plusDays %)))
       (take-while #(= month (.getMonthValue %)))
       (map #(vector (weekdays (dec (.. % getDayOfWeek getValue)))
                     [(.getYear %) (.getMonthValue %) (.getDayOfMonth %)]))))

(defn build-ordinal->date
  "Builds a map of { [ordinal weekday] -> date } from a sequence of [weekday date] tuples.
   A particular date can appear several times in the map.
   E.g. `:fourth` and `:last`, or `:second` and `:teenth."
  [month-days]
  (let [step (fn [o->d [weekday [_dy _dm dd :as d]]]
               (let [o (cond
                         (o->d [:fourth weekday]) :fifth
                         (o->d [:third weekday])  :fourth
                         (o->d [:second weekday]) :third
                         (o->d [:first weekday])   :second
                         :else                    :first)
                     o->d (assoc o->d
                                 [o weekday] d
                                 [:last weekday] d)]
                 (if (< 12 dd 20)
                   (assoc o->d [:teenth weekday] d)
                   o->d)))]
    (reduce step {} month-days)))

(defn meetup [month year weekday ordinal]
  (let [o->d (->> (build-month-days year month)
                  (build-ordinal->date))]
    (o->d [ordinal weekday])))

(comment
  (meetup 4 2020 :thursday :teenth)
  ;; => [2020 4 16]
  )