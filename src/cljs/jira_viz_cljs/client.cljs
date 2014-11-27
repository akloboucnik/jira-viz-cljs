(ns hello-clojurescript
  (:require [ajax.core :refer [GET POST]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cognitect.transit :as t]))

(def r (t/reader :json))

(defn to-json [response-text]
  (t/read r response-text))

(defn get-issues [json]
  (get json "issues"))

(def app-state (atom))

(defn issues-handler [response]
  (let [data (to-json (str response))
        issues (get-issues data)
        first-issue-tile (get (get (first issues) "fields") "summary")]
    (swap! app-state assoc :text first-issue-tile)))

(GET "/issues.json"
     {:handler issues-handler})

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
