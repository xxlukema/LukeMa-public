
CREATE TABLE
    luke.USERS
    (
        USER_ID INTEGER NOT NULL,
        USERNAME VARCHAR(20) NOT NULL,
        PASSWORD VARCHAR(200) NOT NULL,
        ENABLED INTEGER,
        PRIMARY KEY (USER_ID)
    );

CREATE TABLE
    luke.USER_ROLES
    (
        user_role_id INTEGER NOT NULL,
        user_id INTEGER NOT NULL,
        authority VARCHAR(20) NOT NULL,
        PRIMARY KEY (user_role_id),
        CONSTRAINT user_role_fk1 FOREIGN KEY (user_id) REFERENCES luke.USERS (user_id)
    );
    

-- Users
INSERT INTO luke.users (USER_ID, USERNAME, PASSWORD, ENABLED)
VALUES (100, 'admin', 'admin', 1);
 
INSERT INTO luke.users (USER_ID, USERNAME, PASSWORD, ENABLED)
VALUES (101, 'user', 'user', 1);

INSERT INTO luke.users (USER_ID, USERNAME, PASSWORD, ENABLED)
VALUES (102, 'xma', 'xma', 1);

-- Roles
INSERT INTO luke.user_roles (USER_ROLE_ID, USER_ID, AUTHORITY)
VALUES (1, 100, 'ROLE_ADMIN');

INSERT INTO luke.user_roles (USER_ROLE_ID, USER_ID, AUTHORITY)
VALUES (2, 101, 'ROLE_USER');

INSERT INTO luke.user_roles (USER_ROLE_ID, USER_ID, AUTHORITY)
VALUES (3, 102, 'ROLE_ADMIN');
 
INSERT INTO luke.user_roles (USER_ROLE_ID, USER_ID, AUTHORITY)
VALUES (4, 102, 'ROLE_USER');

---------------------------------
-- ALTER TABLE luke.users ALTER COLUMN password TYPE varchar(200);

UPDATE luke.users
SET password = '$2a$10$XCN1a0kjzUQevrbeyvWL.eBN8W3TdloiYZjuIKUxO48YHdONo4aHK'
WHERE username = 'admin';

UPDATE luke.users
SET password = '$2a$10$j06mvwb91EAgjoq1pilUUOk6.69gKhA7Jyh2.2BGkHYB2eGrgFjbG'
WHERE username = 'user';

UPDATE luke.users
SET password = '$2a$10$K/Ew7usNhg33mqzUrQqEwuVB/zLaEr/skuoj5S6KG3zSWq6lta.XO'
WHERE username in ('xma');

