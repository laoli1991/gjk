package com.jk.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jk.bans.VoucherResponse;
import com.jk.dao.ScreenDao;
import com.jk.dao.StockDao;
import com.jk.dao.VoucherDao;
import com.jk.bans.ScreenPo;
import com.jk.bans.StockPo;
import com.jk.bans.VoucherPo;
import com.jk.utils.AppUtils;
import com.jk.bans.ScreenVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 * @Author: liyang117
 * @Date: 2018/5/10 20:55
 * @Description:
 */
@Service
public class AppService {
    private static final Logger ERROR = Logger.getLogger(AppService.class);
    @Autowired
    private ScreenDao screenDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private VoucherDao voucherDao;


    public List<VoucherPo> getVoucherPos(HttpServletRequest request, Integer type) {
        if (type == null) {
            return voucherDao.getVoucherList(request);
        }
        return voucherDao.getVoucherPos(request, type);
    }

    public VoucherResponse addVoucherPo(HttpServletRequest request, String dec, String name, Double amount, Integer type) {
        VoucherResponse voucherResponse = new VoucherResponse();
        VoucherPo voucherPo = new VoucherPo(AppUtils.generateUId(), dec, name, amount, type);
        if (voucherDao.findVoucherPo(request, voucherPo)) {
            voucherResponse.setCode(1);
            voucherResponse.setVoucherPos(voucherDao.getVoucherList(request));
        } else {
            voucherResponse.setCode(0);
            voucherResponse.setVoucherPos(voucherDao.addVoucherPo(request, voucherPo));
        }
        return voucherResponse;
    }

    public List<VoucherPo> removeVoucherPo(HttpServletRequest request, String uId) {
        return voucherDao.removeVoucherPo(request, uId);
    }

    public List<ScreenPo> getScreenPos(HttpServletRequest request) {
        return screenDao.getScreenPos(request);
    }

    public List<ScreenPo> addScreenPo(HttpServletRequest request, String macAddress, String ipAddress, String stockUid) {
        ScreenPo screenPo = new ScreenPo(macAddress, ipAddress, stockUid);
        return screenDao.addScreenPo(request, screenPo);
    }

    public List<StockPo> getStockPos(HttpServletRequest request) {
        return stockDao.getStockPos(request);
    }

    public List<StockPo> addStockPo(HttpServletRequest request, StockPo stockPo) {
        stockPo.setStockUid(AppUtils.getStockUid(stockPo));
        return stockDao.addStockPo(request, stockPo);
    }

    public List<StockPo> removeStockPo(HttpServletRequest request, StockPo stockPo) {
        return stockDao.removeStockPo(request, stockPo);
    }

    public List<ScreenVo> getScreenVos(HttpServletRequest request) {
        List<ScreenVo> screenVos = Lists.newArrayList();
        Map<String, ScreenPo> stringScreenPoMap = screenDao.getStockUid2ScreenPo(request);
        Map<String, StockPo> stringStockPoMap = stockDao.getStockUid2StockPo(request);
        for (Map.Entry<String, ScreenPo> entry : stringScreenPoMap.entrySet()) {
            String macAddress = entry.getKey();
            ScreenPo screenPo = entry.getValue();
            ScreenVo screenVo = new ScreenVo();
            BeanUtils.copyProperties(screenPo, screenVo);
            StockPo stockPo = stringStockPoMap.get(screenPo.getStockUid());
            if (stockPo != null) {
                BeanUtils.copyProperties(stockPo, screenVo);
            }
            screenVos.add(screenVo);
        }
        return screenVos;
    }

    public void sendAll(HttpServletRequest request) {
        List<ScreenVo> screenVos = getScreenVos(request);
        if (CollectionUtils.isNotEmpty(screenVos)) {
            for (ScreenVo screenVo : screenVos) {
                String ipAddress = screenVo.getIpAddress();
                send(ipAddress, JSONObject.toJSONString(screenVo));
            }
        }
    }


    private synchronized void send(String ipAddress, String text) {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = text.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ipAddress), 9002);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            ERROR.error(e.getMessage());
        }
    }

}