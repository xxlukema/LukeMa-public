--
-- Logon as postgres/postgres
-- Create User luke/luke
CREATE USER luke WITH PASSWORD 'luke';
ALTER USER luke VALID UNTIL 'infinity';
--
--
-- Create role admins
CREATE role admin WITH CREATEDB CREATEROLE;
--
--
-- Grant admin to luke
GRANT admin TO luke;
-- GRANT ALL PRIVILEGES ON DATABASE test TO luke;
--
--
-- Create database test
CREATE DATABASE test OWNER luke;
--
--
--
-------------------------------------------------------
-------------------------------------------------------
-------------------------------------------------------
CREATE SCHEMA luke; --- Do this after logon as luke
-- Set default schema
ALTER DATABASE test SET search_path TO luke;
SHOW search_path;

-- ALTER USER luke WITH SUPERUSER;
-- ALTER USER luke WITH NOSUPERUSER;
--
-- Test Connection
select current_date, 1 as num, 'hello world' as hello;
--
--
--
-------------------------------------------------------
-------------------------------------------------------
-------------------------------------------------------
-- http://www.mastertheboss.com/jboss-jbpm/jbpm5/jbpm-5-tutorial-first-example
-- For jBPM
-- Must be done with user postgres
CREATE USER jbpm WITH password 'jbpm';
ALTER USER jbpm VALID UNTIL 'infinity';
GRANT admin TO jbpm;
CREATE DATABASE jbpm OWNER jbpm;
-- DROP DATABASE jbpm;





