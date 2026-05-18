SELECT DISTINCT
/*
    p_stmt_cob_date --cob_date
    ,
*/
    cs.run_number --feed_id
    ,
    bnf.shrt_bnk_nm --legal_entity
    ,
    cs.customer_spn --src_client_id
    ,
    'SPN' --src_client_id_type
    ,
    'COAST' --source
    ,
    cs.agreement_number --src_client_account
    ,
    ia.ia_group_name --CLEARING_HOUSE_ID
    ,
    cs.ch_spn --ch_spn
    ,
    'Group Level Initial Margin'--asset_class
    ,
    ia.ia_ccy --im_ccy
    ,
    ia.tot_ia_amt --local_ccy_im_amt
    ,
    ia.tot_ia_amt --local_ccy_coll_req_amt
    ,
    cs.is_ncca --IS_NCCA
    ,
    'BILATERAL' --row_type
    ,
    CASE
        WHEN cs.ch_spn IS NULL
        THEN 'N'
        ELSE 'Y'
    END --IS_CLEARED
    ,
    cs.ia_reporting_ccy --reporting_ccy
    ,
/*
    mo_utility_pkg.convert_coast_ccy ( 'USD' , cs.ia_reporting_ccy ) --im_exchange_rate
    ,
    ia.tot_ia_amt * mo_utility_pkg.convert_coast_ccy ( 'USD' , cs.ia_reporting_ccy )--
    -- reporting_ccy_adj_im_amt
    ,
    ia.tot_ia_amt * mo_utility_pkg.convert_coast_ccy ('USD' , cs.ia_reporting_ccy ) --
    -- REPORTING_CCY_COLL_REQ_AMT
    ,
*/
    SYSDATE ,
    cs.ia_offset_flg ,
    cs.ia_offset_group_id
FROM
    cs_cel_customer_setup_tmp cs ,
    (
        SELECT
            col_contract_nbr ,
            ia_group_name ,
            ia_ccy ,
            SUM (
                CASE
                    WHEN pledgor ='CP'
                    THEN -1
                    ELSE 1
                END * ia_amt) tot_ia_amt
        FROM
            cs_cms_ia
        WHERE
--            run_number = p_feed_run_number
--        AND 

ia_group_name IN
            (
                SELECT
                    IA_GROUP_NAME
                FROM
                    cs_ia_group
                WHERE
                    include_cftc_stmt_ind = 'Y'
            )
        GROUP BY
            col_contract_nbr ,
            ia_group_name ,
            ia_ccy
    )
    ia ,
    cs_bnk_nm_short_frm bnf
WHERE
    cs.agreement_number = ia.col_contract_nbr
AND cs.agreement_processing_status = 'ACTIVE'
AND cs.status_code = 'A'
AND bnf.cs_ucn_lead_office = cs.cs_ucn_lead_office;