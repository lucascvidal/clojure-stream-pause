(ns playback_api.handler
  (:require [compojure.core :refer :all]
            [ring.util.response :as response :refer [content-type]]
            [cheshire.core :as json]
            [playback_api.models.position :as position]
            [playback_api.utils :as utils]))

(defn post-position [request]
  (let [body (:body request)]
    (cond
      (or (nil? body) (empty? body)) 
      (-> (response/response "Invalid request payload")
          (assoc :status 400))

      (not (utils/validate-position body)) 
      (-> (response/response "Payload validation failed")
          (assoc :status 422))
      
      :else 
      (do
        (position/insert-position body)
        (-> (response/response "Successful operation.")
          (assoc :status 201))))))

(defn get-positions [request]
  (let [{:strs [page per_page user_id title_id media_id finished]} (:query-params request)
        filters (utils/compact {:user_id (utils/string-to-uuid user_id)
                                :title_id (utils/string-to-uuid title_id)
                                :media_id (utils/string-to-uuid media_id)
                                :finished (case finished
                                            "true" true
                                            "false" false
                                            nil nil)})
        page-int (max 0 (or (try (Integer/parseInt page) (catch Exception _ 0)) 0))
        per_page-int (max 5 (min 50 (or (try (Integer/parseInt per_page) (catch Exception _ 10)) 10)))
        positions (position/list-positions filters) ; page-int per_page-int)
        total (count positions)
        total-pages (Math/ceil (/ total (float per_page-int)))]
    (-> (response/response positions)
        (assoc :status 200)
        (assoc :headers {"x-page" (str page-int)
                         "x-per-page" (str per_page-int)
                         "x-total" (str total)
                         "x-total-pages" (str (int total-pages))}))))