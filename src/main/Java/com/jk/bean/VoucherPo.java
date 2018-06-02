package com.jk.bean;

import com.google.common.collect.ComparisonChain;

/**
 * @Author: liyang117
 * @Date: 2018/5/6 19:03
 * @Description:
 */
public class VoucherPo implements Comparable<VoucherPo> {
    private String uId;
    private String desc;
    private String name;
    private Double amount;
    private Integer type;
    private String typeDesc;

    public VoucherPo(String uId, String desc, String name, Double amount, Integer type, String typeDesc) {
        this.uId = uId;
        this.desc = desc;
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.typeDesc = typeDesc;
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

    @Override
    public int compareTo(VoucherPo other) {
        return ComparisonChain.start()
                .compare(this.type, other.type)
                .compare(other.amount, this.amount)
                .result();
    }
}
