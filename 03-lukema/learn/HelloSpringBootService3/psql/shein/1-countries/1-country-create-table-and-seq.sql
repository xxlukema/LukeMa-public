CREATE TABLE
    country
    (
        id            BIGINT NOT NULL,
        insert_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        last_update_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT now(),

        code          CHAR(20) NOT NULL,
        name          VARCHAR(40) NOT NULL,

        PRIMARY KEY (id)
    );

CREATE SEQUENCE country_pk_seq START 322;
