SELECT
    i.*
FROM
    Investor i,
    Client c,
    CMGClient cc
WHERE
    i.ClientID = c.ClientID
AND c.CMGClientId = cc.CMGClientId
ORDER BY
    CAST(i.InvestorNumber AS INT);
--
/*
SELECT
    *
FROM
    Loan
WHERE
    InvestorID = 28
    */