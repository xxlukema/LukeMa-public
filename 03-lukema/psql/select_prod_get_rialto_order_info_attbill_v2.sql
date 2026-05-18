SELECT
    site_main_number,
    product_id,
    *
FROM
    rm_prod_sa.get_rialto_order_info('36543', CAST('06/17/2015' AS TIMESTAMP), CAST
    (CURRENT_TIMESTAMP AS TIMESTAMP))
WHERE
    company_name = 'The Chimney Guy';
    
  