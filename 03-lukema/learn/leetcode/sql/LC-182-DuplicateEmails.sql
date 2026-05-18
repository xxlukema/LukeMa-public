# 

# Runtime: 479 ms, faster than 42.39% of MySQL online submissions for Duplicate Emails.
# Memory Usage: 0B, less than 100.00% of MySQL online submissions for Duplicate Emails.

------------------------------
-- For postgres
------------------------------
DELETE
FROM
    person as p1
WHERE
    p1.id NOT IN
    (   SELECT
            MIN(p2.id) AS id
        FROM
            person AS p2
        GROUP BY
            p2.email );

---------------------------
-- For MySQL
---------------------------
DELETE
FROM
    person AS p1
WHERE
    p1.id NOT IN
    (   SELECT 
            id 
        FROM 
            (   SELECT
                    MIN(p2.id) AS id
                FROM
                    person AS p2
                GROUP BY
                    p2.email ) AS sub);
--------------------------
--------------------------
