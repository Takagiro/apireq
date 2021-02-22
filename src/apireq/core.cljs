(ns apireq.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [ajax.core :refer [GET]]))

(def state (r/atom {:users
                    {:data false
                     :url "https://jsonplaceholder.typicode.com/users"}}))

(defn update! [key data]
  (swap! state update-in [(keyword key)] assoc :data data))

;; (GET url) >> #object[Object [object Object]]
;; (js->clj data) >> #object[Object [object Object]]
(defn fetch [module]
  (let [url (get-in @state [:users :url])
        data (GET url)]
    (update! module (js->clj data))))

(defn home-page []
  [fetch "users"])

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
