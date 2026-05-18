package com.learn.bean;


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
    private String mortgageBank;
    private Integer principlaBalance;
    private Float interestRate;
    private String maturityDate;
    private Float monthlyPrincipalPayment;
    private Float monthlyInterestPayment;
    private Float monthlyPropertyTax;
    private Float monthlyMUDTax = 0f;
    private Float monthlyLIDTax = 0f;
    private Float monthlyInsurancePayment;
    private Float monthlyHOAPayment;
    private Float monthlyEscrow;
    private Float monthlyManagementChargeRate = 0f;

    private Float monthlyRentIncome = 0f;

    private Integer squareFootage;
    private Integer lotSizeSquareFeet;
    private String taxId;
    private String lotDemesion;

    public Float getMonthlyMortgagePayment() {
        return this.getMonthlyPrincipalPayment() + this.getMonthlyInterestPayment();
    }

    public Float getMonthlyManagementFee() {
        return this.getMonthlyManagementChargeRate() * this.getMonthlyRentIncome();
    }

    public Float getMonthlyPaymentsForLoanNoLidHudManagement() {
        return this.getMonthlyMortgagePayment() + this.getMonthlyEscrow() + this.getMonthlyHOAPayment();
    }

    public Float getMonthlyPaymentsForLukeWithLidHudManagement() {
        return this.getMonthlyPaymentsForLoanNoLidHudManagement() + this.getMonthlyLIDTax() + this.getMonthlyMUDTax() + this.getMonthlyManagementFee();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getMortgageBank() {
        return mortgageBank;
    }

    public void setMortgageBank(String mortgageBank) {
        this.mortgageBank = mortgageBank;
    }

    public Integer getPrinciplaBalance() {
        return principlaBalance;
    }

    public void setPrinciplaBalance(Integer principlaBalance) {
        this.principlaBalance = principlaBalance;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Float getMonthlyPrincipalPayment() {
        return monthlyPrincipalPayment;
    }

    public void setMonthlyPrincipalPayment(Float monthlyPrincipalPayment) {
        this.monthlyPrincipalPayment = monthlyPrincipalPayment;
    }

    public Float getMonthlyInterestPayment() {
        return monthlyInterestPayment;
    }

    public void setMonthlyInterestPayment(Float monthlyInterestPayment) {
        this.monthlyInterestPayment = monthlyInterestPayment;
    }

    public Float getMonthlyPropertyTax() {
        return monthlyPropertyTax;
    }

    public void setMonthlyPropertyTax(Float monthlyPropertyTax) {
        this.monthlyPropertyTax = monthlyPropertyTax;
    }

    public Float getMonthlyMUDTax() {
        return monthlyMUDTax;
    }

    public void setMonthlyMUDTax(Float monthlyMUDTax) {
        this.monthlyMUDTax = monthlyMUDTax;
    }

    public Float getMonthlyLIDTax() {
        return monthlyLIDTax;
    }

    public void setMonthlyLIDTax(Float monthlyLIDTax) {
        this.monthlyLIDTax = monthlyLIDTax;
    }

    public Float getMonthlyInsurancePayment() {
        return monthlyInsurancePayment;
    }

    public void setMonthlyInsurancePayment(Float monthlyInsurancePayment) {
        this.monthlyInsurancePayment = monthlyInsurancePayment;
    }

    public Float getMonthlyHOAPayment() {
        return monthlyHOAPayment;
    }

    public void setMonthlyHOAPayment(Float monthlyHOAPayment) {
        this.monthlyHOAPayment = monthlyHOAPayment;
    }

    public Float getMonthlyRentIncome() {
        return monthlyRentIncome;
    }

    public void setMonthlyRentIncome(Float monthlyRentIncome) {
        this.monthlyRentIncome = monthlyRentIncome;
    }

    public Float getMonthlyEscrow() {
        return monthlyEscrow;
    }

    public void setMonthlyEscrow(Float monthlyEscrow) {
        this.monthlyEscrow = monthlyEscrow;
    }

    public Float getMonthlyManagementChargeRate() {
        return monthlyManagementChargeRate;
    }

    public void setMonthlyManagementChargeRate(Float monthlyManagementChargeRate) {
        this.monthlyManagementChargeRate = monthlyManagementChargeRate;
    }

    public Integer getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Integer squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Integer getLotSizeSquareFeet() {
        return lotSizeSquareFeet;
    }

    public void setLotSizeSquareFeet(Integer lotSizeSquareFeet) {
        this.lotSizeSquareFeet = lotSizeSquareFeet;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getLotDemesion() {
        return lotDemesion;
    }

    public void setLotDemesion(String lotDemesion) {
        this.lotDemesion = lotDemesion;
    }

    @Override
    public String toString() {
        return "House [id=" + id + ", description=" + description + ", address=" + address + ", city=" + city + ", state=" + state + ", zip=" + zip + ", purchaseDate="
                + purchaseDate + ", purchasePrice=" + purchasePrice + ", currentPrice=" + currentPrice + ", mortgageBank=" + mortgageBank + ", principlaBalance="
                + principlaBalance + ", interestRate=" + interestRate + ", maturityDate=" + maturityDate + ", monthlyPrincipalPayment=" + monthlyPrincipalPayment
                + ", monthlyInterestPayment=" + monthlyInterestPayment + ", monthlyPropertyTax=" + monthlyPropertyTax + ", monthlyMUDTax=" + monthlyMUDTax
                + ", monthlyLIDTax=" + monthlyLIDTax + ", monthlyInsurancePayment=" + monthlyInsurancePayment + ", monthlyHOAPayment=" + monthlyHOAPayment
                + ", monthlyRentIncome=" + monthlyRentIncome + ", monthlyEscrow=" + monthlyEscrow + ", monthlyManagementChargeRate=" + monthlyManagementChargeRate
                + ", squareFootage=" + squareFootage + ", lotSizeSquareFeet=" + lotSizeSquareFeet + ", taxId=" + taxId + ", lotDemesion=" + lotDemesion + "]";
    }

}
