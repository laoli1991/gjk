package com.jk.enums;

/**
 * @Author: liyang117
 * @Date: 2018/6/8 18:48
 * @Description:
 */
public enum OperateResponseEnums {
    RUKU_SUCCESS(1, "入库-成功"),
    CHUKU_FAILED_NOT_FIND_RUKU(2, "出库-失败，找不到入库记录"),
    CHUKU_FAILED_BIGGER_THEN_RUKU(3, "出库-失败，找到入库记录,但是出库比入库多"),
    CHUKU_TRY_SUCCESS(4, "出库-尝试合法"),
    CHUKU_SUCCESS(5, "出库-成功");

    private Integer code;
    private String description;

    OperateResponseEnums(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
