# `org.springframework.data.domain.Pageable`

## Controller Log

    # 1. Controller with @PageableDefault()
    Pageable: Page request [number: 0, size 10, sort: UNSORTED]

    # 2. Controller without @PageableDefault()
    Pageable: Page request [number: 0, size 20, sort: UNSORTED]

## Controller

    @GetMapping(value = "pageFn", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(BmsFeatures.VIEW_NO_BMI_QUERY)
    public Page<NoBmiQuerySummary> pageFn(@Valid final NoBmiQuerySearchCriteria criteria, @PageableDefault() Pageable pageable) {

        ////////////
        /// 
        
        log.debug("criteria: {}", criteria);
        log.debug("pageable: {}", pageable);

        String statementStartDate = convertCriteriaDate(criteria.getStatementStartDate());
        String statementEbdDate = convertCriteriaDate(criteria.getStatementEndDate());

        return noBmiQuerySummaryService.getPageFn(statementStartDate, statementEbdDate, sortService.sortNoBmiQueryFn(pageable));
    }

## URL params for `Pageable`

    GET {{localBaseUrl}}/bms/bmi/nobmiquery/pageFn
        ?noBmiType=Comp+Plans
        &page=3     # <===== page number of Pageable
        &size=8     # <===== page size of Pageable
    Content-Type: application/json
    Accept: application/json
    Authorization: Bearer {{securityToken}}

## SQL

    order by CYCLE_DT asc, STMT_NUM asc, COMP_PLAN_NUM asc offset ? rows fetch next ? rows only

## Logs

    -- ### 2025-04-28 22:28:07 [TRACE] org.hibernate.resource.jdbc.internal.ResourceRegistryStandardImpl(186) close()
    select
      COMP_PLAN_NUM,
      CYCLE_DT,
      STMT_NUM,
      ACCT_TYPE_CD,
      COMP_PLAN_NAME,
      COUNT_NO_BMI,
      COUNT_NO_DERIVED,
      COUNT_NO_REPORTED,
      REPORTER_TYPE_CD,
      REPORTER_TYPE_NAME,
      REPORTING_LEVELS
    from
      BMS.WF_NO_BMI_SUMM_VW_FN(('2020-11-01'), ('2020-11-01'))
    order by
      CYCLE_DT asc,
      STMT_NUM asc,
      COMP_PLAN_NUM asc offset ('6' :: int4) rows fetch next ('3' :: int4) rows only;
    
    -----------------
    -----------------
    -- ### 2025-04-28 22:28:05 [DEBUG] org.hibernate.SQL(135) logStatement()
    select
      COMP_PLAN_NUM,
      CYCLE_DT,
      STMT_NUM,
      ACCT_TYPE_CD,
      COMP_PLAN_NAME,
      COUNT_NO_BMI,
      COUNT_NO_DERIVED,
      COUNT_NO_REPORTED,
      REPORTER_TYPE_CD,
      REPORTER_TYPE_NAME,
      REPORTING_LEVELS
    from
      BMS.WF_NO_BMI_SUMM_VW_FN(?, ?)
    order by
      CYCLE_DT asc,
      STMT_NUM asc,
      COMP_PLAN_NUM asc offset ? rows fetch next ? rows only;
