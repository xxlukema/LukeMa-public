

/*
-- 2.2 COAST GMI Account Tables (CGMIACTF)
SELECT 
                ID_SPN as spn, 
                AFIRM as firm,
                AOFFIC as office,
                AACCT as account,
                ACISC as code,
                YCLASS as clazz, 
                YSUBCL as subclazz,
                nameAddress = 
                case
                   when YNA2 is null
                      then YNA1 
                   else YNA1 || ' ' || YNA2 
                end,
                YCURAC as accountBaseCurrency, 
                RAGRPDTLR as groupMarginRule,
YFMPCT as fempct
            FROM CCSTSPNF
            JOIN IACSTXF_1 on ID_SPN = ASPN
            JOIN IACMFAI_1 on AFIRM = YFIRM and AOFFIC = YOFFIC and AACCT = YACCT
            JOIN IAACCTSU_1 on AFIRM = RAFIRM and AOFFIC = RAOFFICE and AACCT = RADTLACCT;
*/


/*
-- 2.3 GMI Money Line Table (CGMIMNYF)
SELECT 
                ID_SPN as spn, 
                AFIRM as firm, 
                AOFFIC as office, 
                AACCT as account, 
                MATYPE as accountType, 
                MCURAT as currencyCode, 
                MBAL as cashBalance, 
                MFIR as futuresInitialMargin, 
                MME as marginExcessDeficit, 
                MOTE as openTradeEquity, 
                MEIR as equitiesIntialMargin, 
                MCVTFB as conversRateToAcctBaseCurcy, 
                MPNDIV as pendingDividendAmount, 
                MPNINT as pendingInterestAmount, 
                MMRGVL as mktValueOfCollInBaseCurcy, 
                MTE as totalEquity, 
                MPNOTH as pendingOtherAmount
            FROM CGMIACTF
            JOIN IAMNYFF_1 on AFIRM = MFIRM and AOFFIC = MOFFIC and AACCT = MACCT
            WHERE MRECID in ('M','R')
*/


/*
-- 2.4 GMI Margin Summary Table (CMRGCLCF2)
SELECT 
	ID_SPN,
	AFIRM,
	AOFFIC,
	AACCT,
	BATYPE, 
        BEXCH, 
        BCCC, 
        BIFCT, 
        BEXIR
FROM CGMIACTF
JOIN IACLCF2_1 on AFIRM = BFIRM and AOFFIC = BOFFIC and AACCT = BACCNT;
*/


/*
-- 2.5 GMI Account Type Table (CACTTYPF)
SELECT 
	MFIRM,
	substring(MKEY1, 1,2) datype,
	substring(MDTL1,39,3) dcurat, 
        substring(MDTL1,3,20) dname,     
	substring(MDTL1,23,1) dseg, 
        substring(MDTL1,54,1) dscm, 
        substring(MDTL1,91,1) dusreg,      
	substring(MDTL1,34,2) dstmgrp
FROM IAMASTF_1 
WHERE MRID1 = 'AT'
AND MFIRM IN (SELECT AFIRM FROM CGMIACTF);
*/

-- IAMASTF_1 :
-- substr(k00001,1,1) -> mfirm
-- substr(k00001,2,2) -> mrid1
-- substr(k00001,4,7) -> mkey1
-- F00001 maps to the single field mdtl1


/*
-- 2.5 GMI Account Type Table (CACTTYPF)
SELECT
   MFIRM,                             -- firm
   substring(MKEY1,1,1) dclass,
   substring(MKEY1,2,1) dsubclass,
   substring(MKEY1,1,2) datype,
   substring(MDTL1,39,3) dcurat,      -- currencyCode
   substring(MDTL1,73,1) dseg,        -- segregated
   substring(MDTL1,3,20) dname,       -- name
   substring(MDTL1,54,1) dscm,        -- scm
   substring(MDTL1,91,1) dusreg,      -- usRegulation
   substring(MDTL1,34,2) dstmgrp,     -- statementGroupCode
   MRID1 dkey
FROM IAMASTF_1
WHERE (MRID1 = 'C '
       AND MFIRM || substring(MKEY1,1,1) || substring(MKEY1,2,1) in
          (SELECT AFIRM || YCLASS || YSUBCL FROM CGMIACTF))
OR   (MRID1 = 'AT' AND MFIRM IN (SELECT AFIRM FROM CGMIACTF))
*/


/*
SELECT
   MFIRM,                             -- firm
   substring(MKEY1,1,1)  as dclass,
   substring(MKEY1,2,1)  as dsubclass,
   substring(MKEY1,1,2)  as datype,
   substring(MDTL1,39,3) as dcurat,      -- currencyCode
   substring(MDTL1,73,1) as dseg,        -- segregated
   substring(MDTL1,3,20) as dname,       -- currencyCodeDescription
   substring(MDTL1,54,1) as dscm,        -- scm
   substring(MDTL1,91,1) as dusreg,      -- usRegulation
   substring(MDTL1,34,2) as dstmgrp,     -- statementGroupCode
   MRID1 as dkey
FROM IAMASTF_1
WHERE ((MRID1 = 'C '
       AND MFIRM || substring(MKEY1,1,1) || substring(MKEY1,2,1) in
          (SELECT AFIRM || YCLASS || YSUBCL FROM CGMIACTF))
OR   (MRID1 = 'AT' AND MFIRM IN (SELECT AFIRM FROM CGMIACTF)))   
--and substring(MDTL1,73,1) <> ' ' 
*/


/*
-- 2.6 GMI Currency Rate Table (CCUUATF)
SELECT
	substring(SCUSIP,3,3) as STOCUR, 
	substring(SCUSIP,6,3) as SFRMCUR, 
	SCLOSE, 
	SSDSC1
FROM IAPPMFF_1
WHERE SCUSIP like '##%'
ORDER BY SCUSIP;
*/


/*
--2.7
SELECT 
                ID_SPN as spn, 
                AFIRM as firm, 
                AOFFIC as office, 
                AACCT as account,
                YCLASS as accountClass,
                YSUBCL as accountSubClass, 
                PATYPE as accountType, 
                PCUSIP as cusip, 
                PSTYPE as securityTypeCode, 
                PSUBTY as securitySubTypeCode,
                PEXPDT as expiryDate,
                PTDATE as tradeDate,
                PTPRIC as tradePrice,
                PBS as buySellIndicator, 
                PQTY as quantity, 
                PSDSC1 as productDescription, 
                PSDSC2 as productDescriptionDeal, 
                PTDSC2 as tradeDescription, 
                PMKVAL as marketValue, 
                PEXCH as exchangeCode, 
                PTYPE as contractTypeCode,
                PPTYPE as productTypeCode,
                PCLOSE as closingPrice,
                PPRVCP as previousClosingPrice,
                PCURSY as productCurrencyCode,
                PMVARN as marginVariationRate,
                CTPCNT as ctpercent,
                PMULTF as multiplicationFactor
            FROM CGMIACTF
			JOIN IAPOSFF_1 on AFIRM = PFIRM and AOFFIC = POFFIC and AACCT = PACCT
			JOIN IACATFF on CTACCT=PACCT and CTATYP = PATYPE and CTPCUS = PCUSIP and CTFIRM = PFIRM and CTOFFI = POFFIC
			WHERE substring(PTRACE,5,1) != '*'
			AND PEXCH = ' ' and PSTYPE in (' ','B')
*/


/*
-- 2.8 GMI Product Table (CGMIPPMF)
SELECT 
			    SCUSIP as cusip, 
			    SSDSC1 as productDescription, 
			    SXCPRZ as couponRate
			FROM IAPOSFF_1 
			JOIN IAPPMFF_1 on PCUSIP = SCUSIP 
			WHERE PEXCH = ' ' and PSTYPE in (' ', 'B')
*/


/*
-- 2.9 GMI Trade Table (CGMITRNF)
SELECT
			    ID_SPN as spn,
			    AFIRM as firm,
			    AOFFIC as office,
			    AACCT as account,
			    YCLASS as accountClass,
			    YSUBCL as accountSubClass,
			    PRECID as recordIdentifier,
			    PATYPE as accountType,
			    PCUSIP as cusip,
			    PSTYPE as securityTypeCode,
			    PSUBTY as securitySubTypeCode,
			    PEXPDT as expiryDate,
			    PTDATE as tradeDate,
			    PTPRIC as tradePrice,
			    PBS as buySellIndicator,
			    PQTY as quantity,
			    PSDSC1 as productDescription,
			    PSDSC2 as productDescriptionDeal,
			    PTDSC2 as tradeDescription,
			    PMKVAL as marketValue,
			    PMULTF as multiplicationFactor,
			    PEXCH as exchangeCode,
			    PTYPE as contractTypeCode,
			    PPTYPE as productTypeCode,
			    PCLOSE as closingPrice,
			    PCURSY as productCurrencyCode,
			    PORDER as porder,
			    PMVARN as marginVariationRate,
			    substring(PSORTK,13,1) as sortCodeRecordId
			FROM CGMIACTF
			JOIN IAST4FF_1 on AFIRM = PFIRM and AOFFIC = POFFIC and AACCT = PACCT
			WHERE substring(PTRACE,5,1) != '*'
*/


/* **********
-- 2.10 GMI Date Table (CGMIPRMF)
SELECT substr(F00001,20,8) as cobdate
FROM "GMI.PARM" 
WHERE substr(f00001,1,1) = 'D'
*/


/* ****************
-- 2.10 GMI Date Table (CGMIPRMF) Alternative
SELECT 
    DATEDAY as cobDayDate,
    DATEMTH as previousMonthEndDate,
    DATENGT as cobNightDate,
    DATEEXT as currentBusinessDate
FROM IADATEF
*/



/*
-- 2.11	GMI Firm Profile Table (CGMIFRMF)
SELECT 
    MFIRM as DFIRM, 
    substring(MDTL1,16,35) as DNAME
--FROM IAMASTF_1
FROM PFKMAST
WHERE MRID1 = '  ' and substring(MKEY1,1,2) = '1A'
*/



/*
SELECT 
                ID_SPN as spn, 
                AFIRM as firm, 
                AOFFIC as office, 
                AACCT as account,
                YCLASS as accountClass,
                YSUBCL as accountSubClass, 
                PATYPE as accountType, 
                PCUSIP as cusip, 
                PSTYPE as securityTypeCode, 
                PSUBTY as securitySubTypeCode,
                PEXPDT as expiryDate,
                PTDATE as tradeDate,
                PTPRIC as tradePrice,
                PBS as buySellIndicator, 
                PQTY as quantity, 
                PSDSC1 as productDescription, 
                PSDSC2 as productDescriptionDeal, 
                PTDSC2 as tradeDescription, 
                PMKVAL as marketValue, 
                PEXCH as exchangeCode, 
                PTYPE as contractTypeCode,
                PPTYPE as productTypeCode,
                PCLOSE as closingPrice,
                PPRVCP as previousClosingPrice,
                PCURSY as productCurrencyCode,
                PMVARN as marginVariationRate,
                CTPCNT as ctpercent,
                PMULTF as multiplicationFactor
            FROM CGMIACTF
			JOIN IAPOSFF_1 on AFIRM = PFIRM and AOFFIC = POFFIC and AACCT = PACCT
			JOIN IACATFF_1 on CTACCT=PACCT and CTATYP = PATYPE and CTPCUS = PCUSIP and CTFIRM = PFIRM and CTOFFI = POFFIC
			WHERE substring(PTRACE,5,1) != '*'
			and CTIRMR = 'I'
			AND PEXCH = ' ' and PSTYPE in (' ','B')
*/

/*
-- 2.12  GMI Class/Sub-Class Table (CGMICLSF)
SELECT 
                MFIRM as firm,
                substring(MKEY1,1,1) clazz, 
                substring(MKEY1,2,1) subclazz, 
                substring(MDTL1,1,30) as classSubClassDescription,
                substring(MDTL1,73,1) as segregated 
            FROM IAMASTF_1
            WHERE MRID1 = 'C '
            AND MFIRM || substring(MKEY1,1,1) || substring(MKEY1,2,1) IN
                (SELECT AFIRM || YCLASS || YSUBCL FROM CGMIACTF)
*/



/*
-- 2.13	SDB Meta Table (IARFSHF)
SELECT 
			    TGTSYSNME as targetSystemTableName, 
			    convert(varchar(8) , 20000000 + REFDTE, 112) as lastRefreshDate, 
			    substring( convert( varchar(7), 9000000 + REFTIM), 2, 2) + ':' + substring( convert( varchar(7), 9000000 + REFTIM) , 4, 2) as lastRefreshTime
			FROM IARFSHF
                        
                        WHERE TGTSYSNME in
                                (
                                'CCSTSPNF',
                                'CGMIACTF',
                                'GMI.PARM',
                                'IAACCTSU_1',
                                'IACLCF2_1',
                                'IACMFAI_1',
                                'IACSTXF_1',
                                'IAMASTF_1',
                                'IAMNYFF_1',
                                'IAPOSFF_1',
                                'IAPPMFF_1',
                                'IARFSHF',
                                'IAST4FF_1',
                                --
                                'IAACCTSU',
                                'IACLCF2',
                                'IACMFAI',
                                'IACSTXF',
                                'IAMASTF',
                                'IAMNYFF',
                                'IAPOSFF',
                                'IAPPMFF',
                                'IARFSHF',
                                'IAST4FF'
                                )
                         
			ORDER BY lastRefreshDate DESC, lastRefreshTime DESC
*/


--select distinct TGTSYSNME as targetSystemTableName FROM IARFSHF


/* *****************
-- 3.1.1 BIFCT
SELECT 
	bifct
FROM cmrgclcf2
WHERE afirm||aoffic||aacct||batype in
(select afirm||aoffic||aacct||matype from nasser/cgmimnyf)
*/


/*
-- 3.2.2 DCURAT
SELECT 
	dcurat
FROM cacttypf
WHERE dfirm||datype in
(select bfirm||batype from gmiclcf2)
*/


/* *****************
-- 3.2.3 SCLOSE
SELECT 
	sclose
FROM ccurratf
WHERE ( sfrmcur in
( select dcurat from cacttypf ))
AND ( stocur in (
(select ycurac from CGMIACTF )))
*/


/* *****************
-- 3.3.1 SXCPRZ
SELECT 
	sxcprz
FROM cgmippmf
WHERE scusip = pcusip
*/


--truncate table CGMIACTF;


--select getdate() as cobdate;



/*
-- Coast SPNs
SELECT 
    CUSTOMER_SPN
FROM MO_COMBINED_CUSTOMER_SETUP
*/


/*
SELECT 
			    MFIRM as gmiFirmCode, 
			    substring(MDTL1,16,35) as gmiFirmName
			FROM IAMASTF_1   
			WHERE MRID1 = '  ' and substring(MKEY1,1,2) = '1A'
*/


--select * from mo_gmi_stg_account_type

/*
SELECT 
                ID_SPN as spn, 
                AFIRM as firm, 
                AOFFIC as office, 
                AACCT as account,
                YCLASS as accountClass,
                YSUBCL as accountSubClass, 
                PATYPE as accountType, 
                PCUSIP as cusip, 
                PSTYPE as securityTypeCode, 
                PSUBTY as securitySubTypeCode,
                PEXPDT as expiryDate,
                PTDATE as tradeDate,
                PTPRIC as tradePrice,
                PBS as buySellIndicator, 
                PQTY as quantity, 
                PSDSC1 as productDescription, 
                PSDSC2 as productDescriptionDeal, 
                PTDSC2 as tradeDescription, 
                PMKVAL as marketValue, 
                PEXCH as exchangeCode, 
                PTYPE as contractTypeCode,
                PPTYPE as productTypeCode,
                PCLOSE as closingPrice,
                PPRVCP as previousClosingPrice,
                PCURSY as productCurrencyCode,
                PMVARN as marginVariationRate,
                CTPCNT as ctpercent,
                PMULTF as multiplicationFactor
            FROM CGMIACTF
			JOIN IAPOSFF_1 on AFIRM = PFIRM and AOFFIC = POFFIC and AACCT = PACCT
			JOIN IACATFF on CTACCT=PACCT and CTATYP = PATYPE and CTPCUS = PCUSIP and CTFIRM = PFIRM and CTOFFI = POFFIC
			WHERE substring(PTRACE,5,1) != '*'
			AND PEXCH = ' ' and PSTYPE in (' ','B')
*/


/*
SELECT 
                ID_SPN as spn, 
                AFIRM as firm, 
                AOFFIC as office, 
                AACCT as account,
                YCLASS as accountClass,
                YSUBCL as accountSubClass, 
                PATYPE as accountType, 
                PCUSIP as cusip, 
                PSTYPE as securityTypeCode, 
                PSUBTY as securitySubTypeCode,
                PEXPDT as expiryDate,
                PTDATE as tradeDate,
                PTPRIC as tradePrice,
                PBS as buySellIndicator, 
                PQTY as quantity, 
                PSDSC1 as productDescription, 
                PSDSC2 as productDescriptionDeal, 
                PTDSC2 as tradeDescription, 
                PMKVAL as marketValue, 
                PEXCH as exchangeCode, 
                PTYPE as contractTypeCode,
                PPTYPE as productTypeCode,
                PCLOSE as closingPrice,
                PPRVCP as previousClosingPrice,
                PCURSY as productCurrencyCode,
                PMVARN as marginVariationRate,
           --     CTPCNT as ctpercent,
                PMULTF as multiplicationFactor
            FROM CGMIACTF
			JOIN IAPOSFF_1 on AFIRM = PFIRM and AOFFIC = POFFIC and AACCT = PACCT
		--	left outer JOIN IACATFF_1 on CTACCT=PACCT and CTATYP = PATYPE and CTPCUS = PCUSIP and CTFIRM = PFIRM and CTOFFI = POFFIC
			WHERE substring(PTRACE,5,1) != '*'
order by 1
		--	and CTIRMR = 'I'
                FETCH first row only 
*/



--SELECT top 1 CTPCNT
SELECT CTPCNT
FROM IACATFF_1
JOIN CGMIACTF ON CTFIRM=AFIRM AND CTOFFI=AOFFIC AND CTACCT=AACCT 
--JOIN IACATFF_1 on CTACCT=PACCT and CTATYP = PATYPE and CTPCUS = PCUSIP and CTFIRM = PFIRM and CTOFFI = POFFIC
JOIN IAPOSFF_1 on AFIRM = PFIRM and AOFFIC = POFFIC and AACCT = PACCT
--AND CTATYP = PATYPE 
--AND CTPCUS=PCUSIP
WHERE CTIRMR = 'I' 
--FETCH first row only 
--AND PEXCH = ' ' 
--AND PSTYPE IN (' ','B')



--sp_help CGMIACTF




