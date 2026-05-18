WITH
    pfs_Signed_StatusDate
    (
        StatusDate,
        PropertyID,
        CalendarID,
        UserID
    ) AS
    (
        SELECT
            MIN(statusPfs.StatusDate),
            statusPfs.PropertyID,
            statusPfs.CalendarID,
            statusPfs.UserID
        FROM
            Client cmg WITH(NOLOCK)
        INNER JOIN
            Calendar ca WITH(NOLOCK)
        ON
            ca.ClientID = cmg.ClientID
        INNER JOIN
            PropertyFinancialStatusHistory statusPfs WITH(NOLOCK)
        ON
            statusPfs.CalendarID = ca.CalendarID
        WHERE
            statusPfs.FinancialTaskID = 5
        AND cmg.CMGClientId = 1
        AND dbo.fncCalIDToCal(ca.CalendarId) =dbo.fncCalIDToCal(111)
        GROUP BY
            statusPfs.PropertyID,
            statusPfs.CalendarID,
            statusPfs.UserID
    )
    ,
    pfs_Pushed_StatusDate
    (
        StatusDate,
        PropertyID,
        CalendarID
    ) AS
    (
        SELECT
            MAX(statusPfs.StatusDate),
            statusPfs.PropertyID,
            statusPfs.CalendarID
        FROM
            pfs_Signed_StatusDate cte WITH(NOLOCK)
        INNER JOIN
            PropertyFinancialStatusHistory statusPfs WITH(NOLOCK)
        ON
            statusPfs.PropertyID = cte.propertyId
        AND statusPfs.CalendarID = cte.CalendarID
        WHERE
            statusPfs.FinancialTaskID = 6
        GROUP BY
            statusPfs.PropertyID,
            statusPfs.CalendarID
    )
SELECT
    P.PropertyId,
    pfs.CalendarID,
    ISNULL(cd.firstname, ' ') + ' ' + ISNULL(cd.Lastname,' ')             AS CreditAnalyst ,
    ISNULL(Reviewer.firstname, ' ') + ' ' + ISNULL(Reviewer.Lastname,' ') AS ReviewerID ,
    ISNULL(am.firstname, ' ') + ' ' + ISNULL(am.Lastname,' ')             AS AssetManager ,
    ISNULL(pm.firstname, ' ') + ' ' + ISNULL(pm.Lastname,' ')             AS PSRTeamLead ,
    I.InvestorNumber ,
    I.InvestorLongName ,
    L.LoanNumber ,
    P.PropertyNumber ,
    PT.PropertyType ,
    P.PropertyName ,
    P.propAddr1 AS PropertyStreet ,
    CASE
        WHEN FND.FSRequestedItem = 1
        THEN 'YES'
        ELSE 'NO'
    END AS FSRequestedItem ,
    CASE
        WHEN FND.RRRequestedItem = 1
        THEN 'YES'
        ELSE 'NO'
    END AS RRRequestedItem ,
    I.SecuritizationDate ,
    CASE
        WHEN P.Top10 = 1
        THEN 'YES'
        ELSE 'NO'
    END AS Top10 ,
    CASE
        WHEN P.Top20 = 1
        THEN 'YES'
        ELSE 'NO'
    END AS Top20 ,
    CASE
        WHEN P.WatchList = 1
        THEN 'YES'
        ELSE 'NO'
    END                       AS WatchList ,
    L.CurrentPrincipleBalance AS CurrentPrincipalBalance ,
    (
        SELECT
            LL.loannumber
        FROM
            Loan LL WITH(NOLOCK)
        WHERE
            LL.LoanID = L.LeadLoanID) AS LeadLoanFlag ,
    L.LoanNoteType ,
    CONVERT(VARCHAR(10), ASG.AssignDate, 101) AS FSAAssignmentDate ,
    FND.FSCollected                           AS FSWFBReceived ,
    FND.RRCollected                           AS RRWFBReceived ,
    CASE
        WHEN FND.RRImageDate IS NOT NULL
        THEN 'YES'
        ELSE 'NO'
    END AS RRImage ,
    CASE
        WHEN FND.FSImageDate IS NOT NULL
        THEN 'YES'
        ELSE 'NO'
    END               AS FSImage ,
    FND.OSARCollected AS OSARWFBReceived ,
    FND.OSARImageDate ,
    COMP.COMPANYNAME AS FSAAssignedBusinessUnit ,
    CASE
        WHEN OA.IsFlagForReview = 1
        THEN 'YES'
        ELSE 'NO'
    END AS AnalystReviewRequired ,
    CASE
        WHEN OA.ApprovalStatusId = 1
        THEN 'YES'
        ELSE 'NO'
    END             AS ApprovalStatus ,
    OA.ApprovedDate AS StatusModifiedDate ,
    OA.ApprovedBy   AS StatusModifiedBy ,
    CASE
        WHEN L.SubServiced = 'Y'
        THEN 'YES'
        ELSE 'NO'
    END               AS SubServiced ,
    L.SubServicerName AS SubServicerCode ,
    CASE
        WHEN L.CASHFLAG ='1'
        THEN 'CASH'
        WHEN L.CASHFLAG ='L'
        THEN 'NON-CASH'
        ELSE 'NON-SUBSERVICED'
    END           AS 'CashNonCashSubservicer' ,
    P.SubServicer AS SubServicerName ,
    L.MaturityDate ,
    '12/31/2011'                  AS CalendarPeriod ,
    P.StatusCode                  AS PropertyStatusCode ,
    P.PropertyStatusEffectiveDate AS PropertyStatusCodeEffectiveDate ,
    I.FirstReportingPeriodYear ,
    I.FirstReportingPeriodQuarter ,
    L.FSRRequiredQuarterly ,
    L.FSRRequiredAnnually ,
    I.OSARRequiredFlagAnnually ,
    I.OSARRequiredFlagQuarterly ,
    FT.FinancialTask                           AS CurrentFSAWorkflowStatus ,
    CONVERT(VARCHAR(10), PFS.LastUpdated, 101) AS CurrentFSAWorkflowStatusDate ,
    ISNULL(POV.CurrentAllocatedLoanPercent, 0) AS CurrentAllocatedLoanPercent ,
    CASE
        WHEN FL.IsIssue = 1
        THEN 'YES'
        ELSE 'NO'
    END                                        AS FSAIssueFlag ,
    CONVERT(VARCHAR(10), FL.IssueCloseDt, 101) AS FSAIssueCloseDate ,
    CONVERT(VARCHAR(10), FL.IssueOpenDt, 101)  AS FSAIssueOpenDate ,
    DATEDIFF(dd, CONVERT(VARCHAR(10), FL.IssueCloseDt, 101), CONVERT(VARCHAR(10), FL.IssueOpenDt,
    101))                                     AS TotalDaysOpen ,
    CONVERT(VARCHAR(10), cte.StatusDate, 101) AS FirstSpreadCompleteDate,
    CONVERT(VARCHAR(10), pD.StatusDate, 101)  AS FSADataWarehouseUploadDate ,
    CASE L.SpecialServiced
        WHEN 'Y'
        THEN 'YES'
        ELSE 'NO'
    END AS SpeciallyServiced ,
    CASE
        WHEN I.NonPerformingLoan = 1
        THEN 'YES'
        ELSE 'NO'
    END AS MSNonPerformingLoan ,
    CASE
        WHEN I.SSNonPerformingLoan = 1
        THEN 'YES'
        ELSE 'NO'
    END AS SSNonPerformingLoan ,
    CASE I.IsReo
        WHEN 'Y'
        THEN 'YES'
        ELSE 'NO'
    END AS MSREO ,
    CASE
        WHEN I.IsSSReo = 1
        THEN 'YES'
        ELSE 'NO'
    END AS SSREO ,
    L.SpecialServicer ,
    '' AS OSARReportingRequiredFlag ,
    substring(I.InvestorDescription, charindex(':', I.InvestorDescription) + 1, LEN
    (I.InvestorDescription) - charindex(':', I.InvestorDescription)) AS DealID ,
    P.ProspectusIDNumber ,
    CASE
        WHEN L.Defeased = 1
        THEN 'YES'
        ELSE 'NO'
    END                                   AS Defeased ,
    ''                                    AS CTL ,
    L.RelatedMortgageLoanAggregateBalance AS AggregateLoanBalance ,
    CASE
        WHEN L.CASHFLAG ='1'
        THEN CONVERT(VARCHAR(10), ASG.AssignDate, 101)
    END AS 'SubOSARAssignmentDate' ,
    (
        SELECT
            TOP 1 ISNULL(aui.firstname,'') + ' ' + ISNULL(aui.lastname,'')
        FROM
            pfs_Signed_StatusDate pss WITH(NOLOCK)
        INNER JOIN
            appuserinfo aui WITH(NOLOCK)
        ON
            aui.UserID = pss.UserID
        WHERE
            pss.PropertyID = P.PropertyID
        AND pss.CalendarID = 111 ) AS FirstSpreadCompletedBy
FROM
    PROPERTY P WITH(NOLOCK)
INNER JOIN
    LOAN L WITH(NOLOCK)
ON
    P.LOANID = L.LOANID
INNER JOIN
    INVESTOR I WITH(NOLOCK)
ON
    L.INVESTORID = I.INVESTORID
INNER JOIN
    PROPERTYTYPES PT WITH(NOLOCK)
ON
    PT.PropTypeCD = P.PropTypeCD
LEFT OUTER JOIN
    PROPERTYOVERVIEW POV WITH(NOLOCK)
ON
    P.PROPERTYID = POV.PROPERTYID
AND dbo.fncCalIDToCal(POV.CalendarId) ='12/31/2011'
LEFT OUTER JOIN
    PROPERTYFINANCIALSTATUS PFS WITH(NOLOCK)
ON
    PFS.PROPERTYID = P.PROPERTYID
AND pfs.CalendarID = POV.CalendarID
LEFT OUTER JOIN
    FINANCIALTASKS FT WITH(NOLOCK)
ON
    FT.FINANCIALTASKID = PFS.FINANCIALTASKID
LEFT OUTER JOIN
    pfs_Signed_StatusDate cte
ON
    P.PropertyID =cte.PropertyId
LEFT OUTER JOIN
    ASSIGNMENT ASG WITH(NOLOCK)
ON
    ASG.CalendarId = POV.CalendarId
AND ASG.PROPERTYID = POV.PROPERTYID
LEFT OUTER JOIN
    OSARAPPROVAL OA WITH(NOLOCK)
ON
    OA.AssignmentID = ASG.AssignmentID
LEFT OUTER JOIN
    FSIssueLog FL WITH(NOLOCK)
ON
    FL.ASSIGNMENTID = ASG.ASSIGNMENTID
LEFT OUTER JOIN
    COMPANY COMP WITH(NOLOCK)
ON
    COMP.COMPANYID = ASG.BUID
LEFT OUTER JOIN
    FILENETDOCUMENTS FND WITH(NOLOCK)
ON
    FND.CalendarId = PFS.CalendarId
AND FND.PropertyId = PFS.PropertyID
LEFT OUTER JOIN
    pfs_Pushed_StatusDate pD
ON
    pD.PropertyId = PFS.PropertyID
AND pD.CalendarId = pfs.calendarId
LEFT OUTER JOIN
    AppUserInfo am
ON
    am.UserID = I.AssetManager
LEFT OUTER JOIN
    AppUserInfo pm
ON
    pm.userId = I.PSRTeamLead
LEFT OUTER JOIN
    AppUserInfo reviewer
ON
    reviewer.UserID = Oa.ReviewerID
LEFT OUTER JOIN
    AppUserInfo cd
ON
    cd.UserID = I.CreditAnalyst
WHERE
    I.DealType = 'S'
AND I.osarrequiredFlagQuarterly = 'Y'
AND I.osarrequiredflagannually = 'Y'
AND P.bActiveProp = 1
ORDER BY
    I.InvestorNumber ,
    L.LoanNumber ,
    P.PropertyNumber