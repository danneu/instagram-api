# instagram-api

A restful Instagram client for Clojure.

Inspired by [adamwynne/twitter-api](https://github.com/adamwynne/twitter-api).

## Usage

The objective of `instagram-api` is to have a flexible, predictable interface that's consistent with Instagram's own documentation.

Each Instagram endpoint is exposed as a method.

- Pass in a map of params. They will be passed along.
- Any params that match the colon-prefixed parts of the endpoint path will replace that part of the url. For instance, if the endpoint is `users/:user-id/media/recent`, then providing `{:user-id 42}` will create `users/42/media/recent`.
- You must provide `:client-id` or `:access-token` params.

``` clojure
(ns my.app
  (:use [instagram-api.restful]))

(def client-id "...")
(def client-secret "...")

(let [user-id "317627251"]
  (get-users {:user-id user-id :client-id client-id}))
```

Output:

``` clojure
{:body
 {:meta {:code 200},
  :data
  {:username "danneu",
   :bio "",
   :website "",
   :profile_picture
   "http://images.ak.instagram.com/profiles/profile_317627251_75sq_1362163073.jpg",
   :full_name "Dan Neumann",
   :counts {:media 1, :followed_by 32, :follows 2},
   :id "317627251"}}
  ...} ; The rest of a standard Ring-like response map follows.
```

## Endpoints

Instagram Endpoint docs: http://instagram.com/developer/endpoints

- Users: http://instagram.com/developer/endpoints/users
- Relationships: http://instagram.com/developer/endpoints/relationships
- Media: http://instagram.com/developer/endpoints/media
- Comments: http://instagram.com/developer/endpoints/comments
- Likes: http://instagram.com/developer/endpoints/likes
- Tags: http://instagram.com/developer/endpoints/tags
- Locations: http://instagram.com/developer/endpoints/locations
- Geographies: http://instagram.com/developer/endpoints/geographies

``` clojure
;; Users
(definstagram get-users :get "users/:user-id")
(definstagram get-users-feed :get "users/self/feed")
(definstagram get-users-media-recent :get "users/:user-id/media/recent")
(definstagram get-users-media-liked :get "users/self/media/liked")
(definstagram get-users-search :get "users/search")

;; Relationships
(definstagram get-users-follows :get "users/:user-id/follows")
(definstagram get-users-followed-by :get "users/:user-id/followed-by")
(definstagram get-users-requested-by :get "users/self/requested-by")
(definstagram get-users-relationship :get "users/:user-id/relationship")
(definstagram post-users-relationship :post "users/:user-id/relationship")

;; Media
(definstagram get-media :get "media/:media-id")
(definstagram get-media-search :get "media/search")
(definstagram get-media-popular :get "media/popular")

;; Comments
(definstagram get-media-comments :get "media/:media-id/comments")
(definstagram post-media-comments :post "media/:media-id/comments")
(definstagram delete-media-comments :del "media/:media-id/comments/:comment-id")

;; Likes
(definstagram get-media-likes :get "media/:media-id/likes")
(definstagram post-media-likes :post "media/:media-id/likes")
(definstagram del-media-likes :del "media/:media-id/likes")

;; Tags
(definstagram get-tags :get "tags/:tag-name")
(definstagram get-tags-media-recent :get "tags/:tag-name/media/recent")
(definstagram get-tags-search :get "tags/search")

;; Locations
(definstagram get-locations :get "locations/:location-id")
(definstagram get-locations-media-recent :get "locations/:location-id/media/recent")
(definstagram get-locations-search :get "lcoations/search")

;; Geographies
(definstagram get-geographies-media-recent :get "geographies/:geo-id/media/recent")
```

## Todo

- Allow a second map that is merged into entire client/request options map to let user interact with http client like {:debug true :throw-entire-message? true :debug-body true}

## License

Distributed under the Eclipse Public License, the same as Clojure.
