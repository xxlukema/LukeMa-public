# `MongoDB` Setup For Shein

[Ref]<README-MongoDB.md>

## Install `MongoDB` server community edition

    # add to PATH:
    C:\Program Files\MongoDB\Server\7.0\bin

## Install `mongosh`

    # add to PATH:
    C:\Tools\mongosh-2.1.1-win32-x64\bin

## Create user `luke/luke` with `readWrite` role to `lukedb`

    #
    # 1. Connect to `MongoDB` server using `mongosh`
    mongosh
    #
    # 2. Create database `lukedb` if it does not exist, or switch to `lukedb` if it exists.
    use lukedb
    #
    # 3. Create user `luke/luke` with `readWrite` permission to `lukedb`.
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

    #
    # 4. init `CategoryConditions` collection
    db.CategoryConditions.insertMany([
      { category: 'electronics',
        conditions: ['Brand New - Sealed', 'New - Open box', 'New - Seller refurbished', 'Like New - Excellent', 'Seller refurbished', 'Very Good', 'Good', 'Acceptable'] },
      { category: 'tool',
        conditions: ['New', 'New - Open box', 'Seller refurbished', 'Used', 'For parts or not working'] },
      { category: 'cloth',
        conditions: ['New with tags', 'New without tags', 'New with defects', 'Pre-owned'] },
      { category: 'other',
        conditions: ['Brand New - Sealed', 'New - Open box', 'Like New - Excellent', 'Very Good', 'Good', 'Acceptable'] },
    ])

## (default) security is **NOT** enabled. Endable `MongoDB` server authentication

    # Add `security.authorization=enabled` to `[install directory]\bin\mongod.cfg`:
    # [Ref]<https://www.mongodb.com/docs/manual/reference/configuration-options/>
    security:
      authorization: enabled

## Restart `MongoDB` windows service

## Connect to `MongoDB` server using `mongosh`

    mongosh lukedb -u luke -p luke
    # or
    mongosh localhost:27017/lukedb -u luke -p luke
    # or
    mongosh --port 27017 --authenticationDatabase lukedb -u luke -p luke

## `MongoDBCompass` Authentication Setup

    New Connection :: Advanced Connection Options :: Authentication
    User/password/database: luke/luke/lukedb ====> (click) "Save & Connect" button :: Save connection to favortites: lukedb :: (click) "Save & Connect" button
    (skip. updated automatically after filling in user/password/database) URI: mongodb://luke:luke@localhost:27017/?authSource=lukedb

## Credentials for boot

    spring:
      application:
        name: hello-mongodb-boot
      data:
        jpa:
          repositories:
            enabled: true
        mongodb:
          host: @server-ip-or-fullname@
          port: 27017
          database: lukedb
          # username: luke
          # password: luke
          username: ENC(zjpIoh+5DcY+QGRUOxINeA==)
          password: ENC(DIM8/d6v+5pS57wfdRfgaA==)

    # MongoDB
    spring.data.mongodb.host: @server-ip-or-fullname@
    spring.data.mongodb.port: 27017
    spring.data.mongodb.database: lukedb
    # spring.data.mongodb.username: luke
    # spring.data.mongodb.password: luke
    spring.data.mongodb.username: ENC(zjpIoh+5DcY+QGRUOxINeA==)
    spring.data.mongodb.password: ENC(DIM8/d6v+5pS57wfdRfgaA==)
