(ns hello-clojurescript
  (:require [ajax.core :refer [GET POST]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(def app-state (atom))

(GET "/issues.json"
     {:handler #(swap! app-state assoc :text (str %))})

(swap! app-state assoc :text "Do it live!")

(om/root
  (fn [app owner]
    (reify om/IRender
      (render [_]
        (dom/h1 nil (:text app)))))
  app-state
  {:target (. js/document (getElementById "my-app"))})

; (defn handle-click []
;   (GET "http://localhost:3000/issues.json")
;   (js/alert "Hello!"))
;
; (def clickable (.getElementById js/document "clickable"))
; (.addEventListener clickable "click" handle-click)
