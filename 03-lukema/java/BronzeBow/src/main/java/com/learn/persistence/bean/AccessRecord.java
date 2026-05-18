package com.learn.persistence.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.learn.util.EjbConstants;


@Entity
@Table(name = "TMP_LUKE_ACCESS_RECORD", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "Access_Record_Id", sequenceName = "TMP_LUKE_ACCESS_RECORD_Id")
@NamedQueries({
        @NamedQuery(name = AccessRecord.GetRecentAccessCount, query = AccessRecord.GetRecentAccessCount_QUERY) })
public class AccessRecord
    extends AccessBase {
    private static final long serialVersionUID = 1L;

    protected static final String GetRecentAccessCount_QUERY = "select count(*) from AccessRecord where remoteAddress = :remoteAddress and createDate >= :date";
    public static final String GetRecentAccessCount = "GetRecentAccessCount";

    @Id
    @Column(name = "Access_Record_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Access_Record_Id")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
