SELECT
    LOAN_ID,
    PERIODIC_YEAR,
    PERIODIC_MONTH,
    DELINQUENT_COMMENTS
FROM
    T_CMSA_PERIODIC_LOAN
WHERE
    LOAN_ID = 310900868
AND DELINQUENT_COMMENTS IS NOT NULL
ORDER BY
    ( PERIODIC_YEAR|| lpad(PERIODIC_MONTH, 2, '0')) DESC;
--AND DELINQUENT_COMMENTS LIKE
 --   '%demand letter was sent on% The property management company was replaced in June.  Third % possible foreclosure.%'