(ns gram.restful
  (:require [clj-http.client :as client]
            [clojure.string :as str]))

;; Macro setup ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn underscore-keys
  "Underscores the keyword keys of a hash-map for clj-http.client.
   Ex: {:hey-there 42} => {:hey_there 42}"
  [hmap]
  (let [underscore-key (fn [k] (keyword (str/replace (name k) #"-" "_")))]
    (into {} (map (fn [[k v]] [(underscore-key k) v]) hmap))))

(defn stringify-keys-vals
  "Stringifies the keys and values of a hash-map.
   Ex: {:foo 42} => {\":foo\" \"42\"}"
  [hmap]
  (into {} (map (fn [[k v]] [(str k) (str v)]) hmap)))

(defmacro defgram [fn-name method path]
  `(defn ~fn-name [params#]
     (let [url# (gen-url ~path params#)
           underscored-params# (underscore-keys params#)]
       (client/request (merge {:method ~method
                               :url url#
                               :as :json}
                              (if (= :get ~method)
                                {:query-params underscored-params#}
                                {:form-params underscored-params#}))))))

(defn gen-url
  "Generates Instagram API url from endpoint path.
   Also, it replaces colon-prefix parts of the path
   with the matching value from the params map.

   Example: (gen-url \"foo/:bar/baz/:qux\" {:bar 42 :qux \"magic\"})
            ;=> foo/42/baz/magic"
  [path params]
  (str "https://api.instagram.com/v1/"
       (if-let [param-keys (seq (map first (re-seq #":([a-z-]+)" path)))]
         (str/replace path
                      (re-pattern (str/join "|" param-keys))
                      (stringify-keys-vals params))
         path)))

;; Instagram API ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Users
(defgram get-users :get "users/:user-id")
(defgram get-users-feed :get "users/self/feed")
(defgram get-users-media-recent :get "users/:user-id/media/recent")
(defgram get-users-media-liked :get "users/self/media/liked")
(defgram get-users-search :get "users/search")

;; Relationships
(defgram get-users-follows :get "users/:user-id/follows")
(defgram get-users-followed-by :get "users/:user-id/followed-by")
(defgram get-users-requested-by :get "users/self/requested-by")
(defgram get-users-relationship :get "users/:user-id/relationship")
(defgram post-users-relationship :post "users/:user-id/relationship")

;; Media
(defgram get-media :get "media/:media-id")
(defgram get-media-search :get "media/search")
(defgram get-media-popular :get "media/popular")

;; Comments
(defgram get-media-comments :get "media/:media-id/comments")
(defgram post-media-comments :post "media/:media-id/comments")
(defgram delete-media-comments :del "media/:media-id/comments/:comment-id")

;; Likes
(defgram get-media-likes :get "media/:media-id/likes")
(defgram post-media-likes :post "media/:media-id/likes")
(defgram del-media-likes :del "media/:media-id/likes")

;; Tags
(defgram get-tags :get "tags/:tag-name")
(defgram get-tags-media-recent :get "tags/:tag-name/media/recent")
(defgram get-tags-search :get "tags/search")

;; Locations
(defgram get-locations :get "locations/:location-id")
(defgram get-locations-media-recent :get "locations/:location-id/media/recent")
(defgram get-locations-search :get "lcoations/search")

;; Geographies
(defgram get-geographies-media-recent :get "geographies/:geo-id/media/recent")
