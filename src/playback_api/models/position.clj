(ns playback_api.models.position
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [playback_api.db :refer [db-spec]]))

(defn insert-position
  "Persists a playback position in the database."
  [{:keys [user_id title_id media_id position finished]}]
  (sql/insert! db-spec :positions
                {:user_id (java.util.UUID/fromString user_id), :title_id (java.util.UUID/fromString title_id), :media_id (java.util.UUID/fromString media_id), :position position, :finished (if finished finished false)}))

(defn list-positions
  "Returns a list of positions in descending order of creation, with support for pagination and filters."
  ([page per_page filters]
   (let [offset (* page per_page)
         base-query {:select [:*]
                     :from [:positions]}
         where-clause (when (not (empty? filters))
                        {:where (reduce-kv (fn [acc k v] (conj acc [:= k (if (= k :finished) (Boolean/parseBoolean v) v)])) [] filters)})
         order-by-clause {:order-by [[:creation_time :desc]]}
         limit-clause {:offset offset, :limit per_page}
         final-query (merge base-query where-clause order-by-clause limit-clause)]
     (sql/query db-spec final-query))))
