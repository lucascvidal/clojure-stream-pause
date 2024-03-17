(ns playback_api.models.position
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [playback_api.db :refer [db-spec]]
            [playback_api.utils :refer [string-to-uuid]]))

(defn insert-position
  "Persists a playback position in the database."
  [{:keys [user_id title_id media_id position finished]}]
  (sql/insert! db-spec :positions
                {:user_id (string-to-uuid user_id), :title_id (string-to-uuid title_id), :media_id (string-to-uuid media_id), :position position, :finished (if finished finished false), :created_at (java.sql.Timestamp. (System/currentTimeMillis))}))

(defn list-positions [filters]
  (if (empty? filters)
    (jdbc/execute! db-spec ["SELECT * FROM positions"])
    (sql/find-by-keys db-spec :positions filters)))
