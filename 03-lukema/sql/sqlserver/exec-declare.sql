DECLARE @count int
DECLARE @name nvarchar(50)
DECLARE @sales money
DECLARE @today datetime
SET @count = 1
SET @name = 'Michael Otey'
SET @sales = 100.00
SET @today = '11/05/2007'
PRINT @count
PRINT @name
PRINT @sales
PRINT @today
--
DECLARE @Calender DATETIME
DECLARE	@Client INT
DECLARE	@CalenderId NVARCHAR(20)
    
SET @Client = 1
SET @CalenderId = 111
SET @Calender = dbo.fncCalIDToCal(@CalenderId)

print '11111111111'
--select date;
SELECT @today = GETDATE() 
PRINT @today 
print '22222222222'
        
PRINT @Calender
PRINT @CalenderId
PRINT @Client
