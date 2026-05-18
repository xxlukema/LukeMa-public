create table oa_se_ref
(
 REF               VARCHAR2(5) not null,
 DES               VARCHAR2(23),
 CHG_DESC          VARCHAR2(18),
 RESERVED          VARCHAR2(12),
 NOC_DESC          VARCHAR2(18),
 FLAG1             CHAR(1),
 FLAG2             CHAR(1),
 FLAG3             CHAR(1),
 FLAG4             CHAR(1),
 FLAG5             CHAR(1),
 FLAG6             CHAR(1),
 FLAG7             CHAR(1),
 FLAG8             CHAR(1),
 FLAG9             CHAR(1),
 FLAG10            CHAR(1),
 FLAG11            CHAR(1),
 FLAG12            CHAR(1),
 OASYS_TYPE        CHAR(1),
 OASYS_SUBTYPE     CHAR(1),
 BILLING_TYPE      CHAR(1),
 MARKET            CHAR(2) not null,
 NL CHAR(1)
);

