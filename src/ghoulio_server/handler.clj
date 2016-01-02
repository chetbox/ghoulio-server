(ns ghoulio-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response]]
            [ghoulio-server.runner :as runner]
            [ghoulio-server.middleware :refer [wrap-form-params-as-body wrap-body-keywordize-keys]]))

(defn- app-routes
  []
  (let [worker (runner/create)]
    (routes
      (GET "/"
        []
        "Boo.")
      (POST "/open"
        {page :body}
        (runner/open! worker page)
        (response "ok\n"))
      (route/not-found "Not Found"))))

(def app
  (-> (app-routes)
    wrap-body-keywordize-keys
    wrap-form-params-as-body
    wrap-params
    wrap-json-body
    wrap-json-response
    (wrap-defaults api-defaults)))
