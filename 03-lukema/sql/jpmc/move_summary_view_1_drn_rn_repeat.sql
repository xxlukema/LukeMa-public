SELECT
    *
FROM
    (
        SELECT
            drn,
            rn,
            STMT_ID,
            COUNT(*) repeat
        FROM
            (
                SELECT
                    OPS$COAST.CS_RUN_STATUS_DETAIL.RUN_NUMBER drn,
                    OPS$COAST.MO_CFTC_STMT_SETUP.RUN_NUMBER rn,
                    OPS$COAST.MO_CFTC_STMT_SETUP.STMT_ID,
                    'GGGG',
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.FEED_ID,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.SRC_CLIENT_ID,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.SRC_CLIENT_ACCOUNT,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.LEGAL_ENTITY,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.IM_VM_IND,
                    NVL(OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.IS_NCCA,'N'),
                    UPPER(OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.IS_CLEARED),
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.OFFSET_FLG,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.OFFSET_GROUP_ID,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.SPLIT_TYPE,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.CONSOLIDATED_CCY,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.CONSOLIDATED_MARGIN_CALL_AMT,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.JPM_SETTLEMENT_INSTRUCTION,
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.SOURCE,
                    OPS$COAST.CS_RUN_STATUS_DETAIL.RUN_NUMBER,
                    OPS$COAST.MO_CFTC_STMT_RELATION.CLIENT_ACCT_NUMBER
                FROM
                    OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW,
                    OPS$COAST.CS_RUN_STATUS_DETAIL,
                    OPS$COAST.MO_CFTC_STMT_RELATION,
                    OPS$COAST.MO_CFTC_STMT_SETUP
                WHERE
                    (
                        OPS$COAST.CS_RUN_STATUS_DETAIL.COB_DATE=
                        OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.COB_DATE
                    AND OPS$COAST.CS_RUN_STATUS_DETAIL.FEED_ID=
                        OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.FEED_ID
                    )
                AND
                    (
                        OPS$COAST.MO_CFTC_STMT_RELATION.RUN_NUMBER=
                        OPS$COAST.MO_CFTC_STMT_SETUP.RUN_NUMBER
                    AND OPS$COAST.MO_CFTC_STMT_RELATION.STMT_ID=
                        OPS$COAST.MO_CFTC_STMT_SETUP.STMT_ID
                    )
                AND
                    (
                        OPS$COAST.MO_CFTC_STMT_RELATION.CLIENT_ACCT_NUMBER=
                        OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.SRC_CLIENT_ACCOUNT
                    AND OPS$COAST.MO_CFTC_STMT_RELATION.CLIENT_SOURCE=
                        OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.SOURCE
                    )
                AND
                    (
                        OPS$COAST.MO_COMBINED_MOVE_SUMMARY_VIEW.CONSOLIDATED_MARGIN_CALL_AMT != 0
                    )
            )
        GROUP BY
            drn,
            rn,
            STMT_ID
    )
WHERE
    repeat > 1
ORDER BY
    repeat DESC,
    drn DESC,
    rn DESC
    