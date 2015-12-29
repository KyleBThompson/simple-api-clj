(ns simple-api-clj.post
  (:require [monger.collection :as mc]
            [monger.core :as mg])
  (:import [org.bson.types ObjectId]))

(defn init-db [name]
  (mg/connect!)
  (mg/set-db! (mg/get-db name)))

(defn fetch-post [id]
	(println (str "Id is " id))
  (mc/find-one-as-map "posts" { :_id id }))

(defn fetch-posts []
  (mc/find-maps "posts" {}))

(defn create [post]
  (let [id (ObjectId.)]
    (mc/insert "posts" (assoc post :_id id))
    id))

(defn delete-post [id]
  (mc/remove-by-id "posts" id))