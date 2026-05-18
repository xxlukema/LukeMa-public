SELECT
    Fs.Property_Id,
    an.AN     AS AN,
    an.AN_NOI AS AN_NOI,
    Fs.Data_Type ,
    fs.Noi
FROM
    T_Cmsa_Financial_Summary Fs
LEFT JOIN
    (
        SELECT
            PROPERTY_ID,
            DATA_TYPE AS AN,
            NOI       AS AN_NOI
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