(ns hello-clojurescript
  (:require [ajax.core :refer [GET POST]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cognitect.transit :as t]))

(def r (t/reader :json))
(def app-state (atom))

(defn to-json [response-text]
  (t/read r response-text))

(defn get-issues [json]
  (get json "issues"))

(defn issues-handler [response]
  (let [data (to-json (str response))
        issues (get-issues data)]
    (swap! app-state assoc :items issues)))

(GET "/issues.json"
     {:handler issues-handler})

(swap! app-state assoc :text "Do it live!")

(defn story-view [story owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "story"} (get (get story "fields") "summary")))))

(defn stories-view [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
               (dom/h2 nil "Stories")
               (apply dom/div #js {:className "stories"}
                      (om/build-all story-view (:items app)))))))

(om/root stories-view app-state
  {:target (. js/document (getElementById "my-app"))})

