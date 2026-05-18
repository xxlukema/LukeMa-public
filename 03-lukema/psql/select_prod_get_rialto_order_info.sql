SELECT
    site_main_number, *
FROM
    rm_prod_sa.get_rialto_order_info('36543', CAST('05/18/2015' AS TIMESTAMP), CAST
    (CURRENT_TIMESTAMP AS TIMESTAMP)) ;
    
  