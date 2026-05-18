package com.learn.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.learn.util.EjbConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "spring_security_authority", schema = EjbConstants.SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class SpringSecurityAuthorityEntity
    extends MyEntityBase {

    private static final long serialVersionUID = 1L;

    /** sequence based identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spring_security_authority_seq")
    @SequenceGenerator(name = "spring_security_authority_seq", sequenceName = "spring_security_authority_pk_sequence", allocationSize = 1)
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
