
---------------------------------------------------
ALTER
PROCEDURE USP_GetErrorInfo
AS
    SELECT
        ERROR_NUMBER()    AS ErrorNumber ,
        ERROR_SEVERITY()  AS ErrorSeverity ,
        ERROR_STATE()     AS ErrorState ,
        ERROR_PROCEDURE() AS ErrorProcedure ,
        ERROR_LINE()      AS ErrorLine ,
        ERROR_MESSAGE()   AS ErrorMessage;

---------------------------------------------------
BEGIN TRANSACTION;

BEGIN TRY
    -- Generate a constraint violation error.
    DELETE FROM Production.Product
    WHERE ProductID = 980;
END TRY
BEGIN CATCH
    EXECUTE USP_GetErrorInfo;

    IF @@TRANCOUNT > 0
        ROLLBACK TRANSACTION;
END CATCH;

IF @@TRANCOUNT > 0
    COMMIT TRANSACTION;
GO

---------------- @@ERROR integer ------------------
-- DECLARE @ErrorVar INT;
-- @@ERROR = sys.messages.message_id
-- sys.messages.text
UPDATE HumanResources.EmployeePayHistory
    SET PayFrequency = 4
    WHERE BusinessEntityID = 1;
IF @@ERROR = 547
    PRINT N'A check constraint violation occurred.';
	
-- Execute the DELETE statement.
DELETE FROM HumanResources.JobCandidate
    WHERE JobCandidateID = @CandidateID;
-- Test the error value.
IF @@ERROR <> 0 
    BEGIN
        PRINT N'An error occurred deleting the candidate information.';
        RETURN 99;
    END
ELSE
    BEGIN
        PRINT N'The job candidate has been deleted.';
        RETURN 0;
    END;	
--
-- Declare variables used in error checking.
DECLARE @ErrorVar INT;
DECLARE @RowCountVar INT;

-- Execute the UPDATE statement.
UPDATE PurchaseOrderHeader 
    SET BusinessEntityID = @BusinessEntityID 
    WHERE PurchaseOrderID = @PurchaseOrderID;

-- Save the @@ERROR and @@ROWCOUNT values in local 
-- variables before they are cleared.
SELECT @ErrorVar = @@ERROR
    ,@RowCountVar = @@ROWCOUNT;

-- Check for errors. If an invalid @BusinessEntityID was specified,
-- the UPDATE statement returns a foreign key violation error #547.
IF @ErrorVar <> 0
    BEGIN
        IF @ErrorVar = 547
            BEGIN
                PRINT N'ERROR: Invalid ID specified for new employee.';
                RETURN 1;
            END
        ELSE
            BEGIN
                PRINT N'ERROR: error '
                    + RTRIM(CAST(@ErrorVar AS NVARCHAR(10)))
                    + N' occurred.';
                RETURN 2;
            END
    END

-- Check the row count. @RowCountVar is set to 0 
-- if an invalid @PurchaseOrderID was specified.
IF @RowCountVar = 0
    BEGIN
        PRINT 'Warning: The BusinessEntityID specified is not valid';
        RETURN 1;
    END
ELSE
    BEGIN
        PRINT 'Purchase order updated with the new employee';
        RETURN 0;
    END;
---------------------------------------------------
RAISERROR (N'This is message %s %d.', -- Message text.
           10, -- Severity, Validate values: [0, 18]
           1, -- State, Valide values: [0, 255]
           N'number', -- First argument.
           5); -- Second argument.
-- The message text returned is: This is message number 5.

BEGIN CATCH
    DECLARE @ErrorMessage NVARCHAR(4000);
    DECLARE @ErrorSeverity INT;
    DECLARE @ErrorState INT;

    SELECT 
        @ErrorMessage = ERROR_MESSAGE(),
        @ErrorSeverity = ERROR_SEVERITY(),
        @ErrorState = ERROR_STATE();

    -- Use RAISERROR inside the CATCH block to return error
    -- information about the original error that caused
    -- execution to jump to the CATCH block.
    RAISERROR (@ErrorMessage, -- Message text.
               @ErrorSeverity, -- Severity.
               @ErrorState -- State.
               );
END CATCH;

--
DECLARE @StringVariable NVARCHAR(50);
SET @StringVariable = N'<<%7.3s>>';

RAISERROR (@StringVariable, -- Message text.
           10, -- Severity,
           1, -- State,
           N'abcde'); -- First argument supplies the string.
-- The message text returned is: <<    abc>>.
---------------------------------------------------

---------------------------------------------------

---------------------------------------------------

---------------------------------------------------

---------------------------------------------------

---------------------------------------------------

---------------------------------------------------

---------------------------------------------------


