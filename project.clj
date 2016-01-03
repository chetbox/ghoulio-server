(defproject ghoulio-server "2.0.0"
  :description "A web server allowing you to automate web pages with JavaScript"
  :url "https://github.com/chetbox/ghoulio-server"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.async "0.2.374"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.4.0"]
                 [me.raynes/conch "0.8.0"]
                 [http-kit "2.1.18"]
                 [com.taoensso/timbre "4.2.0"]]
  :main ghoulio-server.core
  :aot [ghoulio-server.core]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler ghoulio-server.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
