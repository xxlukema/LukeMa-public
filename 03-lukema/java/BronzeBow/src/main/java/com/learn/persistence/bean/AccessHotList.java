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
@Table(name = "TMP_LUKE_ACCESS_HOT_LIST", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "Access_Hot_List_Id", sequenceName = "TMP_LUKE_ACCESS_HOT_LIST_Id")
@NamedQueries({
        @NamedQuery(name = AccessHotList.FindAccessHotListBySymbol, query = AccessHotList.FindAccessHotListBySymbol_QUERY),
        @NamedQuery(name = AccessHotList.RetrieveAccessHotList, query = AccessHotList.RetrieveAccessHotList_QUERY) })
public class AccessHotList
    extends AccessBase {
    private static final long serialVersionUID = 1L;

    private long accessCounter;

    protected static final String FindAccessHotListBySymbol_QUERY = "from AccessHotList where symbol = :symbol";
    public static final String FindAccessHotListBySymbol = "FindAccessHotListBySymbol";

    protected static final String RetrieveAccessHotList_QUERY = "from AccessHotList order by updateDate desc, accessCounter desc";
    public static final String RetrieveAccessHotList = "RetrieveAccessHotList";

    @Id
    @Column(name = "Access_Hot_List_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Access_Hot_List_Id")
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setAccessCounter(long accessCounter) {
        this.accessCounter = accessCounter;
    }

    public long getAccessCounter() {
        return accessCounter;
    }
}
