(ns server
  (:require [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [compojure.api.sweet :refer [api context GET routes]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clj.bytebreaks :refer [bytebreak-routes]])
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