CREATE TABLE
    category
    (
        id            BIGINT NOT NULL,
        insert_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        last_update_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT now(),

        name CHARACTER VARYING(50) NOT NULL,

        PRIMARY KEY (id)
    );

CREATE SEQUENCE category_pk_seq START 1;

