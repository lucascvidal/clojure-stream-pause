(ns playback-api.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [playback-api.handler :as handler]
            [cheshire.core :as json]
            [playback-api.models.position :as position]))

(deftest test-post-position
  (let [body-map {:user_id "123e4567-e89b-12d3-a456-426614174000"
                  :title_id "123e4567-e89b-12d3-a456-426614174000"
                  :media_id "123e4567-e89b-12d3-a456-426614174000"
                  :position 10
                  :finished true}
        body-json (json/encode body-map)
        request (mock/request :post "/position" body-json)]
    (testing "Successful operation."
      (let [response (handler/post-position (assoc request :body (json/parse-string body-json true)))]
        (is (= 201 (:status response)))))))


(deftest test-post-position-with-invalid-uuid
  (let [body-map {:user_id "123e4567-e89b-12d3-a456-426614174000"
                  :title_id "123"
                  :media_id "123e4567-e89b-12d3-a456-426614174000"
                  :position 10
                  :finished true}
        body-json (json/encode body-map)
        request (mock/request :post "/position" body-json)]
    (testing "Payload validation failed"
      (let [response (handler/post-position (assoc request :body (json/parse-string body-json true)))]
        (is (= 422 (:status response)))))))

(deftest test-post-position-with-empty-body
  (let [body-map clojure.lang.PersistentHashMap/EMPTY
        body-json (json/encode body-map)
        request (mock/request :post "/position" body-json)]
    (testing "Invalid request payload"
      (let [response (handler/post-position (assoc request :body (json/parse-string body-json true)))]
        (is (= 400 (:status response)))))))

