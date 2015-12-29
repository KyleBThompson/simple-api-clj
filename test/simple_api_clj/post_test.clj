(ns simple-api-clj.post-test
  (:require [clojure.test :refer :all]
            [simple-api-clj.post :refer :all]))

(init-db "test")

(deftest persist-test
  (let [id (create { :title "Foo" })
        retrieved (fetch-post id)]
    (testing "Inserts and retrieves a document."
      (is (= (retrieved :title) "Foo")))))
