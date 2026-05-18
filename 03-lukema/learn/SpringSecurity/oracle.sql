
CREATE TABLE
    USERS
    (
        USER_ID INTEGER NOT NULL,
        USERNAME VARCHAR2(20) NOT NULL,
        PASSWORD VARCHAR2(20) NOT NULL,
        ENABLED INTEGER,
        PRIMARY KEY (USER_ID)
    );

CREATE TABLE
    LUKE.user_roles
    (
        user_role_id INTEGER NOT NULL,
        user_id INTEGER NOT NULL,
        authority VARCHAR2(20) NOT NULL,
        PRIMARY KEY (user_role_id),
        CONSTRAINT user_role_fk1 FOREIGN KEY (user_id) REFERENCES LUKE.USERS (user_id)
    );
    
    
INSERT INTO users (USER_ID, USERNAME, PASSWORD, ENABLED)
VALUES (100, 'mkyong', '123456', 1);
 
INSERT INTO user_roles (USER_ROLE_ID, USER_ID, AUTHORITY)
VALUES (1, 100, 'ROLE_USER');

INSERT INTO users (USER_ID, USERNAME, PASSWORD, ENABLED)
VALUES (101, 'lukema', '123456', 1);
 
INSERT INTO user_roles (USER_ROLE_ID, USER_ID, AUTHORITY)
VALUES (2, 101, 'ROLE_USER');

INSERT INTO user_roles (USER_ROLE_ID, USER_ID, AUTHORITY)
VALUES (3, 101, 'ROLE_ADMIN');
