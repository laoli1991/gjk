package com.jk.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jk.bean.MsgDto;
import com.jk.bean.MsgPo;
import com.jk.bean.ScreenMapperStock;
import com.jk.bean.ScreenPo;
import com.jk.bean.ScreenVo;
import com.jk.bean.StockPo;
import com.jk.bean.VoucherPo;
import com.jk.dao.ScreenDao;
import com.jk.dao.StockDao;
import com.jk.dao.VoucherDao;
import com.jk.utils.AppUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    public Integer addVoucherPo(HttpServletRequest request, VoucherPo voucherPo) {
        if (voucherDao.findVoucherPo(request, voucherPo)) {
            return 0;
        } else {
            voucherDao.addVoucherPo(request, voucherPo);
            return 1;
        }
    }

    public List<VoucherPo> removeVoucherPo(HttpServletRequest request, String uId) {
        return voucherDao.removeVoucherPo(request, uId);
    }

    public List<ScreenPo> getScreenPos(HttpServletRequest request) {
        return screenDao.getScreenPos(request);
    }

    public List<ScreenPo> removeScreenPo(HttpServletRequest request, String macAddress) {
        return screenDao.removeScreenPo(request, macAddress);
    }

    public ScreenPo getScreenPoByMacAddress(HttpServletRequest request, String macAddress) {
        return screenDao.getScreenPoByMacAddress(request, macAddress);
    }

    public List<ScreenPo> updateScreenPoByMacAddress(HttpServletRequest request, ScreenPo screenPo) {
        return screenDao.updateScreenPoByMacAddress(request, screenPo);
    }

    public List<ScreenPo> addScreenPo(HttpServletRequest request, String macAddress, Integer port, String ipAddress) {
        ScreenPo screenPo = new ScreenPo(macAddress, ipAddress);
        screenPo.setUpdateTime(AppUtils.getNowStr());
        screenPo.setPort(port);
        return screenDao.addScreenPo(request, screenPo);
    }

    public List<StockPo> getStockPos(HttpServletRequest request) {
        return stockDao.getStockPos(request);
    }

    public StockPo getStockPoByStock(HttpServletRequest request, StockPo stockPo) {
        return stockDao.getStockPoByStockUid(request, AppUtils.getStockUid(stockPo));
    }

    public List<StockPo> addStockPo(HttpServletRequest request, StockPo stockPo) {
        stockPo.setStockUid(AppUtils.getStockUid(stockPo));
        return stockDao.addStockPo(request, stockPo);
    }

    public List<StockPo> removeStockPo(HttpServletRequest request, String stockUid) {
        return stockDao.removeStockPoBystockUid(request, stockUid);
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

    public List<ScreenMapperStock> getScreenMapperStocks(HttpServletRequest request) {
        List<ScreenMapperStock> screenMapperStocks = Lists.newArrayList();
        List<ScreenPo> screenPos = screenDao.getScreenPos(request);
        Map<String, StockPo> stringStockPoMap = stockDao.getStockUid2StockPo(request);
        if (CollectionUtils.isNotEmpty(screenPos)) {
            for (ScreenPo screenPo : screenPos) {
                if (StringUtils.isNotBlank(screenPo.getStockUid())) {
                    StockPo stockPo = stringStockPoMap.get(screenPo.getStockUid());
                    if (stockPo != null) {
                        ScreenMapperStock msgDto = new ScreenMapperStock();
                        msgDto.setScreenPo(screenPo);
                        msgDto.setStockPo(stockPo);
                        screenMapperStocks.add(msgDto);
                    }
                }
            }
        }
        return screenMapperStocks;
    }

    public List<MsgPo> getMsgPos(HttpServletRequest request, String commonInfo) {
        List<MsgPo> msgPos = Lists.newArrayList();
        List<ScreenMapperStock> screenMapperStocks = getScreenMapperStocks(request);
        if (CollectionUtils.isNotEmpty(screenMapperStocks)) {
            for (ScreenMapperStock screenMapperStock : screenMapperStocks) {
                StockPo stockPo = screenMapperStock.getStockPo();
                ScreenPo screenPo = screenMapperStock.getScreenPo();
                MsgDto msgDto = new MsgDto();
                msgDto.setNowTime(AppUtils.getNowStr());
                if (StringUtils.isNotBlank(commonInfo)) {
                    msgDto.setCommonInfo(commonInfo);
                }
                msgDto.setVoucherName(stockPo.getVoucherName());
                msgDto.setTypeDesc(stockPo.getTypeDesc());
                String amount = stockPo.getAmount();
                if (StringUtils.isNotBlank(amount)) {
                    BigDecimal num = new BigDecimal(amount);
                    num = num.divide(new BigDecimal(10000), 3, BigDecimal.ROUND_HALF_UP);
                    msgDto.setAmount("合计： " + num.setScale(3, BigDecimal.ROUND_HALF_UP).toString() + "万元");
                }
                if (StringUtils.isNotBlank(stockPo.getXiangCount())) {
                    msgDto.setKey1(stockPo.getXiangCount());
                    msgDto.setValue1("箱");
                }
                if (StringUtils.isNotBlank(stockPo.getDaiCount())) {
                    msgDto.setKey1(stockPo.getDaiCount());
                    msgDto.setValue1("袋");
                }
                if (stockPo.getVoucherType() == 1) {//纸币
                    BigDecimal num = BigDecimal.ZERO;
                    boolean find = false;
                    if (StringUtils.isNotBlank(stockPo.getKunCount())) {
                        num = new BigDecimal(stockPo.getKunCount());
                        find = true;
                    }
//                    if (StringUtils.isNotBlank(stockPo.getBaCount())) {
//                        num = num.add(new BigDecimal(stockPo.getBaCount()).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP));
//                        find = true;
//                    }
                    if (find) {
                        msgDto.setKey2(num.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                    } else {
                        msgDto.setKey2("0");
                    }
                    msgDto.setValue2("捆");
                } else {//银币
                    if (StringUtils.isBlank(stockPo.getHeCount())) {
                        msgDto.setKey2("0");
                    } else {
                        msgDto.setKey2(stockPo.getHeCount());
                    }
                    msgDto.setValue2("盒");
                }
                MsgPo msgPo = new MsgPo();
                msgPo.setIpAddress(screenPo.getIpAddress());
                msgPo.setMacAddress(screenPo.getMacAddress());
                msgPo.setPort(screenPo.getPort());
                msgPo.setMsgDto(msgDto);
                msgPos.add(msgPo);
            }
        }
        return msgPos;
    }

    public synchronized void sendMsg(HttpServletRequest request, String commonInfo) {
        List<MsgPo> msgPos = getMsgPos(request, commonInfo);
        if (CollectionUtils.isNotEmpty(msgPos)) {
            for (MsgPo msgPo : msgPos) {
                try {
                    DatagramSocket socket = new DatagramSocket();
                    byte[] buf = JSONObject.toJSONString(msgPo.getMsgDto()).getBytes("UTF-8");
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(msgPo.getIpAddress()), msgPo.getPort());
                    socket.send(packet);
                    socket.close();
                } catch (Exception e) {
                    ERROR.error(e.getMessage());
                }
            }
        }
    }

}