UPDATE
    MO_COMBINED_STMT_SETUP
SET
    STMT_NAME =#statementName#,
    CSR=#csr#,
    REGION=#region#,
    NOTIFICATION_DEADLINE=#notificationDeadline#,
    STP_LEVEL= #stpLevel#,
    CODED_STATEMENT_REQUIRED= #codedStatementRequired#,
    CODED_STATEMENT_NAME= #codedStatementName#,
    PASSWORD_REQUIRED= #passwordRequired#,
    AUTOGENERATE_PASSWORD= #autoGeneratePassword#,
    PASSWORD= #password#,
    AUTO_INCLUDE_GMI_ACCOUNTS=#autoIncludeNewGmiAccounts#,
    AUTO_INCLUDE_COAST_AGR= #autoIncludeNewCoastAgreement#,
    FX_SETTLEMENT= #fxSettlementDisplayType#,
    DERIVATIVES_SETTLEMENT= #derivativesSettlementDisplayType#,
    UPDATE_DATETIME = sysdate
WHERE
    STMT_ID = #statementTemplateId#