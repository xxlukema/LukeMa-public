package com.learn.entity;


import com.learn.util.EjbConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "Portfolio_User", schema = EjbConstants.SCHEMA)
//@Table(name = "User")
@SequenceGenerator(name = "Portfolio_User_Id", sequenceName = "Portfolio_User_Id")
@Getter
@Setter
@ToString
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

}
