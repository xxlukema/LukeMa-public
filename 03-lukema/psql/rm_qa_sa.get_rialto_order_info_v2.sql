-- Function: rm_qa_sa.get_rialto_order_info(text, timestamp without time zone, timestamp without
-- time zone)
-- DROP FUNCTION rm_qa_sa.get_rialto_order_info(text, timestamp without time zone, timestamp
-- without time zone);
CREATE OR REPLACE FUNCTION rm_qa_sa.get_rialto_order_info_v2( IN in_party_id text,
                                                            IN from_date TIMESTAMP without TIME
                                                            zone,
                                                            IN thru_date TIMESTAMP without TIME
                                                            zone) RETURNS TABLE(row_number bigint,
    order_id CHARACTER VARYING, order_item_seq_id CHARACTER VARYING, status_id CHARACTER VARYING,
    customer_id CHARACTER VARYING, company_name CHARACTER VARYING, main_contact_name CHARACTER
    VARYING, email_address CHARACTER VARYING, country_code CHARACTER VARYING, local_number text,
    address1 CHARACTER VARYING, address2 CHARACTER VARYING, city CHARACTER VARYING,
    state_province_geo_id CHARACTER VARYING, postal_code CHARACTER VARYING, site_id CHARACTER
    VARYING, company_location_name CHARACTER VARYING, location_contact_name CHARACTER VARYING,
    site_main_number CHARACTER VARYING, site_email_address CHARACTER VARYING, site_country_code
    CHARACTER VARYING, site_local_number text, site_address1 CHARACTER VARYING, site_address2
    CHARACTER VARYING, site_city CHARACTER VARYING, site_state_province_geo_id CHARACTER VARYING,
    site_postal_code CHARACTER VARYING, type_id CHARACTER VARYING, product_id CHARACTER VARYING,
    service_profile_item_seq_id CHARACTER VARYING, quantity DOUBLE PRECISION, unit_price NUMERIC,
    seller_sales_code CHARACTER VARYING, entry_sales_code CHARACTER VARYING) AS $BODY$
    /*Get Orders Items that went to completed*/
    SELECT
        row_number,
        order_id,
        order_item_seq_id,
        status_id,
        customer_id,
        company_name,
        main_contact_name,
        email_address,
        country_code,
        local_number,
        address1,
        address2,
        city,
        state_province_geo_id,
        postal_code,
        site_id,
        company_location_name,
        location_contact_name,
        CASE
            WHEN site_main_number IS NULL
            THEN '1112223333'
            WHEN LENGTH(LTrim(RTRIM(site_main_number))) = 0
            THEN '1112220000'
            ELSE site_main_number
        END AS site_main_number,
        site_email_address,
        site_country_code,
        site_local_number,
        site_address1,
        site_address2,
        site_city,
        site_state_province_geo_id,
        site_postal_code,
        type_id,
        product_id,
        service_profile_item_seq_id,
        quantity,
        unit_price,
        seller_sales_code,
        entry_sales_code
    FROM
        rm_qa_sa.get_rialto_order_info($1, $2, $3) a $BODY$ LANGUAGE SQL
        VOLATILE COST 100 ROWS 1000;
ALTER
FUNCTION rm_qa_sa.get_rialto_order_info_v2(text,
                                          TIMESTAMP without TIME zone,
                                          TIMESTAMP without TIME zone) OWNER TO "rmAdmin";
