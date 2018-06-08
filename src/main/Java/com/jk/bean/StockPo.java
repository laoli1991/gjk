package com.jk.bean;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.jk.utils.AppUtils;


/**
 * @Author: liyang117
 * @Date: 2018/5/6 19:43
 * @Description:
 */
public class StockPo implements Comparable<StockPo> {
    private String stockUid;
    private String voucherUid;
    private Integer voucherType;
    private String voucherName;
    private Double voucherAmount;
    private Long voucherXiang2Kun;
    private Long voucherDai2Kun;
    private Long voucherKun2Ba;
    private Long voucherBa2Zhang;
    private Long voucherXiang2He;
    private Long voucherHe2Mei;
    private Integer type;
    private String typeDesc;
    private String xiangCount;
    private String kunCount;
    private String baCount;
    private String daiCount;
    private String heCount;
    private String allCount;
    private String amount;
    private String createTime;

    public StockPo() {
        this.createTime = AppUtils.getNowStr();
        this.stockUid = AppUtils.getStockUid(this);
    }

    public StockPo(Integer type, String voucherUid) {
        this.voucherUid = voucherUid;
        this.type = type;
        this.stockUid = AppUtils.getStockUid(this);
        this.createTime = AppUtils.getNowStr();
    }

    public String getVoucherUid() {
        return voucherUid;
    }

    public void setVoucherUid(String voucherUid) {
        this.voucherUid = voucherUid;
    }

    public Integer getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(Integer voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Double getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(Double voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getXiangCount() {
        return xiangCount;
    }

    public void setXiangCount(String xiangCount) {
        this.xiangCount = xiangCount;
    }

    public String getKunCount() {
        return kunCount;
    }

    public void setKunCount(String kunCount) {
        this.kunCount = kunCount;
    }

    public String getBaCount() {
        return baCount;
    }

    public void setBaCount(String baCount) {
        this.baCount = baCount;
    }

    public String getDaiCount() {
        return daiCount;
    }

    public void setDaiCount(String daiCount) {
        this.daiCount = daiCount;
    }

    public String getHeCount() {
        return heCount;
    }

    public void setHeCount(String heCount) {
        this.heCount = heCount;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getStockUid() {
        if (stockUid == null) {
            stockUid = AppUtils.getStockUid(this);
        }
        return stockUid;
    }

    public void setStockUid(String stockUid) {
        this.stockUid = stockUid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getVoucherXiang2Kun() {
        return voucherXiang2Kun;
    }

    public void setVoucherXiang2Kun(Long voucherXiang2Kun) {
        this.voucherXiang2Kun = voucherXiang2Kun;
    }

    public Long getVoucherDai2Kun() {
        return voucherDai2Kun;
    }

    public void setVoucherDai2Kun(Long voucherDai2Kun) {
        this.voucherDai2Kun = voucherDai2Kun;
    }

    public Long getVoucherKun2Ba() {
        return voucherKun2Ba;
    }

    public void setVoucherKun2Ba(Long voucherKun2Ba) {
        this.voucherKun2Ba = voucherKun2Ba;
    }

    public Long getVoucherBa2Zhang() {
        return voucherBa2Zhang;
    }

    public void setVoucherBa2Zhang(Long voucherBa2Zhang) {
        this.voucherBa2Zhang = voucherBa2Zhang;
    }

    public Long getVoucherXiang2He() {
        return voucherXiang2He;
    }

    public void setVoucherXiang2He(Long voucherXiang2He) {
        this.voucherXiang2He = voucherXiang2He;
    }

    public Long getVoucherHe2Mei() {
        return voucherHe2Mei;
    }

    public void setVoucherHe2Mei(Long voucherHe2Mei) {
        this.voucherHe2Mei = voucherHe2Mei;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockPo stockPo = (StockPo) o;
        return Objects.equal(stockUid, stockPo.stockUid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stockUid);
    }

    public int compareTo(StockPo other) {
        return ComparisonChain.start()
                .compare(other.voucherAmount, this.voucherAmount)
                .compare(this.type, other.type)
                .result();
    }
}
