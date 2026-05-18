SELECT
    a.id,
    a.email
FROM
    person AS a
WHERE
    a.email IN
    (   SELECT
            b.email
        FROM
            person AS b
        GROUP BY
            b.email
        HAVING
            COUNT(b.email) > 1 );
----------------
----------------
DELETE
FROM
    person AS p1
WHERE
    p1.id NOT IN
    (   SELECT
            MIN(p2.id) AS id
        FROM
            person AS p2
        GROUP BY
            p2.email );
-----------------
-- For MySQL
-----------------
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
-------------
-------------