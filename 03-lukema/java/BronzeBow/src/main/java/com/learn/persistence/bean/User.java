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
@Table(name = "TMP_LUKE_USER", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "User_Id", sequenceName = "TMP_LUKE_USER_Id") // Oracle
@NamedQueries({ @NamedQuery(name = User.FindUserByUsername, query = User.FindUserByUsername_QUERY),
        @NamedQuery(name = User.FindUserByEmail, query = User.FindUserByEmail_QUERY) })
public class User
    extends BeanBase {
    private static final long serialVersionUID = 1L;

    protected static final String FindUserByUsername_QUERY = "from User where username = :username";
    public static final String FindUserByUsername = "FindUserByUsername";

    protected static final String FindUserByEmail_QUERY = "from User where email = :email";
    public static final String FindUserByEmail = "FindUserByEmail";

    @Id
    @Column(name = "User_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_Id") // Oracle
    private Long id;

    private String username;

    private String password;

    private String email;

    private String stockList;

    private String remoteAddress;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setStockList(String stockList) {
        this.stockList = stockList;
    }

    public String getStockList() {
        return stockList;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRemoteAddress() {
        return remoteAddress;
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
