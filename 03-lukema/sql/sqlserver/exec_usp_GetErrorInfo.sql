BEGIN TRY
SELECT
    1/0;
END TRY
--
BEGIN CATCH
   print @@error
   EXECUTE USP_GetErrorInfo;
END CATCH