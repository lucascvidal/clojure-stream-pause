(ns playback-api.db
  (:require [environ.core :refer [env]]
            [next.jdbc.sql :as sql]
            [next.jdbc :as jdbc]))

(def db-spec
  {:dbtype "postgresql"
   :dbname (env :db-name "playbackdb")
   :host (env :db-host "db")
   :port 5432
   :user (env :db-user "user")
   :password (env :db-password "password")})

(defn create-positions-table []
  (jdbc/execute! db-spec ["CREATE TABLE IF NOT EXISTS positions (
                      id SERIAL PRIMARY KEY,
                      user_id UUID NOT NULL,
                      title_id UUID NOT NULL,
                      media_id UUID NOT NULL,
                      position INTEGER NOT NULL,
                      finished BOOLEAN NOT NULL,
                      created_at timestamp
                      )"]))
