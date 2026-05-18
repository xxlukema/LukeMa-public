
ALTER PROCEDURE "dbo"."USP_RptOSARWorkFlow" (@Client    INT, @Calendar NVARCHAR(20)) AS

BEGIN
SET NOCOUNT ON
DECLARE @calendarPeriod datetime
SET @calendarPeriod  = dbo.fncCalIDToCal(@Calendar)
;with pfs_Signed_StatusDate (StatusDate,PropertyID,CalendarID,UserID) as 
(
 SELECT Min(statusPfs.StatusDate), statusPfs.PropertyID,statusPfs.CalendarID, statusPfs.UserID 
						 FROM Client cmg  INNER JOIN Calendar ca ON ca.ClientID = cmg.ClientID 
							INNER JOIN PropertyFinancialStatusHistory statusPfs  ON statusPfs.CalendarID = ca.CalendarID  
							
		
						
					 WHERE
						 statusPfs.FinancialTaskID = 5  AND cmg.CMGClientId = @Client AND dbo.fncCalIDToCal(ca.CalendarId) =@calendarPeriod  
						GROUP BY statusPfs.PropertyID,statusPfs.CalendarID,statusPfs.UserID 
					
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

			  , I.SecuritizationDate
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
			  , (SELECT LL.loannumber from Loan LL WHERE LL.LoanID = L.LeadLoanID) as LeadLoanFlag
			  , L.LoanNoteType
			  , convert(VARCHAR(10), ASG.AssignDate, 101) AS FSAAssignmentDate
			  , FND.FSCollected AS FSWFBReceived
			  , FND.RRCollected AS RRWFBReceived
			  , CASE
					WHEN FND.RRImageDate IS NOT NULL THEN
						'YES'
					ELSE
						'NO'
				END AS RRImage
			  , CASE
					WHEN FND.FSImageDate IS NOT NULL THEN
						'YES'
					ELSE
						'NO'
				END AS FSImage
			  , FND.OSARCollected AS OSARWFBReceived
			  , FND.OSARImageDate		 
			  , COMP.COMPANYNAME AS FSAAssignedBusinessUnit

			   , CASE
					WHEN OA.IsFlagForReview = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS AnalystReviewRequired
			  , CASE
					WHEN OA.ApprovalStatusId = 1 THEN
						'YES'
					ELSE
						'NO'
				END AS ApprovalStatus
			  , OA.ApprovedDate as StatusModifiedDate
			  , OA.ApprovedBy as StatusModifiedBy
			 , CASE
					WHEN L.SubServiced = 'Y' THEN
						'YES'
					ELSE
						'NO'
				END AS SubServiced
			  , L.SubServicerName as SubServicerCode
	

				,CASE WHEN L.CASHFLAG ='1' THEN 'CASH'
				  WHEN L.CASHFLAG ='L' THEN 'NON-CASH'
				 ELSE 'NON-SUBSERVICED' 
                 END AS 'CashNonCashSubservicer'


			  , P.SubServicer as SubServicerName
			  , L.MaturityDate
			  , @calendarPeriod	as CalendarPeriod	
			  , P.StatusCode as PropertyStatusCode
			  , P.PropertyStatusEffectiveDate AS PropertyStatusCodeEffectiveDate
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
			  , '' as OSARReportingRequiredFlag
			  , substring(I.InvestorDescription, charindex(':', I.InvestorDescription) + 1, len(I.InvestorDescription) - charindex(':', I.InvestorDescription)) AS DealID
			  , P.ProspectusIDNumber
			  , CASE
				WHEN L.Defeased  = 1 THEN
					'YES'
				ELSE
					'NO'
			    END AS Defeased
			  , '' as CTL
			  , L.RelatedMortgageLoanAggregateBalance AS AggregateLoanBalance
				,CASE WHEN L.CASHFLAG ='1' THEN convert(VARCHAR(10), ASG.AssignDate, 101) 
				 
                 END AS 'SubOSARAssignmentDate'
		
				, (SELECT TOP 1 isnull(aui.firstname,'') + ' ' +  isnull(aui.lastname,'')  from pfs_Signed_StatusDate pss
						inner join appuserinfo aui on aui.UserID  = pss.UserID  
					where pss.PropertyID = P.PropertyID AND pss.CalendarID = @Calendar   ) as FirstSpreadCompletedBy
							  
FROM	
	 PROPERTY P WITH(NOLOCK)  
	INNER JOIN LOAN L WITH(NOLOCK) ON P.LOANID = L.LOANID
	INNER JOIN INVESTOR  I WITH(NOLOCK)
				ON L.INVESTORID = I.INVESTORID	
	INNER JOIN PROPERTYTYPES PT WITH(NOLOCK)
		ON PT.PropTypeCD = P.PropTypeCD	
	LEFT OUTER JOIN PROPERTYOVERVIEW POV WITH(NOLOCK)
		ON P.PROPERTYID = POV.PROPERTYID AND dbo.fncCalIDToCal(POV.CalendarId) =@calendarPeriod 
		LEFT OUTER JOIN PROPERTYFINANCIALSTATUS PFS WITH(NOLOCK)
		ON PFS.PROPERTYID = P.PROPERTYID AND  pfs.CalendarID = POV.CalendarID 
	LEFT OUTER JOIN FINANCIALTASKS FT WITH(NOLOCK)
		ON FT.FINANCIALTASKID = PFS.FINANCIALTASKID
	LEFT OUTER JOIN pfs_Signed_StatusDate cte  ON P.PropertyID =cte.PropertyId 
	LEFT OUTER JOIN ASSIGNMENT ASG WITH(NOLOCK)
		ON ASG.CalendarId = POV.CalendarId AND ASG.PROPERTYID =   POV.PROPERTYID
	LEFT OUTER JOIN OSARAPPROVAL OA WITH(NOLOCK)
		ON OA.AssignmentID = ASG.AssignmentID	
	LEFT OUTER JOIN FSIssueLog FL WITH(NOLOCK)
		ON FL.ASSIGNMENTID = ASG.ASSIGNMENTID
	LEFT OUTER JOIN COMPANY COMP  WITH(NOLOCK)
		ON COMP.COMPANYID = ASG.BUID
	LEFT OUTER JOIN FILENETDOCUMENTS FND WITH(NOLOCK)
		ON FND.CalendarId = PFS.CalendarId AND  FND.PropertyId = PFS.PropertyID 
	LEFT OUTER JOIN  pfs_Pushed_StatusDate pD ON pD.PropertyId = PFS.PropertyID AND pD.CalendarId = pfs.calendarId
	LEFT OUTER JOIN AppUserInfo am ON am.UserID = I.AssetManager
	LEFT OUTER JOIN AppUserInfo pm ON pm.userId = I.PSRTeamLead
	LEFT OUTER JOIN AppUserInfo reviewer ON reviewer.UserID = Oa.ReviewerID
	LEFT OUTER JOIN AppUserInfo cd ON cd.UserID = I.CreditAnalyst
WHERE
	I.DealType = 'S' AND I.osarrequiredFlagQuarterly  = 'Y'
				   AND I.osarrequiredflagannually = 'Y'
				   AND P.bActiveProp = 1 
ORDER BY
	I.InvestorNumber
  , L.LoanNumber
  , P.PropertyNumber

SET NOCOUNT OFF
END
