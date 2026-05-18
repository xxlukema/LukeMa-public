package com.learn.entity;


import com.learn.util.EjbConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "PortfolioItem", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "PortfolioItem_Id", sequenceName = "PortfolioItem_Id")
@Getter
@Setter
public class PortfolioItem
    extends BeanBase {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PortfolioItem_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PortfolioItem_Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Portfolio_Id")
    private Portfolio portfolio;

    private String title;
    private String description;
    private String address;
    private String city;
    private String state;
    private String zip;
    private Float price;
    private Boolean actived;
}
