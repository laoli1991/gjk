package com.jk.bean;

/**
 * @Author: liyang117
 * @Date: 2018/5/10 20:12
 * @Description:
 */
public class ScreenRequest {
    private String macAddress;
    private String ipAddress;

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
}
