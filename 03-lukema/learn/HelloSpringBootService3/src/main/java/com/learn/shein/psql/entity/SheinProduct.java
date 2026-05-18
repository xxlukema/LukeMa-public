package com.learn.shein.psql.entity;


import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.learn.entity.MyEntityBase;
import com.learn.util.EjbConstants;

import jakarta.annotation.Nonnull;
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
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "shein_product", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "shein_product_pk_seq", sequenceName = "shein_product_pk_seq", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class SheinProduct
    extends MyEntityBase {

    private static final long serialVersionUID = 1L;

    /** sequence based identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shein_product_pk_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Valid
    @Nonnull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "image_url_prefix", nullable = false)
    private String imageUrlPrefix;

    @Valid
    @Nonnull
    @Column(name = "price", nullable = false)
    private Float price;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SheinProductImage> images;

}
