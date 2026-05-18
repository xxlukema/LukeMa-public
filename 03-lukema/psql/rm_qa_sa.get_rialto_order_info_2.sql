-- Function: rm_qa_sa.get_rialto_order_info(text, timestamp without time zone, timestamp without
-- time zone)
-- DROP FUNCTION rm_qa_sa.get_rialto_order_info(text, timestamp without time zone, timestamp
-- without time zone);
CREATE OR REPLACE FUNCTION rm_qa_sa.get_rialto_order_info_2( IN in_party_id text,
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
    WITH
        company_address_phone AS
        (
            SELECT
                ca.party_id,
                ca.company_name,
                ca.main_contact_name,
                ca.last_updated_stamp,
                rm_qa_sa.get_address_data (ca.party_id, 'PRIMARY_LOCATION') AS addr,
                rm_qa_sa.get_email_data (ca.party_id, 'PRIMARY_EMAIL')      AS email,
                rm_qa_sa.get_phone_data (ca.party_id, 'PHONE_WORK')         AS phone
            FROM
                rm_qa_sa.company_account ca
        )
        ,
        site_address_phone AS
        (
            SELECT
                cl.party_id,
                cl.company_location_name,
                cl.location_contact_name,
                rm_qa_sa.get_address_data (cl.party_id, 'E911_LOCATION') AS sAddr,
                rm_qa_sa.get_email_data (cl.party_id, 'LOCATION_EMAIL')  AS sEmail,
                rm_qa_sa.get_phone_data (cl.party_id, 'LOCATION_NUMBER') AS sPhone
            FROM
                rm_qa_sa.company_location cl
        )
    SELECT
        row_number() OVER() row_number,
        *
    FROM
        (
            SELECT
                oh.order_id,
                oi.order_item_seq_id,
                oh.status_id,
                ca.party_id AS "customer_id",
                ca.company_name,
                ca.main_contact_name,
                (email).email_address,
                (phone).country_code,
                (phone).contact_number AS "local_number",
                (addr).address1,
                (addr).address2,
                (addr).city,
                (addr).state_province_geo_id,
                (addr).postal_code,
                cl.party_id AS "site_id",
                cl.company_location_name,
                cl.location_contact_name,
                (
                    SELECT
                        tn_id
                    FROM
                        rm_qa_sa.service_profile_phones
                    WHERE
                        location_id = cl.party_id
                    AND status_id = 'SERVICE_ITEM_ACTIVE'
                    AND phone_type_id = 'MAIN'
                    AND thru_date IS NULL
                    ORDER BY
                        last_updated_stamp DESC LIMIT 1)      AS "site_main_number",
                (sEmail).email_address                        AS "site_email_address",
                (sPhone).country_code                         AS "site_country_code",
                (sPhone).area_code || (sPhone).contact_number AS "site_local_number",
                (sAddr).address1                              AS "site_address1",
                (sAddr).address2                              AS "site_address2",
                (sAddr).city                                  AS "site_city",
                (sAddr).state_province_geo_id                 AS "site_state_province_geo_id",
                (sAddr).postal_code                           AS "site_postal_code",
                CASE
                    WHEN (oh.type_id = 'DELETE')
                    THEN
                        CASE
                            WHEN ((
                                        SELECT
                                            attr_value
                                        FROM
                                            rm_qa_sa.order_attribute
                                        WHERE
                                            order_id = oh.order_id
                                        AND attr_name = 'action') = 'removeSite')
                            THEN 'DISCONNECT_SITE'
                            ELSE 'DISCONNECT'
                        END
                    WHEN (oh.type_id = 'CHANGE')
                    THEN
                        CASE
                            WHEN ((
                                        SELECT
                                            attr_value
                                        FROM
                                            rm_qa_sa.order_attribute
                                        WHERE
                                            order_id = oh.order_id
                                        AND attr_name = 'orderType') = 'updateMain')
                            THEN 'CHANGE_MAIN'
                            ELSE
                                CASE
                                    WHEN ((
                                                SELECT
                                                    add_to_service_profile
                                                FROM
                                                    rm_qa_sa.product_provisioning_config
                                                WHERE
                                                    product_id = oi.product_id) = 'N'
                                        AND type_id <> 'MOVE')
                                    THEN 'ONE_TIME_FEE'
                                    ELSE
                                        CASE
                                            WHEN (map.ma_product_id = 'RXNONPUB')
                                            THEN 'ONE_TIME_FEE'
                                            ELSE oh.type_id
                                        END
                                END
                        END
                    WHEN (map.ma_product_id = 'RXNONPUB')
                    THEN 'ONE_TIME_FEE'
                    ELSE
                        CASE
                            WHEN ((
                                        SELECT
                                            add_to_service_profile
                                        FROM
                                            rm_qa_sa.product_provisioning_config
                                        WHERE
                                            product_id = oi.product_id) = 'N'
                                AND type_id <> 'MOVE')
                            THEN 'ONE_TIME_FEE'
                            ELSE oh.type_id
                        END
                END,
                map.ma_product_id,
                CASE
                    WHEN (oh.type_id = 'DELETE')
                    THEN
                        (
                            SELECT
                                attr_value
                            FROM
                                rm_qa_sa.order_item_attribute
                            WHERE
                                order_id = oh.order_id
                            AND order_item_seq_id = oi.order_item_seq_id
                            AND attr_name = 'serviceProfileItemSeqId')
                    ELSE spi.service_profile_item_seq_id
                END,
                CASE
                    WHEN (oh.type_id IN ('DELETE',
                                         'FIELD_SERVICE'))
                    THEN oi.quantity
                    ELSE
                        CASE
                            WHEN (spi.service_profile_item_seq_id <> '')
                            THEN CAST(spi.quantity AS NUMERIC(18,4))
                            ELSE oi.quantity
                        END
                END,
                oi.unit_price,
                (
                    SELECT
                        external_id
                    FROM
                        rm_qa_sa.order_role eor,
                        rm_qa_sa.party ep
                    WHERE
                        eor.order_id = oh.order_id
                    AND ep.party_id = eor.party_id
                    AND role_type_id = 'AGENT') AS "seller_sales_code",
                (
                    SELECT
                        external_id
                    FROM
                        rm_qa_sa.user_login ul,
                        rm_qa_sa.party ep
                    WHERE
                        ul.user_login_id = oh.created_by
                    AND ul.party_id = ep.party_id) AS "entry_sales_code"
            FROM
                rm_qa_sa.order_header oh,
                rm_qa_sa.order_item oi
            LEFT OUTER JOIN
                rm_qa_sa.service_profile_item spi
            ON
                spi.order_id = oi.order_id
            AND spi.order_item_seq_id = oi.order_item_seq_id,
                rm_qa_sa.order_status os,
                rm_qa_sa.order_role orc,
                rm_qa_sa.party par,
                rm_qa_sa.order_role orl,
                rm_qa_sa.party so,
                rm_qa_sa.party sa,
                rm_qa_sa.party ma,
                company_address_phone AS ca,
                site_address_phone    AS cl,
                rm_qa_sa.master_agent_product map
            WHERE
                oh.order_id = oi.order_id
            AND ca.party_id = orc.party_id
            AND par.party_id = ca.party_id
            AND orc.role_type_id = 'END_USER_CUSTOMER'
            AND orc.order_id = oh.order_id
            AND cl.party_id = orl.party_id
            AND orl.role_type_id = 'CUSTOMER_LOCATION'
            AND orl.order_id = oh.order_id
            AND par.parent_party_id = sa.party_id
            AND sa.parent_party_id = so.party_id
            AND so.parent_party_id = ma.party_id
            AND oh.status_id = 'ORDER_COMPLETED'
            AND os.order_id = oh.order_id
            AND os.order_item_seq_id IS NULL
            AND os.status_id = 'ORDER_COMPLETED'
            AND ma.party_id = $1
            AND os.status_datetime BETWEEN $2 AND $3
            AND map.product_id = oi.product_id
            AND map.party_id = $1
            AND map.ma_product_id IS NOT NULL
            AND map.ma_product_id <> ''
            UNION
            /*Get Change Main Records*/
            SELECT
                oh.order_id,
                oi.order_item_seq_id,
                oh.status_id,
                ca.party_id AS "customer_id",
                ca.company_name,
                ca.main_contact_name,
                (email).email_address,
                (phone).country_code,
                (phone).contact_number AS "local_number",
                (addr).address1,
                (addr).address2,
                (addr).city,
                (addr).state_province_geo_id,
                (addr).postal_code,
                cl.party_id AS "site_id",
                cl.company_location_name,
                cl.location_contact_name,
                (
                    SELECT
                        tn_id
                    FROM
                        rm_qa_sa.service_profile_phones
                    WHERE
                        location_id = cl.party_id
                    AND status_id = 'SERVICE_ITEM_ACTIVE'
                    AND phone_type_id = 'MAIN'
                    AND thru_date IS NULL
                    ORDER BY
                        last_updated_stamp DESC LIMIT 1)      AS "site_main_number",
                (sEmail).email_address                        AS "site_email_address",
                (sPhone).country_code                         AS "site_country_code",
                (sPhone).area_code || (sPhone).contact_number AS "site_local_number",
                (sAddr).address1                              AS "site_address1",
                (sAddr).address2                              AS "site_address2",
                (sAddr).city                                  AS "site_city",
                (sAddr).state_province_geo_id                 AS "site_state_province_geo_id",
                (sAddr).postal_code                           AS "site_postal_code",
                'CHANGE_MAIN',
                oi.product_id,
                spi.service_profile_item_seq_id,
                CAST(spi.quantity AS NUMERIC(18,4)),
                oi.unit_price,
                (
                    SELECT
                        external_id
                    FROM
                        rm_qa_sa.order_role eor,
                        rm_qa_sa.party ep
                    WHERE
                        eor.order_id = oh.order_id
                    AND ep.party_id = eor.party_id
                    AND role_type_id = 'AGENT') AS "seller_sales_code",
                (
                    SELECT
                        external_id
                    FROM
                        rm_qa_sa.user_login ul,
                        rm_qa_sa.party ep
                    WHERE
                        ul.user_login_id = oh.created_by
                    AND ul.party_id = ep.party_id) AS "entry_sales_code"
            FROM
                rm_qa_sa.order_header oh,
                rm_qa_sa.order_item oi
            LEFT OUTER JOIN
                rm_qa_sa.service_profile_item spi
            ON
                spi.order_id = oi.order_id
            AND spi.order_item_seq_id = oi.order_item_seq_id,
                rm_qa_sa.order_status os,
                rm_qa_sa.order_role orc,
                rm_qa_sa.party par,
                rm_qa_sa.order_role orl,
                rm_qa_sa.party so,
                rm_qa_sa.party sa,
                rm_qa_sa.party ma,
                company_address_phone AS ca,
                site_address_phone    AS cl,
                rm_qa_sa.master_agent_product map
            WHERE
                oh.order_id = oi.order_id
            AND ca.party_id = orc.party_id
            AND par.party_id = ca.party_id
            AND orc.role_type_id = 'END_USER_CUSTOMER'
            AND orc.order_id = oh.order_id
            AND cl.party_id = orl.party_id
            AND orl.role_type_id = 'CUSTOMER_LOCATION'
            AND orl.order_id = oh.order_id
            AND par.parent_party_id = sa.party_id
            AND sa.parent_party_id = so.party_id
            AND so.parent_party_id = ma.party_id
            AND oh.status_id = 'ORDER_COMPLETED'
            AND os.order_id = oh.order_id
            AND os.order_item_seq_id IS NULL
            AND os.status_id = 'ORDER_COMPLETED'
            AND ma.party_id = $1
            AND os.status_datetime BETWEEN $2 AND $3
            AND oh.type_id = 'CHANGE'
            AND oi.product_id = 'co-001'
            UNION
            SELECT
                oh.order_id,
                order_adjustment_id,
                oh.status_id,
                ca.party_id AS "customer_id",
                ca.company_name,
                ca.main_contact_name,
                (email).email_address,
                (phone).country_code,
                (phone).contact_number AS "local_number",
                (addr).address1,
                (addr).address2,
                (addr).city,
                (addr).state_province_geo_id,
                (addr).postal_code,
                cl.party_id AS "site_id",
                cl.company_location_name,
                cl.location_contact_name,
                (
                    SELECT
                        tn_id
                    FROM
                        rm_qa_sa.service_profile_phones
                    WHERE
                        location_id = cl.party_id
                    AND status_id = 'SERVICE_ITEM_ACTIVE'
                    AND phone_type_id = 'MAIN'
                    AND thru_date IS NULL
                    ORDER BY
                        last_updated_stamp DESC LIMIT 1)      AS "site_main_number",
                (sEmail).email_address                        AS "site_email_address",
                (sPhone).country_code                         AS "site_country_code",
                (sPhone).area_code || (sPhone).contact_number AS "site_local_number",
                (sAddr).address1                              AS "site_address1",
                (sAddr).address2                              AS "site_address2",
                (sAddr).city                                  AS "site_city",
                (sAddr).state_province_geo_id                 AS "site_state_province_geo_id",
                (sAddr).postal_code                           AS "site_postal_code",
                'ONE_TIME_FEE',
                'RXSHIPPING',
                '',
                1,
                oa.amount,
                (
                    SELECT
                        external_id
                    FROM
                        rm_qa_sa.order_role eor,
                        rm_qa_sa.party ep
                    WHERE
                        eor.order_id = oh.order_id
                    AND ep.party_id = eor.party_id
                    AND role_type_id = 'AGENT') AS "seller_sales_code",
                (
                    SELECT
                        external_id
                    FROM
                        rm_qa_sa.user_login ul,
                        rm_qa_sa.party ep
                    WHERE
                        ul.user_login_id = oh.created_by
                    AND ul.party_id = ep.party_id) AS "entry_sales_code"
            FROM
                rm_qa_sa.order_header oh,
                rm_qa_sa.order_adjustment oa,
                rm_qa_sa.order_status os,
                rm_qa_sa.order_role orc,
                rm_qa_sa.party par,
                rm_qa_sa.order_role orl,
                rm_qa_sa.party so,
                rm_qa_sa.party sa,
                rm_qa_sa.party ma,
                company_address_phone AS ca,
                site_address_phone    AS cl
            WHERE
                oh.order_id = oa.order_id
            AND ca.party_id = orc.party_id
            AND par.party_id = ca.party_id
            AND orc.role_type_id = 'END_USER_CUSTOMER'
            AND orc.order_id = oh.order_id
            AND cl.party_id = orl.party_id
            AND orl.role_type_id = 'CUSTOMER_LOCATION'
            AND orl.order_id = oh.order_id
            AND par.parent_party_id = sa.party_id
            AND sa.parent_party_id = so.party_id
            AND so.parent_party_id = ma.party_id
            AND oh.status_id = 'ORDER_COMPLETED'
            AND os.order_id = oh.order_id
            AND os.order_item_seq_id IS NULL
            AND os.status_id = 'ORDER_COMPLETED'
            AND ma.party_id = $1
            AND os.status_datetime BETWEEN $2 AND $3
            AND oa.order_adjustment_type_id = 'SHIPPING_CHARGES'
            UNION
            SELECT
                i.invoice_id           AS "order_id",
                ii.invoice_item_seq_id AS "order_item_seq_id",
                '',
                ii.customer_id,
                ca.company_name,
                ca.main_contact_name,
                (email).email_address,
                (phone).country_code,
                (phone).contact_number AS "local_number",
                (addr).address1,
                (addr).address2,
                (addr).city,
                (addr).state_province_geo_id,
                (addr).postal_code,
                cl.party_id AS "site_id",
                cl.company_location_name,
                cl.location_contact_name,
                (
                    SELECT
                        tn_id
                    FROM
                        rm_qa_sa.service_profile_phones
                    WHERE
                        location_id = cl.party_id
                    AND status_id = 'SERVICE_ITEM_ACTIVE'
                    AND phone_type_id = 'MAIN'
                    AND thru_date IS NULL
                    ORDER BY
                        last_updated_stamp DESC LIMIT 1)      AS "site_main_number",
                (sEmail).email_address                        AS "site_email_address",
                (sPhone).country_code                         AS "site_country_code",
                (sPhone).area_code || (sPhone).contact_number AS "site_local_number",
                (sAddr).address1                              AS "site_address1",
                (sAddr).address2                              AS "site_address2",
                (sAddr).city                                  AS "site_city",
                (sAddr).state_province_geo_id                 AS "site_state_province_geo_id",
                (sAddr).postal_code                           AS "site_postal_code",
                'ONE_TIME_FEE',
                CASE
                    WHEN (invoice_item_type_id = 'INTL_USAGE')
                    THEN 'RXOPSVCS'
                    WHEN (invoice_item_type_id = 'OPER_USAGE')
                    THEN 'RXOPSVCS'
                    WHEN (invoice_item_type_id = 'DA_USAGE')
                    THEN 'RXDIRASSIST'
                    ELSE
                        CASE
                            WHEN (irc.ma_product_id = 'International_Usage_Tier1')
                            THEN 'RXINTLA'
                            WHEN (irc.ma_product_id = 'International_Usage_Tier2')
                            THEN 'RXINTLB'
                            ELSE 'RXINTLC'
                        END
                END,
                '',
                ii.quantity,
                ii.amount,
                cap.external_id AS "seller_sales_code",
                ''              AS "entry_sales_code"
            FROM
                rm_qa_sa.invoice i,
                rm_qa_sa.invoice_item ii
            LEFT OUTER JOIN
                rm_qa_sa.intl_rate_code irc
            ON
                irc.rate_zone_id = ii.rate_zone
            AND thru_date IS NULL,
                company_address_phone AS ca,
                site_address_phone    AS cl,
                rm_qa_sa.party cap,
                rm_qa_sa.party sa
            WHERE
                i.invoice_id = ii.invoice_id
            AND i.party_id = $1
            AND i.status_id <> 'INVOICE_CANCELLED'
            AND invoice_date BETWEEN $2 AND $3
            AND invoice_item_type_id IN ('INTL_USAGE',
                                         'OPER_USAGE',
                                         'DA_USAGE',
                                         'INTL_RATE_USAGE')
            AND ca.party_id = ii.customer_id
            AND cl.party_id = ii.location_id
            AND cap.party_id = ca.party_id
            AND sa.party_id = cap.parent_party_id
            UNION
            SELECT
                CAST('' AS VARCHAR(20)),
                CAST('' AS VARCHAR(20)),
                CAST('' AS VARCHAR(20)),
                ca.party_id,
                ca.company_name,
                ca.main_contact_name,
                (email).email_address,
                (phone).country_code,
                (phone).contact_number AS "local_number",
                (addr).address1,
                (addr).address2,
                (addr).city,
                (addr).state_province_geo_id,
                (addr).postal_code,
                CAST('' AS VARCHAR(20)),
                CAST('' AS VARCHAR(60)),
                CAST('' AS VARCHAR(60)),
                CAST('' AS VARCHAR(20)),
                CAST('' AS VARCHAR(255)),
                CAST('' AS VARCHAR(10)),
                CAST('' AS text),
                CAST('' AS VARCHAR(255)),
                CAST('' AS VARCHAR(255)),
                CAST('' AS VARCHAR(100)),
                CAST('' AS VARCHAR(20)),
                CAST('' AS VARCHAR(60)),
                CAST('MANAGE_CUSTOMER' AS VARCHAR(20)),
                CAST('' AS VARCHAR(20)),
                CAST('' AS VARCHAR(20)),
                CAST(0 AS DOUBLE PRECISION),
                CAST(0 AS NUMERIC(18,2)),
                sa.external_id,
                CAST('' AS VARCHAR(20))
            FROM
                rm_qa_sa.party cap,
                rm_qa_sa.party so,
                rm_qa_sa.party sa,
                rm_qa_sa.party ma,
                rm_qa_sa.service_profile sp,
                company_address_phone AS ca
            WHERE
                ca.party_id = cap.party_id
            AND sa.party_id = cap.parent_party_id
            AND so.party_id = sa.parent_party_id
            AND ma.party_id = so.parent_party_id
            AND ma.party_id = $1
            AND sp.party_id = ca.party_id
            AND sp.status_id = 'SERVICE_ACTIVE'
            AND (
                    ca.last_updated_stamp BETWEEN $2 AND $3
                OR  (
                        addr).last_updated_stamp BETWEEN $2 AND $3
                OR  (
                        email).last_updated_stamp BETWEEN $2 AND $3
                OR  (
                        phone).last_updated_stamp BETWEEN $2 AND $3)
            ORDER BY
                order_id,
                order_item_seq_id ASC) a $BODY$ LANGUAGE SQL VOLATILE COST 100 ROWS 1000;
ALTER
FUNCTION rm_qa_sa.get_rialto_order_info(text,
                                        TIMESTAMP without TIME zone,
                                        TIMESTAMP without TIME zone) OWNER TO "rmAdmin";
