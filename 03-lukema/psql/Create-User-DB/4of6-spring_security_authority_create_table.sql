CREATE TABLE
    spring_security_authority
    (
        authority_id BIGINT NOT NULL,
        insert_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        last_update_timestamp TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
        authority CHARACTER VARYING(255),
        username CHARACTER VARYING(255) NOT NULL,
        user_id BIGINT,
        PRIMARY KEY (authority_id),
        CONSTRAINT fk22oxboxgr2g3livdnryxsbi0p FOREIGN KEY (user_id) REFERENCES
        "spring_security_user" ("user_id")
    );
