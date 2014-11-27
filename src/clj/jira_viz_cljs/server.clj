(ns jira-viz-cljs.server
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :as resources]
            [ring.util.response :as response])
  (:gen-class))

(defn render-index []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (slurp "resources/public/index.html")})

(defn handler [request]
   (if (= "/issues.json" (:uri request))
       {:status 200
        :header {"Content-Type" "application/json"}
        :body (slurp "resources/public/jira-mock.json")}
       (render-index)))

(def app
  (-> handler
    (resources/wrap-resource "public")))

(defn -main [& args]
  (jetty/run-jetty app {:port 3001}))

