(ns hello-clojurescript
  (:require [ajax.core :refer [GET POST]]))

(defn handle-click []
  (GET "http://localhost:3000/issues.json")
  (js/alert "Hello!"))

(def clickable (.getElementById js/document "clickable"))
(.addEventListener clickable "click" handle-click)
