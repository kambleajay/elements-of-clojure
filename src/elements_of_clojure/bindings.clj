(ns elements-of-clojure.bindings)

;;basic setup of functions
(defn library-compute-1
  [x]
  (inc x))

(defn c1 [x] (library-compute-1 x))

(defn b1 [x] (c1 x))

(defn a1 [x] (b1 x))

;;library compute adds new parameter, naive approach to handle it
(defn library-compute-2
  [x turbo-mode?]
  (inc x))

(defn c2 [x turbo-mode?] (library-compute-2 x turbo-mode?))

(defn b2 [x turbo-mode?] (c2 x turbo-mode?))

(defn a2 [x turbo-mode?] (b2 x turbo-mode?))

;;we realize that turbo-mode is mostly true; so we add default values
;;but the problem ...
;;1. we introduced a positional parameter which is mostly not used
;;2. when we add new parameters, we need to switch to option map or start specifying turbo-mode? everywhere

(defn library-compute-3
  [x turbo-mode?]
  (inc x))

(defn c3 [x turbo-mode?] (library-compute-3 x turbo-mode?))

(defn b3 [x turbo-mode?] (c3 x turbo-mode?))

(defn a3
  ([x turbo-mode?] (b3 x turbo-mode?))
  ([x] (a3 x true)))

;;dynamic binding

(def ^:dynamic *turbo-mode* true)

(defmacro slowly [& body]
  `(binding [*turbo-mode* false]
     ~@body))

(defn library-compute-4
  [x turbo-mode?]
  (if turbo-mode?
    (inc x)
    (dec x)))

(defn c4 [x]
  (library-compute-4 x *turbo-mode*))

(defn b4 [x]
  (c4 x))

(defn a4 [x]
  (b4 x))

;;safe binding
(def ^:dynamic *turbo-mode* true)

(defn library-compute-5
  [x turbo-mode?]
  (if turbo-mode?
    (inc x)
    (dec x)))

(defn c5 [x]
  (library-compute-5 x *turbo-mode*))

(defn b5 [x]
  (c5 x))

(defn a5
  ([x] (b5 x))
  ([x turbo-mode?]
   (binding [*turbo-mode* turbo-mode?]
     (b5 x))))
