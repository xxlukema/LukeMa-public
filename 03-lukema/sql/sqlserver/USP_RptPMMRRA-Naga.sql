DECLARE
    @Client INT
    DECLARE
        @Calendar NVARCHAR(20)
        --
        --
        SET @Client = 1
        SET @Calendar = 111
        --
        --
        WITH
            cte
            (
                RentRollID,
                ExpireLease
            )AS
            (
                SELECT
                    ROV.RentRollID ,
                    CASE
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) < 7
                        THEN 'Expiring0to6Months'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) > 6
                        AND DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) < 10
                        THEN 'Expiring6to9Months'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) > 9
                        AND DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) < 13
                        THEN 'Expiring9to12Months'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) < 13
                        THEN 'ExpiredorExpiringWithin12Months'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) > 12
                        THEN 'Expiringgreaterthan12Months'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) > 0
                        AND DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) < 12
                        THEN 'ExpYr'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) > 12
                        AND DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) < 25
                        THEN 'ExpYr2'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) > 25
                        AND DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) < 37
                        THEN 'ExpYr3'
                        WHEN DATEDIFF(MONTH, CONVERT(VARCHAR(10), GETDATE(), 101), CONVERT(VARCHAR
                            (10), RRD.EndDate, 101)) > 25
                        THEN 'ExpYr4Plus'
                    END AS ExpireLease
                FROM
                    RENTROLLDETAILS RRD (NOLOCK)
                INNER JOIN
                    RENTROLLoverview ROV (NOLOCK)
                ON
                    ROV.RENTROLLID = RRD.RENTROLLID
                WHERE
                    dbo.fncCalIDToCal(ROV.CALENDARID) = dbo.fncCalIDToCal(71)
            )
            ,
            GrouperVal
            (
                rentRollId,
                expirelease,
                total
            )AS
            (
                SELECT
                    rentRollId,
                    expirelease,
                    COUNT(*)
                FROM
                    cte
                GROUP BY
                    rentRollId,
                    expirelease
            )
            ,
            LeaseInfo
            (
                rentRollId,
                exp0to6,
                exp6to9,
                exp9to12,
                expin12,
                expG12,
                ExpYr,
                ExpYr2,
                ExpYr3,
                ExpYr4
            )AS
            (
                SELECT
                    rentRollId,
                    SUM(
                        CASE
                            WHEN expirelease = 'Expiring0to6Months'
                            THEN total
                            ELSE 0
                        END) AS Q1,
                    SUM(
                        CASE
                            WHEN expirelease = 'Expiring6to9Months'
                            THEN total
                            ELSE 0
                        END) AS Q2,
                    SUM(
                        CASE
                            WHEN expirelease = 'Expiring9to12Months'
                            THEN total
                            ELSE 0
                        END) AS Q3,
                    SUM(
                        CASE
                            WHEN expirelease = 'ExpiredorExpiringWithin12Months'
                            THEN total
                            ELSE 0
                        END) AS Q4,
                    SUM(
                        CASE
                            WHEN expirelease = 'Expiringgreaterthan12Months'
                            THEN total
                            ELSE 0
                        END) AS Q5,
                    SUM(
                        CASE
                            WHEN expirelease = 'ExpYr'
                            THEN total
                            ELSE 0
                        END) AS Q6,
                    SUM(
                        CASE
                            WHEN expirelease = 'ExpYr2'
                            THEN total
                            ELSE 0
                        END) AS Q7,
                    SUM(
                        CASE
                            WHEN expirelease = 'ExpYr3'
                            THEN total
                            ELSE 0
                        END) AS Q8,
                    SUM(
                        CASE
                            WHEN expirelease = 'ExpYr4Plus'
                            THEN total
                            ELSE 0
                        END) AS Q9
                FROM
                    GrouperVal
                    -- additional where clause goes here...
                GROUP BY
                    rentRollId
            )
            ,
            PropertyCounter
            (
                LoanId,
                PropCount
            ) AS
            (
                SELECT
                    LoanId,
                    COUNT(*)
                FROM
                    Property(NOLOCK)
                GROUP BY
                    LoanID
            )
        SELECT DISTINCT
            L.LoanNumber ,
            P.ProspectusIDNumber ,
            P.PropertyNumber ,
            I.InvestorNumber ,
            PropCount AS PropertyCount ,
            PT.PropertyType ,
            P.PropertyName ,
            ISNULL(P.propAddr1,'') + ' ' + ISNULL(P.propAddr2,'') AS PropertyStreet ,
            P.propState                                           AS PropertyState ,
            P.propCity                                            AS PropertyCity ,
            CASE
                WHEN LEN(RTRIM(P.propzip)) = 9
                THEN LEFT(P.propzip, 5) + '-' + RIGHT(RTRIM(P.propzip), 4)
                ELSE P.propzip
            END AS PropertyZipCode ,
            P.YearBuilt ,
            P.YearRenovated ,
            L.CurrentPrincipleBalance ,
            POV.CurrentAllocatedLoanPercent ,
            P.NetRentableSQFT                               AS NetSquareFeetAtContribution ,
            CONVERT(VARCHAR(10), I.SecuritizationDate, 101) AS SecuritizationDate ,
            CONVERT(VARCHAR(10), L.MaturityDate, 101)       AS MaturityDate ,
            L.IOtoPIDate ,
            L.DSCRTrigger AS LockboxReserveTriggerYN ,
            CASE
                WHEN L.SubServiced = 'Y'
                THEN 'YES'
                ELSE 'NO'
            END AS         SubServiced
            ,
            CASE
                WHEN L.CASHFLAG ='1'
                THEN 'CASH'
                WHEN L.CASHFLAG ='L'
                THEN 'NON-CASH'
                ELSE 'NON-SUBSERVICED'
            END AS CashNonCashSubservicer ,
            (
                SELECT
                    loannumber
                FROM
                    loan
                WHERE
                    loanid = L.LeadLoanID) AS Groups ,
            L.LoanNoteType                 AS GroupOSARCode ,
            CASE
                WHEN L.SpecialServiced = 'Y'
                THEN 'Yes'
                ELSE 'No'
            END                        AS SpeciallyServiced ,
            L.SpeciallyServicedInDate  AS SpecialServiceLoanBeginDate ,
            L.SpeciallyServicedOutDate AS SpecialServiceLoanEndDate ,
            L.DefeasanceDate ,
            P.StatusCode AS PropertyStatusCode ,
            CASE
                WHEN P.WatchList = 1
                THEN 'Yes'
                ELSE 'No'
            END                                      AS Watchlisted ,
            CONVERT(VARCHAR(10), L.WLDateAdded, 101)    AS WatchlistAddDate ,
            L.WLTriggers                                AS WatchlistTriggers ,
            ''                                          AS PSAFinancialsAnnualDueDate ,
            CONVERT(VARCHAR(10), P.AssumptionDate, 101) AS AssumptionClosingDate ,
            CASE
                WHEN P.CreditNetLease = 1
                THEN 'YES'
                ELSE 'NO'
            END AS CreditNetLease ,
            CASE
                WHEN P.GroundLease = 1
                THEN 'Yes'
                ELSE 'No'
            END               AS GroundLease ,
            CMSF.EGI          AS UWEGI ,
            CMSF.TotalExpense AS UWTOE ,
            CMSF.NOI          AS UWNOI ,
            CMSF.DSCRNCF      AS UWDSCR_NCF ,
            POV.Occupancy     AS UWOccupancy ,
            F.Frequency       AS Period ,
            CMSF.FiscalYear   AS StatementClassification_yr ,
            (
                SELECT
                    CONVERT(VARCHAR(10), POV.Statementend, 101) + ' - ' + CONVERT(VARCHAR(10),
                    POV.StatementBegin, 101)) AS NumberofMonthsCovered ,
            CMSF.EGI ,
            CMSF.TotalExpense AS TOE ,
            CMSF.NOI ,
            CMSF.TotalCapital                       AS TotalCapitalExpenditures ,
            CMSF.DebtService                           AS TotalDebtService ,
            POV.Occupancy                              AS OccupancyRate ,
            POV.AvgDailyRate                           AS AverageDailyRate ,
            POV.RevenuePerAvgRoom                      AS RevenuePerAvgRoom ,
            POV.AvgRentalRate                          AS AverageRentalRate ,
            CONVERT(VARCHAR(10), Y.StatusDate, 101)    AS eServicerUploadDate ,
            CONVERT(VARCHAR(10), FND.RRImageDate, 101) AS RR_WFB_Recieved ,
            CONVERT(VARCHAR(10), Z.lastupdated, 101)   AS LeaseStartDate ,
            CONVERT(VARCHAR(10), W.lastupdated, 101)   AS LeaseEndDate ,
            ExVal.exp0to6                              AS Expiring0to6Months,
            ExVal.exp6to9                              AS Expiring6to9Months,
            ExVal.exp9to12                             AS Expiring9to12Months,
            ExVal.expin12                              AS ExpiredorExpiringWithin12Months,
            ExVal.expG12                               AS Expiringgreaterthan12Months,
            ExVal.ExpYr                                AS ExpYr,
            ExVal.ExpYr2                               AS ExpYr2,
            ExVal.ExpYr3                               AS ExpYr3,
            ExVal.ExpYr4                               AS ExpYr4Plus ,
            CMSF.DSCRNOI                               AS DSCR ,
            POV.Occupancy ,
            CONVERT(VARCHAR(10), POV.OccupancyDate, 101)                OccupancyDate ,
            CONVERT(VARCHAR(10), X.StatusDate, 101)                     AS ApprovedByDate ,
            ISNULL(APP.FirstName, ' ') + ' ' + ISNULL(APP.LastName,' ')  AS StatusModifiedBy ,
            CONVERT(VARCHAR(10), X.statusdate, 101)                      AS PWC_COMPLETED_DATE ,
            ISNULL(APP1.FirstName,' ') + ' ' + ISNULL(APP1.LastName,' ') AS PSRAnalyst ,
            ISNULL(APP2.FirstName,' ') + ' ' + ISNULL(APP2.LastName,' ') AS AssetManager ,
            ISNULL(APP3.FirstName,' ') + ' ' + ISNULL(APP3.LastName,' ') AS CreditAnalyst ,
            RRD.PercentageofTotal                                        AS
            'percentofPropertyforthistenant' ,
            '' AS 'percentofpropertyexpiringwithinoneyear' ,
            '' AS 'Propertieswithatleast30percentofNRAexpiring' ,
            '' AS 'TenantpercentofProperty' ,
            T.TenantName ,
            CONVERT(VARCHAR(10), ROV.RentRollDate, 101) AS RentRollDate ,
            substring(I.InvestorDescription, charindex(':', I.InvestorDescription) + 1, LEN
            (I.InvestorDescription) - charindex(':', I.InvestorDescription)) AS DealId ,
            I.InvestorLongName ,
            '' AS ReportedSqFt ,
            ROV.PercentOccupancy ,
            RRD.TenantDescription ,
            RRD.NetSQFT ,
            RRD.PercentageofTotal ,
            RRD.SpaceNumber ,
            RRD.MonthlyRent ,
            RRD.MonthlyRent AS MonthlyRentperSF ,
            RRD.MTM ,
            L.RelatedMortgageLoanAggregateBalance AS AgregateLoanBalance
        FROM
            CMGCLIENT CMGC (NOLOCK )
        INNER JOIN
            CLIENT C (NOLOCK )
        ON
            C.CMGClientId = CMGC.CMGClientId
        INNER JOIN
            INVESTOR I (NOLOCK )
        ON
            I.CLIENTID = C.CLIENTID
        INNER JOIN
            LOAN L (NOLOCK )
        ON
            L.INVESTORID = I.INVESTORID
        INNER JOIN
            PROPERTY P (NOLOCK )
        ON
            P.LOANID = L.LOANID
        INNER JOIN
            CALENDAR CAL (NOLOCK )
        ON
            CAL.CLIENTID = C.CLIENTID
        INNER JOIN
            PropertyCounter pc
        ON
            pc.LoanId = L.LoanID
        LEFT OUTER JOIN
            PROPERTYOVERVIEW POV (NOLOCK )
        ON
            P.PROPERTYID = POV.PROPERTYID
        AND POV.CalendarId = CAL.CalendarId
        LEFT OUTER JOIN
            CMSAFINANCIALSUMMARY CMSF (NOLOCK )
        ON
            CMSF.PROPERTYID = P.PROPERTYID
        AND CMSF.CalendarId = CAL.CalendarId
        INNER JOIN
            FREQUENCY F (NOLOCK )
        ON
            F.FREQUENCYCD = CMSF.FREQUENCYCD
        LEFT OUTER JOIN
            FILENETDOCUMENTS FND (NOLOCK )
        ON
            FND.CalendarId = Cal.CalendarId
        AND FND.PropertyID = P.PropertyID
        LEFT OUTER JOIN
            ASSIGNMENT ASS (NOLOCK )
        ON
            ASS.CalendarId = CAL.CalendarId
        AND ASS.PropertyID = P.PropertyID
        LEFT OUTER JOIN
            OSARAPPROVAL OA (NOLOCK )
        ON
            OA.AssignmentID = ASS.AssignmentID
        INNER JOIN
            PROPERTYTYPES PT (NOLOCK )
        ON
            PT.PropTypeCD = P.PropTypeCD
        LEFT OUTER JOIN
            RENTROLLoverview ROV (NOLOCK )
        ON
            ROV.PROPERTYID = P.PROPERTYID
        AND ROV.CALENDARID = CAL.CALENDARID
        LEFT OUTER JOIN
            RENTROLLDETAILS RRD (NOLOCK )
        ON
            RRD.RENTROLLID = ROV.RENTROLLID
        LEFT OUTER JOIN
            LeaseInfo ExVal
        ON
            ExVal.RentRollId = ROV.RentRollID
        LEFT OUTER JOIN
            TENANT T (NOLOCK )
        ON
            T.TENANTID = RRD.TENANTID
        LEFT OUTER JOIN
            (
                SELECT
                    statusdate ,
                    propertyid ,
                    Userid
                FROM
                    PropertyFinancialStatusHistory (NOLOCK )
                WHERE
                    FinancialTaskID = 5
                AND dbo.fncCalIDToCal(CALENDARID) = dbo.fncCalIDToCal(@Calendar) ) AS X
        ON
            X.propertyId = p.propertyid
        LEFT OUTER JOIN
            (
                SELECT
                    StatusDate ,
                    userid ,
                    propertyid
                FROM
                    PropertyFinancialStatusHistory (NOLOCK )
                WHERE
                    FinancialTaskID = 6
                AND dbo.fncCalIDToCal(CALENDARID) = dbo.fncCalIDToCal(@Calendar) ) AS Y
        ON
            Y.propertyId = P.propertyid
        LEFT OUTER JOIN
            (
                SELECT
                    rh.lastupdated ,
                    rov.Propertyid
                FROM
                    Rentrollhistory rh
                INNER JOIN
                    rentrolloverview rov
                ON
                    rov.rentrollid = rh.rentrollid
                WHERE
                    rh.rentrollstatusid = 2
                AND dbo.fncCalIDToCal(ROV.CALENDARID) = dbo.fncCalIDToCal(@Calendar)) AS Z
        ON
            Z.Propertyid = P.propertyid
        LEFT OUTER JOIN
            (
                SELECT
                    MIN(ra.lastupdated) AS lastupdated ,
                    r.propertyid
                FROM
                    Rentrollhistory ra
                INNER JOIN
                    (
                        SELECT
                            rh.rentrollid ,
                            rh.RentRollHistoryID ,
                            rh.LastUpdated ,
                            rov.propertyid AS propertyid
                        FROM
                            Rentrollhistory rh
                        INNER JOIN
                            rentrolloverview rov
                        ON
                            rov.rentrollid = rh.rentrollid
                        WHERE
                            rh.rentrollstatusid = 2
                        AND dbo.fncCalIDToCal(ROV.CALENDARID) = dbo.fncCalIDToCal(@Calendar)) AS r
                ON
                    r.RentRollID = ra.RentRollID
                AND ra.RentRollHistoryID > r.RentRollHistoryID
                AND ra.rentrollstatusid != 2
                GROUP BY
                    r.propertyid) AS W
        ON
            W.propertyid = P.propertyid
        LEFT OUTER JOIN
            APPUSERINFO APP
        ON
            APP.Userid = X.Userid
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
            APP3.Userid = I.CreditAnalyst
        WHERE
            dbo.fncCalIDToCal(CAL.CALENDARID) = dbo.fncCalIDToCal(@Calendar)
        AND CMGC.CMGClientId = @Client
        AND I.DealType = 'S'
        AND I.osarrequiredFlagQuarterly = 'Y'
        AND I.osarrequiredflagannually = 'Y'
        AND P.bActiveProp = 1
        AND P.DocTypeID IN(2,3)
        ORDER BY
            I.InvestorNumber,
            L.LoanNumber ,
            P.PropertyNumber SET NOCOUNT OFF