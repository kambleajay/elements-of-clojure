(ns elements-of-clojure.bindings-test
  (:require [clojure.test :refer :all]
            [elements-of-clojure.bindings :refer :all]))

(deftest test-basic-use-of-a
  (is (= (a1 1) 2)))

(deftest test-naive-way-for-additional-param
  (is (= (a2 1 true) 2)))

(deftest test-additional-param-with-default-value
  (is (= (a3 1) 2)))

(deftest test-dynamic-binding
  (is (= (a4 1) 2)))

(deftest test-slowly-single-chunk-seq-case
  (let [a identity
        values (take 32 (range))
        result (slowly
                (let [values' (map a values)]
                  (if (empty? values')
                    (throw (IllegalArgumentException. "empty input"))
                    values')))]
    (is (= result (range 32)))))

;;the first chunk, that is first 32 elements get evaluated inside macro where turbo-mode is false,
;;thus first 32 elements get decremented, but rest elements are not realized, they are evaluated outside
;;macro where turbo-mode is true, thus rest all elements are incremented
(deftest test-slowly-with-first-chunk-realized
  (let [values (lazy-seq (range 40))
        result (slowly
                (let [values' (map a4 values)]
                  (if (empty? values')
                    (throw (IllegalArgumentException. "empty input"))
                    values')))]
    (is (= result [-1 0 1 2 3 4 5 6 7 8
                   9 10 11 12 13 14 15 16 17 18
                   19 20 21 22 23 24 25 26 27 28
                   29 30 33 34 35 36 37 38 39 40]))))

;;the whole seq is returned lazily from macro, so when evaluated outside turbo-mode is bound to true
(deftest test-slowly-with-whole-seq-lazy
  (let [values (lazy-seq (range 40))
        result (slowly
                (if (empty? (map a4 values))
                  (throw (IllegalArgumentException. "empty input"))
                  (map a4 values)))]
    (is (= result [1 2 3 4 5 6 7 8 9 10
                   11 12 13 14 15 16 17 18 19 20
                   21 22 23 24 25 26 27 28 29 30
                   31 32 33 34 35 36 37 38 39 40]))))

(deftest test-safe-binding-with-turbo-mode-enabled-as-default
  (is (= (a5 0) 1)))

(deftest test-safe-binding-with-turbo-mode-explicitly-enabled
  (is (= (a5 0 true) 1)))

(deftest test-safe-binding-with-turbo-mode-disabled
  (is (= (a5 0 false) -1)))
