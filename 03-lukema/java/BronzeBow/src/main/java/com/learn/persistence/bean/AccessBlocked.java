package com.learn.persistence.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.learn.util.EjbConstants;


@Entity
@Table(name = "TMP_LUKE_ACCESS_BLOCKED", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "Access_Blocked_Id", sequenceName = "TMP_LUKE_ACCESS_BLOCKED_Id")
public class AccessBlocked
    extends AccessBase {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Access_Blocked_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Access_Blocked_Id")
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
