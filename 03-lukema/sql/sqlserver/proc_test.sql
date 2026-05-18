--CREATE OR
ALTER
PROCEDURE dbo.USP_Z_Luke_Test (
                               @param1 INT,
                               @param2 VARCHAR(20) ) AS
    BEGIN
        SET nocount ON
        PRINT 'Hello Sql Server'
        PRINT 'Hello Sql Server 1: ' + CAST(@param1 AS VARCHAR(10))
        
        select ClientID, ClientName, CMGClientId from Client
        
        PRINT 'Hello Sql Server 2: ' + @param2
    END