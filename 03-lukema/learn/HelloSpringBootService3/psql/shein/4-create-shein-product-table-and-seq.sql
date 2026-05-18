CREATE TABLE
    shein_product
    (
        id BIGINT NOT NULL,
        insert_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        last_update_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        name CHARACTER VARYING(255) NOT NULL,
        description CHARACTER VARYING(255),
        image_url_prefix CHARACTER VARYING(255) NOT NULL,
        price NUMERIC(10, 2) NOT NULL,
        PRIMARY KEY (id)
    );



CREATE SEQUENCE shein_product_pk_seq START 1;

