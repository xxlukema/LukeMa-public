package com.learn.pojo;

import lombok.Data;

@Data
public class House {

    private Integer id;
    private String description;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String purchaseDate;
    private Integer purchasePrice;
    private Integer currentPrice;
    private String mortgageBank = "Chase Mortgage";
    private Integer initialPrincipalBalance;
    private Integer remainingPrincipalBalance;
    private Float interestRate;
    private String loanStartDate;
    private String maturityDate;
    private Float monthlyPrincipalPayment;
    private Float monthlyInterestPayment;
    private Float monthlyCountyPropertyTax;
    private Float monthlyMUDTax = 0f;
    private Float monthlyLIDTax = 0f;
    private Float monthlyInsurancePayment;
    private Float monthlyHOAPayment;
    private Float calculatedMonthlyEscrow;
    private Float actualMonthlyEscrow;
    private Float monthlyManagementChargeRate = 0f;

    private final Float monthlyManagementFee = 150f;

    private Float monthlyRentIncome = 0f;

    private Integer squareFootage;
    private Integer lotSizeSquareFeet;
    private String taxId;
    private String lotDemesion;

    public Float getMonthlyMortgagePayment() {
        return this.getMonthlyPrincipalPayment() + this.getMonthlyInterestPayment();
    }

    public Float getMonthlyManagementFee() {
        // return this.getMonthlyManagementChargeRate() * this.getMonthlyRentIncome();
        return monthlyManagementFee;
    }

    public Float getMonthlyPaymentsForLoanNoLidHudManagement() {
        return this.getMonthlyMortgagePayment() + this.getMonthlyCountyPropertyTax() + this.getMonthlyInsurancePayment()
                + this.getMonthlyHOAPayment();
    }

    public Float getMonthlyPaymentsForLukeWithLidHudManagement() {
        return this.getMonthlyPaymentsForLoanNoLidHudManagement() + this.getMonthlyLIDTax() + this.getMonthlyMUDTax()
                + this.getMonthlyManagementFee();
    }

    public Float getMonthlyNetProfitForLoanNoPrincipal() {
        return this.getMonthlyRentIncome() - this.getMonthlyPaymentsForLoanNoLidHudManagement();
    }

    public Float getMonthlyNetProfitForLoanWithPrincipal() {
        return this.getMonthlyNetProfitForLoanNoPrincipal() + this.getMonthlyPrincipalPayment();
    }

    public Float getMonthlyNetProfitForLukeNoPrincipal() {
        return this.getMonthlyRentIncome() - this.getMonthlyPaymentsForLukeWithLidHudManagement();
    }

    public Float getMonthlyNetProfitForLukeWithPrincipal() {
        return this.getMonthlyNetProfitForLukeNoPrincipal() + this.getMonthlyPrincipalPayment();
    }
}
