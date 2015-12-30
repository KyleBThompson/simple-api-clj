(ns simple-api-clj.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [cheshire.core :refer [generate-string]]
            [cheshire.generate :refer [add-encoder encode-str]]
            [simple-api-clj.post :as post]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:import org.bson.types.ObjectId))

(add-encoder org.bson.types.ObjectId encode-str) ;so ObjectId can be enconded to json

(def to-json json/generate-string)

(defn create [params]
  (let [id (post/create params)]
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (to-json (assoc params :_id (str id) :links {:self (str "/posts/" id)}))}))

(defn fetch [id]
  (let [object-id (ObjectId. id)
        post (post/fetch-post object-id)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (to-json (assoc post :_id (str id)))}))

(defn fetch-all []
  (let [posts (post/fetch-posts)]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (to-json {:list posts} )}))

(defn delete [id]
  (let [object-id (ObjectId. id)
        post (post/delete-post object-id)]
    {:status 204
     :headers {"Content-Type" "application/json"}}))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/posts/:id" [id] (fetch id))
  (GET "/posts" [] (fetch-all))
  (POST "/posts" {params :body} (create params))
  (DELETE "/posts/:id" [id] (delete id))
  (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false))
      (middleware/wrap-json-body {:keywords? true})
       middleware/wrap-json-response))