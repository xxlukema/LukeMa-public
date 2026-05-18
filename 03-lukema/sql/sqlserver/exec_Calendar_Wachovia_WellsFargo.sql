DECLARE
    @Client INT
DECLARE
    @Calendar NVARCHAR(20)
DECLARE
    @calendarPeriod DATETIME
SET @Client = 2
SET @Calendar = 118
SET @calendarPeriod = dbo.fncCalIDToCal(@Calendar) 
PRINT @calendarPeriod
--
--

                SELECT
                /*
                    statusPfs.StatusDate,
                    statusPfs.PropertyID,
                    statusPfs.CalendarID,
                    statusPfs.UserID ,
                    dense_rank()OVER (PARTITION BY statusPfs.PropertyID,statusPfs.CalendarID
                    ORDER BY statusPfs.StatusDate ASC ) AS FirstSpread
                    */
                    ca.*,
                    '---',
                    cmg.*
                    
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
               --  AND ca.CalendarId = @Calendar 
                 AND dbo.fncCalIDToCal(ca.CalendarId) =dbo.fncCalIDToCal(@Calendar)
                
        