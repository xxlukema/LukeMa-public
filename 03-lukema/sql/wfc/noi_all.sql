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
            Property_Id IN (804,816,904)
        AND Statement_Period = 2011
        AND Data_Type = 'YTD') ytd
ON
    Fs.Property_Id = Ytd.Property_Id
WHERE
    Fs.Property_Id IN (804,816,904)
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
            Property_Id IN (804,816,904)
        AND Statement_Period = 2011
        AND Data_Type = 'AN') an
ON
    Fs.Property_Id = an.Property_Id
WHERE
    Fs.Property_Id IN (804,816,904)
AND Fs.Statement_Period = 2011
AND Fs.Data_Type = 'YTD'