
/*
select PEXCH, PSTYPE, *
from IAPOSFF_1
where PEXCH = ' '
*/



/*
SELECT CTPCNT
FROM IACATFF_1
JOIN
(
    SELECT * 
    FROM
    (
        SELECT ID_SPN, AFIRM, AOFFIC, AACCT, YCLASS, YSUBCL, PATYPE, PCUSIP, PSTYPE, PSUBTY, PTDATE, PTPRIC, PBS, PQTY, PSDSC1, PSDSC2, 
        PTDSC2, PMKVAL, PMULTF, PEXCH, PPTYPE, PCLOSE, PPRVCP, PCURSY, PMVARN
        FROM CGMIACTF
        JOIN IAPOSFF_1 ON AFIRM = PFIRM AND AOFFIC = POFFIC AND AACCT = PACCT
        WHERE substring(PTRACE,5,1) != '*'
    ) as POSITINS
    where PEXCH = ' ' and PSTYPE in (' ', 'B')
) as HOLDINGS
ON CTFIRM = AFIRM 
and CTOFFI = AOFFIC 
and CTACCT = AACCT 
and CTATYP = PATYPE 
and CTPCUS = PCUSIP
WHERE CTIRMR = 'I' 
*/


/*
SELECT distinct ID_SPN, AFIRM, AOFFIC, AACCT, YCLASS, YSUBCL, PATYPE, PCUSIP, PSTYPE, PSUBTY, PTDATE, PTPRIC, PBS, PQTY, PSDSC1, PSDSC2, 
        PTDSC2, PMKVAL, PMULTF, PEXCH, PPTYPE, PCLOSE, PPRVCP, PCURSY, PMVARN
        FROM CGMIACTF
        JOIN IAPOSFF_1 ON AFIRM = PFIRM AND AOFFIC = POFFIC AND AACCT = PACCT
        WHERE substring(PTRACE,5,1) != '*'
*/

--drop table tmp_luke



/*
SELECT distinct ID_SPN, AFIRM, AOFFIC, AACCT, YCLASS, YSUBCL, PATYPE, PCUSIP, PSTYPE, PSUBTY, PTDATE, PTPRIC, PBS, PQTY, PSDSC1, PSDSC2, 
        PTDSC2, PMKVAL, PMULTF, PEXCH, PPTYPE, PCLOSE, PPRVCP, PCURSY, PMVARN, 
CTPCNT, CTIRMR
        FROM CGMIACTF
        JOIN IAPOSFF_1 ON AFIRM = PFIRM AND AOFFIC = POFFIC AND AACCT = PACCT
left join IACATFF_1
ON (CTFIRM = AFIRM 
and CTOFFI = AOFFIC 
and CTACCT = AACCT 
and CTATYP = PATYPE 
and CTPCUS = PCUSIP
and CTIRMR = 'I'
)
WHERE substring(PTRACE,5,1) != '*'
*/



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
                CTPCNT as ctpercent,
                PMULTF as multiplicationFactor,
                CTIRMR as ctirmr
            FROM CGMIACTF
			JOIN IAPOSFF_1 on AFIRM = PFIRM and AOFFIC = POFFIC and AACCT = PACCT
			LEFT JOIN IACATFF_1
			ON (CTFIRM = AFIRM 
				and CTOFFI = AOFFIC 
				and CTACCT = AACCT 
				and CTATYP = PATYPE 
				and CTPCUS = PCUSIP
				and CTIRMR = 'I')
			WHERE substring(PTRACE,5,1) != '*'





