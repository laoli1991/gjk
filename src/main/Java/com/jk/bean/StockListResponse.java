package com.jk.bean;

import java.util.List;

/**
 * @Author: liyang117
 * @Date: 2018/6/22 21:19
 * @Description:
 */
public class StockListResponse {
    private List<StockPo> stockPos;
    private String allSum;
    private String wzSum;
    private String cqSum;

    public List<StockPo> getStockPos() {
        return stockPos;
    }

    public void setStockPos(List<StockPo> stockPos) {
        this.stockPos = stockPos;
    }

    public String getAllSum() {
        return allSum;
    }

    public void setAllSum(String allSum) {
        this.allSum = allSum;
    }

    public String getWzSum() {
        return wzSum;
    }

    public void setWzSum(String wzSum) {
        this.wzSum = wzSum;
    }

    public String getCqSum() {
        return cqSum;
    }

    public void setCqSum(String cqSum) {
        this.cqSum = cqSum;
    }
}
