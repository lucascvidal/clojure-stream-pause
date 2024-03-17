(ns playback-api.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [playback-api.handler :as handler]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]))

(defroutes app-routes
  (POST "/positions" [] handler/post-position)
  (GET "/positions" [] handler/get-positions)
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      wrap-params
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response))
