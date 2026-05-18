package com.learn.persistence.dao.impl;


import java.io.Serializable;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;


@Repository("accessRecordsCleanupStoredProcedure")
public class AccessRecordsCleanupStoredProcedure
    extends StoredProcedure
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STORED_PROC_NAME = "Access_Records_Cleanup_Proc";

    private static final String AccessRecordsCleanuoParamName = "RowNumToKeep";

    private static final int AccessRecordsCleanuoParamValue = 10 * 1000;

    @Autowired(required = true)
    public AccessRecordsCleanupStoredProcedure(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource, STORED_PROC_NAME);

        declareParameter(new SqlParameter(AccessRecordsCleanuoParamName, Types.INTEGER));

        compile();
    }

    public void execute() {
        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put(AccessRecordsCleanuoParamName, AccessRecordsCleanuoParamValue);

        execute(inParams);
    }

}
