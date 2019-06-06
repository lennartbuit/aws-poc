(ns aws-poc.core-test
  (:require
   [clojure.test :refer :all]
   [cognitect.aws.client.api :as aws])
  (:import (java.util UUID)))

(deftest aws-timing
  (testing "Try invoking the amazon AWS mock multiple times."
    (let [s3      (aws/client {:api :s3
                               :endpoint-override {:protocol :http
                                                   :hostname "localhost"
                                                   :port     4572}
                               :region            "eu-central-1"})
          bucket  "test"
          content "ABC"]
      (aws/invoke s3 {:op      :CreateBucket
                      :request {:Bucket bucket}})
      (dotimes [_ 5]
        (let [start (System/currentTimeMillis)
              key   (str (UUID/randomUUID))]
          (aws/invoke s3 {:op      :PutObject
                          :request {:Bucket bucket
                                    :Key    key
                                    :Body   (.getBytes content)}})
          (println "PutObject took:" (- (System/currentTimeMillis) start) "ms")
          (is (= content
                 (slurp (:Body (aws/invoke s3 {:op      :GetObject
                                               :request {:Bucket bucket
                                                         :Key    key}})))))
          (println "GetObject took:" (- (System/currentTimeMillis) start) "ms"))))))
