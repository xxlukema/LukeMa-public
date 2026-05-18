
ALTER PROCEDURE "dbo"."USP_SaveIndividualAssignments" 
(
	@XMLData    NTEXT,
	@ClientId Int,
	@Calendar varchar(10),
	@userID Int
) 
AS
/***************************************
	DESCRIPTION:  	creates recordset of Mannual Assignments
	CREATED:  	2/29/2012 
	EXEC [dbo].[USP_ListMannualAssignments] [pass xml here]
****************************************/

BEGIN
	SET NOCOUNT OFF
	
	Declare @TempTable TABLE
	(
		BUID Int,
		PropertyID Int,
		DocTypeID Int, 
		IsPriority Bit
	)
	
	Declare @TempResults TABLE
	(
		BUID Int,
		CalendarID int,
		PropertyID Int,
		DocTypeID Int, 
		IsPriority Bit
	)
		
	BEGIN TRY
	SET @XMLData = cast(replace(cast(@XMLData as nvarchar(max)),'&','and') as ntext) 
	DECLARE @iDoc int 
	DECLARE @ErrNo int,  @Section VARCHAR(255)
	Set @ErrNo = 0
	
	EXEC sp_xml_preparedocument @iDoc OUTPUT, @XMLData

	INSERT INTO @TempTable 
	(
		BUID, PropertyID, DocTypeID, IsPriority
	)  
	SELECT  
		X.BUID, X.PROPERTYID, X.DOCTYPEID, X.ISPRIORITY
		FROM OPENXML(@iDoc, '/root/insertassignments/r',2)  
	WITH 
	(  
		buid int 'buid',  
		propertyid int 'propertyid',   
		doctypeid int 'doctypeid',  
		ispriority bit 'ispriority'
	) x

	
	Insert into @TempResults
	select tmp.buid, cal.calendarid, tmp.propertyid, tmp.doctypeid, tmp.ispriority from Property pro
	Inner Join Loan  lo On pro.loanid = lo.loanid
	inner join calendar cal on lo.clientid = cal.clientid and cal.calendar = @Calendar
	inner join @TempTable tmp on pro.propertyid = tmp.propertyid
	
	--Select * from @TempResults
	--Insert new records
	Insert into assignment (BUID, CalendarID, PropertyID, DocTypeID, AssignDate, IsPriority, CreatedDate, CreatedBy)
	Select  tmp.buid, tmp.CalendarID, tmp.propertyid, tmp.doctypeid, Getdate(), tmp.ispriority, Getdate(), @userID from @TempResults tmp
	Left Outer Join assignment assign On tmp.PropertyID = assign.PropertyID and tmp.Calendarid = assign.Calendarid
	Where assign.Assignmentid is null
	
	--Update existing records
	Update  assign
	SET
		assign.BUID = x.buid,
		assign.isPriority = x.ispriority,
		assign.ModifiedDate = Getdate(),
		assign.ModifiedBy = @userID
	FROM assignment assign, @TempResults x
	WHERE 
			assign.PropertyID = x.propertyid 
		And	assign.CalendarID = x.calendarid 
		
	END TRY
	BEGIN CATCH
		SET @ErrNo = @@Error
	END CATCH	

	RETURN @ErrNo
END


