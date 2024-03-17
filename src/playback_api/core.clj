(ns playback_api.core
  (:require [ring.adapter.jetty :as jetty]
            [playback_api.routes :refer [app]]
            [playback_api.db :refer [create-positions-table]]))

(defn -main [& args]
  (create-positions-table)
  (jetty/run-jetty app {:port 8080 :join? false}))
