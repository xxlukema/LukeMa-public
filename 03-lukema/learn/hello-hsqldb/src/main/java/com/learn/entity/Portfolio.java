package com.learn.entity;


import java.util.ArrayList;
import java.util.List;

import com.learn.util.EjbConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "Portfolio", schema = EjbConstants.SCHEMA)
@SequenceGenerator(name = "Portfolio_Id", sequenceName = "Portfolio_Id")
@Getter
@Setter
@ToString
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
            portfolioItems = new ArrayList<>();
        }

        return portfolioItems;
    }
}
