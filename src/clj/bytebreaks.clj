;; ## Welcome to Bytebreaks!

;; We start by querying holidayapi.com for a list of holidays

(ns clj.bytebreaks
  (:require [clj-http.client :as http]
            [clj-time.core :as t]
            [clj-time.local :as l]
            [compojure.api.sweet :refer [GET]]
            [ring.util.http-response :as res]
            [schema.core :as s]))

{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(def api-key 
  "api key for holidayapi"
  "72dd852a-c2a9-4cfd-86e6-2830514b3ba4")

{:nextjournal.clerk/visibility {:code :hide :result :show}}

;; A good practise when writing REST APIs is to define a schema for the response
;; This helps us to define the response format and also helps us to validate the response
;; This is especially useful when we are writing tests for our API
;; We use `plumatic/schema` library to define the schema

(s/defschema HolidaySchema
  "schema for holidayapi response"
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

(def base-url
  "holidayapi url"
  "https://holidayapi.com/v1/holidays")

(def query-params 
  "query params for holidayapi" 
  {:country "IN"
   :year (dec (t/year (l/local-now)))
   :key api-key})

(defn get-holidays
  "get holidays from holidayapi" 
  [options]
  (let [resp (->> (merge {:as :json
                          :proxy-host "127.0.0.1"
                          :proxy-port 8080
                          :proxy-ignore-hosts [*]
                          :query-params query-params} options)
                  (http/get base-url))]
    (if (= 200 (:status resp))
      (res/ok (get-in resp [:body :holidays]))
      (res/bad-request (str "Error: " (:status resp) " " (:body resp))))))

;; We define the routes for our API using `compojure.api.sweet`
;; We define the routes in a separate file and then import them in our server file
(def bytebreak-routes
  "Routes for bytebreaks"
  [(GET "/holidays" []
     :summary "Get all holdays"
     :return HolidaySchema
     (get-holidays {}))
   
   (GET "/upcoming-holidays" []
     :summary "Get upcoming holdays"
     :return HolidaySchema
     (get-holidays {:upcoming true
                    :public true
                    :pretty true
                    :day (t/day (l/local-now))}))])

;; Every salaried employee in India wonders when they can take a break from work but without spending too many of their leaves. And, India has a lot of holidays! How about we try to find a window of holidays that will allow you to combine weekends, holidays and as little of your leaves as possible to give you the longest possible break window.

