# gram

a restful instagram client for clojure.

## Usage

``` clojure
(def client-id "...")
(def client-secret "...")

(let [user-id "317627251"]
  (get-users {:user-id user-id :client-id client-id}))
```

Output:

``` clojure
{:cookies
 {"csrftoken"
  {:discard false,
   :expires #inst "2014-06-24T16:16:46.000-00:00",
   :path "/",
   :secure false,
   :value "a8a606abc7386cb8af9f8bb88ccd81ec",
   :version 0}},
 :trace-redirects ["https://api.instagram.com/v1/users/317627251"],
 :request-time 973,
 :status 200,
 :headers
 {"server" "nginx",
  "content-encoding" "gzip",
  "content-language" "en",
  "content-type" "application/json; charset=utf-8",
  "date" "Tue, 25 Jun 2013 16:16:46 GMT",
  "x-ratelimit-remaining" "4995",
  "vary" "Cookie, Accept-Language, Accept-Encoding",
  "x-ratelimit-limit" "5000",
  "content-length" "206",
  "connection" "Close"},
 :body
 {:meta {:code 200},
  :data
  {:username "danneu",
   :bio "",
   :website "",
   :profile_picture
   "http://images.ak.instagram.com/profiles/profile_317627251_75sq_1362163073.jpg",
   :full_name "Dan Neumann",
   :counts {:media 1, :followed_by 32, :follows 2},
   :id "317627251"}}}
```

- Pass in a map of params. They will be passed along.
- Any params that match the colon-prefixed parts of the endpoint path will replace that part of the url. For instance, if the endpoint is users/:user-id/media/recent, then providing {:user-id 42} will create users/42/media/recent.
- You must provide :client-id or :access-token keys.

## Todo

- Allow a second map that is merged into entire client/request options map to let user interact with http client like {:debug true :throw-entire-message? true :debug-body true}

## License

Copyright © 2013 Dan Neumann

Distributed under the Eclipse Public License, the same as Clojure.
