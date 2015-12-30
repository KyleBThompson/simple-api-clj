(ns simple-api-clj.post
  (:require [monger.collection :as mc]
            [monger.core :as mg])
  (:import [org.bson.types ObjectId]))

(defn init-db [name]
  (def conn (mg/connect))
  (def db   (mg/get-db conn name)))

(defn fetch-post [id]
	(println (str "Id is " id))
  (mc/find-one-as-map db "posts" { :_id id }))

(defn fetch-posts []
  (mc/find-maps db "posts" {}))

(defn create [post]
  (let [id (ObjectId.)]
    (mc/insert db "posts" (assoc post :_id id))
    id))

(defn delete-post [id]
  (mc/remove-by-id db "posts" id))