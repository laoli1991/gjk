package com.jk.utils;

import com.google.common.primitives.Doubles;
import com.jk.bean.StockPo;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @Author: liyang117
 * @Date: 2018/5/7 15:23
 * @Description:
 */
public class AppUtils {

    private static final Logger ERROR = Logger.getLogger(AppUtils.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String createStockTableIfNotExist(HttpServletRequest request) {
        String dir = request.getSession().getServletContext().getRealPath("/") + "datas";
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "datas" + File.separator + "stock.txt";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                ERROR.error(e.getMessage());
            }
        }
        return path;
    }

    public static String createVoucherTableIfNotExist(HttpServletRequest request) {
        String dir = request.getSession().getServletContext().getRealPath("/") + "datas";
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "datas" + File.separator + "voucher.txt";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                ERROR.error(e.getMessage());
            }
        }
        return path;
    }

    public static String createScreenTableIfNotExist(HttpServletRequest request) {
        String dir = request.getSession().getServletContext().getRealPath("/") + "datas";
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "datas" + File.separator + "screen.txt";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                ERROR.error(e.getMessage());
            }
        }
        return path;
    }

    public static String generateUId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getStockUid(StockPo stockPo) {
        return String.format("%d-%s", stockPo.getType(), stockPo.getVoucherUid());
    }

    public static String getStockUid(Integer type, String voucherUid) {
        return String.format("%d-%s", type, voucherUid);
    }

    public static String getNowStr() {
        Date date = new Date();
        return sdf.format(date);
    }

    public static StockPo subStockPo(StockPo old, StockPo out) {
        BigInteger nowAllCount = new BigInteger(old.getAllCount()).subtract(new BigInteger(out.getAllCount()));
        if (nowAllCount.compareTo(BigInteger.ZERO) < 0) {
            return null;
        }
        old.setAllCount(nowAllCount.toString());
        old.setAmount(BigDecimal.valueOf(old.getVoucherAmount()).multiply(new BigDecimal(nowAllCount)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        if (old.getVoucherType() == 1) {//纸币
            BigInteger xiangOrDaiCount = BigInteger.ZERO;
            BigInteger kunCount = BigInteger.ZERO;
            BigInteger baCount = BigInteger.ZERO;
            //      5角/1角	    1箱/袋 = 25捆	1捆 = 10把	1把 = 100张
            //      其他	        1箱/袋 = 20捆	1捆 = 10把	1把 = 100张
            if (Doubles.compare(old.getVoucherAmount(), 0.5) == 0 || Doubles.compare(old.getVoucherAmount(), 0.1) == 0) {
                xiangOrDaiCount = nowAllCount.divide(BigInteger.valueOf(25 * 10 * 100));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(25 * 10 * 100));
                kunCount = nowAllCount.divide(BigInteger.valueOf(10 * 100));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(10 * 100));
                baCount = nowAllCount.divide(BigInteger.valueOf(100));
            } else {
                xiangOrDaiCount = nowAllCount.divide(BigInteger.valueOf(20 * 10 * 100));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(20 * 10 * 100));
                kunCount = nowAllCount.divide(BigInteger.valueOf(10 * 100));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(10 * 100));
                baCount = nowAllCount.divide(BigInteger.valueOf(100));
            }

            if (old.getType() <= 3) {//完整卷
                old.setXiangCount(xiangOrDaiCount.toString());
            } else {//残损卷
                old.setDaiCount(xiangOrDaiCount.toString());
            }
            old.setKunCount(kunCount.toString());
            old.setBaCount(baCount.toString());
        } else {//硬币
            //	    1元	1箱 = 10盒	1盒 = 400枚
            //      5角	1箱 = 10盒	1盒 = 400枚
            //      1角	1箱 = 10盒	1盒 = 500枚
            BigInteger xiangCount = BigInteger.ZERO;
            BigInteger heCount = BigInteger.ZERO;
            if (Doubles.compare(old.getVoucherAmount(), 0.1) == 0) {
                xiangCount = nowAllCount.divide(BigInteger.valueOf(10 * 500));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(10 * 500));
                heCount = nowAllCount.divide(BigInteger.valueOf(500));
            } else {
                xiangCount = nowAllCount.divide(BigInteger.valueOf(10 * 400));
                nowAllCount = nowAllCount.mod(BigInteger.valueOf(10 * 400));
                heCount = nowAllCount.divide(BigInteger.valueOf(400));
            }
            old.setXiangCount(xiangCount.toString());
            old.setHeCount(heCount.toString());
        }
        return old;
    }

}
