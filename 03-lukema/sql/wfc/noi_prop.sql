SELECT
    *
FROM
    T_CMSA_PROPERTY
WHERE
    PROPERTY_ID IN
    (
        /** 336 PROPERTY_IDs **/
        SELECT
            PROPERTY_ID
        FROM
            T_CMSA_LOAN_PROPERTY
        WHERE
            LOAN_ID IN
            (
                SELECT
                    LOAN_ID
                FROM
                    T_CMSA_POOL
                WHERE
                    INVESTOR_ID IN
                    (
                        /** 3 Inverstor_IDs **/
                        SELECT
                            INVESTOR_ID
                        FROM
                            T_CMSA_INVESTOR
                        WHERE
                            CLOSING_DT >= to_date('2011', 'yyyy')
                            /** 3 Inverstor_IDs **/
                    ))
            /** 336 PROPERTY_IDs **/
    )