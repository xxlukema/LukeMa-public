SELECT
    pool.INVESTOR_ID,
    i.CLOSING_DT              AS securitization_Date,
    i.TRANSACTION_ID          AS Deal_Id,
    pool.LOAN_ID              AS Loan_Number,
    lp.PROSPECTUS_PROPERTY_ID AS Prosup,
    lp.PROPERTY_SEQ_NO        AS Prosup_Ext,
    fs.Q3_NOI,
    fs.FYE_NOI,
    p.NOI_CONTRIBUTION AS "UW_NOI",
    CASE
        WHEN p.NOI_CONTRIBUTION IS NULL
        OR  p.NOI_CONTRIBUTION = 0
        THEN 'NoN'
        ELSE ROUND( (NVL(fs.Q3_NOI, 0) / p.NOI_CONTRIBUTION - 1) * 100, 2) || '%'
    END AS "Variance_Q3_vs_UW",
    CASE
        WHEN p.NOI_CONTRIBUTION IS NULL
        OR  p.NOI_CONTRIBUTION = 0
        THEN 'NoN'
        ELSE ROUND( (NVL(fs.FYE_NOI, 0) / p.NOI_CONTRIBUTION - 1) * 100, 2) || '%'
    END AS "Variance_FYE_vs_UW"
FROM
    T_CMSA_PROPERTY p,
    (
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
        AND Fs.Data_Type = 'YTD' ) fs, -- joined
    T_CMSA_POOL pool,
    T_CMSA_LOAN_PROPERTY lp,
    T_CMSA_INVESTOR i
WHERE
    p.PROPERTY_ID = fs.PROPERTY_ID
AND pool.LOAN_ID = lp.LOAN_ID
AND lp.PROPERTY_ID = p.PROPERTY_ID
AND fs.PROPERTY_ID = p.PROPERTY_ID
AND i.INVESTOR_ID = pool.INVESTOR_ID
AND i.CLOSING_DT >= to_date('2011', 'yyyy')
    --AND fs.Statement_Period = 2011
ORDER BY
    1,2,3,4,5,6,7