CREATE TABLE
    spring_security_user
    (
        user_id BIGINT NOT NULL,
        insert_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        last_update_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        enabled BOOLEAN NOT NULL,
        password CHARACTER VARYING(255),
        username CHARACTER VARYING(255) NOT NULL,
        PRIMARY KEY (user_id)
    );
