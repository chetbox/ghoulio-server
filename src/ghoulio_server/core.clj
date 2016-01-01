(ns ghoulio-server.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]]
            [ghoulio-server.handler :refer [app]]))

(defn -main
  [& _]
  (let [port-str (or (System/getenv "PORT")
                     "1337")]
  (println "Starting server on port" port-str)
  (run-server app {:port (Integer/parseInt port-str)})))