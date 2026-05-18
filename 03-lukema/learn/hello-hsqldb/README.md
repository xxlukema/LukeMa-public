# HyperSQL

<https://nvd.nist.gov/vuln/detail/CVE-2022-41853>

<https://www.hsqldb.org/doc/2.0/guide/running-chapt.html>

    mvn exec:java@file

    mvn exec:java@mem
    
    mvn exec:java@ui

    mvn antrun:run@delete-hsqldb

    SET DATABASE SQL SYNTAX PGS TRUE

    CREATE SCHEMA BMS AUTHORIZATION SA;
   -- ALTER USER SA SET INITIAL SCHEMA BMS;

    select current_timestamp
    SELECT * FROM INFORMATION_SCHEMA.SYSTEM_USERS
    SELECT * FROM INFORMATION_SCHEMA.SYSTEM_SCHEMAS;
    SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES
    SELECT * FROM INFORMATION_SCHEMA.TABLES

## Select

<https://hsqldb.org/doc/guide/compatibility-chapt.html>

    # Shutdown
    SHUTDOWN

    SELECT current_date AS today, current_time AS now FROM (VALUES(0))
    SELECT * FROM (VALUES (current_timestamp)) v(date)
    SELECT * as date FROM (VALUES (current_timestamp))
    SELECT c1 as date FROM (VALUES (current_timestamp))

    # PostgeSQL
    SET DATABASE SQL SYNTAX PGS TRUE
    select current_timestamp
    SELECT * FROM INFORMATION_SCHEMA.SYSTEM_USERS
    SELECT * FROM INFORMATION_SCHEMA.SYSTEM_SCHEMAS;
    SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES
    SELECT * FROM INFORMATION_SCHEMA.TABLES

    # Oracle
    SET DATABASE SQL SYNTAX ORA TRUE
    select systimestamp from dual

    SET DATABASE SQL SYNTAX MYS TRUE

    SET DATABASE SQL AVG SCALE

## Browser

    # java -cp hsqldb.jar org.hsqldb.util.DatabaseManagerSwing

    mvn exec:java -Dexec.mainClass="org.hsqldb.util.DatabaseManagerSwing"

## Server

### Conventional (Most Commonly Used)

    # To start the server with one (default) database with files named "mydb.*" and the public name of "xdb".
    # The public name hides the file names from users.

    # java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:mydb --dbname.0 xdb

    # mem
    mvn exec:java -Dexec.mainClass=org.hsqldb.server.Server -Dexec.args="--database.0 mem:mydb --dbname.0 xdb"

    # file
    mvn exec:java -Dexec.mainClass=org.hsqldb.server.Server -Dexec.args="--database.0 file:mydb --dbname.0 xdb" -Dexec.workingdir="/target"

    Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");

### Web based (Rare)

When the computer hosting the database server is restricted to the HTTP protocol. The only reason for
using this method of access is restrictions imposed by firewalls on the client or server machines and
it should not be used where there are no such restrictions.

    # java -cp ../lib/hsqldb.jar org.hsqldb.server.WebServer --database.0 file:mydb --dbname.0 xdb

    mvn exec:java -Dexec.mainClass=org.hsqldb.server.WebServer -Dexec.args="--database.0 file:mydb --dbname.0 xdb"

    Connection c = DriverManager.getConnection("jdbc:hsqldb:http://localhost/xdb", "SA", "");

## Connections

    Connection c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
    Connection c = DriverManager.getConnection("jdbc:hsqldb:file:/opt/db/testdb", "SA", "");
    Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
    Connection c = DriverManager.getConnection("jdbc:hsqldb:res:org.my.path.resdb", "SA", "");

## JDBC

    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver" );
    } catch (Exception e) {
        System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
        e.printStackTrace();
        return;
    }
   
    Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");

## `https` and `hsqls`

    Connection c = DriverManager.getConnection("jdbc:hsqldb:hsqls://localhost/xdb", "SA", "");
    Connection c = DriverManager.getConnection("jdbc:hsqldb:https://localhost/xdb", "SA", "");

## Shutdown

    Connection c = DriverManager.getConnection("jdbc:hsqldb:file:/opt/db/testdb;shutdown=true", "SA", "");

## Create

When a server instance is started, or when a connection is made to an `in-process` database, a new, empty database is created if no database exists at the given path.

With HyperSQL 2.0 the user name and password that are specified for the connection are used for the new database.
Both the user name and password are **case-sensitive**. (The exception is the default **SA** user, which is not case-sensitive).
If no user name or password is specified, the default **SA** user and an **empty** password are used.

    Connection c = DriverManager.getConnection("jdbc:hsqldb:file:/opt/db/testdb;ifexists=true", "SA", "");

## Type of dbs

### Types of catalog data

- mem: stored entirely in RAM - without any persistence beyond the JVM process's life
- file: stored in file system
- res: stored in a Java resource, such as a Jar and always read-only

All-in-memory mem: catalogs can be used for test data or as sophisticated caches for an application. These databases do not have any files.

A file: catalog consists of between 2 to 6 files, all named the same but with different extensions, located in the same directory.
For example, the database named "testdb" consists of the following files:

- testdb.properties
- testdb.script
- testdb.log
- testdb.data
- testdb.backup
- testdb.lobs
