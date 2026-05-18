WITH
    sub AS
    (
        SELECT
            p.*
        FROM
            Property p,
            Loan l,
            Investor i,
            Client c,
            CMGClient g
        WHERE
            g.CMGClientId = c.CMGClientId
        AND c.ClientID = i.ClientID
        AND l.InvestorID = i.InvestorID
        AND p.LoanID = l.LoanID
        AND g.CMGClientId = 1
    )
SELECT
    COUNT(*) as Records
FROM
    sub