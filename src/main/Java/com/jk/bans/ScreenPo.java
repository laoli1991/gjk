package com.jk.bans;

import java.util.Date;

/**
 * @Author: liyang117
 * @Date: 2018/5/10 20:12
 * @Description:
 */
public class ScreenPo {
    private String macAddress;
    private String ipAddress;
    private String stockUid;
    private Date updateTime;

    public ScreenPo(String macAddress, String ipAddress, String stockUid) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.stockUid = stockUid;
        this.updateTime = new Date();
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
}
