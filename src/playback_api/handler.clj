(ns playback-api.handler
  (:require [compojure.core :refer :all]
            [ring.util.response :as response :refer [content-type]]
            [playback-api.models.position :as position]
            [playback-api.utils :as utils]))

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

(defn- validate-and-convert-uuid [uuid-str]
  (when (and uuid-str (not (empty? uuid-str)))
    (if-let [uuid (utils/string-to-uuid uuid-str)]
      uuid
      (throw (Exception. "Invalid UUID")))))

(defn get-positions [request]
  (try
    (let [{:strs [page per_page user_id title_id media_id finished]} (:query-params request)
          user-id-uuid (validate-and-convert-uuid user_id)
          title-id-uuid (validate-and-convert-uuid title_id)
          media-id-uuid (validate-and-convert-uuid media_id)
          filters (utils/compact {:user_id user-id-uuid
                                  :title_id title-id-uuid
                                  :media_id media-id-uuid
                                  :finished (case finished "true" true "false" false nil nil)})
          page-int (max 0 (or (try (Integer/parseInt page) (catch Exception _ 0)) 0))
          per_page-int (max 1 (min 50 (or (try (Integer/parseInt per_page) (catch Exception _ 10)) 10)))
          result (position/list-positions filters page-int per_page-int)
          positions (:positions result)
          total (:total-count result)
          total-pages (Math/ceil (/ total (float per_page-int)))]
      (-> (response/response positions)
          (assoc :status 200)
          (assoc :headers {"x-page" (str page-int)
                           "x-per-page" (str per_page-int)
                           "x-total" (str total)
                           "x-total-pages" (str (int total-pages))})))
    (catch Exception e
      (-> (response/response (str "Error: " (.getMessage e)))
          (assoc :status 422)))))