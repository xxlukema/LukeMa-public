CREATE
    TABLE range_part
    (
        rid NUMBER,
        col1 VARCHAR2(10),
        col2 VARCHAR2(100)
    )
    PARTITION BY RANGE
    (
        rid
    )
    (
        partition p1 VALUES LESS THAN (1000),
        partition p3 VALUES LESS THAN (3000),
        partition pm VALUES LESS THAN (MAXVALUE)
    );