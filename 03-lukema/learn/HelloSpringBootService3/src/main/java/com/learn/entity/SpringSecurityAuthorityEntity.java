package com.learn.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.learn.util.EjbConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "spring_security_authority", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "spring_security_authority_seq", sequenceName = "spring_security_authority_pk_seq", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class SpringSecurityAuthorityEntity
    extends MyEntityBase {

    private static final long serialVersionUID = 1L;

    /** sequence based identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spring_security_authority_seq")
    @Column(name = "authority_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    /**
     * Role names as String: ROLE_ADMIN, ROLE_USER
     */
    @Column(name = "authority", nullable = true)
    private String authority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private SpringSecurityUserEntity user;

}
