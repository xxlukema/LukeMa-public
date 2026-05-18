SELECT
    customergroup_id,
    COUNT(*)
FROM
    directorynumber
GROUP BY
    customergroup_id;