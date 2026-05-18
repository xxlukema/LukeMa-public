package com.learn.persistence.bean;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
public abstract class BeanBase
    implements Serializable {
    private static final long serialVersionUID = 1L;

    public abstract Long getId();

    public abstract void setId(Long id);

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    private String notes;

    public Date getUpdateDate() {
        if (updateDate != null) {
            return updateDate;
        } else {
            return new Date();
        }
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        if (createDate != null) {
            return createDate;
        } else {
            return new Date();
        }
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
}
