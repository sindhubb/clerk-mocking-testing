;; ## Welcome to Bytebreaks!
;; Every salaried employee in India wonders when they can take a break from work but without spending too many of their leaves. And, India has a lot of holidays!

;; How about we try to find a window of holidays that will allow you to combine weekends, holidays and as little of your leaves as possible to give you the longest possible break window.

;; We start by querying holidayapi.com for a list of holidays

(ns clj.bytebreaks
  (:require [clj-http.client :as client]
            [compojure.api.sweet :refer [GET]]
            [schema.core :as s]
            [ring.util.http-response :as res]))

;; A good practise when writing REST APIs is to define a schema for the response
;; This helps us to define the response format and also helps us to validate the response
;; This is especially useful when we are writing tests for our API
;; We use `plumatic/schema` library to define the schema
(s/defschema HolidaySchema
  [{:name s/Str
    :date s/Str
    :observed s/Str
    :public s/Bool
    :country s/Str
    :uuid s/Str
    :weekday {:date {:name s/Str
                     :numeric s/Str}
              :observed {:name s/Str
                         :numeric s/Str}}}])

(def base-url "https://holidayapi.com/v1/holidays?pretty")

(def query-params {:country "IN"
                   :year "2022"
                   :month 1
                   :key "72dd852a-c2a9-4cfd-86e6-2830514b3ba4"})

(defn get-holidays [request]
  (res/ok (->> (client/get base-url {:as
                                     :json
                                     :query-params query-params})
               (#(get-in % [:body :holidays])))))

;; We define the routes for our API using `compojure.api.sweet`
;; We define the routes in a separate file and then import them in our server file
(def bytebreak-routes
  [(GET "/holidays" []
     :summary "Get all holdays"
     :return HolidaySchema
     get-holidays)])