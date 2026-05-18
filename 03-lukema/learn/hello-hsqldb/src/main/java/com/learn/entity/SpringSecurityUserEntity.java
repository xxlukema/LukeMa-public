package com.learn.entity;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.learn.util.EjbConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "spring_security_user", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "spring_security_user_seq", sequenceName = "spring_security_user_pk_seq", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class SpringSecurityUserEntity
    extends MyEntityBase {

    private static final long serialVersionUID = 1L;

    /** sequence based identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spring_security_user_seq")
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    /**
     * for Shein
     */
    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "is_buy_only")
    private Boolean isBuyOnly;

    /**
     * join column is in table for SpringSecurityAuthorityEntity
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<SpringSecurityAuthorityEntity> authorities = new ArrayList<>();

}
