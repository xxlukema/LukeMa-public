package com.learn.boot.jpa.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import com.learn.boot.jpa.pojo.SysdatePojo;
import com.learn.util.EjbConstants;
import com.learn.util.JdbcConstants;


/**
 * NamedNativeQueries annotation can be applied to an entity or mapped superclass. --- from javadoc of NamedNativeQueries
 * A mapped superclass has no separate table defined for it. --- from javadoc of MappedSuperclass
 * 
 * There is no mapped entity in this example. And there is no intention to create an entity. Therefore, it is applied to @MappedSuperclass.
 */
// @formatter:off
@NamedNativeQueries({
    @NamedNativeQuery(name = EjbConstants.Select_Current_Date, query = JdbcConstants.SQL_SELECT_SYSDATE_NAMED_QUERY, resultSetMapping = "NamedNativeMaps")
})
@SqlResultSetMapping(name="NamedNativeMaps", classes ={
    @ConstructorResult(targetClass  = SysdatePojo.class,
    columns  = {
        /**
         * sysdate or sysDate does not matter because @ConstructorResult will be used for column name to pojo property mapping.
         * The order of @ColumnResult must match the constructor argument order.
         */
        @ColumnResult(name = "sys_date", type = Date.class),
        @ColumnResult(name = "note", type = String.class)
    })
})
// @formatter:on
@MappedSuperclass
public class BeanBase
    implements Serializable {
    private static final long serialVersionUID = 0L;
}
