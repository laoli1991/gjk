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
    private static final String dataDir = PropertiesUtils.getProperty("data.dir");

    public static String createStockTableIfNotExist(HttpServletRequest request) {
        File folder = new File(dataDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = String.format("%s%s%s", dataDir, File.separator, "stock.txt");
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
        File folder = new File(dataDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = String.format("%s%s%s", dataDir, File.separator, "voucher.txt");
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
        File folder = new File(dataDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = String.format("%s%s%s", dataDir, File.separator, "screen.txt");
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

}
