package com.learn.shein.psql.entity;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learn.entity.MyEntityBase;
import com.learn.util.EjbConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "shein_product_image", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "shein_product_image_pk_seq", sequenceName = "shein_product_image_pk_seq", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class SheinProductImage
    extends MyEntityBase {

    private static final long serialVersionUID = 1L;

    /** sequence based identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shein_product_image_pk_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private SheinProduct product;

    @Column(name = "file_name", nullable = false)
    private String fileName;

}
