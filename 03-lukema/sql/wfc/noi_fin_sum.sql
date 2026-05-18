SELECT
    *
FROM
    T_Cmsa_Financial_Summary
WHERE
    Property_Id IN
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
                        SELECT
                            INVESTOR_ID
                        FROM
                            T_CMSA_INVESTOR
                        WHERE
                            CLOSING_DT >= to_date('2011', 'yyyy')))
            /** 336 PROPERTY_IDs **/
    )
AND Statement_Period = 2011
AND (
        Data_Type='AN'
    OR  Data_Type = 'YTD')