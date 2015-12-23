(ns simple-api-clj.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [simple-api-clj.post :as post]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:import org.bson.types.ObjectId))

(def to-json json/generate-string)

(defn create [params]
  (let [id (post/create params)]
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (to-json (assoc params :_id (str id)))}))

(defn fetch [id]
  (let [object-id (ObjectId. id)
        post (post/fetch object-id)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (to-json (assoc post :_id (str id)))}))

(defroutes app-routes
  (POST "/" request
        (let [name (or (get-in request [:params :name])
                       (get-in request [:body :name])
                       "John Doe")]
          {:status 200
           :body {:name name
                  :desc (str "The name you sent to me was " name)}}))
  (GET "/" [] "Hello World")
  (POST "/posts" {params :body} (create params))
  (GET "/posts/:id" [id] (fetch id))
  (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false))
      (middleware/wrap-json-body {:keywords? true})
       middleware/wrap-json-response))