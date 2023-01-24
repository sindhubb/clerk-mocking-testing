(ns clj.bytebreaks
  (:require [clj-http.client :as client]
            [compojure.api.sweet :refer [GET]]
            [schema.core :as s]
            [ring.util.http-response :as res]))

(s/defschema HolidaySchema
  {:holidays s/Any})

(def base-url "https://holidayapi.com/v1/holidays?pretty")

(def query-params {:country "IN"
                   :year "2022"
                   :key "72dd852a-c2a9-4cfd-86e6-2830514b3ba4"})

(defn get-holidays []
  (->> (client/get base-url {:as
                             :json
                             :query-params query-params})
       (#(get-in % [:body :holidays])
        (str))))

(def bytebreak-routes
  [(GET "/holidays" request
   :summary "Get all holdays"
   :return [HolidaySchema]
   (get-holidays))])