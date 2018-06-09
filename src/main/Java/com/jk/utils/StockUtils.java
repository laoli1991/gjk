package com.jk.utils;

import com.google.common.primitives.Doubles;
import com.jk.bean.StockPo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Author: liyang117
 * @Date: 2018/6/8 17:24
 * @Description:
 */
public class StockUtils {

    public static StockPo addStockPo(StockPo old, StockPo other) {
        return calcStockPo(old, other , 1);
    }

    public static StockPo subStockPo(StockPo old, StockPo other) {
        return calcStockPo(old, other , -1);
    }

    private static StockPo calcStockPo(StockPo old, StockPo otherPo, Integer type) {
        BigInteger nowAllCount = null;
        if (type == -1) {
            nowAllCount = new BigInteger(old.getAllCount()).subtract(new BigInteger(otherPo.getAllCount()));
        }
        else if (type == 1){
            nowAllCount = new BigInteger(old.getAllCount()).add(new BigInteger(otherPo.getAllCount()));
        }
        if (nowAllCount == null || nowAllCount.compareTo(BigInteger.ZERO) < 0) {
            return null;
        }
        old.setAllCount(nowAllCount.toString());
        old.setAmount(BigDecimal.valueOf(old.getVoucherAmount()).multiply(new BigDecimal(nowAllCount)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        if (old.getVoucherType() == 1) {//纸币
            BigInteger xiangCount = BigInteger.ZERO;
            BigInteger daiCount = BigInteger.ZERO;
            BigInteger kunCount = BigInteger.ZERO;
            BigInteger baCount = BigInteger.ZERO;
            if (old.getType() <= 1) {//完整券 原封券
                old.setXiangCount(xiangCount.toString());
                xiangCount = nowAllCount.divide(BigInteger.valueOf(old.getVoucherXiang2Kun() * old.getVoucherKun2Ba() * old.getVoucherBa2Zhang()));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(old.getVoucherXiang2Kun() * old.getVoucherKun2Ba() * old.getVoucherBa2Zhang()));
                old.setXiangCount(xiangCount.toString());
            } else {
                old.setDaiCount(daiCount.toString());
                daiCount = nowAllCount.divide(BigInteger.valueOf(old.getVoucherDai2Kun() * old.getVoucherKun2Ba() * old.getVoucherBa2Zhang()));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(old.getVoucherDai2Kun() * old.getVoucherKun2Ba() * old.getVoucherBa2Zhang()));
                old.setDaiCount(daiCount.toString());
            }
            kunCount = nowAllCount.divide(BigInteger.valueOf(old.getVoucherKun2Ba() * old.getVoucherBa2Zhang()));
            nowAllCount = nowAllCount.mod(BigInteger.valueOf(old.getVoucherKun2Ba() * old.getVoucherBa2Zhang()));
            baCount = nowAllCount.divide(BigInteger.valueOf(old.getVoucherBa2Zhang()));
            old.setKunCount(kunCount.toString());
            old.setBaCount(baCount.toString());
        } else {//硬币
            BigInteger xiangCount = BigInteger.ZERO;
            BigInteger heCount = BigInteger.ZERO;
            xiangCount = nowAllCount.divide(BigInteger.valueOf(old.getVoucherXiang2He() * old.getVoucherHe2Mei()));
            nowAllCount = nowAllCount.mod(BigInteger.valueOf(old.getVoucherXiang2He() * old.getVoucherHe2Mei()));
            heCount = nowAllCount.divide(BigInteger.valueOf(old.getVoucherHe2Mei()));
            old.setXiangCount(xiangCount.toString());
            old.setHeCount(heCount.toString());
        }
        return old;
    }
}
