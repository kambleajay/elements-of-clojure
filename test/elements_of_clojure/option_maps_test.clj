(ns elements-of-clojure.option-maps-test
  (:require [clojure.test :refer :all]
            [elements-of-clojure.option-maps :refer :all]))

(deftest test-basic-use-of-pi
  (is (= (pi 3) 1)))

(deftest test-pi-with-efficiently
  (is (= (pi 3 true)) 2))

(deftest test-pi-with-correctly
  ;;need to pass efficiently as well
  (is (= (pi 3 true true)) 0))

(deftest test-pi-incorrectly
  ;;the problem is that you need to specify all optional parameters
  (is (= (pi 3 true false)) 0))

(deftest test-pi2-incorrectly
  ;;this version is better because we can specify parameter that we care for by name, rather than by position and require to pass all positional parameters that come before
  (is (= (pi2 3 :correctly? false)) 0))

(deftest test-pi3-incorrectly
  (is (= (pi3 3 {:correctly? false})) 0))
