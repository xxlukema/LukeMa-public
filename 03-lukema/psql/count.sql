SELECT
    TYPE,
    count(0)
FROM
    device
GROUP BY
    TYPE
ORDER BY
    TYPE DESC