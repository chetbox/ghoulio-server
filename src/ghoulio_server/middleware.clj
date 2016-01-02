(ns ghoulio-server.middleware
  (:require [clojure.walk :refer [keywordize-keys]]))

(defn wrap-body-keywordize-keys
  [app]
  (fn [req]
    (app (assoc req
                :body (keywordize-keys (:body req))))))

(defn wrap-form-params-as-body
  [app]
  (fn [req]
    (app (assoc req
                :body (if (empty? (:form-params req))
                        (:body req)
                        (:form-params req))))))
