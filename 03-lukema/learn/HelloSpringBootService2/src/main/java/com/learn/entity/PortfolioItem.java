package com.learn.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.learn.util.EjbConstants;


@Entity
@Table(name = "PortfolioItem", schema = EjbConstants.SCHEMA)
//@Table(name = "PortfolioItem")
@SequenceGenerator(name = "PortfolioItem_Id", sequenceName = "PortfolioItem_Id")
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActived() {
        return actived;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
