(defproject playback-api "0.1.0-SNAPSHOT"
  :description "BP Backend Exercise"
  :url "http://localhost:8080/"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.github.seancorfield/next.jdbc "1.3.925"]
                 [org.postgresql/postgresql "42.7.3"]
                 [environ "1.2.0"]
                 [ring/ring-core "1.12.0"]
                 [compojure "1.7.1"]
                 [cheshire "5.12.0"]
                 [ring/ring-jetty-adapter "1.12.0"]
                 [org.slf4j/slf4j-simple "1.7.30"]
                 [ring/ring-json "0.5.1"]]
  :main ^:skip-aot playback-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
