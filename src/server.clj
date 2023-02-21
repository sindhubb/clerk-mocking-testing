(ns server
  (:require [clj.bytebreaks :refer [bytebreak-routes]]
            [compojure.api.sweet :refer [api context routes]]
            [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defroutes app
  (api
   (context "/bytebreaks" []
     :tags []
     :middleware []
     (apply routes bytebreak-routes)))
  (route/files "/" {:root "./docs"}))

(defn -main
  "A simple web server using Ring & Jetty"
  [port]
  (run-jetty app
             {:port (Integer/parseInt port)}))