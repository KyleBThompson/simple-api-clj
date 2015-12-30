(defproject simple-api-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-defaults "0.1.5"]
                 [cheshire "5.1.1"]
                 [com.novemberain/monger "3.0.0-rc2"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler simple-api-clj.handler/app
         :init simple-api-clj.core/init}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})