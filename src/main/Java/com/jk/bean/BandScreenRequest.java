package com.jk.bean;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: liyang117
 * @Date: 2018/5/13 14:42
 * @Description:
 */
public class BandScreenRequest {
    private String macAddress;
    private String voucherUid;
    private Integer type;
    private String bandStockInfo;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getVoucherUid() {
        return voucherUid;
    }

    public void setVoucherUid(String voucherUid) {
        this.voucherUid = voucherUid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBandStockInfo() {
        return bandStockInfo;
    }

    public void setBandStockInfo(String bandStockInfo) {
        this.bandStockInfo = bandStockInfo;
    }
}
