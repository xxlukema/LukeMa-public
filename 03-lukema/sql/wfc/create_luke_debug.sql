DROP TABLE
    LUKE_DEBUG;
--
CREATE TABLE
    LUKE_DEBUG
    (
        ts TIMESTAMP,
        msg VARCHAR2(4000)
    );
--
INSERT
INTO
    luke_debug
    (
        ts,
        msg
    )
    VALUES
    (
        SYSDATE,
        'test' || ' 000' || SYSDATE
    );    
    