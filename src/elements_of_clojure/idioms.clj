(ns elements-of-clojure.idioms)

(defn pi-to-n-digits
  ([n] 0)
  ([n efficiently?] 1)
  ([n efficiently? correctly?] 2))

(defn pi-to-n-digits2
  ([n & {:keys [efficiently? correctly?]
         :or {efficiently? true correctly? true}}]
   0))

;;positional parameters, using multiple arities to support optional parameters
(defn pi
  ([n] (pi-to-n-digits n true))
  ([n efficiently?] (pi-to-n-digits n efficiently? true))
  ([n efficiently? correctly?]
   (cond
     (not correctly?) 3.0
     (not efficiently?) (->> (repeatedly #(pi n))
                             (take 100)
                             last)
     :else (pi-to-n-digits n))))


;;names parameters
(defn pi2
  ([n & {:keys [efficiently? correctly?]
         :or {efficiently? true correctly? true}
         :as options}]
   (println "Options: " options ", type: " (type options))
   (cond
     (not correctly?) 3.0
     (not efficiently?) (->> (repeatedly #(pi n))
                             (take 100)
                             last)
     :else (->> options
                (apply concat)
                (apply pi-to-n-digits2 n)))))

;;option maps
(defn pi3
  ([n {:keys [efficiently? correctly?]
       :or {efficiently? true correctly? true}
       :as options}]
   (println "Options: " options ", type: " (type options))
   (cond
     (not correctly?) 3.0
     (not efficiently?) (->> (repeatedly #(pi n))
                             (take 100)
                             last)
     :else (pi-to-n-digits2 n options))))
