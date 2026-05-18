SELECT * FROM case_field WHERE id_case LIKE 'AP%XX' ORDER BY ID_CASE DESC;
--
SELECT CASE_FIELD.ISN,
  ID_CASE,
  ID_SUBTYPE_CASE,
  CDE_PGM_CASE,
  CDE_TERM_CASE,
  NO_PROJ_SIC_CASE,
  CDE_STATUS_CASE,
  DESC_PROJ_CASE,
  CDE_ECON_IMPACT_CASE,
  ID_RELAT_PC_CASE,
  DTE_APPL_RECD_CASE,
  AMT_TOT_CONTRACT_CASE,
  DTE_OPERATIVE_CASE,
  CDE_FCG_CASE
FROM CASE_FIELD
WHERE ISN='00084054' ;
--
SELECT *
FROM request
WHERE CDE_TYPE_REQ    = 'SC'
AND CDE_STATUS_REQ    = 'AP'
AND NUM_LOAN_GUAR_REQ = '08085546XX0001';
--
--
SELECT *
FROM request
WHERE NUM_LOAN_GUAR_REQ = '08085546XX0001'
AND CDE_TYPE_REQ        = 'SC'
AND CDE_STATUS_REQ      = 'VE';
--
--
SELECT AMT_RPT_REPYMT_LGD
FROM LG_DISBURSEMENT
WHERE NUM_LOAN_GUAR_LGD = '08085546XX0001'
AND CDE_STAT_LGD        = 'P';
--
SELECT
  /* r.NUM_LOAN_GUAR_REQ,
  r.isn,
  sc.ISN_REQUEST_REQ,
  r.NUM_LOAN_GUAR_REQ,
  r.CDE_TYPE_REQ,
  r.CDE_STATUS_REQ, */
  SUM(sc.AMT_RPT_DISB_REQ),
  --sc.DATE_DUE_REQ,
  lg.DTE_DUE_LGI
FROM request r,
  REQUEST_GRP_SC sc,
  lg_installment lg
WHERE r.isn             = sc.ISN_REQUEST_REQ
AND sc.DATE_DUE_REQ     = lg.DTE_DUE_LGI
AND r.NUM_LOAN_GUAR_REQ = '08085546XX0001'
AND r.CDE_TYPE_REQ      = 'SC'
AND r.CDE_STATUS_REQ    = 'VE'
GROUP BY lg.DTE_DUE_LGI;
--
--
SELECT *
FROM REQUEST_GRP_SC
WHERE ISN IN (30490, 30491, 30492, 30521, 30641, 30687, 32663);
--
-- Add this to next --- 3.1
SELECT TO_CHAR(SUM(AMT_RPT_REPYMT_LGD), '9,999,999.00')
FROM LG_DISBURSEMENT
WHERE NUM_LOAN_GUAR_LGD = '08085546XX0001'
AND CDE_STAT_LGD        = 'P';
--
-- Add previous to here --- 3.1
SELECT AMT_ASSUMED_REPYMT_LGA
FROM LG_AGREEMENT
WHERE NUM_LOAN_GUAR_LGA = '08085546XX0001';
--
-- 3.2
SELECT SUM(sc.AMT_RPT_DISB_REQ),
  lg.DTE_DUE_LGI
FROM request r,
  REQUEST_GRP_SC sc,
  lg_installment lg
WHERE r.isn             = sc.ISN_REQUEST_REQ
AND sc.DATE_DUE_REQ     = lg.DTE_DUE_LGI
AND r.NUM_LOAN_GUAR_REQ = '08085546XX0001'
AND r.CDE_TYPE_REQ      = 'SC'
AND r.CDE_STATUS_REQ    = 'VE'
GROUP BY lg.DTE_DUE_LGI
ORDER BY lg.DTE_DUE_LGI DESC;
--
SELECT sc.AMT_RPT_DISB_REQ,
  lg.DTE_DUE_LGI
FROM request r,
  REQUEST_GRP_SC sc,
  lg_installment lg
WHERE r.isn              = sc.ISN_REQUEST_REQ
AND sc.DATE_DUE_REQ      = lg.DTE_DUE_LGI
AND lg.NUM_LOAN_GUAR_LGI = r.NUM_LOAN_GUAR_REQ
AND lg.CDE_SCHED_LGI     = 'AA'
AND r.NUM_LOAN_GUAR_REQ  = '08085546XX0001'
AND r.CDE_TYPE_REQ       = 'SC'
AND r.CDE_STATUS_REQ     = 'VE'
  --GROUP BY lg.DTE_DUE_LGI
ORDER BY lg.DTE_DUE_LGI DESC;
--
SELECT *
FROM LG_INSTALLMENT
WHERE NUM_LOAN_GUAR_LGI = '08085546XX0001'
AND CDE_SCHED_LGI       = 'AA'
ORDER BY DTE_DUE_LGI;
--
SELECT *
FROM request r
WHERE r.NUM_LOAN_GUAR_REQ = '08085546XX0001'
AND r.CDE_TYPE_REQ        = 'SC'
AND r.CDE_STATUS_REQ      = 'VE';
--
SELECT *
FROM REQUEST_GRP_SC sc
WHERE sc.ISN_REQUEST_REQ IN ( 30490, 30491, 30492, 30521, 30641, 30687, 32663)
ORDER BY sc.DATE_DUE_REQ;
--
SELECT TO_CHAR(SUM(sc.AMT_RPT_DISB_REQ), '9,999,999.00')
FROM REQUEST_GRP_SC sc
WHERE sc.ISN_REQUEST_REQ IN ( 30490);
--
SELECT TO_CHAR(sc.AMT_RPT_DISB_REQ, '9,999,999.00'),
  sc.DATE_DUE_REQ
FROM REQUEST_GRP_SC sc
WHERE sc.ISN_REQUEST_REQ IN ( 30490)
ORDER BY sc.DATE_DUE_REQ;
--
-- Allan -- Working query
SELECT r.isn,
  r.num_loan_guar_req,
  r.num_req,
  r.cde_status_req,
  rs.isn_request_req,
  rs.date_due_req,
  rs.AMT_RPT_DISB_REQ,
  num_loan_guar_lgi,
  cde_sched_lgi,
  l.dte_due_lgi
FROM request r,
  request_grp_sc rs,
  lg_installment l
WHERE cde_type_req      = 'SC'
AND cde_status_req      = 'VE'
AND num_loan_guar_req   = '08085546XX0001'
AND r.isn               = rs.isn_request_req
AND l.num_loan_guar_lgi = '08085546XX0001'
AND l.cde_sched_lgi     = 'AA'
AND l.dte_due_lgi       = rs.date_due_req
  --and num_req = 12019695
ORDER BY NUM_REQ,
  DATE_DUE_REQ;
--
SELECT date_due_req,
  amt_rpt_disb_req,
  amt_rpt_pymt_req,
  amt_rpt_out_req,
  amt_rpt_fee_req
FROM request_grp_sc
WHERE isn_request_req = 0000030490
ORDER BY date_due_req ;