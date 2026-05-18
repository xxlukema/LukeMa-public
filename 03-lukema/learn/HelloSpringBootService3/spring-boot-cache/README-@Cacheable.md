# Spring Boot Cache

[Ref]<https://howtodoinjava.com/spring-boot/spring-boot-cache-example/>

## `pom.xml`

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>

## Config 1 - App level config

    @Configuration
    @EnableCaching
    public class CacheConfig {
        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("employees");
        }
    }

## Config 2 - Class level config

    @Service
    @AllArgsConstructor
    @CacheConfig(cacheNames = { "employees" })
    @EnableCaching
    public class MyService {
    }

## Disable in `application-dev.properties`

    spring.cache.type=none

## Annotations

- @Cacheable: Triggers cache population.
- @CacheEvict: Triggers cache eviction.
- @CachePut: Updates the cache without interfering with the method execution.
- @Caching: Regroups multiple cache operations to be applied on a method.
- @CacheConfig: Shares some standard cache-related settings at the class level.

## Supported caches

- JCache (JSR-107) (`JCache` is the standard caching API for Java.)
- EhCache (example)
- Hazelcast
- Infinispan
- Couchbase
- Redis
- Caffeine (example)
- Simple cache (This is the default implementation.)

`JCache`: `JCache` is the standard caching API for Java. It is provided by `javax.cache.spi.CachingProvider`. The `spring-boot-starter-cache` provides the `JCacheCacheManager`.

`Simple cache`: It is the default implementation. It configures a `ConcurrentHashMap` as a cache store if spring boot does not find any cache provider in the classpath.

## Example

    package com.learn.service;
    
    
    import java.util.List;
    import java.util.Optional;
    
    import org.springframework.cache.annotation.CacheConfig;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.CachePut;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.cache.annotation.Caching;
    import org.springframework.cache.annotation.EnableCaching;
    import org.springframework.stereotype.Service;
    
    import com.learn.entity.SpringSecurityUserEntity;
    import com.learn.pojo.Employee;
    import com.learn.repository.SpringSecurityUserRepository;
    
    import lombok.AllArgsConstructor;
    
    
    @Service
    @AllArgsConstructor
    @CacheConfig(cacheNames = { "employees" })  <========= Class leve config
    @EnableCaching                              <========= Class leve config
    /**
     * - If no params are given, return SimpleKey.EMPTY.
     * - If only one param is given, return that instance.
     * - If more than one param is given, return a SimpleKey that contains all parameters.
     */
    public class MyService {
    
        private final SpringSecurityUserRepository springSecurityUserRepository;
    
        @Cacheable("employees")
        /**
         * - If no params are given, return SimpleKey.EMPTY.
         * - If only one param is given, return that instance.
         * - If more than one param is given, return a SimpleKey that contains all parameters.
         */
        public Optional<SpringSecurityUserEntity> findById(Long id) {
            return springSecurityUserRepository.findById(id);
        }
    
        @Cacheable(value = "employees", key = "#id")
        public Optional<SpringSecurityUserEntity> findById2(Long id) {
            return springSecurityUserRepository.findById(id);
        }
    
        @Cacheable(value = "employees", key = "#department.id")
        public Optional<SpringSecurityUserEntity> findById3(Long id) {
            return springSecurityUserRepository.findById(id);
        }
    
        @Cacheable(value = "employees", key = "#id", condition = "#id > 0")
        public Optional<SpringSecurityUserEntity> findById4(Long id) {
            return springSecurityUserRepository.findById(id);
        }
    
        @CachePut(cacheNames = "employees", key = "#employee.id")
        public Employee updateEmployee(Employee employee) {
            return new Employee();
        }
    
        @CacheEvict(cacheNames = "employees", key = "#id")
        public void deleteEmployee(Long id) {
        }
    
        @CacheEvict(cacheNames = "employees", allEntries = true)
        public void deleteAllEmployees() {
        }
    
        @Caching(evict = {
                @CacheEvict(cacheNames = "departments", allEntries = true),
                @CacheEvict(cacheNames = "employees", key = "data") })
        public boolean importEmployees(List<Employee> data) {
            return true;
        }
    }

## Cache parameters

- If no params are given, return SimpleKey.EMPTY.
- If only one param is given, return that instance.
- If more than one param is given, return a SimpleKey that contains all parameters.
