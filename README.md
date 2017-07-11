# reactive

[![Build Status](https://travis-ci.org/springbootbuch/reactive.svg?branch=master)](https://travis-ci.org/springbootbuch/reactive)

This project includes three modules

* filmstore
* watchednow
* client

Start them with `./mvnw spring-boot:run` in the order above.

_filmstore_ provides a list of films that can be streamed. _filmstore_ is also protected by username and password (which are "spring" and "boot"):

```
curl -u spring:boot http://localhost:8080/api/films
```

"Stream" any given film with this (replace foobar with one of the ids above):

```
curl -u spring:boot http://localhost:8080/api/films/foobar/stream
```

You can see the list of films being streamed with

```
curl  http://localhost:8081/api/watchedRightNow
```

Or open http://localhost:8081 in Chrome.