# MongoDB Init

## Set up

[Set Up]<README-MongoDB.md>

## Create user

db.createUser(
  {
    user: "luke",
    pwd:  "luke",   // or passwordPrompt()
    roles: [
             { role: "readWrite", db: "lukedb" },
             { role: "read", db: "test" }
           ]
  }
)

## Boot properties/Credentials

    # 1. application.yml
    spring:
      application:
        name: hello-mongodb-boot
      data:
        jpa:
          repositories:
            enabled: true
        mongodb:
          host: localhost
          port: 27017
          # username: luke
          # password: luke
          username: ENC(zjpIoh+5DcY+QGRUOxINeA==)
          password: ENC(DIM8/d6v+5pS57wfdRfgaA==)
          database: lukedb

    # 2. application.properties
    spring.data.mongodb.host: localhost
    spring.data.mongodb.port: 27017
    spring.data.mongodb.database: lukedb
    # spring.data.mongodb.username: luke
    # spring.data.mongodb.password: luke
    spring.data.mongodb.username: ENC(zjpIoh+5DcY+QGRUOxINeA==)
    spring.data.mongodb.password: ENC(DIM8/d6v+5pS57wfdRfgaA==)
