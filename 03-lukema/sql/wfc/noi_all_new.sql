SELECT
    Fs.Property_Id,
    Ytd.Q3,
    YTD.Q3_NOI,
    Fs.Data_Type AS FYE,
    fs.Noi       AS FYE_NOI
FROM
    T_Cmsa_Financial_Summary Fs
LEFT JOIN
    (
        SELECT
            PROPERTY_ID,
            DATA_TYPE AS Q3,
            NOI       AS Q3_NOI
        FROM
            T_CMSA_FINANCIAL_SUMMARY
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
        AND Statement_Period = 2011
        AND Data_Type = 'YTD') ytd
ON
    Fs.Property_Id = Ytd.Property_Id
WHERE
    Fs.Property_Id IN
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
AND Fs.Statement_Period = 2011
AND Fs.Data_Type = 'AN'
UNION
SELECT
    Fs.Property_Id,
    Fs.Data_Type AS Q3 ,
    fs.Noi       AS Q3_NOI,
    an.FYE       AS FYE,
    an.FYE_NOI   AS FYE_NOI
FROM
    T_Cmsa_Financial_Summary Fs
LEFT JOIN
    (
        SELECT
            PROPERTY_ID,
            DATA_TYPE AS FYE,
            NOI       AS FYE_NOI
        FROM
            T_CMSA_FINANCIAL_SUMMARY
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
        AND Statement_Period = 2011
        AND Data_Type = 'AN') an
ON
    Fs.Property_Id = an.Property_Id
WHERE
    Fs.Property_Id IN
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
AND Fs.Statement_Period = 2011
AND Fs.Data_Type = 'YTD'