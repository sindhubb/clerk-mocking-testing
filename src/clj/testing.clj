;; ## Testing Bytebreaks micro app!
;; We are going to use clojure.test to write tests for our API
;; We will use ring.mock.request to mock the request and response
(ns clj.testing
  (:require [clj.bytebreaks :as bytebreaks]
            [clojure.test :refer [deftest is run-test run-tests
                                  testing]]
            [ring.mock.request :as mock]))

;; Testing the holidays endpoint
(deftest holidays-test
  (testing "Testing the holidays endpoint"
    (is (= (bytebreaks/get-holidays (mock/request :get "/bytebreaks/holidays"))
           {:status  200
            :headers {}
            :body    [{:name "Republic Day",
                       :date "2022-01-26",
                       :observed "2022-01-26",
                       :public true,
                       :country "IN",
                       :uuid "2e10b51e-08a0-4595-a234-f2cf0a1d0863",
                       :weekday {:date {:name "Wednesday",
                                        :numeric "3"},
                                 :observed {:name "Wednesday",
                                            :numeric "3"}}}]}))))
(require '[nextjournal.clerk :as clerk])

^::clerk/no-cache (run-tests 'clj.testing)
((run-test holidays-test) :summary)