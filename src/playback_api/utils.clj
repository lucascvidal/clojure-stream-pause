(ns playback_api.utils
  (:require [clojure.string :as str]
            [clojure.uuid :as uuid])
  (:import (java.util UUID)))

(defn is_uuid?
  "Checks if the string is a valid UUID."
  [s]
  (try
    (UUID/fromString s)
    true
    (catch IllegalArgumentException e false)))

(defn validate-required-uuid
  "Validates that the map has a UUID for a specific key."
  [position key]
  (if-let [s (position key)]
    (is_uuid? s)
    false))

(defn validate-integer
  "Validates that the map has an integer for a specific key, and it meets the minimum requirement."
  [position key minimum]
  (if-let [n (position key)]
    (and (integer? n) (>= n minimum))
    false))

(defn validate-boolean-or-default
  "Validates that the map has a boolean for a specific key, or defaults to false."
  [position key]
  (let [b (position key false)]
    (boolean? b)))

(defn validate-position
  "Validates a position map against the specified schema."
  [position]
  (and
    (validate-required-uuid position :user_id)
    (validate-required-uuid position :title_id)
    (validate-required-uuid position :media_id)
    (validate-integer position :position 0)
    (validate-boolean-or-default position :finished)))

(defn compact [m]
  (into {} (filter (comp some? val) m)))

(defn string-to-uuid [s]
  (when s
    (try
      (java.util.UUID/fromString s)
      (catch IllegalArgumentException e nil))))

