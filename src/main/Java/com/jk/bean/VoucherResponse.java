package com.jk.bean;

import java.util.List;

/**
 * @Author: liyang117
 * @Date: 2018/5/6 19:03
 * @Description:
 */
public class VoucherResponse {
    private Integer code;
    private List<VoucherPo> voucherPos;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<VoucherPo> getVoucherPos() {
        return voucherPos;
    }

    public void setVoucherPos(List<VoucherPo> voucherPos) {
        this.voucherPos = voucherPos;
    }
}
