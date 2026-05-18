
ALTER PROCEDURE "dbo"."USP_SaveAssignments" 
(
	@XMLData    NTEXT,
	@userID Int
) 
AS
/****************************************
	DESCRIPTION:  	creates recordset of Mannual Assignments
	CREATED:  	2/29/2012 
	EXEC [dbo].[USP_USP_SaveAssignments] [pass xml here]
*****************************************/

BEGIN
      SET NOCOUNT ON

	Declare @TempAssignments TABLE
	(
		BUID Int,
		CalendarID int,
		PropertyID Int,
		DocTypeID Int, 
		IsPriority Bit
	)


	   SET @XMLData = cast(replace(cast(@XMLData as nvarchar(max)),'&','and') as ntext) 

    --  DECLARE @MANNUALDATA1 TABLE ( ACCTNO varchar(20),ACCTYPE varchar(30),PROPID int)
      BEGIN 
		DECLARE @iDoc int --Document Handle  
  BEGIN TRY
		EXEC sp_xml_preparedocument @iDoc OUTPUT, @XMLData
		
			INSERT INTO @TempAssignments (BUID, CalendarID,PropertyID,DocTypeID,IsPriority)  
					 (SELECT  
					  X.BUID,  
					  X.CALENDARID,  
				      X.PROPERTYID,  
					  X.DOCTYPEID,  					 
					  X.ISPRIORITY					  				 						  
					FROM OPENXML(@iDoc, '/root/insertassignments/r',2)  
					 WITH (  
					  buid int 'buid',  
					  calendarid int 'calendarid',  					  
					  propertyid int 'propertyid',   
					  doctypeid int 'doctypeid',  						
					  ispriority bit 'ispriority'			 
					  ) x  
					 ) 

		
SET NOCOUNT ON;		
					
		EXEC sp_xml_removedocument @idoc

		-- Insert new records to Assignment table

		Insert into assignment (BUID, CalendarID, PropertyID, DocTypeID, AssignDate, IsPriority, CreatedDate, CreatedBy)
		Select  tmp.buid, tmp.CalendarID, tmp.propertyid, tmp.doctypeid, Getdate(), tmp.ispriority, Getdate(), @userID from @TempAssignments tmp
		Left Outer Join assignment assign On tmp.PropertyID = assign.PropertyID and tmp.Calendarid = assign.Calendarid
		Where assign.Assignmentid is null

	--Update existing records
	Update  assign
	SET
		assign.BUID = x.buid,
		assign.isPriority = x.ispriority,
		assign.ModifiedDate = Getdate(),
		assign.ModifiedBy = @userID
	FROM assignment assign, @TempAssignments x
	WHERE 
			assign.PropertyID = x.propertyid 
		And	assign.CalendarID = x.calendarid 


	END TRY
	BEGIN CATCH		
		RAISERROR ('An error occured in procedure ' , 16, 1) 
	END CATCH

END

SET NOCOUNT OFF
END
