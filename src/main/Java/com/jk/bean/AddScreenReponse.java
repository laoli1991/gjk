package com.jk.bean;

/**
 * @Author: liyang117
 * @Date: 2018/5/15 19:59
 * @Description:
 */
public class AddScreenReponse {
    private Integer code;
    private MsgDto msgDto;
    private String ipAddress;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public MsgDto getMsgDto() {
        return msgDto;
    }

    public void setMsgDto(MsgDto msgDto) {
        this.msgDto = msgDto;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
