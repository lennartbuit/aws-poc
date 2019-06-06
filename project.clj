(defproject aws-poc "0.1.0-SNAPSHOT"
  :description "AWS POC showing slowness of the Cognitect AWS api + localstack + CircleCI"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.cognitect.aws/api "0.8.305"]
                 [com.cognitect.aws/endpoints "1.1.11.549"]
                 [com.cognitect.aws/s3 "714.2.430.0"]]
  :main ^:skip-aot aws-poc.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
