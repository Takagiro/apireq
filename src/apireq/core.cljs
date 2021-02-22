(ns apireq.core
  (:require
   [reagent.core :as r :refer [atom]]
   [reagent.dom :as d]
   [ajax.core :refer [GET]]))

(defn fetch-users! [data]
  (GET "https://jsonplaceholder.typicode.com/users"
    {:handler #(reset! data %) :response-format :json :keywords? true}))

(defn home-page []
  (let [data (atom nil)]
    (fetch-users! data)
    (fn []
      [:ul {:class "list-group"}
       (for [user @data]
         ^{:key (:id user)}
         [:li {:class "list-group-item"} (:name user)])
       [:button.btn.btn-primary
        {:on-click #(fetch-users! data)}
        "Get users..."]
       [:button.btn.btn-danger
        {:on-click #(reset! data nil)}
        "Clear users..."]])))

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
