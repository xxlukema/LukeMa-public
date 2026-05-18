package com.learn.entity;


import java.io.Serializable;
import java.util.Date;

import com.learn.pojo.CurrentDatePojo;
import com.learn.util.EjbConstants;
import com.learn.util.JdbcConstants;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Setter;


// @formatter:off
@NamedNativeQueries({
    // @NamedNativeQuery(name = EjbConstants.Select_Current_Date, query = "select to_char(current_date, 'YYYY-MM-DD') as date, 'desc' as note", resultSetMapping = "NamedNativeMaps"),
    @NamedNativeQuery(name = EjbConstants.Select_Current_Date, query = JdbcConstants.SQL_SELECT_SYSDATE_NAMED_QUERY, resultSetMapping = "NamedNativeMaps"),
    // @NamedNativeQuery(name = "selectAuthorEntities", query = "SELECT a.id, a.version, a.firstname, a.lastname FROM Author a", resultClass = Author.class),
    // @NamedNativeQuery(name = "selectAuthorValue", query = "SELECT a.id, a.firstname, a.lastname, count(b.id) as numBooks FROM Author a JOIN BookAuthor ba on a.id = ba.authorid JOIN Book b ON b.id = ba.bookid GROUP BY a.id", resultSetMapping = "AuthorValueMapping")
})
@SqlResultSetMapping(name="NamedNativeMaps", classes ={
    @ConstructorResult(targetClass  = CurrentDatePojo.class,
    columns  = {
        @ColumnResult(name = "date", type = Date.class),
        @ColumnResult(name = "note", type = String.class)
    })
})
// @formatter:on
@Setter
@MappedSuperclass
public abstract class BeanBase
    implements Serializable {
    private static final long serialVersionUID = 0L;

    public abstract Long getId();

    public abstract void setId(Long id);

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    public Date getUpdateDate() {
        if (updateDate != null) {
            return updateDate;
        } else {
            return new Date();
        }
    }

    public Date getCreateDate() {
        if (createDate != null) {
            return createDate;
        } else {
            return new Date();
        }
    }
}
