DROP TABLE IF EXISTS people;

CREATE TABLE people  (
    person_id BIGINT NOT NULL, -- PRIMARY KEY,
    first_name CHARACTER VARYING(20),
    last_name CHARACTER VARYING(20)
);

