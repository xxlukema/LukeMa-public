
0. Start mysql:

      mysqld.exe

   Stop mysql:

      mysqladmin -u root -p shutdown

   quite mysql:

      mysql>\q

0. root user

   root has no passwd upon install.

      mysql -u root -p <databaseName>

      mysql --user=monty --password=guess db_name
      mysql --user=monty --password db_name
      mysql -u monty -pguess db_name
      mysql -u monty -p db_name


1. Create super usr: luke passwd: luke

      GRANT ALL PRIVILEGES ON *.* TO 'luke'@'localhost'
         IDENTIFIED BY 'luke' WITH GRANT OPTION;

      or

      GRANT ALL PRIVILEGES ON *.* TO 'luke'@'%'
         IDENTIFIED BY 'luke' WITH GRANT OPTION;

      or

      GRANT USAGE ON *.* TO 'luke'@'localhost'

      select user();


1.5 DELETE USER:

         delete from user where user='luke'; 
         FLUSH PRIVILEGES;


2. Change root passwd

      mysqladmin -u root password 'new-password'

      or

      SET PASSWORD FOR 'root'@'localhost' = PASSWORD('secret_password');

3. Specify database

      use mysql

4. Show database

      SHOW DATABASES;

5. Useful commands


      CREATE DATABASE luke_db;
      SHOW TABLES;

      CREATE TABLE LukeTest (id integer, fname VARCHAR(20), lname VARCHAR(20));

      DESCRIBE LukeTest; or DESC LukeTest;

      SELECT VERSION(), CURRENT_DATE;
      select curdate() from dual;
      select curdate();
      SELECT 1 IS NULL, 1 IS NOT NULL;
      SELECT IFNULL(1,0);


6. Load data

     LukeTest.txt containing one record per line, with values separated 
     by tabs, and given in the order in which the columns were listed in 
     the CREATE TABLE statement. For missing values, you can use NULL 
     values(\N backslash, capital-N).

     1	'luke 1'	'ma 2'
     2	'luke 2'	\N


      LOAD DATA LOCAL INFILE 'LukeTest.txt' INTO TABLE lukeTest;


7. Help

      HELP LOAD DATA
      HELP INSERT

8. Insert

      INSERT INTO LukeTest VALUES (3, 'luke 3', NULL);

9. Disable autocommit

      MySql sets auto commit to true by default. To disable autocommit:

      SET AUTOCOMMIT=0; 

      If you want to switch from AUTOCOMMIT mode for one series of statements, 
      you can use the START TRANSACTION or BEGIN or BEGIN WORK statement.

      SAVEPOINT identifier
      ROLLBACK [WORK] TO SAVEPOINT identifier
      RELEASE SAVEPOINT identifier


      mysql --user=luke --password=luke test

10. Install MySQL as windows service:

     mysqld --install

11. Start MySQL service:

     NET START MySQL

12. Stop MySQL service:

     NET STOP MySQL

13. Remove MySQL service:

     NET STOP MySQL
     mysqld --remove

14. Set auto commit off:

     SET AUTOCOMMIT = 0;

15. Select for update:

     15.1 Set autocommit off or use START TRANSACTION;
     15.2 select id, name from company where id = 2 or id = 3 for update;
     15.3 Use commit or rollback to end select for update;

16. DROP and CREATE database:

    DROP DATABASE test;
    CREATE DATABASE test;



