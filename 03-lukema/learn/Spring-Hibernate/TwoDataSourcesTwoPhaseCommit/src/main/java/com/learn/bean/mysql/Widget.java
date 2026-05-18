package com.learn.bean.mysql;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.learn.bean.BeanBase;
import com.learn.util.EjbConstants;


@Entity
@Table(name = "Widget", schema = EjbConstants.SCHEMA_MYSQL)
@TableGenerator(name = "Widget_Id", table = "GENERATOR_TABLE")
public class Widget
    extends BeanBase {
    private static final long serialVersionUID = 0L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Widget_Id")
    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
