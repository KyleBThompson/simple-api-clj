(ns simple-api-clj.core
  (:require [simple-api-clj.post :as post]
            [compojure.handler :as handler]))

(defn init []
  (post/init-db "development"))