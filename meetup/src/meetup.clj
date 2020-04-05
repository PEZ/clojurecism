(ns meetup
  (:import [java.time LocalDate]))

(def ^:private day-names [:monday :tuesday :wednesday :thursday :friday :saturday :sunday])

(defn- build-month-days [year month]
  (->> (range)
       (map (fn [d]
              (.. (LocalDate/of year month 1) (plusDays d))))
       (take-while #(= month (.getMonthValue %)))
       (map (fn [zdt]
              [(day-names (dec (.. zdt getDayOfWeek getValue)))
               [(.getYear zdt) (.getMonthValue zdt) (.getDayOfMonth zdt)]]))))

(defn build-ordinal->date [month-days]
  (let [step (fn [o->d [wd [_dy _dm dd :as d]]]
               (let [o (cond
                         (o->d [:fourth wd]) :fifth
                         (o->d [:third wd])  :fourth
                         (o->d [:second wd]) :third
                         (o->d [:first wd])   :second
                         :else               :first)
                     o->d (assoc o->d
                                 [o wd] d
                                 [:last wd] d)]
                 (if (< 12 dd 20)
                   (assoc o->d [:teenth wd] d)
                   o->d)))] 
    (reduce step {} month-days)))

(defn meetup [month year day-name ordinal]
  (let [o->d (->> (build-month-days year month)
                  (build-ordinal->date))]
    (o->d [ordinal day-name])))

(comment
  (meetup 4 2020 :thursday :teenth)
  ;; => [2020 4 16]
  )