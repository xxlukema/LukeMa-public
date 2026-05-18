WITH
    bulk_assign AS
    (
        SELECT
            cli.ClientID,
            inv.InvestorID,
            inv.OSARRequiredFlagAnnually,
            inv.OSARRequiredFlagQuarterly,
            inv.NonPerformingLoan,
            inv.PerformingLoan,
            inv.IsReo,
            inv.IsSSReo,
            inv.SSNonPerformingLoan,
            inv.SSPerformingLoan,
            lo.LoanNumber,
            lo.LeadLoanID,
            lo.SpecialServiced,
            lo.SubServiced,
            lo.CashFlag,
            lo.FSRRequiredAnnually,
            lo.FSRRequiredQuarterly,
            lo.SpecialServicer,
            pro.PropertyID,
            pro.Top10,
            pro.Top20,
            pro.WatchList,
            pro.StatusCode,
            pro.RRARequiredOverride,
            proty.PropTypeCD,
            cal.CalendarID,
            cal.Calendar,
            fnd.FSImageDate,
            fnd.RRImageDate,
            fnd.OSARImageDate
        FROM
            CMGClient cmgC
        INNER JOIN
            Client cli
        ON
            cmgC.CMGClientID = cli.CMGClientID
        INNER JOIN
            Investor inv
        ON
            cli.ClientID = inv.ClientID
        INNER JOIN
            Loan lo
        ON
            inv.InvestorID = lo.InvestorID
        INNER JOIN
            Property pro
        ON
            lo.LoanID = pro.LoanID
        INNER JOIN
            PropertyTypes proty
        ON
            pro.PropTypeCD = proty.PropTypeCD
        INNER JOIN
            Calendar cal
        ON
            lo.ClientID = cal.ClientID
        LEFT OUTER JOIN
            FileNetDocuments fnd
        ON
            pro.PropertyId = fnd.PropertyID
        AND cal.CalendarID = fnd.CalendarID
        LEFT OUTER JOIN
            Assignment assign
        ON
            pro.PropertyID = assign.PropertyID
        AND assign.CalendarID = cal.CalendarID
        --
        where cal.CalendarID = 239
        and inv.ClientID = 18
        --
    )
--
/**
SELECT
    COUNT(*)
FROM
    bulk_assign;
    */
--
Select Count(PropertyID) from bulk_assign Where FSImageDate Is Not Null And RRImageDate Is Not Null ;