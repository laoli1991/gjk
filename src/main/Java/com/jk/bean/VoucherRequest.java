package com.jk.bean;

import com.google.common.collect.ComparisonChain;

/**
 * @Author: liyang117
 * @Date: 2018/5/6 19:03
 * @Description:
 */
public class VoucherRequest {
    private String uId;
    private String desc;
    private String name;
    private Double amount;
    private Integer type;
    private String typeDesc;
    private Long xiang2Kun;
    private Long dai2Kun;
    private Long kun2Ba;
    private Long ba2Zhang;
    private Long xiang2He;
    private Long he2Mei;

    public VoucherRequest() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Long getXiang2Kun() {
        return xiang2Kun;
    }

    public void setXiang2Kun(Long xiang2Kun) {
        this.xiang2Kun = xiang2Kun;
    }

    public Long getDai2Kun() {
        return dai2Kun;
    }

    public void setDai2Kun(Long dai2Kun) {
        this.dai2Kun = dai2Kun;
    }

    public Long getKun2Ba() {
        return kun2Ba;
    }

    public void setKun2Ba(Long kun2Ba) {
        this.kun2Ba = kun2Ba;
    }

    public Long getBa2Zhang() {
        return ba2Zhang;
    }

    public void setBa2Zhang(Long ba2Zhang) {
        this.ba2Zhang = ba2Zhang;
    }

    public Long getXiang2He() {
        return xiang2He;
    }

    public void setXiang2He(Long xiang2He) {
        this.xiang2He = xiang2He;
    }

    public Long getHe2Mei() {
        return he2Mei;
    }

    public void setHe2Mei(Long he2Mei) {
        this.he2Mei = he2Mei;
    }
}
