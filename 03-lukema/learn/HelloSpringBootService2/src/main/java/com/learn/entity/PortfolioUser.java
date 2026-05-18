package com.learn.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.learn.util.EjbConstants;


@Entity
@Table(name = "Portfolio_User", schema = EjbConstants.SCHEMA)
//@Table(name = "User")
@SequenceGenerator(name = "Portfolio_User_Id", sequenceName = "Portfolio_User_Id")
public class PortfolioUser
    extends BeanBase {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "User_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Portfolio_User_Id")
    private Long id;

    @Column(unique = true, length = 60)
    private String username;

    @Column(length = 20)
    @NotNull
    private String password;

    @Column(name = "firstname", length = 40)
    private String firstname;

    @Column(name = "middlename", length = 40)
    private String middlename;

    @Column(name = "lastname", length = 40)
    private String lastname;

    @Column(unique = true, length = 60)
    @NotNull
    private String email;

    @Column(unique = true, length = 20)
    private String phone;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname + ", middlename=" + middlename + ", lastname="
                + lastname + ", email=" + email + ", phone=" + phone + ", portfolio=" + portfolio + "]";
    }

}
