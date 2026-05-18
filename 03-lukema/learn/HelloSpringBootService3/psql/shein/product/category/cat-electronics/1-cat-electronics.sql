CREATE TABLE
    cat_electr
    (
        id            BIGINT NOT NULL,
        insert_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        last_update_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT now(),

        name CHARACTER VARYING(50) NOT NULL,
        
        cat_id            BIGINT NOT NULL,

        PRIMARY KEY (id),
        FOREIGN KEY (cat_id) REFERENCES category(id)
    );

CREATE SEQUENCE cat_electr_pk_seq START 1;

