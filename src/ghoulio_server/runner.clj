(ns ghoulio-server.runner
  (:require [clojure.core.async :refer [>!! <! chan go-loop]]
            [ghoulio-server.ghoulio :as ghoulio]
            [taoensso.timbre :as log]))

(def GHOULIO_MAX_QUEUE_SIZE
  (Integer/parseInt
    (get (System/getenv) "GHOULIO_MAX_QUEUE_SIZE" "5")))

(def GHOULIO_WORKERS
  (Integer/parseInt
    (get (System/getenv) "GHOULIO_WORKERS" "1")))

(defn -open!
  [page]
  (ghoulio/open! (:url page) (:script page)))

(defn open!
  [channel page]
  {:pre [(:url page)
         (:script page)]}
  (log/debugf "Open %s requested" (:url page))
  (>!! channel page))

(defn create
  []
  (log/infof "Starting %d workers" GHOULIO_WORKERS)
  (let [channel (chan GHOULIO_MAX_QUEUE_SIZE)]
    (dotimes [i GHOULIO_WORKERS]
      (go-loop []
        (let [page (<! channel)]
          (log/debugf "Opening %s on worker %d" (:url page) i)
          (-open! page)
          (log/tracef "Done with %s on worker %d" (:url page) i))
        (recur)))
    channel))
