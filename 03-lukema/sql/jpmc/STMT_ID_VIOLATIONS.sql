SELECT
    'MO_COMBINED_STMT_RELATION' tname, count(*) violates
FROM
    MO_COMBINED_STMT_RELATION
WHERE
    STMT_ID NOT IN
    (
        SELECT
            STMT_ID
        FROM
            MO_COMBINED_STMT_SETUP
    )

UNION ALL

SELECT
    'MO_COMBINED_STMT_CONTACT', count(*)
FROM
    MO_COMBINED_STMT_CONTACT
WHERE
    STMT_ID NOT IN
    (
        SELECT
            STMT_ID
        FROM
            MO_COMBINED_STMT_SETUP
    )

UNION ALL

SELECT
    'MO_COMBINED_CONTACT_RELATION', count(*)
FROM
    MO_COMBINED_CONTACT_RELATION
WHERE
    STMT_ID NOT IN
    (
        SELECT
            STMT_ID
        FROM
            MO_COMBINED_STMT_SETUP
    );

SELECT
    *
FROM
    MO_COMBINED_STMT_CONTACT
WHERE
    STMT_ID NOT IN
    (
        SELECT
            STMT_ID
        FROM
            MO_COMBINED_STMT_SETUP
    )
