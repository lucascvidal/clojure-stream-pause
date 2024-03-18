(ns playback-api.handler-test
  (:require [clojure.test :refer :all]
            [playback-api.handler :refer :all]
            [playback-api.models.position :as position]))

(deftest test-post-position
  (testing "Valid payload"
    (with-redefs [position/insert-position (fn [_] nil)]
      (let [request {:body {:user_id "123e4567-e89b-12d3-a456-426614174000"
                            :title_id "123e4567-e89b-12d3-a456-426614174000"
                            :media_id "123e4567-e89b-12d3-a456-426614174000"
                            :position 10
                            :finished false}}]
        (let [response (post-position request)]
          (is (= 201 (:status response)))))))

  (testing "Invalid payload"
    (with-redefs [position/insert-position (fn [_] nil)]
      (let [request {:body {:user_id "123"
                            :title_id "123e4567-e89b-12d3-a456-426614174000"
                            :media_id "123e4567-e89b-12d3-a456-426614174000"
                            :position 10
                            :finished false}}]
        (let [response (post-position request)]
          (is (= 422 (:status response)))))))

  (testing "Missing body"
    (with-redefs [position/insert-position (fn [_] nil)]
      (let [request {:body {}}]
        (let [response (post-position request)]
          (is (= 400 (:status response))))))))

(deftest test-get-positions
  (testing "Valid UUID for user_id"
    (with-redefs [position/list-positions (fn [_ _ _] {:positions [] :total-count 0})]
      (let [request {:query-params {"user_id" "123e4567-e89b-12d3-a456-426614174000"}}]
        (let [response (get-positions request)]
          (is (= 200 (:status response)))))))

  (testing "Invalid UUID for user_id"
    (with-redefs [position/list-positions (fn [_ _ _] {:positions [] :total-count 0})]
      (let [request {:query-params {"user_id" "invalid-uuid"}}]
        (let [response (get-positions request)]
          (is (= 422 (:status response))))))))