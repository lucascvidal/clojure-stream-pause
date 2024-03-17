(ns playback_api.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [playback_api.handler :as handler]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defroutes app-routes
  (POST "/positions" [] handler/post-position)
  (GET "/positions" [] handler/get-positions)
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response))
