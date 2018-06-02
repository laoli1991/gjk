package com.jk.bean;

import java.util.List;

/**
 * @Author: liyang117
 * @Date: 2018/6/2 13:50
 * @Description:
 */
public class StockResponse {
    private List<StockPo> stockPos;
    private Integer code;
    private StockPo curStockPo;

    public List<StockPo> getStockPos() {
        return stockPos;
    }

    public void setStockPos(List<StockPo> stockPos) {
        this.stockPos = stockPos;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public StockPo getCurStockPo() {
        return curStockPo;
    }

    public void setCurStockPo(StockPo curStockPo) {
        this.curStockPo = curStockPo;
    }
}
