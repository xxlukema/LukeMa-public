
psql -U luke -d mydb

psql -U luke -d mydb -f somefile.sql

CREATE USER luke WITH ENCRYPTED PASSWORD 'luke';
CREATE DATABASE mydb OWNER luke;


