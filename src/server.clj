(ns server
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn welcome
  "A ring handler to respond with a simple welcome message"
  [request]
  {:status 200
   :body "<h1>Hello, Clojure World</h1>
     <p>Welcome to your first Clojure app, I now update automatically</p>
   <p>I now use defroutes to manage incoming requests</p>"
   :headers {}})

(defroutes app
  (GET "/hello" [] welcome)
  (route/files "/" {:root "./docs"}))

(defn -main
  "A simple web server using Ring & Jetty"
  [port]
  (run-jetty app
             {:port (Integer/parseInt port)}))