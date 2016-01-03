(ns ghoulio-server.ghoulio
  (:require [clojure.java.io :refer [as-file]]
            [me.raynes.conch.low-level :as sh]))

(def ^:const SLIMERJS-PATH
  (or (System/getenv "SLIMERJS_PATH")
      "/usr/bin/slimerjs"))

(def ^:const GHOULIO-PATH
  (or (System/getenv "GHOULIO_PATH")
      "/app/ghoulio.js"))

(def ^:const GHOULIO-PAGE-TIMEOUT
  (Integer/parseInt
    (or (System/getenv "GHOULIO_PAGE_TIMEOUT")
        "60000")))

(def ^:const TIMEOUT-SCRIPT (str "setTimeout(function(){ fail('Timed out') }, " GHOULIO-PAGE-TIMEOUT ");"))

(defn- open-process!
  [url callback-url user-script]
  (sh/proc SLIMERJS-PATH
    GHOULIO-PATH
    url
    callback-url
    (str TIMEOUT-SCRIPT "\n\n" user-script)))

(defn open!
  [url callback-url user-script]
  (let [process (open-process! url (or callback-url "") user-script)]
    (sh/stream-to-out process :out)
    (sh/stream-to process :err *err*)))
