package com.learn.bean;


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
import javax.persistence.Transient;

import com.learn.util.EjbConstants;

import net.neurotech.quotes.Quote;


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

    private String symbol;

    private Integer shares;

    @Transient
    private Quote quote;

    private double currentValue;

    private double gainLoss;

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer value) {
        this.shares = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String value) {
        this.symbol = value;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double value) {
        this.currentValue = value;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote value) {
        this.quote = value;
    }

    public double getGainLoss() {
        return gainLoss;
    }

    public void setGainLoss(double value) {
        this.gainLoss = value;
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
