package com.jk.bean;

import com.google.common.collect.ComparisonChain;

/**
 * @Author: liyang117
 * @Date: 2018/5/10 20:12
 * @Description:
 */
public class ScreenPo implements Comparable<ScreenPo> {
    private String macAddress;
    private Integer port;
    private String ipAddress;
    private String stockUid;
    private String updateTime;
    private String bandStockInfo;
    private Double voucherAmount;
    private Integer voucherType;

    public ScreenPo() {
    }

    public ScreenPo(String macAddress, String ipAddress) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
    }

    public ScreenPo(String macAddress, String ipAddress, String stockUid) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.stockUid = stockUid;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStockUid() {
        return stockUid;
    }

    public void setStockUid(String stockUid) {
        this.stockUid = stockUid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBandStockInfo() {
        return bandStockInfo;
    }

    public void setBandStockInfo(String bandStockInfo) {
        this.bandStockInfo = bandStockInfo;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Double getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(Double voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public Integer getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(Integer voucherType) {
        this.voucherType = voucherType;
    }

    public int compareTo(ScreenPo other) {
        return  ComparisonChain.start()
                .compare(this.macAddress, other.macAddress)
                .result();
    }


}
