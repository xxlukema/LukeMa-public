SELECT 
-- DISTINCT
    nn.CONTACT_ID ,
    nn.CONTACT_TYPE,
    nn.OVERALL_STATUS,
    nn.AGREEMENT_STATUS,
    nn.CONTACT_STATUS,
    nn.CONTACT_NAME,
    nn.NO_ACTIVE_AGREEMENTS,
    nn.NO_PENDING_AGREEMENTS,
    nn.NO_REJECTED_AGREEMENTS,
    nn.NO_TEMPLATE_IDS,
    get_next_statement_fnl.for_contact2(nn.contact_id) DELIVERY_DATE,
    nn.APPROVE_STATUS
FROM
    (
        SELECT DISTINCT
            (c1.contact_record_id) CONTACT_ID,
            c1.contact_type CONTACT_TYPE,
            c1.email_address,
            DECODE((v1.PCOUNT+v1.RCOUNT+v1.CON_ZCOUNT+v1.AGR_ZCOUNT),0,DECODE(v1.ACOUNT,0,'Z',
            'ACTIVE'), DECODE(v1.PCOUNT,0,DECODE(v1.RCOUNT,0,'ACTIVE','REJECTED'),
            'WAITING FOR APPROVAL')) OVERALL_STATUS ,
            DECODE((v1.AGR_PCOUNT+v1.AGR_RCOUNT+v1.AGR_ZCOUNT),0,DECODE(v1.AGR_ACOUNT,0,' ',
            'ACTIVE'), DECODE(v1.AGR_PCOUNT,0,DECODE(v1.AGR_RCOUNT,0,'ACTIVE','REJECTED'),'PENDING'
            )) AGREEMENT_STATUS ,
            DECODE(c1.status_code,'A','ACTIVE','P','PENDING','R','REJECTED',' ') CONTACT_STATUS ,
            c1.contact_name CONTACT_NAME ,
            v1.AGR_ACOUNT NO_ACTIVE_AGREEMENTS ,
            v1.AGR_PCOUNT NO_PENDING_AGREEMENTS ,
            v1.AGR_RCOUNT NO_REJECTED_AGREEMENTS ,
            v1.COMB_CONTACT NO_TEMPLATE_IDS ,
            DECODE(
            (
                SELECT
                    1 FROM cs_contact_info WHERE contact_record_id = c1.contact_record_id
                AND status_code = 'P'
                AND status_code != 'D'
                AND input_id = 'F411479'
                UNION
                SELECT
                    1
                FROM
                    cs_contact_agreement
                WHERE
                    contact_record_id = c1.contact_record_id
                AND status_code = 'P'
                AND status_code != 'D'
                AND input_id = 'F411479'
            )
            , 1, 'N','Y') APPROVE_STATUS,
            c1.group_id,
            s1.stmt_id
        FROM
            cs_contact_info c1,
            cs_contact_status v1,
            cs_contact_agreement a1,
            mo_combined_stmt_contact s1
        WHERE
            c1.contact_record_id = v1.contact_record_id
        AND c1.contact_record_id = a1.contact_record_id (+)
        AND c1.contact_record_id = s1.contact_record_id (+)
        AND c1.status_code != 'D'
        AND a1.status_code (+) = 'A'
and s1.status_code != 'D'
        AND internal_contact_ind IS NULL
        AND
            (
                (
                    email_address IS NOT NULL
                )
             OR
                (
                    fax_number IS NOT NULL
                AND email_address IS NULL
                )
            )
        AND
            (
                c1.contact_type = 'CUSTOMER'
             OR c1.contact_type = 'OTHER'
            )
    )
    nn
WHERE
    nn.contact_status != ' '
AND nn.contact_name = 'CITIADMINISTRATORS'
AND nn.group_id = '4'
--AND nn.stmt_id = '212'
AND rownum < 250
ORDER BY
    4