package com.learn.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.learn.util.EjbConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "spring_security_user", schema = EjbConstants.SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class SpringSecurityUserEntity
    extends MyEntityBase {

    private static final long serialVersionUID = 1L;

    /** sequence based identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spring_security_user_seq")
    @SequenceGenerator(name = "spring_security_user_seq", sequenceName = "spring_security_user_pk_sequence", allocationSize = 1)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    /**
     * join column is in table for SpringSecurityAuthorityEntity
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<SpringSecurityAuthorityEntity> authorities = new ArrayList<>();

}
