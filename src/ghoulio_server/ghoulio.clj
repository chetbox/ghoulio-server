(ns ghoulio-server.ghoulio
  (:require [clojure.java.io :refer [as-file]]
            [me.raynes.conch.low-level :as sh]))

(def SLIMERJS-PATH
  (get (System/getenv) "SLIMERJS_PATH" "/usr/bin/slimerjs"))

(def GHOULIO-PATH
  (get (System/getenv) "GHOULIO_PATH" "/app/ghoulio.js"))

(def GHOULIO-PAGE-TIMEOUT
  (Integer/parseInt
    (get (System/getenv) "GHOULIO_PAGE_TIMEOUT" "60000")))

(def TIMEOUT-SCRIPT (str "setTimeout(function(){ fail('Timed out') }, " GHOULIO-PAGE-TIMEOUT ");"))

(defn- open-process!
  [url user-script]
  (sh/proc SLIMERJS-PATH
    GHOULIO-PATH
    url
    (str TIMEOUT-SCRIPT "\n\n" user-script)))

(defn open!
  [url user-script]
  (let [process (open-process! url user-script)]
    (sh/stream-to-out process :out)
    (sh/stream-to process :err *err*)))
