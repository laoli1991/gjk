package com.jk.bans;

/**
 * @Author: liyang117
 * @Date: 2018/5/6 19:03
 * @Description:
 */
public class VoucherPo {
    private String uId;
    private String desc;
    private String name;
    private Double amount;
    private Integer type;

    public VoucherPo(String uId, String desc, String name, Double amount, Integer type) {
        this.uId = uId;
        this.desc = desc;
        this.name = name;
        this.amount = amount;
        this.type = type;
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
}
