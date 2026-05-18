-- DROP TABLE
--     shein_product;


-- ALTER TABLE
--     shein_product RENAME COLUMN image_path TO image_url_prefix;


CREATE TABLE
    shein_product_image
    (
        id BIGINT NOT NULL,
        insert_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        last_update_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        file_name CHARACTER VARYING(255) NOT NULL,
        product_id BIGINT REFERENCES shein_product (id),
        PRIMARY KEY (id)
    );

CREATE SEQUENCE shein_product_image_pk_seq START 1;
