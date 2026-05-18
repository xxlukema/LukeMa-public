package com.learn.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.learn.util.EjbConstants;


@Entity
@Table(name = "Customer", schema = EjbConstants.SCHEMA)
//@Table(name = "Customer")
@SequenceGenerator(name = "Customer_Id", sequenceName = "Customer_Id")
public class Customer
    extends BeanBase {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Customer_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_Id")
    private Long id;

    private String username;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
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
