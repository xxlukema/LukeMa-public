SELECT
    query_to_xml
    (
    'SELECT    
site_main_number,    
product_id,    
*
FROM    
rm_qa_sa.get_rialto_order_info_attbill_v2(''14169'', ''04/18/2015'', CAST    
(CURRENT_TIMESTAMP AS TIMESTAMP))'
    , true, false, '' )