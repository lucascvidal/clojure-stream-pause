(ns playback-api.utils-test
  (:require [clojure.test :refer :all]
            [playback-api.utils :as utils]))

(deftest test-is-uuid?
  (testing "Valid UUID"
    (is (true? (utils/is_uuid? "123e4567-e89b-12d3-a456-426614174000"))))
  (testing "Invalid UUID"
    (is (false? (utils/is_uuid? "not-a-uuid")))))

(deftest test-validate-position
  (testing "Valid position map"
    (let [position {:user_id "123e4567-e89b-12d3-a456-426614174000"
                    :title_id "123e4567-e89b-12d3-a456-426614174000"
                    :media_id "123e4567-e89b-12d3-a456-426614174000"
                    :position 10
                    :finished true}]
      (is (true? (utils/validate-position position)))))
  (testing "Invalid position map"
    (let [position {:user_id "invalid-uuid"
                    :title_id "123e4567-e89b-12d3-a456-426614174000"
                    :media_id "123e4567-e89b-12d3-a456-426614174000"
                    :position -1
                    :finished "yes"}]
      (is (false? (utils/validate-position position))))))
