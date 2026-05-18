DECLARE
    @Client INT, @Calendar NVARCHAR(20)
BEGIN
    SET NOCOUNT ON
	
	SET @Client = 1
	SET @Calendar = 1
	
    SELECT DISTINCT
        P.propertyid,
        CONVERT(VARCHAR(10), CAL.Calendar, 101)                     AS Period ,
        ISNULL(APP1.FirstName,'') + ' '+ ISNULL(APP1.LastName,'')   AS PSRAnalyst ,
        ISNULL(APP2.FirstName,'') + ' '+ ISNULL(APP2.LastName,'')   AS AssetManager ,
        ISNULL(APP3.FirstName,'') + ' '+ ISNULL(APP3.LastName,'')   AS PSRTeamLead ,
        ISNULL(APPOA.FirstName,'') + ' '+ ISNULL(APPOA.LastName,'') AS ReviewerID ,
        I.InvestorNumber ,
        L.LoanNumber ,
        P.ProspectusIDNumber ,
        P.PropertyNumber ,
        CASE P.Top10
            WHEN 1
            THEN 'YES'
            ELSE 'NO'
        END AS TOP10 ,
        CASE P.Top20
            WHEN 1
            THEN 'YES'
            ELSE 'NO'
        END AS TOP20 ,
        CASE P.WatchList
            WHEN 1
            THEN 'YES'
            ELSE 'NO'
        END AS WatchList ,
        (
            SELECT
                TOP 1 CONVERT(VARCHAR(10), PFSH.StatusDate, 101)
            FROM
                PropertyFinancialStatusHistory PFSH
            WHERE
                PFSH.FinancialTaskID = 5
            AND dbo.fncCalIDToCal(PFSH.CALENDARID) = dbo.fncCalIDToCal(@Calendar)
            AND PFSH.PropertyID = P.propertyid
            ORDER BY
                PFSH.StatusDate DESC ) AS PWC_COMPLETED_DATE ,
        CASE OA.IsFlagForReview
            WHEN 1
            THEN 'YES'
            ELSE 'NO'
        END                AS AnalystReview ,
        APS.Approvalstatus AS AnalystApproved ,
        (
            SELECT
                top 1 apu.FirstName + ' ' + apu.LastName
            FROM
                OsarApproval oa1
            INNER JOIN
                Assignment ass
            ON
                oa1.AssignmentID = ass.AssignmentID
            INNER JOIN
                OsarApprovalHistory oah
            ON
                oa1.OsarApprovalID = oah.OsarApprovalID
            INNER JOIN
                AppUserInfo apu
            ON
                apu.UserID = oah.LastUpdatedBy
            WHERE
                dbo.fncCalIDToCal(ass.CalendarID) = dbo.fncCalIDToCal(@Calendar)
            AND ass.PropertyID = P.PropertyID
            AND (
                    oa1.ApprovalStatusId = 1
                OR  oa1.ApprovalStatusId = 9)
            ORDER BY
                oah.LastUpdated DESC ) AS ApprovedBy ,
        (
            SELECT
                top 1 CONVERT(VARCHAR(10), oah.LastUpdated, 101)
            FROM
                OsarApproval oa1
            INNER JOIN
                Assignment ass
            ON
                oa1.AssignmentID = ass.AssignmentID
            INNER JOIN
                OsarApprovalHistory oah
            ON
                oa1.OsarApprovalID = oah.OsarApprovalID
            INNER JOIN
                AppUserInfo apu
            ON
                apu.UserID = oah.LastUpdatedBy
            WHERE
                dbo.fncCalIDToCal(ass.CalendarID) = dbo.fncCalIDToCal(@Calendar)
            AND ass.PropertyID = P.PropertyID
            AND (
                    oa1.ApprovalStatusId = 1
                OR  oa1.ApprovalStatusId = 9)
            ORDER BY
                oah.LastUpdated DESC ) AS ApprovedByDate ,
        CASE
            WHEN ( ISNULL(L.CashFlag, 'R') = 'R')
            OR  (ISNULL(L.CashFlag, 'R') = 'W')
            THEN 'Yes'
            ELSE 'NO'
        END AS SubServiced ,
        L.CurrentPrincipleBalance ,
        P.StatusCode ,
        CMSF.DSCRNOI ,
        L.RelatedMortgageLoanAggregateBalance AS AgregateLoanBalance
    FROM
        CMGCLIENT CMGC
    INNER JOIN
        CLIENT C
    ON
        C.CMGClientId = CMGC.CMGClientId
    INNER JOIN
        INVESTOR I
    ON
        I.CLIENTID = C.CLIENTID
    INNER JOIN
        LOAN L
    ON
        L.INVESTORID = I.INVESTORID
    INNER JOIN
        PROPERTY P
    ON
        P.LOANID = L.LOANID
    INNER JOIN
        CALENDAR CAL
    ON
        CAL.CLIENTID = C.CLIENTID
    LEFT OUTER JOIN
        PROPERTYOVERVIEW POV
    ON
        P.PROPERTYID = POV.PROPERTYID
    AND POV.CalendarId = Cal.CalendarId
    LEFT OUTER JOIN
        ASSIGNMENT ASS
    ON
        ASS.CalendarId = CAL.CalendarId
    AND ASS.Propertyid = P.PropertyId
    LEFT OUTER JOIN
        OSARAPPROVAL OA
    ON
        OA.AssignmentID = ASS.AssignmentID
    LEFT OUTER JOIN
        (
            SELECT
                top 1 oah.LastUpdated,
                oah.LastUpdatedBy,
                ass.PropertyID
            FROM
                OsarApproval oa1
            INNER JOIN
                Assignment ass
            ON
                oa1.AssignmentID = ass.AssignmentID
            INNER JOIN
                OsarApprovalHistory oah
            ON
                oa1.OsarApprovalID = oah.OsarApprovalID
            WHERE
                dbo.fncCalIDToCal(ass.CalendarID) = dbo.fncCalIDToCal(@Calendar)
            AND ass.PropertyID IN
                (
                    SELECT
                        PP1.PropertyID
                    FROM
                        property PP1)
            AND (
                    oa1.ApprovalStatusId = 1
                OR  oa1.ApprovalStatusId = 9)
            ORDER BY
                oah.LastUpdated DESC ) AS OOA
    ON
        OOA.PropertyID =P.PropertyID
    LEFT OUTER JOIN
        CMSAFINANCIALSUMMARY CMSF
    ON
        CMSF.PROPERTYID = P.PROPERTYID
    AND CMSF.CalendarId = CAL.CalendarId
    LEFT OUTER JOIN
        Approvalstatus APS
    ON
        APS.ApprovalstatusId = OA.ApprovalstatusId
    LEFT OUTER JOIN
        propertyfinancialstatus PFS
    ON
        PFS.PropertyID =P.PropertyID
    AND PFS.CalendarID =CAL.CalendarID
    LEFT OUTER JOIN
        (
            SELECT
                TOP 1 PFSH.StatusDate ,
                PFSH.Userid ,
                PFSH.propertyid
            FROM
                PropertyFinancialStatusHistory PFSH
            WHERE
                PFSH.FinancialTaskID = 5
            AND dbo.fncCalIDToCal(PFSH.CALENDARID) = dbo.fncCalIDToCal(@Calendar)
            ON
                X.propertyId = P.propertyid
            LEFT OUTER JOIN
                APPUSERINFO APP
            ON
                APP.Userid = X.Userid
            AND OOA.LastUpdatedBy = APP.Userid
            LEFT OUTER JOIN
                APPUSERINFO APP1
            ON
                APP1.Userid = I.PSRAnalyst
            LEFT OUTER JOIN
                APPUSERINFO APP2
            ON
                APP2.Userid = I.AssetManager
            LEFT OUTER JOIN
                APPUSERINFO APP3
            ON
                APP3.Userid = I.PSRTeamLead
            LEFT OUTER JOIN
                AppUserInfo APPOA
            ON
                APPOA.UserID = OA.ReviewerID
            WHERE
                dbo.fncCalIDToCal(CAL.CALENDARID) = dbo.fncCalIDToCal(@Calendar)
            AND CMGC.CMGClientId = @Client
            AND I.DealType ='S'
            AND (
                    I.osarrequiredFlagQuarterly = 'Y'
                OR  I.osarrequiredflagannually = 'Y')
            AND (
                    OA.ApprovalStatusId BETWEEN 1 AND 9)
            ORDER BY
                I.InvestorNumber,
                L.LoanNumber,
                P.PropertyNumber SET NOCOUNT OFF
        END