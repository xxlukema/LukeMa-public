# `redis` and `hazelclast`

## Requires `REDIS_PASSWORD` env var

    # `.env` file:
    REDIS_PASSWORD=changeit!

## Cache Eviction

[Cache Eviction]<https://www.baeldung.com/spring-boot-evict-cache>

Two ways to evict cache:

1. Using the @CacheEvict annotation on a method.
2. Or, auto-wiring the CacheManger and clearing it by calling the clear() method.

## Spring Caching

*Note:* Just enable one cache provider, `redis` or `hazelclast`, but not both.

### Redis

#### Dependency for redis

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

#### `application.yml`

    spring:
      cache:
        redis:
          time-to-live: 100S
        type: redis

#### How to install Redis using Docker?

* docker pull redis:7.4.1-alpine
* `.env` file: `REDIS_PASSWORD=changeit!`
* docker run --name cache.learn.com -p 6379:6379 -d redis

### Hazelcast

#### Dependency for hazelclast

    <dependency>
        <groupId>com.hazelcast</groupId>
        <artifactId>hazelcast-all</artifactId>
        <version>4.2.4</version>
    </dependency>

#### Add hazelcast.yaml

    hazelcast:
      network:
        join:
          multicast:
            enabled: true
