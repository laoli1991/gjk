package com.jk.bean;

import java.util.List;

/**
 * @Author: liyang117
 * @Date: 2018/5/13 14:30
 * @Description:
 */
public class BandScreenResponse {
    private Integer code;
    private List<ScreenPo> screenPos;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<ScreenPo> getScreenPos() {
        return screenPos;
    }

    public void setScreenPos(List<ScreenPo> screenPos) {
        this.screenPos = screenPos;
    }
}
