/* CREATE */
ALTER
PROCEDURE "dbo"."USP_ZZ_RptOSARWorkFlow" (@Client INT,
                                          @Calendar NVARCHAR(20))
AS
BEGIN
    SET NOCOUNT ON
    DECLARE
        @calendarPeriod DATETIME
        SET @calendarPeriod = dbo.fncCalIDToCal(@Calendar) ;
WITH
    pfs_Signed_StatusDate
    (
        StatusDate,
        PropertyID,
        CalendarID,
        UserID,
        id
    ) AS
    (
        SELECT
            *
        FROM
            (
                SELECT
                    statusPfs.StatusDate,
                    statusPfs.PropertyID,
                    statusPfs.CalendarID,
                    statusPfs.UserID ,
                    dense_rank()OVER (PARTITION BY statusPfs.PropertyID,statusPfs.CalendarID
                    ORDER BY statusPfs.StatusDate ASC ) AS FirstSpread
                FROM
                    Client cmg
                INNER JOIN
                    Calendar ca
                ON
                    ca.ClientID = cmg.ClientID
                INNER JOIN
                    PropertyFinancialStatusHistory statusPfs
                ON
                    statusPfs.CalendarID = ca.CalendarID
                WHERE
                    statusPfs.FinancialTaskID = 5
                AND cmg.CMGClientId = 1
                AND dbo.fncCalIDToCal(ca.CalendarId) =dbo.fncCalIDToCal(@Calendar)) cte1
        WHERE
            FirstSpread =1
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
            pfs_Signed_StatusDate cte
        INNER JOIN
            PropertyFinancialStatusHistory statusPfs
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
    END                                             AS RRRequestedItem ,
    CONVERT(VARCHAR(10), I.SecuritizationDate, 101) AS SecuritizationDate ,
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
    LL.loannumber             AS LeadLoanFlag ,
    L.LoanNoteType ,
    CASE
        WHEN ASG.DocTypeID =1
        THEN CONVERT(VARCHAR(10), ASG.AssignDate, 101)
        ELSE ''
    END                                                     AS FSAAssignmentDate ,
    ISNULL(CONVERT(VARCHAR(10), FND.FSImageDate , 101) ,'')   AS FSWFBReceived ,
    ISNULL(CONVERT(VARCHAR(10), FND.RRImageDate , 101) ,'')   AS RRWFBReceived ,
    ISNULL(CONVERT(VARCHAR(10), FND.RRImageDate , 101) ,'')   AS RRImage ,
    ISNULL(CONVERT(VARCHAR(10), FND.FSImageDate , 101) ,'')   AS FSImage ,
    ISNULL(CONVERT(VARCHAR(10), FND.OSARImageDate , 101) ,'') AS OSARWFBReceived ,
    ISNULL(CONVERT(VARCHAR(10), FND.OSARImageDate , 101) ,'') AS OSARImageDate ,
    CASE
        WHEN ASG.DocTypeID =1
        THEN COMP.COMPANYNAME
        ELSE ''
    END AS FSAAssignedBusinessUnit ,
    CASE
        WHEN OA.IsFlagForReview = 1
        THEN 'YES'
        ELSE 'NO'
    END                                                     AS AnalystReviewRequired ,
    AP.ApprovalStatus                                                            AS ApprovalStatus ,
    CONVERT(VARCHAR(10), ISNULL(OA.ApprovedDate, ISNULL(OA.ReviewDate,OA.CreatedDate) ), 101) AS
    StatusModifiedDate ,
    CASE
        WHEN OA.ApprovedBy IS NULL
        THEN
            CASE
                WHEN OA.ReviewerID IS NULL
                THEN ISNULL(Apprvr1.firstname, ' ') + ' ' + ISNULL(Apprvr1.Lastname,' ')
                ELSE ISNULL(reviewer.firstname, ' ') + ' ' + ISNULL(reviewer.Lastname,' ')
            END
        ELSE ISNULL(Apprvr.firstname, ' ') + ' ' + ISNULL(Apprvr.Lastname,' ')
    END AS StatusModifiedBy ,
    CASE
        WHEN L.SubServiced IS NULL
        THEN 'No'
        ELSE 'Yes'
    END               AS SubServiced ,
    L.SubServicerName AS SubServicerName ,
    CASE
        WHEN L.CASHFLAG ='1'
        THEN 'CASH'
        WHEN L.CASHFLAG ='L'
        THEN 'NON-CASH'
        ELSE 'NON-SUBSERVICED'
    END                                                      AS 'CashNonCashSubservicer' ,
    P.SubServicer                                  AS SubServicerCode ,
    CONVERT(VARCHAR(10), L.MaturityDate, 101)                AS MaturityDate ,
    CONVERT(VARCHAR(10), @calendarPeriod, 101)               AS CalendarPeriod ,
    P.StatusCode                                             AS PropertyStatusCode ,
    CONVERT(VARCHAR(10), P.PropertyStatusEffectiveDate, 101) AS PropertyStatusCodeEffectiveDate ,
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
    CASE
        WHEN MONTH(@calendarPeriod) = 12
        THEN
            CASE I.OSARRequiredFlagAnnually
                WHEN 'Y'
                THEN 'Yes'
                ELSE 'No'
            END
        ELSE
            CASE I.OSARRequiredFlagQuarterly
                WHEN 'Y'
                THEN 'Yes'
                ELSE 'No'
            END
    END AS OSARReportingRequiredFlag ,
    substring(I.InvestorDescription, charindex(':', I.InvestorDescription) + 1, LEN
    (I.InvestorDescription) - charindex(':', I.InvestorDescription)) AS DealID ,
    P.ProspectusIDNumber ,
    CASE
        WHEN L.Defeased = 1
        THEN 'YES'
        ELSE 'NO'
    END AS Defeased ,
    CASE
        WHEN P.CreditNetLease = 1
        THEN 'YES'
        ELSE 'NO'
    END           AS CTL ,
    L.LoanBalance AS AggregateLoanBalance ,
    CASE
        WHEN L.CASHFLAG ='1'
        THEN CONVERT(VARCHAR(10), ASG.AssignDate, 101)
    END                                                        AS 'SubOSARAssignmentDate' ,
    ISNULL(spUserId.FirstName,'')+ISNULL(spUserId.LastName,'') AS FirstSpreadCompletedBy
FROM
    INVESTOR I WITH(NOLOCK)
INNER JOIN
    LOAN L WITH(NOLOCK)
ON
    L.InvestorID = I.InvestorID
INNER JOIN
    PROPERTY P WITH(NOLOCK)
ON
    L.LoanID = P.LoanID
INNER JOIN
    PROPERTYTYPES PT WITH(NOLOCK)
ON
    PT.PropTypeCD = P.PropTypeCD
LEFT OUTER JOIN
    PROPERTYOVERVIEW POV WITH(NOLOCK)
ON
    P.PROPERTYID = POV.PROPERTYID
AND dbo.fncCalIDToCal(POV.CalendarId) =@calendarPeriod
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
AND dbo.fncCalIDToCal(cte.Calendarid)= @calendarPeriod
LEFT OUTER JOIN
    ASSIGNMENT ASG WITH(NOLOCK)
ON
    dbo.fncCalIDToCal(ASG.CalendarId )= @calendarPeriod
AND ASG.PROPERTYID = P.PROPERTYID
AND ASG.DocTypeID = 1
LEFT OUTER JOIN
    AppUserInfo spUserId
ON
    spUserId.UserId = cte.UserId
LEFT OUTER JOIN
    OSARAPPROVAL OA WITH(NOLOCK)
ON
    OA.AssignmentID = ASG.AssignmentID
LEFT OUTER JOIN
    ApprovalStatus AP
ON
    AP.ApprovalStatusId = OA.ApprovalStatusId
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
    AppUserInfo Apprvr
ON
    Apprvr.UserID = Oa.ApprovedBy
LEFT OUTER JOIN
    AppUserInfo Apprvr1
ON
    Apprvr1.UserID = OA.CreatedBy
LEFT OUTER JOIN
    AppUserInfo cd
ON
    cd.UserID = I.CreditAnalyst
LEFT OUTER JOIN
    LOAN LL WITH(NOLOCK)
ON
    LL.LoanID = L.LeadLoanID
WHERE
    (
        I.DealType = 'S'
    OR  ASG.AssignmentID IS NOT NULL )
AND (
        I.osarrequiredFlagQuarterly = 'Y'
    OR  I.osarrequiredflagannually = 'Y'
    OR  ASG.AssignmentID IS NOT NULL)
AND (
        P.DocTypeID =1
    OR  P.DocTypeID =3)
ORDER BY
    I.InvestorNumber ,
    L.LoanNumber ,
    P.PropertyNumber SET NOCOUNT OFF
END