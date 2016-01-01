(ns ghoulio-server.runner
  (:require [clojure.core.async :refer [>!! <! chan go]]
            [ghoulio-server.ghoulio :as ghoulio]))

(def ^:const GHOULIO_MAX_QUEUE_SIZE
  (Integer/parseInt
    (or (System/getenv "GHOULIO_MAX_QUEUE_SIZE")
        "5")))

(def ^:const GHOULIO_WORKERS
  (Integer/parseInt
    (or (System/getenv "GHOULIO_WORKERS")
        "1")))

(defn -open!
  [job]
  (ghoulio/open! (:url job) (:script job)))

(defn open!
  [channel page]
  {:pre [(:url page)
         (:script page)]}
  (>!! channel page))

(defn create
  []
  (let [channel (chan GHOULIO_MAX_QUEUE_SIZE)]
    (dotimes [_ GHOULIO_WORKERS]
      (go
        (while true
          (-open! (<! channel)))))
    channel))
