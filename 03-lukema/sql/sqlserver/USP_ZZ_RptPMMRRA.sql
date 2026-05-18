
ALTER PROCEDURE "dbo"."USP_RptPMMRRA" (@Client    INT, @Calendar NVARCHAR(20)) AS
/*********************************************************************************************************
	DESCRIPTION:  	creates recordset of Mannual Assignments
	CREATED:  	2/29/2012 
	EXEC [dbo].[USP_RptPMMRRA]1,71
**********************************************************************************************************/

BEGIN

SET NOCOUNT ON 

declare diff int
diff = 

;WITH cte (RentRollID,ExpireLease)as (
SELECT ROV.RentRollID
	 , CASE
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) < 7 THEN
			   'Expiring0to6Months'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) > 6 AND datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) < 10 THEN
			   'Expiring6to9Months'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) > 9 AND datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) < 13 THEN
			   'Expiring9to12Months'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) < 13 THEN
			   'ExpiredorExpiringWithin12Months'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) > 12 THEN
			   'Expiringgreaterthan12Months'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) > 0 AND datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) < 12 THEN
			   'ExpYr'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) > 12 AND datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) < 25 THEN
			   'ExpYr2'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) > 25 AND datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) < 37 THEN
			   'ExpYr3'
		   WHEN datediff(MONTH, convert(VARCHAR(10), getdate(), 101), convert(VARCHAR(10), RRD.EndDate, 101)) > 25 THEN
			   'ExpYr4Plus'
	   END AS ExpireLease
FROM
	RENTROLLDETAILS  RRD (NOLOCK)
	INNER JOIN RENTROLLoverview ROV (NOLOCK)
		ON ROV.RENTROLLID = RRD.RENTROLLID
WHERE
	dbo.fncCalIDToCal(ROV.CALENDARID) = dbo.fncCalIDToCal(@Calendar)),
GrouperVal  (rentRollId,expirelease,total)as
(
	SELECT rentRollId,expirelease,count(*) FROM  cte Group BY  rentRollId,expirelease
),
LeaseInfo (rentRollId,exp0to6,exp6to9,exp9to12,expin12,expG12,ExpYr,ExpYr2,ExpYr3,ExpYr4)as
(
	SELECT rentRollId,
    SUM(CASE WHEN expirelease =  'Expiring0to6Months' THEN total ELSE 0 END) AS Q1,
    SUM(CASE WHEN expirelease = 'Expiring6to9Months' THEN total ELSE 0 END) AS Q2,
    SUM(CASE WHEN expirelease =  'Expiring9to12Months' THEN total ELSE 0 END) AS Q3,
    SUM(CASE WHEN expirelease =  'ExpiredorExpiringWithin12Months' THEN total ELSE 0 END) AS Q4,
    SUM(CASE WHEN expirelease =  'Expiringgreaterthan12Months' THEN total ELSE 0 END) AS Q5,
    SUM(CASE WHEN expirelease =  'ExpYr' THEN total ELSE 0 END) AS Q6,
    SUM(CASE WHEN expirelease =  'ExpYr2' THEN total ELSE 0 END) AS Q7,
    SUM(CASE WHEN expirelease =  'ExpYr3' THEN total ELSE 0 END) AS Q8, 
    SUM(CASE WHEN expirelease =  'ExpYr4Plus' THEN total ELSE 0 END) AS Q9 
  FROM GrouperVal
-- additional where clause goes here...
GROUP BY rentRollId
),
PropertyCounter (LoanId,PropCount) AS
(
   Select LoanId,count(*) from Property(NOLOCK)  GROUP BY LoanID  
) 
SELECT DISTINCT L.LoanNumber
							 , P.ProspectusIDNumber
							 , P.PropertyNumber
							 , I.InvestorNumber
							 , PropCount AS PropertyCount
							 , PT.PropertyType
							 , P.PropertyName
							 , isnull(P.propAddr1,'') + ' ' + isnull(P.propAddr2,'') as  PropertyStreet
							 , P.propState AS PropertyState
							 , P.propCity AS PropertyCity
							 , CASE
								   WHEN len(rtrim(P.propzip)) = 9 THEN
									   left(P.propzip, 5) + '-' + right(rtrim(P.propzip), 4)
								   ELSE
									   P.propzip
							   END AS PropertyZipCode
							 , P.YearBuilt
							 , P.YearRenovated
							 , L.CurrentPrincipleBalance
							 , POV.CurrentAllocatedLoanPercent
							 , P.NetRentableSQFT AS NetSquareFeetAtContribution
							 , convert(VARCHAR(10), I.SecuritizationDate, 101) AS SecuritizationDate
							 ,  convert(VARCHAR(10), L.MaturityDate, 101) AS MaturityDate 
							 , L.IOtoPIDate
							 , L.DSCRTrigger AS LockboxReserveTriggerYN
							 , CASE
								   WHEN L.SubServiced = 'Y' THEN
									   'YES'
								   ELSE
									   'NO'
							   END AS SubServiced
--							 , CASE
--								   WHEN L.CashFlag = 'Y' THEN
--									   'Cash'
--								   ELSE
--									   'Non-Cash'
--							   END AS CashNonCashSubservicer
							
							,CASE WHEN L.CASHFLAG ='1' THEN 'CASH'
											 WHEN L.CASHFLAG ='L' THEN 'NON-CASH'
											ELSE 'NON-SUBSERVICED' 
							 END AS CashNonCashSubservicer	

							 , (select loannumber from loan where loanid = L.LeadLoanID) AS Groups
							 , L.LoanNoteType AS GroupOSARCode
							 , CASE
								   WHEN L.SpecialServiced = 'Y' THEN
									   'Yes'
								   ELSE
									   'No'
							   END AS SpeciallyServiced
							 , L.SpeciallyServicedInDate AS SpecialServiceLoanBeginDate
							 , L.SpeciallyServicedOutDate AS SpecialServiceLoanEndDate
							 ,P.DefeasanceDate
							 , P.StatusCode AS PropertyStatusCode
							 , CASE
								   WHEN P.WatchList = 1 THEN
									   'Yes'
								   ELSE
									   'No'
							   END AS Watchlisted
							 , convert(VARCHAR(10), L.WLDateAdded, 101) AS WatchlistAddDate
							 , L.WLTriggers AS WatchlistTriggers
							 , '' AS PSAFinancialsAnnualDueDate
							 , convert(VARCHAR(10), P.AssumptionDate, 101) AS AssumptionClosingDate
							, CASE
								   WHEN P.CreditNetLease = 1 THEN
									   'YES'
								   ELSE
									   'NO'
							   END AS CreditNetLease
							 , CASE
								   WHEN  P.GroundLease = 1 THEN
									   'Yes'
								   ELSE
									   'No'
							   END AS GroundLease
							
							 ,  UW.UWEGI AS UWEGI
							 , UW1.UWTOE  AS UWTOE
							 , (UW.UWEGI - UW1.UWTOE)  AS UWNOI
							 
							, CASE
								   WHEN UW3.UWDS = 0 THEN
									   0
								   ELSE
									  ((UW.UWEGI - (UW1.UWTOE + UW2.UWCE) )/UW3.UWDS )
							   END AS UWDSCR_NCF

							--,  ((UW.UWEGI - (UW1.UWTOE + UW2.UWCE) )/UW3.UWDS ) AS UWDSCR_NCF
							
							 ,(SELECT POH.Occupancy  FROM PropertyOverviewHistory POH where POH.FiscalYear =0 AND POH.PropertyID =P.PropertyID ) AS UWOccupancy
							
							 , F.Frequency AS Period
							 , CMSF.FiscalYear AS StatementClassification_yr
							 , (SELECT convert(VARCHAR(10), POV.StatementBegin , 101) + ' - ' + convert(VARCHAR(10), POV.Statementend, 101)) AS NumberofMonthsCovered
							 , CMSF.EGI
							 , CMSF.TotalExpense AS TOE
							 , CMSF.NOI
							 , CMSF.TotalCapital AS TotalCapitalExpenditures
							 , CMSF.DebtService AS TotalDebtService
							 , POV.Occupancy AS OccupancyRate
							 , POV.AvgDailyRate AS AverageDailyRate
							 , POV.RevenuePerAvgRoom AS RevenuePerAvgRoom
							 , POV.AvgRentalRate AS AverageRentalRate
							 , convert(VARCHAR(10), Y.StatusDate, 101) AS eServicerUploadDate
							 , convert(VARCHAR(10), FND.RRImageDate, 101) AS RR_WFB_Recieved
							 , convert(VARCHAR(10), Z.lastupdated, 101) AS LeaseStartDate
							 , convert(VARCHAR(10), W.lastupdated, 101) AS LeaseEndDate
							,ExVal.exp0to6 AS Expiring0to6Months,
							ExVal.exp6to9 AS Expiring6to9Months,
							ExVal.exp9to12 AS Expiring9to12Months,
							ExVal.expin12 AS ExpiredorExpiringWithin12Months,
							ExVal.expG12  AS Expiringgreaterthan12Months,
							ExVal.ExpYr AS ExpYr,
							ExVal.ExpYr2 AS ExpYr2,
							ExVal.ExpYr3 AS ExpYr3,
							ExVal.ExpYr4 AS ExpYr4Plus 
							, CMSF.DSCRNOI AS DSCR
							 , POV.Occupancy
							 , convert(VARCHAR(10), POV.OccupancyDate, 101) OccupancyDate
							 , convert(VARCHAR(10), X.StatusDate, 101) AS ApprovedByDate
							 ,  Isnull(APP.FirstName, ' ') + ' ' +  Isnull(APP.LastName,' ') as StatusModifiedBy
							 , convert(VARCHAR(10), X.statusdate, 101)  AS PWC_COMPLETED_DATE
							
							 ,Isnull(APP1.FirstName,' ') + ' ' + isnull(APP1.LastName,' ') as PSRAnalyst
							 ,isnull(APP2.FirstName,' ') + ' ' + isnull(APP2.LastName,' ') as AssetManager
							 ,isnull(APP3.FirstName,' ') + ' ' + isnull(APP3.LastName,' ') as CreditAnalyst
							 , RRD.PercentageofTotal AS 'percentofPropertyforthistenant'
							 , '' AS 'percentofpropertyexpiringwithinoneyear'
							 , '' AS 'Propertieswithatleast30percentofNRAexpiring'
							 , '' AS 'TenantpercentofProperty'
							 , T.TenantName
							 , convert(VARCHAR(10), ROV.RentRollDate, 101) AS RentRollDate
							 , substring(I.InvestorDescription, charindex(':', I.InvestorDescription) + 1, len(I.InvestorDescription) - charindex(':', I.InvestorDescription)) AS DealId
							 , I.InvestorLongName
							 , '' AS ReportedSqFt
							 , ROV.PercentOccupancy
							 , RRD.TenantDescription
							 , RRD.NetSQFT
							 , RRD.PercentageofTotal
							 , RRD.SpaceNumber
							 , RRD.MonthlyRent
							 , RRD.MonthlyRent AS MonthlyRentperSF
							 , RRD.MTM
							 ,L.RelatedMortgageLoanAggregateBalance AS AgregateLoanBalance


			   FROM
			   
				   CMGCLIENT CMGC (NOLOCK )
				   INNER JOIN CLIENT C (NOLOCK )
					   ON C.CMGClientId = CMGC.CMGClientId
				   INNER JOIN INVESTOR I (NOLOCK )
					   ON I.CLIENTID = C.CLIENTID
				   INNER JOIN LOAN L (NOLOCK )
					   ON L.INVESTORID = I.INVESTORID
				   INNER JOIN PROPERTY P (NOLOCK )
					   ON P.LOANID = L.LOANID
				   INNER JOIN CALENDAR CAL (NOLOCK )
					   ON CAL.CLIENTID = C.CLIENTID
					   INNER JOIN PropertyCounter pc  ON pc.LoanId = L.LoanID  
				  LEFT OUTER JOIN PROPERTYOVERVIEW POV	 (NOLOCK )			 
					   ON P.PROPERTYID = POV.PROPERTYID AND POV.CalendarId = CAL.CalendarId
				  LEFT OUTER JOIN CMSAFINANCIALSUMMARY CMSF (NOLOCK )
					   ON CMSF.PROPERTYID = P.PROPERTYID AND CMSF.CalendarId = CAL.CalendarId
				   INNER JOIN FREQUENCY F (NOLOCK )
					   ON F.FREQUENCYCD = CMSF.FREQUENCYCD
				   LEFT OUTER JOIN FILENETDOCUMENTS FND (NOLOCK )
					   ON FND.CalendarId = Cal.CalendarId AND FND.PropertyID = P.PropertyID and FND.CalendarId = CAL.CalendarID
				   LEFT OUTER JOIN ASSIGNMENT ASS (NOLOCK )
					   ON ASS.CalendarId = CAL.CalendarId AND ASS.PropertyID = P.PropertyID and ASS.DocTypeID =2
				   LEFT OUTER JOIN OSARAPPROVAL OA (NOLOCK )
					   ON OA.AssignmentID = ASS.AssignmentID
				   INNER JOIN PROPERTYTYPES PT (NOLOCK )
					   ON PT.PropTypeCD = P.PropTypeCD
				   LEFT OUTER JOIN RENTROLLoverview ROV (NOLOCK )
					   ON ROV.PROPERTYID = P.PROPERTYID AND ROV.CALENDARID = CAL.CALENDARID
				   LEFT OUTER JOIN RENTROLLDETAILS RRD (NOLOCK )
					   ON RRD.RENTROLLID = ROV.RENTROLLID
					LEFT OUTER JOIN  LeaseInfo ExVal  
					ON ExVal.RentRollId = ROV.RentRollID  
				   LEFT OUTER JOIN TENANT T (NOLOCK )
					   ON T.TENANTID = RRD.TENANTID
				   LEFT OUTER JOIN (SELECT top 1 statusdate
											   , propertyid
											   , Userid
									FROM
										PropertyFinancialStatusHistory (NOLOCK )
									WHERE
										FinancialTaskID = 5
										AND dbo.fncCalIDToCal(CALENDARID) = dbo.fncCalIDToCal(@Calendar) ORDER BY StatusDate desc
									) AS X
					   ON X.propertyId = p.propertyid
				   LEFT OUTER JOIN (SELECT TOP 1 StatusDate
											   , userid
											   , propertyid
									FROM
										PropertyFinancialStatusHistory (NOLOCK )
									WHERE
										FinancialTaskID = 6
										AND dbo.fncCalIDToCal(CALENDARID) = dbo.fncCalIDToCal(@Calendar) ORDER BY StatusDate desc
									) AS Y
					   ON Y.propertyId = P.propertyid
				   LEFT OUTER JOIN (SELECT  rh.lastupdated
											   , rov.Propertyid
									FROM
										Rentrollhistory rh
										INNER JOIN rentrolloverview rov
											ON rov.rentrollid = rh.rentrollid
									WHERE
										rh.rentrollstatusid = 2
										AND dbo.fncCalIDToCal(ROV.CALENDARID) = dbo.fncCalIDToCal(@Calendar)) AS Z
					   ON Z.Propertyid = P.propertyid
				   LEFT OUTER JOIN (SELECT min(ra.lastupdated) AS lastupdated
										 , r.propertyid
									FROM
										Rentrollhistory ra
										INNER JOIN (SELECT  rh.rentrollid
															   , rh.RentRollHistoryID
															   , rh.LastUpdated
															   , rov.propertyid AS propertyid
													FROM
														Rentrollhistory rh
														INNER JOIN rentrolloverview rov
															ON rov.rentrollid = rh.rentrollid
													WHERE
														rh.rentrollstatusid = 2
														AND dbo.fncCalIDToCal(ROV.CALENDARID) = dbo.fncCalIDToCal(@Calendar)) AS r
											ON r.RentRollID = ra.RentRollID AND ra.RentRollHistoryID > r.RentRollHistoryID AND ra.rentrollstatusid != 2
									GROUP BY
										r.propertyid) AS W
					   ON W.propertyid = P.propertyid
	
					LEFT OUTER JOIN
					(SELECT SUM(Isnull(OH.NetValue,0)) AS UWEGI, OH.PropertyID    FROM OSARHistory OH 
										INNER JOIN CMSALineItems CM ON CM.CMSALineItemID = OH.CMSALineItemID 
										WHERE CM.AcctTypeCD ='I' AND OH.FiscalYear=0  GROUP BY OH.PropertyID 
					) AS UW
					ON UW.PROPERTYID = P.PropertyID 

					
					LEFT OUTER JOIN
					(SELECT SUM(Isnull(OH.NetValue,0)) AS UWTOE, OH.PropertyID    FROM OSARHistory OH 
										INNER JOIN CMSALineItems CM ON CM.CMSALineItemID = OH.CMSALineItemID 
										WHERE CM.AcctTypeCD ='E' AND OH.FiscalYear=0  GROUP BY OH.PropertyID 
					) AS UW1
					ON UW1.PROPERTYID = P.PropertyID 

					LEFT OUTER JOIN
					(SELECT SUM(Isnull(OH.NetValue,0)) AS UWCE, OH.PropertyID    FROM OSARHistory OH 
										INNER JOIN CMSALineItems CM ON CM.CMSALineItemID = OH.CMSALineItemID 
										WHERE CM.AcctTypeCD ='C' AND OH.FiscalYear=0  GROUP BY OH.PropertyID 
					) AS UW2
					ON UW2.PROPERTYID = P.PropertyID 

					LEFT OUTER JOIN
					(SELECT SUM(Isnull(OH.NetValue,0)) AS UWDS, OH.PropertyID    FROM OSARHistory OH 
										INNER JOIN CMSALineItems CM ON CM.CMSALineItemID = OH.CMSALineItemID 
										WHERE CM.AcctTypeCD ='D' AND OH.FiscalYear=0  GROUP BY OH.PropertyID 
					) AS UW3
					ON UW3.PROPERTYID = P.PropertyID 
					
					
					LEFT OUTER JOIN APPUSERINFO APP on APP.Userid = X.Userid  
					LEFT OUTER JOIN APPUSERINFO APP1 on APP1.Userid = I.PSRAnalyst 
					LEFT OUTER JOIN APPUSERINFO APP2 on APP2.Userid =  I.AssetManager					
					LEFT OUTER JOIN APPUSERINFO APP3 on APP3.Userid =  I.CreditAnalyst 

			   WHERE
				   dbo.fncCalIDToCal(CAL.CALENDARID) = dbo.fncCalIDToCal(@Calendar)
				   AND CMGC.CMGClientId = @Client
				   AND (I.DealType = 'S' )--OR ASS.AssignmentID IS NOT NULL )
				  AND (	I.osarrequiredFlagQuarterly  = 'Y' 
						OR	 I.osarrequiredflagannually = 'Y' --OR ASS.AssignmentID IS NOT NULL
					  )
				   AND P.bActiveProp = 1
				   AND P.DocTypeID in(2,3) 
				--	and L.LoanNumber = 700201194
				  ORDER BY I.InvestorNumber, L.LoanNumber ,P.PropertyNumber   

SET NOCOUNT OFF
END
