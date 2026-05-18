package com.learn.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "SwapLeg")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "legId", discriminatorType = DiscriminatorType.INTEGER)
//@TableGenerator(name = "SwapLegIdGenerator", table = "id_obj_gen_tmp", pkColumnName = "str_type", pkColumnValue = "SwapLeg", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
@TableGenerator(name = "SwapLegIdGenerator", table = "idobj_gen_view", pkColumnName = "type", pkColumnValue = "530", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
//@TableGenerator(name = "SwapLegIdGenerator", table = "idobj_gen", pkColumnName = "type", pkColumnValue = "530", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
abstract public class SwapLeg
    implements Serializable {
    private static final long serialVersionUID = 1L;

    protected static final int LONG = 1;

    protected static final int SHORT = 2;

    protected static final int EQUITY = 0;

    protected static final int INTEREST = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SwapLegIdGenerator")
    @Column(name = "fiId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parentId", nullable = false, updatable = false)
    private Swap parent;

    private int basisType;

    private float borrowSpread = 0.0f;

    private float borrowThreshold = 0.0f;

    private int ccyPairId;

    private int compoundDefault = 0;

    private int currencyId;

    private float dividendReinvestPct;

    private int dividendTreatmentType;

    private int divPay;

    @Temporal(TemporalType.TIMESTAMP)
    private Date enterTime;

    private int enterUserId;

    private float firstFixing;

    private int fixedVariableRate;

    private int fxDivDate;

    private int fxDivs;

    private int fxId;

    private int fxResetBumpConvention;

    private int fxResetCalendarId;

    private int fxResetRuleOffset;

    private int fxResetRuleType;

    @Column(length = 20)
    private String fxResetSource;

    private int getDatesFromLinked;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivityTime;

    private int lastActivityUserId;

    @Column(insertable = false, updatable = false)
    private int legId;

    private int legInstrId;

    @Column(name = "linkedLegId")
    private SwapLeg linkedLeg;

    private int longShort;

    private float notionalAmount = 0.0f;

    private int notionalType;

    private int paymentBumpConvention;

    private int paymentCalendarId;

    private int paymentFreqType;

    private int paymentPriority;

    private int paymentRuleOffset;

    private int paymentRuleType;

    private int period;

    private int resetBumpConvention;

    private int resetCalendarId;

    private int resetFreqType;

    private int resetRuleOffset;

    private int resetRuleType;

    @Column(length = 12)
    private String resetSource;

    private int rollDate;

    private float spread;

    private int status;

    private int structureType;

    private float taxCredit;

    public SwapLeg() {
    }

    public SwapLeg(StructureType structureType, LongShort longShort) {
        this.structureType = structureType.getValue();
        this.longShort = longShort.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Swap getParent() {
        return parent;
    }

    public void setParent(Swap parent) {
        this.parent = parent;
    }

    public int getBasisType() {
        return basisType;
    }

    public void setBasisType(int basisType) {
        this.basisType = basisType;
    }

    public int getCcyPairId() {
        return ccyPairId;
    }

    public void setCcyPairId(int ccyPairId) {
        this.ccyPairId = ccyPairId;
    }

    public int getCompoundDefault() {
        return compoundDefault;
    }

    public void setCompoundDefault(int compoundDefault) {
        this.compoundDefault = compoundDefault;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public float getDividendReinvestPct() {
        return dividendReinvestPct;
    }

    public void setDividendReinvestPct(float dividendReinvestPct) {
        this.dividendReinvestPct = dividendReinvestPct;
    }

    public int getDividendTreatmentType() {
        return dividendTreatmentType;
    }

    public void setDividendTreatmentType(int dividendTreatmentType) {
        this.dividendTreatmentType = dividendTreatmentType;
    }

    public int getDivPay() {
        return divPay;
    }

    public void setDivPay(int divPay) {
        this.divPay = divPay;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public int getEnterUserId() {
        return enterUserId;
    }

    public void setEnterUserId(int enterUserId) {
        this.enterUserId = enterUserId;
    }

    public float getFirstFixing() {
        return firstFixing;
    }

    public void setFirstFixing(float firstFixing) {
        this.firstFixing = firstFixing;
    }

    public int getFixedVariableRate() {
        return fixedVariableRate;
    }

    public void setFixedVariableRate(int fixedVariableRate) {
        this.fixedVariableRate = fixedVariableRate;
    }

    public int getFxDivDate() {
        return fxDivDate;
    }

    public void setFxDivDate(int fxDivDate) {
        this.fxDivDate = fxDivDate;
    }

    public int getFxDivs() {
        return fxDivs;
    }

    public void setFxDivs(int fxDivs) {
        this.fxDivs = fxDivs;
    }

    public int getFxId() {
        return fxId;
    }

    public void setFxId(int fxId) {
        this.fxId = fxId;
    }

    public int getFxResetBumpConvention() {
        return fxResetBumpConvention;
    }

    public void setFxResetBumpConvention(int fxResetBumpConvention) {
        this.fxResetBumpConvention = fxResetBumpConvention;
    }

    public int getFxResetCalendarId() {
        return fxResetCalendarId;
    }

    public void setFxResetCalendarId(int fxResetCalendarId) {
        this.fxResetCalendarId = fxResetCalendarId;
    }

    public int getFxResetRuleOffset() {
        return fxResetRuleOffset;
    }

    public void setFxResetRuleOffset(int fxResetRuleOffset) {
        this.fxResetRuleOffset = fxResetRuleOffset;
    }

    public int getFxResetRuleType() {
        return fxResetRuleType;
    }

    public void setFxResetRuleType(int fxResetRuleType) {
        this.fxResetRuleType = fxResetRuleType;
    }

    public String getFxResetSource() {
        return fxResetSource;
    }

    public void setFxResetSource(String fxResetSource) {
        this.fxResetSource = fxResetSource;
    }

    public int getGetDatesFromLinked() {
        return getDatesFromLinked;
    }

    public void setGetDatesFromLinked(int getDatesFromLinked) {
        this.getDatesFromLinked = getDatesFromLinked;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    public int getLastActivityUserId() {
        return lastActivityUserId;
    }

    public void setLastActivityUserId(int lastActivityUserId) {
        this.lastActivityUserId = lastActivityUserId;
    }

    public int getLegId() {
        return legId;
    }

    public void setLegId(int legId) {
        this.legId = legId;
    }

    public int getLegInstrId() {
        return legInstrId;
    }

    public void setLegInstrId(int legInstrId) {
        this.legInstrId = legInstrId;
    }

    public int getLongShort() {
        return longShort;
    }

    public int getNotionalType() {
        return notionalType;
    }

    public void setNotionalType(int notionalType) {
        this.notionalType = notionalType;
    }

    public int getPaymentBumpConvention() {
        return paymentBumpConvention;
    }

    public void setPaymentBumpConvention(int paymentBumpConvention) {
        this.paymentBumpConvention = paymentBumpConvention;
    }

    public int getPaymentCalendarId() {
        return paymentCalendarId;
    }

    public void setPaymentCalendarId(int paymentCalendarId) {
        this.paymentCalendarId = paymentCalendarId;
    }

    public int getPaymentFreqType() {
        return paymentFreqType;
    }

    public void setPaymentFreqType(int paymentFreqType) {
        this.paymentFreqType = paymentFreqType;
    }

    public int getPaymentPriority() {
        return paymentPriority;
    }

    public void setPaymentPriority(int paymentPriority) {
        this.paymentPriority = paymentPriority;
    }

    public int getPaymentRuleOffset() {
        return paymentRuleOffset;
    }

    public void setPaymentRuleOffset(int paymentRuleOffset) {
        this.paymentRuleOffset = paymentRuleOffset;
    }

    public int getPaymentRuleType() {
        return paymentRuleType;
    }

    public void setPaymentRuleType(int paymentRuleType) {
        this.paymentRuleType = paymentRuleType;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getResetBumpConvention() {
        return resetBumpConvention;
    }

    public void setResetBumpConvention(int resetBumpConvention) {
        this.resetBumpConvention = resetBumpConvention;
    }

    public int getResetCalendarId() {
        return resetCalendarId;
    }

    public void setResetCalendarId(int resetCalendarId) {
        this.resetCalendarId = resetCalendarId;
    }

    public int getResetFreqType() {
        return resetFreqType;
    }

    public void setResetFreqType(int resetFreqType) {
        this.resetFreqType = resetFreqType;
    }

    public int getResetRuleOffset() {
        return resetRuleOffset;
    }

    public void setResetRuleOffset(int resetRuleOffset) {
        this.resetRuleOffset = resetRuleOffset;
    }

    public int getResetRuleType() {
        return resetRuleType;
    }

    public void setResetRuleType(int resetRuleType) {
        this.resetRuleType = resetRuleType;
    }

    public String getResetSource() {
        return resetSource;
    }

    public void setResetSource(String resetSource) {
        this.resetSource = resetSource;
    }

    public int getRollDate() {
        return rollDate;
    }

    public void setRollDate(int rollDate) {
        this.rollDate = rollDate;
    }

    public float getSpread() {
        return spread;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStructureType() {
        return structureType;
    }

    public float getTaxCredit() {
        return taxCredit;
    }

    public void setTaxCredit(float taxCredit) {
        this.taxCredit = taxCredit;
    }

    public float getBorrowSpread() {
        return borrowSpread;
    }

    public float getBorrowThreshold() {
        return borrowThreshold;
    }

    public float getNotionalAmount() {
        return notionalAmount;
    }

    public void setBorrowSpread(float borrowSpread) {
        this.borrowSpread = borrowSpread;
    }

    public void setBorrowThreshold(float borrowThreshold) {
        this.borrowThreshold = borrowThreshold;
    }

    public void setNotionalAmount(float notionalAmount) {
        this.notionalAmount = notionalAmount;
    }

    public SwapLeg getLinkedLeg() {
        return linkedLeg;
    }

    public void setLinkedLeg(SwapLeg linkedLeg) {
        this.linkedLeg = getLinkedLeg();
    }

    /*
    private int           fxDivResetRuleOffset;
     */

}
