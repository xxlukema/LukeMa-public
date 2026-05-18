SELECT
    row_number,
    order_id,
    order_item_seq_id,
    status_id,
    customer_id,
    company_name,
    main_contact_name ,
    email_address ,
    country_code ,
    local_number ,
    address1 ,
    address2 ,
    city ,
    state_province_geo_id ,
    postal_code ,
    site_id ,
    company_location_name ,
    location_contact_name,
    CASE
        WHEN site_main_number IS NULL
        THEN '1112223333'
        WHEN LENGTH(LTrim(RTRIM(site_main_number))) = 0
        THEN '2223334444'
        ELSE site_main_number
    END AS site_main_number,
    site_email_address ,
    site_country_code,
    site_local_number,
    site_address1 ,
    site_address2 ,
    site_city ,
    site_state_province_geo_id,
    site_postal_code ,
    type_id ,
    product_id ,
    service_profile_item_seq_id ,
    quantity ,
    unit_price ,
    seller_sales_code,
    entry_sales_code
FROM
    rm_qa_sa.get_rialto_order_info('14169', CAST('04/01/2014' AS TIMESTAMP), CAST(CURRENT_TIMESTAMP
    AS TIMESTAMP))
WHERE
    --site_main_number IS NOT NULL
    --AND LENGTH(LTrim(RTRIM(site_main_number))) > 0 ;
    LENGTH(LTrim(RTRIM(site_main_number))) = 0 ;