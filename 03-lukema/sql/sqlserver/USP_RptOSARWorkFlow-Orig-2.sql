
ALTER PROCEDURE "dbo"."USP_RptOSARWorkFlow" (@Client    INT, @Calendar NVARCHAR(20)) AS
/*********************************************************************************************************
	DESCRIPTION:  	creates recordset of Mannual Assignments
	CREATED:  	2/29/2012 
	EXEC [dbo].[USP_RptOSARWorkFlow] 1,71
**********************************************************************************************************/

BEGIN
SET NOCOUNT ON
DECLARE @calendarPeriod datetime
SET @calendarPeriod  = dbo.fncCalIDToCal(@Calendar)
;with pfs_Signed_StatusDate (StatusDate,PropertyID,CalendarID,UserID,id) as 
(
 SELECT * FROM (
					SELECT  statusPfs.StatusDate, statusPfs.PropertyID,statusPfs.CalendarID, statusPfs.UserID , dense_rank()OVER (PARTITION BY statusPfs.PropertyID,statusPfs.CalendarID ORDER BY statusPfs.StatusDate ASC ) as FirstSpread 
						 FROM Client cmg  INNER JOIN Calendar ca ON ca.ClientID = cmg.ClientID 
							INNER JOIN PropertyFinancialStatusHistory statusPfs  ON statusPfs.CalendarID = ca.CalendarID  						
					 WHERE
						 statusPfs.FinancialTaskID = 5  AND cmg.CMGClientId = 1 AND dbo.fncCalIDToCal(ca.CalendarId) =dbo.fncCalIDToCal(@Calendar)) cte1 WHERE FirstSpread =1
	
					
)
,
pfs_Pushed_StatusDate (StatusDate,PropertyID,CalendarID) as 
(
                    SELECT MAX(statusPfs.StatusDate), statusPfs.PropertyID,statusPfs.CalendarID 
						 FROM pfs_Signed_StatusDate cte INNER JOIN
						 PropertyFinancialStatusHistory statusPfs   ON  statusPfs.PropertyID = cte.propertyId AND statusPfs.CalendarID = cte.CalendarID
					 WHERE
						 statusPfs.FinancialTaskID = 6 
						GROUP BY statusPfs.PropertyID,statusPfs.CalendarID 
					
)



   SELECT P.PropertyId,pfs.CalendarID,
				  Isnull(cd.firstname, ' ') + ' ' +  Isnull(cd.Lastname,' ') AS CreditAnalyst ,
				  Isnull(Reviewer.firstname, ' ') + ' ' +  Isnull(Reviewer.Lastname,' ') AS ReviewerID , 
				 Isnull(am.firstname, ' ') + ' ' +  Isnull(am.Lastname,' ') AS AssetManager ,  
				  Isnull(pm.firstname, ' ') + ' ' +  Isnull(pm.Lastname,' ') AS PSRTeamLead ,  
				I.InvestorNumber
			  , I.InvestorLongName
			  , L.LoanNumber
			  , P.PropertyNumber
			  , PT.PropertyType
			  , P.PropertyName
			  , P.propAddr1 AS PropertyStreet
			 
			  , CASE
					WHEN FND.FSRequestedItem = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS FSRequestedItem
			  , CASE
					WHEN FND.RRRequestedItem  = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS RRRequestedItem

			  , convert(VARCHAR(10), I.SecuritizationDate, 101)  as SecuritizationDate
			  , CASE
					WHEN P.Top10 = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS Top10
			  , CASE
					WHEN P.Top20 = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS Top20

			  , CASE
					WHEN P.WatchList = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS WatchList
			  , L.CurrentPrincipleBalance as CurrentPrincipalBalance
			  , LL.loannumber      as LeadLoanFlag
			  , L.LoanNoteType
			 , CASE
				WHEN ASG.DocTypeID =1 THEN
					convert(VARCHAR(10), ASG.AssignDate, 101)
				ELSE
					''
				END AS FSAAssignmentDate
			 
			  

				,ISNULL(convert(VARCHAR(10), FND.FSImageDate , 101) ,'') AS FSWFBReceived


			  ,ISNULL(convert(VARCHAR(10),  FND.RRImageDate , 101) ,'')  	 AS RRWFBReceived

--			  , isnull(FND.FSCollected, 'False')  AS FSWFBReceived
--			  , isnull(FND.RRCollected , 'False') AS RRWFBReceived

			   ,ISNULL(convert(VARCHAR(10),  FND.RRImageDate , 101) ,'') AS RRImage
			  , ISNULL(convert(VARCHAR(10), FND.FSImageDate , 101) ,'') AS FSImage

			  ,   ISNULL(convert(VARCHAR(10),  FND.OSARImageDate , 101) ,'')    AS OSARWFBReceived
				-- , isnull(FND.OSARCollected, 'False') AS OSARWFBReceived
			  , ISNULL(convert(VARCHAR(10),  FND.OSARImageDate , 101) ,'') AS OSARImageDate 
			
			 , CASE
				WHEN ASG.DocTypeID =1 THEN
					COMP.COMPANYNAME
				ELSE
					''
				END AS	 FSAAssignedBusinessUnit
			 
			 --, COMP.COMPANYNAME AS FSAAssignedBusinessUnit

			   , CASE
					WHEN OA.IsFlagForReview = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS AnalystReviewRequired
--			  , CASE
--					WHEN OA.ApprovalStatusId = 1 THEN
--						'YES'
--					ELSE
--						'NO'
--				END AS ApprovalStatus
				, AP.ApprovalStatus  AS ApprovalStatus
			   , convert(VARCHAR(10),  isnull(OA.ApprovedDate, IsNULL(OA.ReviewDate,OA.CreatedDate) ), 101) as  StatusModifiedDate
 
			--  , ISNULL(OA.ApprovedBy, ISNULL(OA.ReviewerId, OA.CreatedBy)) as StatusModifiedBy

			, CASE 
				WHEN OA.ApprovedBy is null then
					CASE WHEN OA.ReviewerID is null then
						Isnull(Apprvr1.firstname, ' ') + ' ' +  Isnull(Apprvr1.Lastname,' ') 
						ELSE
							Isnull(reviewer.firstname, ' ') + ' ' +  Isnull(reviewer.Lastname,' ') 
					end
				ELSE
					Isnull(Apprvr.firstname, ' ') + ' ' +  Isnull(Apprvr.Lastname,' ') 
					
				END  as StatusModifiedBy

			 , CASE
					WHEN L.SubServiced IS null THEN
						'No'
					ELSE
						'Yes'
				END AS SubServiced
			  , L.SubServicerName as SubServicerName 
	

				,CASE WHEN L.CASHFLAG ='1' THEN 'CASH'
				  WHEN L.CASHFLAG ='L' THEN 'NON-CASH'
				 ELSE 'NON-SUBSERVICED' 
                 END AS 'CashNonCashSubservicer'


			  , P.SubServicer as SubServicerCode
			  , convert(VARCHAR(10),  L.MaturityDate, 101) as MaturityDate
			   , convert(VARCHAR(10),  @calendarPeriod, 101)  	as CalendarPeriod	
			  , P.StatusCode as PropertyStatusCode
			    , convert(VARCHAR(10),  P.PropertyStatusEffectiveDate, 101)    AS PropertyStatusCodeEffectiveDate
			  , I.FirstReportingPeriodYear
			  , I.FirstReportingPeriodQuarter
			  , L.FSRRequiredQuarterly
			  , L.FSRRequiredAnnually
			  , I.OSARRequiredFlagAnnually
			  , I.OSARRequiredFlagQuarterly
			  , FT.FinancialTask AS CurrentFSAWorkflowStatus
			  , convert(VARCHAR(10), PFS.LastUpdated, 101) AS CurrentFSAWorkflowStatusDate
			  , isnull(POV.CurrentAllocatedLoanPercent, 0) AS CurrentAllocatedLoanPercent
			  , CASE
					WHEN FL.IsIssue = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS FSAIssueFlag
			  , convert(VARCHAR(10), FL.IssueCloseDt, 101) AS FSAIssueCloseDate
			  , convert(VARCHAR(10), FL.IssueOpenDt, 101) AS FSAIssueOpenDate
			  , datediff(dd, convert(VARCHAR(10), FL.IssueCloseDt, 101), convert(VARCHAR(10), FL.IssueOpenDt, 101)) AS TotalDaysOpen	
			  , convert(VARCHAR(10), cte.StatusDate, 101) 	as FirstSpreadCompleteDate, convert(VARCHAR(10), pD.StatusDate, 101)  as  FSADataWarehouseUploadDate
			  , CASE L.SpecialServiced WHEN  'Y' THEN 'YES' ELSE 'NO' END AS SpeciallyServiced
			  , CASE
				WHEN I.NonPerformingLoan  = 1 THEN
					'YES'
				ELSE
					'NO'
			    END AS  MSNonPerformingLoan 
			 , CASE
				WHEN I.SSNonPerformingLoan  = 1 THEN
					'YES'
				ELSE
					'NO'
			    END AS  SSNonPerformingLoan  
			  ,  CASE I.IsReo  WHEN  'Y' THEN 'YES' ELSE 'NO' END AS MSREO 
			  , CASE
				WHEN I.IsSSReo   = 1 THEN
					'YES'
				ELSE
					'NO'
			    END AS  SSREO  
			  , L.SpecialServicer
			 -- , '' as OSARReportingRequiredFlag
			  , CASE when month(@calendarPeriod) = 12 THEN
					case  I.OSARRequiredFlagAnnually WHEN 'Y' THEN 'Yes' ELSE 'No' END
				ELSE
					case  I.OSARRequiredFlagQuarterly  WHEN 'Y' THEN 'Yes' ELSE 'No' END
				END as OSARReportingRequiredFlag

			  , substring(I.InvestorDescription, charindex(':', I.InvestorDescription) + 1, len(I.InvestorDescription) - charindex(':', I.InvestorDescription)) AS DealID
			  , P.ProspectusIDNumber
			  , CASE
				WHEN L.Defeased  = 1 THEN
					'YES'
				ELSE
					'NO'
			    END AS Defeased
			  --, P.CreditNetLease   as CTL

			  , CASE
				WHEN P.CreditNetLease  = 1 THEN
					'YES'
				ELSE
					'NO'
			    END AS CTL
--			  , L.RelatedMortgageLoanAggregateBalance AS AggregateLoanBalance
			  , L.LoanBalance AS AggregateLoanBalance
				,CASE WHEN L.CASHFLAG ='1' THEN convert(VARCHAR(10), ASG.AssignDate, 101) 
				 
                 END AS 'SubOSARAssignmentDate'
			--	, (SELECT convert(VARCHAR(10), statusdate, 101)  from pfs_Pushed_StatusDate where PropertyID = P.PropertyID AND CalendarID = @Calendar   ) as FirstSpreadCompleteDate
				
				--,'' as FSADataWarehouseUploadDate
			--	, 
--					(SELECT TOP 1 isnull(aui.firstname,'') + ' ' +  isnull(aui.lastname,'')  from pfs_Signed_StatusDate pss
--						inner join appuserinfo aui on aui.UserID  = pss.UserID  
--					where pss.PropertyID = P.PropertyID AND pss.CalendarID = @Calendar   ) 

				,	ISNULL(spUserId.FirstName,'')+ISNULL(spUserId.LastName,'') as FirstSpreadCompletedBy
				
				
			  
			 
			 
			  
FROM	
	 INVESTOR  I  WITH(NOLOCK)  
	INNER JOIN LOAN L WITH(NOLOCK) ON L.InvestorID  = I.InvestorID
	INNER JOIN PROPERTY P WITH(NOLOCK)
				ON L.LoanID  = P.LoanID 
	INNER JOIN PROPERTYTYPES PT WITH(NOLOCK)
		ON PT.PropTypeCD = P.PropTypeCD	
	LEFT OUTER JOIN PROPERTYOVERVIEW POV WITH(NOLOCK)
		ON P.PROPERTYID = POV.PROPERTYID AND dbo.fncCalIDToCal(POV.CalendarId) =@calendarPeriod 
		LEFT OUTER JOIN PROPERTYFINANCIALSTATUS PFS WITH(NOLOCK)
		ON PFS.PROPERTYID = P.PROPERTYID AND  pfs.CalendarID = POV.CalendarID 
	LEFT OUTER JOIN FINANCIALTASKS FT WITH(NOLOCK)
		ON FT.FINANCIALTASKID = PFS.FINANCIALTASKID
	LEFT OUTER JOIN pfs_Signed_StatusDate cte  ON P.PropertyID =cte.PropertyId AND dbo.fncCalIDToCal(cte.Calendarid)= @calendarPeriod
	LEFT OUTER JOIN ASSIGNMENT ASG WITH(NOLOCK)
		ON dbo.fncCalIDToCal(ASG.CalendarId )= @calendarPeriod  AND ASG.PROPERTYID =  P.PROPERTYID and ASG.DocTypeID = 1
	 LEFT OUTER JOIN  AppUserInfo spUserId    ON    spUserId.UserId = cte.UserId

	LEFT OUTER JOIN OSARAPPROVAL OA WITH(NOLOCK)
		ON OA.AssignmentID = ASG.AssignmentID	
	LEFT OUTER JOIN ApprovalStatus AP ON AP.ApprovalStatusId = OA.ApprovalStatusId 
	LEFT OUTER JOIN FSIssueLog FL WITH(NOLOCK)

		ON FL.ASSIGNMENTID = ASG.ASSIGNMENTID
	LEFT OUTER JOIN COMPANY COMP  WITH(NOLOCK)
		ON COMP.COMPANYID = ASG.BUID
	LEFT OUTER JOIN FILENETDOCUMENTS FND WITH(NOLOCK)
		ON dbo.fncCalIDToCal(FND.CalendarId) =@calendarPeriod  AND  FND.PropertyId = P.PropertyID 
	LEFT OUTER JOIN  pfs_Pushed_StatusDate pD ON pD.PropertyId = PFS.PropertyID AND pD.CalendarId = pfs.calendarId
	LEFT OUTER JOIN AppUserInfo am ON am.UserID = I.AssetManager
	LEFT OUTER JOIN AppUserInfo pm ON pm.userId = I.PSRTeamLead
	LEFT OUTER JOIN AppUserInfo reviewer ON reviewer.UserID = Oa.ReviewerID
	LEFT OUTER JOIN AppUserInfo Apprvr ON Apprvr.UserID  = Oa.ApprovedBy 
	LEFT OUTER JOIN AppUserInfo Apprvr1 ON Apprvr1.UserID  =  OA.CreatedBy		
	LEFT OUTER JOIN AppUserInfo cd ON cd.UserID = I.CreditAnalyst
	LEFT OUTER JOIN LOAN LL WITH(NOLOCK) ON  LL.LoanID = L.LeadLoanID
WHERE
	(I.DealType = 'S' OR ASG.AssignmentID IS NOT NULL )AND (I.osarrequiredFlagQuarterly  = 'Y'
				   OR I.osarrequiredflagannually = 'Y' OR ASG.AssignmentID IS NOT NULL)
				   --AND P.bActiveProp = 1 
					AND (P.DocTypeID =1 or P.DocTypeID =3)-- and OA.OsarApprovalID is NOT NULL 
		--and L.LoanID = 39617
		
ORDER BY
	I.InvestorNumber
  , L.LoanNumber
  , P.PropertyNumber

SET NOCOUNT OFF
END
