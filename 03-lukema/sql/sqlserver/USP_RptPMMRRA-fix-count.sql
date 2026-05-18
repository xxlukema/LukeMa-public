--
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
            ,
            X
            (
                statusdate ,
                propertyid ,
                Userid
            ) AS
            (
                SELECT
                    statusdate ,
                    propertyid ,
                    Userid
                FROM
                    PropertyFinancialStatusHistory (NOLOCK )
                WHERE
                    FinancialTaskID = 5
                AND CALENDARID = @Calendar
            )
            ,
            Y
            (
                StatusDate ,
                userid ,
                propertyid
            ) AS
            (
                SELECT
                    StatusDate ,
                    userid ,
                    propertyid
                FROM
                    PropertyFinancialStatusHistory (NOLOCK )
                WHERE
                    FinancialTaskID = 6
                AND CALENDARID = @Calendar
            )
            ,
            Z
            (
                lastupdated,
                Propertyid
            ) AS
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
                AND ROV.CALENDARID = @Calendar
            )
            ,
            r
            (
                rentrollid,
                RentRollHistoryID ,
                LastUpdated ,
                propertyid
            ) AS
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
                AND ROV.CALENDARID = @Calendar
            )
            ,
            W
            (
                lastupdated,
                propertyid
            ) AS
            (
                SELECT
                    MIN(ra.lastupdated) AS lastupdated ,
                    r.propertyid
                FROM
                    Rentrollhistory ra
                INNER JOIN
                    r
                ON
                    r.RentRollID = ra.RentRollID
                AND ra.RentRollHistoryID > r.RentRollHistoryID
                AND ra.rentrollstatusid != 2
                GROUP BY
                    r.propertyid
            )
        SELECT
            COUNT(1)
        FROM
            CMGCLIENT CMGC (NOLOCK )
        INNER JOIN
            CLIENT C (NOLOCK )
        ON
            C.CMGClientId = CMGC.CMGClientId
        AND CMGC.CMGClientId = @Client
        INNER JOIN
            INVESTOR I (NOLOCK )
        ON
            I.CLIENTID = C.CLIENTID
        AND I.DealType = 'S'
        AND I.osarrequiredFlagQuarterly = 'Y'
        AND I.osarrequiredflagannually = 'Y'
        INNER JOIN
            LOAN L (NOLOCK )
        ON
            L.INVESTORID = I.INVESTORID
        INNER JOIN
            PROPERTY P (NOLOCK )
        ON
            P.LOANID = L.LOANID
        AND P.bActiveProp = 1
        AND P.DocTypeID IN(2,3)
        INNER JOIN
            CALENDAR CAL (NOLOCK )
        ON
            CAL.CLIENTID = C.CLIENTID
        AND CAL.CALENDARID = @Calendar
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
            X
        ON
            X.propertyId = p.propertyid
        LEFT OUTER JOIN
            Y
        ON
            Y.propertyId = P.propertyid
        LEFT OUTER JOIN
            Z
        ON
            Z.Propertyid = P.propertyid
        LEFT OUTER JOIN
            W
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