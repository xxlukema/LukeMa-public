# MongoDB

## Install Server (Community Edition). This will also install `MongoDBCompass`

[Download `Community Server` + `MongoDBCompass` All in One]<https://www.mongodb.com/download-center/community/releases>
[current release]<https://fastdl.mongodb.org/windows/mongodb-windows-x86_64-6.0.13.zip>

    # add to PATH:
    C:\Program Files\MongoDB\Server\7.0\bin

    # This will also install `MongoDBCompass` into
    # (skip. no need to add into PATH) C:\Users\lma\AppData\Local\MongoDBCompass\MongoDBCompass.exe
    
    mongod - The database server.
    mongos - Sharding router.

    mongod --help
    mongod.exe --dbpath "C:\data"
    "C:\Program Files\MongoDB\Server\7.0\bin\mongod.exe" --dbpath="c:\data\db"

## If You Installed MongoDB as a Windows Service

1. The MongoDB service starts upon successful installation.
2. Download and install `mongosh` separately into `C:\D\Tools\mongosh-2.1.1-win32-x64`. It is not installed automatically by `MongoDB` installer.
3. Open `mongosh` to connect to db, and create database `lukedm` and user `luke` with `readWrite` role to `lukedb`.
4. If you would like to customize the `MongoDB` windows service:
   (a) You must stop the `MongoDB` windows service.
   (b) Customize the MongoDB instance by editing the configuration file at `[install directory]\bin\mongod.cfg` --- enable authentication
   (c) Restart the `MongoDB` windows service.
5. After making changes, re-connect `MongoDBCompass` and `mongosh`.

### `[install directory]\bin\mongod.cfg`

[Configuration Options]<http://docs.mongodb.org/manual/reference/configuration-options/>

    # mongod.conf
    
    # for documentation of all options, see:
    #   http://docs.mongodb.org/manual/reference/configuration-options/
    
    # Where and how to store data.
    storage:
      dbPath: C:\Program Files\MongoDB\Server\7.0\data
    
    # where to write logging data.
    systemLog:
      destination: file
      logAppend: true
      path:  C:\Program Files\MongoDB\Server\7.0\log\mongod.log

    # (default) security is **NOT** enabled.
    # step 1. create user `luke` with `readWrite` role to `lukedb`
    # step 2. add following two lines, then restart `MongoDB` windows service to enable security.
    security:
      authorization: enabled

## To stop `mongodb` windows service

Stop `MongoDB` service from windows service console.

## Download and Install MongoShell: `mongosh`

[Download `mongosh`]<https://www.mongodb.com/docs/mongodb-shell/>
[Current Release (There is a )]<https://www.mongodb.com/try/download/shell?jmp=docs>

The MongoDB Shell `mongosh` is not installed with `MongoDB` Server. You need to download and install mongosh separately.

    # add to PATH:
    C:\D\Tools\mongosh-2.1.1-win32-x64\bin

## `MongoDBCompass` Authentication Setup

    New Connection :: Advanced Connection Options :: Authentication
    User/password/database: luke/luke/lukedb ====> (click) "Save & Connect" button :: Save connection to favortites: lukedb :: (click) "Save & Connect" button
    (skip. updated automatically after filling in user/password/database) URI: mongodb://luke:luke@localhost:27017/?authSource=lukedb

## cmd

    #
    # 1. Connect to `MongoDB` server using `mongosh`
    mongosh
    #
    #
    # use admin
    # db.createUser(
    #   {
    #     user: "root",
    #     pwd: "pass123",
    #     roles: [ { role: "userAdminAnyDatabase", db: "admin" }, "readWriteAnyDatabase" ]
    #   }
    # )
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
    # or
    db.createUser(
      {
        user: "luke",
        pwd:  passwordPrompt(),   // or cleartext password "luke"
        roles: [
                 { role: "readWrite", db: "test" },
                 { role: "read", db: "reporting" },
                 { role: "read", db: "products" },
                 { role: "read", db: "sales" },
                 { role: "readWrite", db: "accounts" }
               ]
      }
    )
    #
    # 4. grant role to user
    use lukedb
    db.grantRolesToUser( "<username>", [ <roles> ], { <writeConcern> (Optional) } )
    db.grantRolesToUser( "luke", [ "read" ] ) //OR
    db.grantRolesToUser( "luke", [ "readWrite" ] )

    use lukedb
    db.grantRolesToUser(
       "luke",
       [ "readWrite" , { role: "readWrite", db: "lukedb" } ]
    )

    #
    # 4. Test user `luke`
    db.getUsers()
    > {
    >   users: [
    >     {
    >       _id: 'lukedb.luke',
    >       userId: UUID('d6f7b366-308c-4da8-8881-759c8704a095'),
    >       user: 'luke',
    >       db: 'lukedb',
    >       roles: [
    >         { role: 'read', db: 'test' },
    >         { role: 'readWrite', db: 'lukedb' }
    >       ],
    >       mechanisms: [ 'SCRAM-SHA-1', 'SCRAM-SHA-256' ]
    >     }
    >   ],
    >   ok: 1
    > }
    #
    # 5. Shutdown the server and exit
    db.shutdownServer()
    exit
    #
    # 6. (for windows) Enable server authentication
    # Add `security.authorization=enabled` to `[install directory]\bin\mongod.cfg`:
    # [Ref]<https://www.mongodb.com/docs/manual/reference/configuration-options/>
    security:
      authorization: enabled
    #
    # 7. (for linux) Restart mongod with --auth
    # (for linux) sudo ./mongodb/bin/mongod --auth --dbpath /mnt/db/
    #
    # 8. Run mongo again in 2 ways:
    # 8.1 Run mongo first then login:
    mongosh localhost:27017
    use lukedb
    db.auth('luke','luke');
    # 8.2 Run & login to mongo in command line.
    mongosh lukedb -u luke -p luke
    mongosh localhost:27017/lukedb -u luke -p luke
    mongosh --port 27017 --authenticationDatabase lukedb -u luke -p luke
    #
    # 9. test user authentication
    db.adminCommand( { listDatabases: 1, nameOnly: true} )
    > { databases: [ { name: 'lukedb' } ], ok: 1 }

    # The `use [Database_name]` command makes a new database in the system if it does not exist, if the database exists it uses that database:
    use lukedb
    db.student.insertOne({Akshay:500})

    # list all users
    # db.system.users.find() <--- This does not show anything
    db.getUsers()
    > { users: [], ok: 1 }
    db.getUsers( {
       showCredentials: true,
       showCustomData: true
    } )
    > { users: [], ok: 1 }
    db.getUsers( {
       showCredentials: true,
       showCustomData: true,
       filter: <document>
    } )

    # list all databases
    db.adminCommand( { listDatabases: 1 } )
    db.adminCommand( { listDatabases: 1, nameOnly: true} )
    db.adminCommand( { listDatabases: 1, filter: { "name": /^luke/ } } )

    # list all collections
    db.runCommand( { listCollections: 1 } )

    db.runCommand(
       {
         listCollections: 1,
         filter: <document>,
         nameOnly: <boolean>,
         authorizedCollections: <boolean>,
         comment: <any>
       }
    )

    show collections
    > test
    show tables
    > test
    db.getCollectionNames()
    > [ 'test' ]
    show dbs
    > lukedb  8.00 KiB

    db.getCollection("mkyong")
    > lukedb.mkyong
    show tables
    > mkyong
    #
    # Find All Documents in a Collection
    db.mkyong.find()
    db.mkyong.find({name: "mkyong"})
    db.mkyong.find({name: { $eq: "mkyong"}})
    db.mkyong.find(
       { "name.last": { $regex: /^N/ } }
    )
    db.mkyong.find( { birth: { $gt: new Date('1940-01-01'), $lt: new Date('1960-01-01') } } )
    db.mkyong.countDocuments()

    db.mkyong.renameCollection("mkyong2")
    > { ok: 1 }
    db.mkyong2.drop()
    > true


    help
    > ...
    > use      Set current database
    > show     'show databases'/'show dbs': Print a list of all available databases.
               'show collections'/'show tables': Print a list of all collections for current database.
               'show profile': Prints system.profile information.
               'show users': Print a list of all users for current database.
               'show roles': Print a list of all roles for current database.
               'show log <type>': log for current connection, if type is not set uses 'global'
               'show logs': Print all logs.
    > ...

## Spring Boot crud application using MongoDB

    uri: mongodb://ENC(//0WoVkmWvulnznZxey00g==):ENC(szibjzsbUyD242hgIAl8Xw==)@${mongo.host:localhost}:${mongo.port:27017}/${mongo.db:lukedb}
    // uri: mongodb://ENC(UNF4jfbQe1LprvayUd9fhcMn9RlKCyyw):ENC(n+7zYHqzINBBi4PMhbsAcpyfQDc32nSs)@localhost:27017/lukedb
    uri: mongodb://luke:luke@localhost:27017/lukedb

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

## Tutorial

[Official Tutorial]<https://www.mongodb.com/docs/manual/tutorial/>
[Tutorialspoint Tutorial]<https://www.tutorialspoint.com/mongodb/index.htm>
[Geeksforgeeks Tutorial]<https://www.geeksforgeeks.org/mongodb-tutorial/>

## Insert

    db.posts.insertOne({
      title: "Post Title 1",
      body: "Body of post.",
      category: "News",
      likes: 1,
      tags: ["news", "events"],
      date: Date()
    })

    db.products.insertMany([
      { item: "card", qty: 15 },
      { item: "envelope", qty: 20 },
      { item: "stamps" , qty: 30 }
    ]);

    db.products.insertMany([
      { item: "card", qty: 15 },
      { "item": "stamps" , 'qty': 30 }
    ]);

## Find

    db.posts.find()
    db.posts.findOne()

    db.SheinItem.find({title: /ocp/i})
    db.SheinItem.find({price: {$gt: 100}})

## Update

    db.posts.find( { title: "Post Title 1" } )
    db.posts.updateOne( { title: "Post Title 1" }, { $set: { likes: 2 } } )
    db.posts.find( { title: "Post Title 1" } )

## Delete

    db.posts.deleteOne({ title: "Post Title 5" })
    db.posts.deleteMany({ category: "Technology" })

    # delete all
    db.posts.deleteMany({})
    db.posts.remove({})

    db.posts.drop()

## Count

    db.posts.count()

## Rename collection

    db.CategoryCondition.renameCollection('CategoryConditions')

## Rename a field

    #
    # rename field
    #
    db.SheinItem.update({
        "imageFiles": {
            $exists: true
        }
    }, {
        $rename: {
            "imageFiles": "imageFileNames"
        }
    }, false, true);
    #
    # The `false`, `true` in the method above are: `{ upsert:false, multi:true }`. You need the `multi:true` to update all your records.
    #

## Add a field

    db.SheinItem.updateMany({}, {$set:{"status": 'list'}})

    db.SheinItem.updateOne({_id: 3}, {$set:{category: 'other'}})
    db.SheinItem.updateOne({_id: 5}, {$set:{category: 'electronics'}})
    db.SheinItem.updateOne({_id: 8}, {$set:{category: 'other'}})
    db.SheinItem.updateOne({_id: 9}, {$set:{category: 'other'}})
    db.SheinItem.updateOne({_id: 11}, {$set:{category: 'other'}})

## Auto-Generated Field for MongoDB using Spring Boot

[Auto-Generated Field for MongoDB using Spring Boot]<https://www.baeldung.com/spring-boot-mongodb-auto-generated-field>

1. In service tier, `AbstractMongoEventListener<SheinItem>` is used to set new document id automatically.
2. Do **NOT** use `spring.data.mongodb.uri: mongodb://ENC(zjpIoh+5DcY+QGRUOxINeA==):ENC(DIM8/d6v+5pS57wfdRfgaA==)@localhost:27017/lukedb`, because `jasypt` does not work this way.

    /**
    * !!! Trick !!!
    * `AbstractMongoEventListener<Person>` is used to set new document id automatically.
    * public class PersonModelListener extends AbstractMongoEventListener<SheinItem>
    */
    public SheinItem insertSheinItem(SheinItem item) {
        // Long seq = this.personSeqGeneratorService.generateSequence(Person.SEQ_NAME);   <=== not needed with `AbstractMongoEventListener<Person>`
        // person.setId(seq);   <=== not needed with `AbstractMongoEventListener<Person>`

        if (item == null) {
            return null;
        }

        return this.sheinItemRepository.insert(item);
    }

## `key` with and without quotes

1. JSON's specification demands key names to be surrounded by double quotes.
2. Mongo shell, which is actually a Javascript interpreter, is more flexible and allows key names (field names and operators) without them, as the language does.

### Example

    db.products.insertMany([
      { item: "card", qty: 15 },
      { "item": "stamps" , 'qty': 30 }
    ]);

    db.products.find({item: 'card'})
    db.products.find({qty: 30}).pretty()
