package com.jk.enums;

/**
 * @Author: liyang117
 * @Date: 2018/6/8 18:48
 * @Description:
 */
public enum OperateRequestEnums {
    RUKU_ADD(0, "入库-新增"),
    RUKU_COVER(1, "入库-覆盖"),
    CHUKU_TRY(2, "出库-尝试"),
    CHUKU_CONFIRM(3, "出库-确认");

    private Integer operate;
    private String description;

    OperateRequestEnums(Integer operate, String description) {
        this.operate = operate;
        this.description = description;
    }

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
