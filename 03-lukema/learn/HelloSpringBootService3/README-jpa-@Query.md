# JPA `@Query`

## Call postgres function

`bms-core` : `NoBmiQuerySummaryRepository`:

    package org.frb.stls.bms.core.repositories.bmi;
    
    import org.frb.stls.bms.core.entities.bmi.NoBmiQuerySummary;
    import org.frb.stls.bms.core.entities.bmi.NoBmiQuerySummaryId;
    import org.frb.stls.bms.shared.jpa.scroll.ScrollingRepository;
    import org.springframework.cglib.core.Predicate;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.Query;
    
    public interface NoBmiQuerySummaryRepository extends ScrollingRepository<NoBmiQuerySummary, NoBmiQuerySummaryId> {
    
        public <T> Page<T> findAll(Predicate predicate, Pageable pageable);
        
        @Query(value = """
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
            from BMS.WF_NO_BMI_SUMM_VW_FN(:statementStartDate, :statementEndDate)
            where (:stmtNum IS NULL OR cast(STMT_NUM as text) like :stmtNum escape '!')
              and (:compPlanNum IS NULL OR cast(COMP_PLAN_NUM as text) like :compPlanNum escape '!')
              and (:reportTypeName IS NULL OR REPORTER_TYPE_NAME = :reportTypeName)
              and (:countNoDerived IS NULL OR COUNT_NO_DERIVED > :countNoDerived)
              and (:countNoReported IS NULL OR COUNT_NO_REPORTED > :countNoReported)
                """,
                nativeQuery = true)
        public Page<NoBmiQuerySummary> getPage(
                @Param("statementStartDate") String statementStartDate,
                @Param("statementEndDate") String statementEndDate,
                @Param("stmtNum") String stmtNum,
                @Param("compPlanNum") String compPlanNum,
                @Param("reportTypeName") String reportTypeName,
                @Param("countNoDerived") Long countNoDerived,
                @Param("countNoReported") Long countNoReported,
                Pageable pageable);
    }

## `@NotBlank` deprecated

Replace org.hibernate.validator.constraints.NotBlank with jakarta.validation.constraints.NotBlank

## `*Criteria.java` `toString()` is needed

Add `@ToString` to the classes
