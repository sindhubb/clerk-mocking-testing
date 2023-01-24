(ns clj.bytebreaks
  (:require [clj-http.client :as client]
            [compojure.api.sweet :refer [GET]]
            [schema.core :as s]
            [ring.util.http-response :as res]))

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
                   :key "72dd852a-c2a9-4cfd-86e6-2830514b3ba4"})



(defn get-holidays []
  (res/ok (->> (client/get base-url {:as
                                     :json
                                     :query-params query-params})
               (#(get-in % [:body :holidays])))))

(def bytebreak-routes
  [(GET "/holidays" request
   :summary "Get all holdays"
   :return HolidaySchema
   (get-holidays))])