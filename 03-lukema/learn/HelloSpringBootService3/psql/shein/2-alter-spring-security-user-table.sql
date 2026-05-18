ALTER TABLE spring_security_user
    ADD COLUMN first_name    VARCHAR(60),
    ADD COLUMN last_name     VARCHAR(60),
    ADD COLUMN phone         VARCHAR(20),
    ADD COLUMN business_name VARCHAR(60),
    ADD COLUMN country_id    BIGINT,
    ADD COLUMN is_buy_only   BOOLEAN;


ALTER TABLE spring_security_user
    ADD CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES country(id);

ALTER TABLE spring_security_user
    ADD CONSTRAINT unique_username UNIQUE (username);

ALTER TABLE spring_security_user
    ADD CONSTRAINT unique_phone UNIQUE (phone);
