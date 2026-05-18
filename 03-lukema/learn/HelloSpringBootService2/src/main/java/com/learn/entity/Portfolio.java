package com.learn.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.learn.util.EjbConstants;


@Entity
@Table(name = "Portfolio", schema = EjbConstants.SCHEMA)
//@Table(name = "Portfolio")
@SequenceGenerator(name = "Portfolio_Id", sequenceName = "Portfolio_Id")
public class Portfolio
    extends BeanBase {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Portfolio_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Portfolio_Id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "portfolio", orphanRemoval = true)
    @JoinColumn(name = "User_Id", unique = true, updatable = false)
    private PortfolioUser user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "portfolio", orphanRemoval = true)
    private List<PortfolioItem> portfolioItems;

    public List<PortfolioItem> getPortfolioItems() {
        if (portfolioItems == null) {
            portfolioItems = new ArrayList<PortfolioItem>();
        }

        return portfolioItems;
    }

    public void setPortfolioItems(List<PortfolioItem> portfolioItems) {
        this.portfolioItems = portfolioItems;
    }

    public void setUser(PortfolioUser value) {
        this.user = value;
    }

    public PortfolioUser getUser() {
        return user;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
