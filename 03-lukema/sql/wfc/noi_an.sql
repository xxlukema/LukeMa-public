SELECT
    Fs.Property_Id,
    Fs.Data_Type AS AN,
    fs.Noi       AS AN_NOI,
    Ytd.Ytd,
    YTD.YTD_NOI
FROM
    T_Cmsa_Financial_Summary Fs
LEFT JOIN
    (
        SELECT
            PROPERTY_ID,
            DATA_TYPE AS YTD,
            NOI       AS YTD_NOI
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