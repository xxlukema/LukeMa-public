package com.learn.shein.psql.entity;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.learn.entity.MyEntityBase;
import com.learn.entity.SpringSecurityUserEntity;
import com.learn.util.EjbConstants;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "country", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "country_pk_seq", sequenceName = "country_pk_seq", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class Country
    extends MyEntityBase {

    private static final long serialVersionUID = 1L;

    /** sequence based identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_pk_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Valid
    @Nonnull
    @Column(name = "code", nullable = false)
    private String code;

    @Valid
    @Nonnull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "country")
    private SpringSecurityUserEntity user;

}
