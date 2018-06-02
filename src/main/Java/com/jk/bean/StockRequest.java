package com.jk.bean;


/**
 * @Author: liyang117
 * @Date: 2018/5/6 19:43
 * @Description:
 */
public class StockRequest {
    private Integer operation ;
    private Integer category;
    private String voucherUid;
    private Integer voucherType;
    private String voucherName;
    private Double voucherAmount;
    private Integer type;
    private String typeDesc;
    private String xiangCount;
    private String kunCount;
    private String baCount;
    private String daiCount;
    private String heCount;
    private String allCount;
    private String amount;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }
}
