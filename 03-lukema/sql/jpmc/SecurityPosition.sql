

/*
select * from MO_GMI_STG_SECURITY_POSITION
where 
PMKVAL > 643641
and PMKVAL < 643642
*/


/*
SELECT DISTINCT
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
                case when average is null then 1.0
                     else average
                end as ctpercent,
                PMULTF as multiplicationFactor
            FROM CGMIACTF
            JOIN IAPOSFF_1 ON AFIRM = PFIRM AND AOFFIC = POFFIC AND AACCT = PACCT
                        LEFT JOIN 
                        (
                            select (fraction / total) as average, CTFIRM as ZZFIRM, CTOFFI as ZZOFFI, CTACCT as ZZACCT, CTPCUS as ZZPCUS
                            from
                            (
                                SELECT CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT, SUM(CTMVUS) as total, CTPCNT * SUM(CTMVUS) as fraction
                                FROM IACATFF_1
                                JOIN IAPOSFF_1 ON CTFIRM=PFIRM AND CTOFFI=POFFIC AND CTACCT=PACCT AND CTATYP = PATYPE AND CTPCUS=PCUSIP
                                WHERE CTIRMR = 'I' AND PEXCH = ' ' AND PSTYPE IN (' ','B')
                                GROUP BY CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT
                            ) as LongHair
                        ) as CutHair
                        ON
                        (
                            AFIRM = ZZFIRM
                            AND AOFFIC = ZZOFFI 
                            AND AACCT = ZZACCT 
                            AND PCUSIP = ZZPCUS
                        ) 
            WHERE substring(PTRACE,5,1) != '*'
and PCUSIP = '9127953C3' 
and AACCT = '20908'
*/


/*
select CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT
from IACATFF_1
WHERE CTACCT = '20908'
and CTPCUS = '9127953C3'
GROUP BY CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT
*/



/*
                            select (fraction / total) as average, CTFIRM as ZZFIRM, CTOFFI as ZZOFFI, CTACCT as ZZACCT, CTPCUS as ZZPCUS
                            from
                            (
                                SELECT CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT, SUM(CTMVUS) as total, CTPCNT * SUM(CTMVUS) as fraction
                                FROM IACATFF_1
                                JOIN IAPOSFF_1 ON CTFIRM=PFIRM AND CTOFFI=POFFIC AND CTACCT=PACCT AND CTATYP = PATYPE AND CTPCUS=PCUSIP
                                WHERE CTIRMR = 'I' AND PEXCH = ' ' AND PSTYPE IN (' ','B')
                                GROUP BY CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT
                            ) as LongHair
*/



select * from
(
                                SELECT CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT, SUM(CTMVUS) as total, CTPCNT * SUM(CTMVUS) as fraction
                                FROM IACATFF_1
                                JOIN IAPOSFF_1 ON CTFIRM=PFIRM AND CTOFFI=POFFIC AND CTACCT=PACCT AND CTATYP = PATYPE AND CTPCUS=PCUSIP
                                WHERE 1 = 1
--and CTIRMR = 'I' 
--AND PEXCH = ' ' 
--AND PSTYPE IN (' ','B')
                                GROUP BY CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT
) as tmp
where CTACCT = '20908'
and CTPCUS = '9127953C3'



/*
 SELECT CTFIRM, CTOFFI, CTACCT, CTPCUS, CTPCNT, CTMVUS, SUM(CTMVUS) as total, CTPCNT * SUM(CTMVUS) as fraction
                                FROM IACATFF_1
                                JOIN IAPOSFF_1 ON CTFIRM=PFIRM AND CTOFFI=POFFIC AND CTACCT=PACCT AND CTATYP = PATYPE AND CTPCUS=PCUSIP
                                WHERE CTIRMR = 'I' AND PEXCH = ' ' AND PSTYPE IN (' ','B')
and CTACCT = '20908'
and CTPCUS = '9127953C3'
*/














