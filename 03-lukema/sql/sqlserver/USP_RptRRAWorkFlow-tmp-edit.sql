DECLARE
    @Client INT
    DECLARE
        @Calendar NVARCHAR(20)
        --
        --
        SET @Client = 1
        SET @Calendar = 111
        --
        WITH
            PropFinHist
            (
                StatusDate ,
                userid ,
                propertyid
            ) AS
            (
                SELECT
                    TOP 1 StatusDate ,
                    userid ,
                    propertyid
                FROM
                    PropertyFinancialStatusHistory
                WHERE
                    FinancialTaskID = 6
                AND CALENDARID = @Calendar
                ORDER BY
                    statusdate DESC
            )
            ,
            Hist
            (
                lastupdated ,
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
                AND rov.CALENDARID = @Calendar
            )
            ,
            RRHist
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
                        AND rov.CALENDARID = @Calendar ) AS r
                ON
                    r.RentRollID = ra.RentRollID
                AND ra.RentRollHistoryID > r.RentRollHistoryID
                AND ra.rentrollstatusid != 2
                GROUP BY
                    r.propertyid
            )
        --
        SELECT DISTINCT
            I.InvestorNumber ,
            L.LoanNumber ,
            P.PropertyNumber ,
            P.PropertyName ,
            L.CurrentPrincipleBalance ,
            L.LoanBalance AS PropertyLoanBalance ,
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
            L.DSCRTrigger ,
            P.RRARequired ,
            CASE L.Defeased
                WHEN 1
                THEN 'YES'
                ELSE 'NO'
            END AS Defeased ,
            CASE P.bActiveProp
                WHEN 1
                THEN 'YES'
                ELSE 'NO'
            END                                      AS bActiveProp ,
            CONVERT(VARCHAR(10), X.lastupdated, 101) AS LeaseReviewIssueOpenDate ,
            CONVERT(VARCHAR(10), Y.lastupdated, 101) AS LeaseReviewIssueClosedDate ,
            CASE
                WHEN L.SpecialServiced = 'Y'
                THEN 'YES'
                ELSE 'NO'
            END                                       AS SpecialServiced ,
            CONVERT(VARCHAR(10), ASS.AssignDate, 101) AS RRAssignDate ,
            CONVERT(VARCHAR(10), CAL.Calendar, 101)   AS Calendarperiod ,
            L.LoanNoteType                            AS GroupOSARCode ,
            FND.RRCollected                           AS RR_WFB_Received ,
            CONVERT(VARCHAR(10), Z.StatusDate, 101)   AS eServicerUploadDate ,
            ''                                        AS eServicerOccupancyDate ,
            LL.loannumber                             AS LeadLoanID ,
            L.LoanNoteType ,
            RS.RentRollStatusName ,
            CONVERT(VARCHAR(10), RH.LastUpdated, 101) AS LastUpdated
        FROM
            CMGCLIENT CMGC (NOLOCK)
        INNER JOIN
            CLIENT C
        ON
            C.CMGClientId = CMGC.CMGClientId
        INNER JOIN
            INVESTOR I (NOLOCK)
        ON
            I.CLIENTID = C.CLIENTID
        INNER JOIN
            LOAN L (NOLOCK)
        ON
            L.INVESTORID = I.INVESTORID
        INNER JOIN
            PROPERTY P (NOLOCK)
        ON
            P.LOANID = L.LOANID
        INNER JOIN
            CALENDAR CAL (NOLOCK)
        ON
            CAL.CLIENTID = C.CLIENTID
            --
            --
        AND CAL.CalendarID = @Calendar
            --
            --
        LEFT OUTER JOIN
            LOAN LL (NOLOCK)
        ON
            LL.loanid = L.LeadLoanID
            --
            --
        LEFT OUTER JOIN
            PROPERTYOVERVIEW POV (NOLOCK)
        ON
            P.PROPERTYID = POV.PROPERTYID
        AND POV.Calendarid = Cal.Calendarid
        LEFT OUTER JOIN
            FILENETDOCUMENTS FND (NOLOCK)
        ON
            FND.CalendarId = Cal.CalendarId
        AND FND.PropertyId = P.PropertyId
        LEFT OUTER JOIN
            ASSIGNMENT ASS (NOLOCK)
        ON
            ASS.CalendarId = CAL.CalendarId
        AND ASS.PropertyId = P.PropertyId
        LEFT OUTER JOIN
            RENTROLLOVERVIEW ROV (NOLOCK)
        ON
            ROV.PROPERTYID = P.PROPERTYID
        AND ROV.CalendarId = Cal.CalendarId
        LEFT OUTER JOIN
            RENTROLLHISTORY RH (NOLOCK)
        ON
            RH.RENTROLLID = ROV.RENTROLLID
        AND ROV.RENTROLLSTATUSID = RH.RENTROLLSTATUSID
        LEFT OUTER JOIN
            RENTROLLSTATUS RS (NOLOCK)
        ON
            RS.RENTROLLSTATUSID = ROV.RENTROLLSTATUSID
        LEFT OUTER JOIN
            Hist AS X
        ON
            X.Propertyid = P.propertyid
        LEFT OUTER JOIN
            RRHist AS Y
        ON
            Y.propertyid = P.propertyid
        LEFT OUTER JOIN
            PropFinHist AS Z
        ON
            X.propertyId = P.propertyid
        WHERE
            CAL.CALENDARID = @Calendar
        AND CMGC.CMGClientId = @Client
        AND I.DealType = 'S'
        AND I.osarrequiredFlagQuarterly = 'Y'
        AND I.osarrequiredflagannually = 'Y'
        AND P.bActiveProp = 1
        AND P.DocTypeID IN (2,3)
            --
            -- AND I.InvestorNumber = 10168
            -- AND L.LoanNumber =700901281
            --
        ORDER BY
            I.InvestorNumber ,
            L.LoanNumber ,
            P.PropertyNumber;