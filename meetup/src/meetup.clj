(ns meetup
  (:import java.util.Calendar))

(def ^:private teens #{13 14 15 16 17 18 19})

(def ^:private day-names [:monday :tuesday :wednesday :thursday :friday :saturday :sunday])

(defn- build-month-days [year month]
  (let [calendar (Calendar/getInstance)]
    (->> (range)
         (map (fn [d]
                (.. calendar (set year (dec month) (inc d)))
                (.. calendar toZonedDateTime)))
         (take-while #(= month (.getMonthValue %)))
         (map (fn [zdt]
                [(day-names (dec (.. zdt getDayOfWeek getValue))) 
                 [(.getYear zdt) (.getMonthValue zdt) (.getDayOfMonth zdt)]])))))

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
                 (if (and (not (o->d [:teenth wd])) (some #{dd} teens))
                   (assoc o->d [:teenth wd] d)
                   o->d)))] 
    (reduce step {} month-days)))

(defn meetup [month year day-name ord]
  (let [o->d (->> (build-month-days year month)
                  (build-ordinal->date))]
    (o->d [ord day-name])))

(comment
  (meetup 4 2020 :thursday :teenth)
  ;; => [2020 4 16]
  )