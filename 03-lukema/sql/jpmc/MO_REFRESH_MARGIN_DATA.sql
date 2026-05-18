SELECT
    *
FROM
    mo_combined_im_vm
WHERE
    asset_class = 'Group Level Initial Margin'
--and cob_date = '23-may-2011'
AND CLEARING_HOUSE_ID NOT IN 
(
   SELECT
       IA_GROUP_NAME
   FROM
       CS_IA_GROUP
)
ORDER BY
    cob_date DESC;

SELECT
    CONS_STMT_IA_GROUP_NAME,
    IA_GROUP_NAME
FROM
    CS_IA_GROUP;
