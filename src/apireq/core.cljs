(ns apireq.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [ajax.core :refer [GET]]))

(def state (r/atom {:users
                    {:data false
                     :url "https://jsonplaceholder.typicode.com/users"}}))

;; https://pastebin.com/KpFSqE0A
(defn update! [key data]
  (swap! state update-in [(keyword key)] assoc :data data))

(defn fetch-handler [response]
  (update! "users" (js->clj response)))

(defn fetch []
  (let [url (get-in @state [:users :url])]
    (GET url {:handler fetch-handler})))

(defn home-page []
  [fetch "users"])

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
