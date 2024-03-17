(ns playback-api.models.position
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [playback-api.db :refer [db-spec]]
            [playback-api.utils :refer [string-to-uuid]]))

(defn insert-position
  "Persists a playback position in the database."
  [{:keys [user_id title_id media_id position finished]}]
  (sql/insert! db-spec :positions
                {:user_id (string-to-uuid user_id), :title_id (string-to-uuid title_id), :media_id (string-to-uuid media_id), :position position, :finished (if finished finished false), :created_at (java.sql.Timestamp. (System/currentTimeMillis))}))

(defn count-positions [filters]
  (if (empty? filters)
    (let [query "SELECT COUNT(*) AS count FROM positions"
          result (sql/query db-spec [query])]
      (-> result first (get :count)))
    (let [result (sql/aggregate-by-keys db-spec :positions "count(*)" filters)]
      (-> result))))

(defn list-positions [filters page per_page]
  (let [query-options {:order-by [[:id :desc]]
                       :offset (* page per_page)
                       :limit per_page}
        positions (if (empty? filters)
                    (sql/find-by-keys db-spec :positions :all query-options)
                    (sql/find-by-keys db-spec :positions filters query-options))
        total-count (count-positions filters)]
    {:positions positions
     :total-count total-count}))
