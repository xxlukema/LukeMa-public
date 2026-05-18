package com.learn.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "Swap")
//@TableGenerator(name = "SwapIdGenerator", table = "id_obj_gen_tmp", pkColumnName = "str_type", pkColumnValue = "Swap", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
@TableGenerator(name = "SwapIdGenerator", table = "idobj_gen_view", pkColumnName = "type", pkColumnValue = "529", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
//@TableGenerator(name = "SwapIdGenerator", table = "idobj_gen", pkColumnName = "type", pkColumnValue = "529", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
public class Swap
    implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SwapIdGenerator")
    @Column(name = "fiId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private SwapLegEquityLong swapLegEquityLong;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private SwapLegEquityShort swapLegEquityShort;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private SwapLegInterestLong swapLegInterestLong;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private SwapLegInterestShort swapLegInterestShort;

    private int autoMature = 0;

    private int bookId;

    private int btbBookId1 = 0;

    private int btbBookId2 = 0;

    private int btbEntityId1 = 0;

    private int btbEntityId2 = 0;

    private int businessUnitId;

    private int calcAgent;

    private int collSwap;

    private float collSwapAmt;

    private float collSwapAmt2;

    private int countryId;

    private float credit = 0.0f;

    private int customerId;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date enterTime;

    private int exchangeNotional;

    private float externalThreshold;

    private float feedComm;

    private int feedCommPayType;

    private int feedCommType;

    private int fundManager;

    private int fundingSwap;

    private float initialMarginAmt = 0.0f;

    private float initialMarginPct = 0.0f;

    private float internalThreshold;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivityTime;

    private int maturityTag;

    @Column(length = 12)
    private String name;

    private int payAsRealized;

    private int rebateIncluded;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    private int status;

    private int stockIncluded = 0;

    private int swapNum;

    private int swapType;

    private int termFunding;

    // private int                  totalReturnSwap;

    @Temporal(TemporalType.DATE)
    private Date tradeDate;

    private int tradePartyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(int businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public int getCalcAgent() {
        return calcAgent;
    }

    public void setCalcAgent(int calcAgent) {
        this.calcAgent = calcAgent;
    }

    public int getCollSwap() {
        return collSwap;
    }

    public void setCollSwap(int collSwap) {
        this.collSwap = collSwap;
    }

    public float getCollSwapAmt() {
        return collSwapAmt;
    }

    public void setCollSwapAmt(float collSwapAmt) {
        this.collSwapAmt = collSwapAmt;
    }

    public float getCollSwapAmt2() {
        return collSwapAmt2;
    }

    public void setCollSwapAmt2(float collSwapAmt2) {
        this.collSwapAmt2 = collSwapAmt2;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public int getExchangeNotional() {
        return exchangeNotional;
    }

    public void setExchangeNotional(int exchangeNotional) {
        this.exchangeNotional = exchangeNotional;
    }

    public float getExternalThreshold() {
        return externalThreshold;
    }

    public void setExternalThreshold(float externalThreshold) {
        this.externalThreshold = externalThreshold;
    }

    public float getFeedComm() {
        return feedComm;
    }

    public void setFeedComm(float feedComm) {
        this.feedComm = feedComm;
    }

    public int getFeedCommPayType() {
        return feedCommPayType;
    }

    public void setFeedCommPayType(int feedCommPayType) {
        this.feedCommPayType = feedCommPayType;
    }

    public int getFeedCommType() {
        return feedCommType;
    }

    public void setFeedCommType(int feedCommType) {
        this.feedCommType = feedCommType;
    }

    public int getFundManager() {
        return fundManager;
    }

    public void setFundManager(int fundManager) {
        this.fundManager = fundManager;
    }

    public int getFundingSwap() {
        return fundingSwap;
    }

    public void setFundingSwap(int fundingSwap) {
        this.fundingSwap = fundingSwap;
    }

    public float getInternalThreshold() {
        return internalThreshold;
    }

    public void setInternalThreshold(float internalThreshold) {
        this.internalThreshold = internalThreshold;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    public int getMaturityTag() {
        return maturityTag;
    }

    public void setMaturityTag(int maturityTag) {
        this.maturityTag = maturityTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayAsRealized() {
        return payAsRealized;
    }

    public void setPayAsRealized(int payAsRealized) {
        this.payAsRealized = payAsRealized;
    }

    public int getRebateIncluded() {
        return rebateIncluded;
    }

    public void setRebateIncluded(int rebateIncluded) {
        this.rebateIncluded = rebateIncluded;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSwapNum() {
        return swapNum;
    }

    public void setSwapNum(int swapNum) {
        this.swapNum = swapNum;
    }

    public int getSwapType() {
        return swapType;
    }

    public void setSwapType(int swapType) {
        this.swapType = swapType;
    }

    public int getTermFunding() {
        return termFunding;
    }

    public void setTermFunding(int termFunding) {
        this.termFunding = termFunding;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public int getTradePartyId() {
        return tradePartyId;
    }

    public void setTradePartyId(int tradePartyId) {
        this.tradePartyId = tradePartyId;
    }

    public int getAutoMature() {
        return autoMature;
    }

    public int getBtbBookId1() {
        return btbBookId1;
    }

    public int getBtbBookId2() {
        return btbBookId2;
    }

    public int getBtbEntityId1() {
        return btbEntityId1;
    }

    public int getBtbEntityId2() {
        return btbEntityId2;
    }

    public float getCredit() {
        return credit;
    }

    public float getInitialMarginAmt() {
        return initialMarginAmt;
    }

    public float getInitialMarginPct() {
        return initialMarginPct;
    }

    public int getStockIncluded() {
        return stockIncluded;
    }

    public SwapLegEquityLong getSwapLegEquityLong() {
        return swapLegEquityLong;
    }

    public void setSwapLegEquityLong(SwapLegEquityLong swapLegEquityLong) {
        this.swapLegEquityLong = swapLegEquityLong;
    }

    public SwapLegEquityShort getSwapLegEquityShort() {
        return swapLegEquityShort;
    }

    public void setSwapLegEquityShort(SwapLegEquityShort swapLegEquityShort) {
        this.swapLegEquityShort = swapLegEquityShort;
    }

    public SwapLegInterestLong getSwapLegInterestLong() {
        return swapLegInterestLong;
    }

    public void setSwapLegInterestLong(SwapLegInterestLong swapLegInterestLong) {
        this.swapLegInterestLong = swapLegInterestLong;
    }

    public SwapLegInterestShort getSwapLegInterestShort() {
        return swapLegInterestShort;
    }

    public void setSwapLegInterestShort(SwapLegInterestShort swapLegInterestShort) {
        this.swapLegInterestShort = swapLegInterestShort;
    }

    /*
    private smalldatetime archiveDate;
    private smalldatetime autoMatureDate;
    private varchar       comment;
    private varchar       contact;
    private varchar       description;
    private tinyint       divRounding;
    private int           docMethodType;
    private smallint      indexDivEntitle;   
    private tinyint       offsetRollForward;
    private smalldatetime proposedTermDate;
    private int           structureNo;
     */

}
