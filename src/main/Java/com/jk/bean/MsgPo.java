package com.jk.bean;

/**
 * @Author: liyang117
 * @Date: 2018/5/13 16:18
 * @Description:
 */
public class MsgPo {
    private String ipAddress;
    private String macAddress;
    private Integer port;
    private MsgDto msgDto;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public MsgDto getMsgDto() {
        return msgDto;
    }

    public void setMsgDto(MsgDto msgDto) {
        this.msgDto = msgDto;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
