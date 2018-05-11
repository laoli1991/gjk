package com.jk.utils;

import com.jk.bans.StockPo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;

/**
 * @Author: liyang117
 * @Date: 2018/5/7 15:23
 * @Description:
 */
public class AppUtils {

    public static String createStockTableIfNotExist(HttpServletRequest request) {
        String dir = request.getSession().getServletContext().getRealPath("/") + "datas" ;
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "datas" + File.separator + "stock.txt";
        return path;
    }

    public static String createVoucherTableIfNotExist(HttpServletRequest request) {
        String dir = request.getSession().getServletContext().getRealPath("/") + "datas" ;
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "datas" + File.separator + "voucher.txt";
        return path;
    }

    public static String createScreenTableIfNotExist(HttpServletRequest request) {
        String dir = request.getSession().getServletContext().getRealPath("/") + "datas" ;
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = request.getSession().getServletContext().getRealPath("/") + "datas" + File.separator + "screen.txt";
        return path;
    }

    public static String generateUId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getStockUid(StockPo stockPo) {
        return String.format("%d%d%s", stockPo.getCategory(), stockPo.getType(), stockPo.getVoucherUid());
    }

    public static String getStockUid(Integer category, Integer type, String voucherUid) {
        return String.format("%d%d%s", category, type, voucherUid);
    }

    public static String getMac() {
        String mac = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] macs = ni.getHardwareAddress();
            Formatter formatter = new Formatter();
            for (int i = 0; i < macs.length; i++) {
                mac = formatter.format(Locale.getDefault(), "%02X%s", macs[i], i < macs.length - 1 ? "-" : "").toString();
            }
        } catch (Exception e) {
        }
        return mac;
    }

//    public static String getIpAdrress(HttpServletRequest request) {
//        String ip = request.getHeader("X-Real-IP");
//        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//            return ip;
//        }
//        ip = request.getHeader("X-Forwarded-For");
//        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//            // 多次反向代理后会有多个IP值，第一个为真实IP。
//            int index = ip.indexOf(',');
//            if (index != -1) {
//                return ip.substring(0, index);
//            } else {
//                return ip;
//            }
//        } else {
//            return request.getRemoteAddr();
//        }
//
//    }

    public static InetAddress getLocalHostLANAddress() throws Exception {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            InetAddress a = getLocalHostLANAddress();
            System.out.println(a.getHostAddress());
        } catch (Exception e) {
        }
    }

}
