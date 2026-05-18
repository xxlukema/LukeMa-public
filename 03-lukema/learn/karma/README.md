# karma and jasmine

<pre>
# ==============================
# json-server
# https://github.com/typicode/json-server
#

npm install -g json-server

</pre>

## Create a db.json file

```ts
{
  "posts": [
    { "id": 1, "title": "json-server", "author": "typicode" }
  ],
  "comments": [
    { "id": 1, "body": "some comment", "postId": 1 }
  ],
  "profile": { "name": "typicode" }
}
```

## Start JSON Server

```txt
json-server --watch db.json
```
