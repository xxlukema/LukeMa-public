
postgres/postgres

psql -U luke -d test

psql -U luke -d test -f somefile.sql

CREATE USER luke WITH ENCRYPTED PASSWORD 'luke';
CREATE DATABASE test OWNER luke;

\l

\du 

