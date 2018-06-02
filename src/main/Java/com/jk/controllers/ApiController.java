package com.jk.controllers;

import com.alibaba.fastjson.JSONObject;
import com.jk.bean.AddScreenReponse;
import com.jk.bean.AddScreenRequest;
import com.jk.bean.BandScreenRequest;
import com.jk.bean.BandScreenResponse;
import com.jk.bean.MsgPo;
import com.jk.bean.ScreenMapperStock;
import com.jk.bean.ScreenPo;
import com.jk.bean.StockPo;
import com.jk.bean.StockRequest;
import com.jk.bean.VoucherPo;
import com.jk.bean.VoucherResponse;
import com.jk.service.AppService;
import com.jk.utils.AppUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: liyang117
 * @Date: 2018/4/15 09:34
 * @Description:
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    protected AppService appService;

    @RequestMapping("/voucher-list")
    @ResponseBody
    public List<VoucherPo> voucherList(HttpServletRequest request, @RequestParam(value = "type", required = false) Integer type) {
        return appService.getVoucherPos(request, type);
    }

    @RequestMapping("/add-voucher")
    @ResponseBody
    public VoucherResponse addVoucher(HttpServletRequest request,
                                      @RequestParam("desc") String dec,
                                      @RequestParam("name") String name,
                                      @RequestParam("amount") Double amount,
                                      @RequestParam("type") Integer type,
                                      @RequestParam("typeDesc") String typeDesc) {
        return appService.addVoucherPo(request, dec, name, amount, type, typeDesc);
    }

    @RequestMapping("/remove-voucher")
    @ResponseBody
    public List<VoucherPo> removeVoucher(HttpServletRequest request, @RequestParam("uId") String uId) {
        return appService.removeVoucherPo(request, uId);
    }

    @RequestMapping("/add-stock")
    @ResponseBody
    public List<StockPo> addStockPo(HttpServletRequest request,
                                    @RequestBody StockRequest stockRequest) {
        StockPo stockPo = new StockPo();
        BeanUtils.copyProperties(stockRequest, stockPo);
        return appService.addStockPo(request, stockPo);
    }

    @RequestMapping("/stock-list")
    @ResponseBody
    public List<StockPo> stockList(HttpServletRequest request) {
        return appService.getStockPos(request);
    }

    @RequestMapping("/remove-stock")
    @ResponseBody
    public List<StockPo> removeStock(HttpServletRequest request, @RequestParam("stockUid") String stockUid) {
        return appService.removeStockPo(request, stockUid);
    }

    @RequestMapping("/add-screen")
    @ResponseBody
    public AddScreenReponse addScreen(HttpServletRequest request, @RequestBody AddScreenRequest addScreenRequest) {
        AddScreenReponse addScreenReponse = new AddScreenReponse();
        try {
            ScreenPo oldScreenPo = appService.getScreenPoByMacAddress(request, addScreenRequest.getMacAddress());
            if (oldScreenPo == null) {
                appService.addScreenPo(request, addScreenRequest.getMacAddress(), addScreenRequest.getPort(), request.getRemoteAddr());
            } else {
                oldScreenPo.setUpdateTime(AppUtils.getNowStr());
                oldScreenPo.setIpAddress(request.getRemoteAddr());
                oldScreenPo.setPort(addScreenRequest.getPort());
                appService.updateScreenPoByMacAddress(request, oldScreenPo);
            }
            addScreenReponse.setCode(1);
            addScreenReponse.setIpAddress(request.getRemoteAddr());
            if (addScreenRequest.getFresh() == 1) {
                List<MsgPo> msgPos = appService.getMsgPos(request, "");
                if (CollectionUtils.isNotEmpty(msgPos)) {
                    for (MsgPo msgPo : msgPos) {
                        if (addScreenRequest.getMacAddress().equals(msgPo.getMacAddress())) {
                            addScreenReponse.setMsgDto(msgPo.getMsgDto());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            addScreenReponse.setCode(0);
        }
        return addScreenReponse;
    }

    @RequestMapping("/screen-list")
    @ResponseBody
    public List<ScreenPo> screenList(HttpServletRequest request) {
        return appService.getScreenPos(request);
    }

    @RequestMapping("/remove-screen")
    @ResponseBody
    public List<ScreenPo> removeScreen(HttpServletRequest request, @RequestParam("macAddress") String macAddress) {
        return appService.removeScreenPo(request, macAddress);
    }

    @RequestMapping("/band-screen")
    @ResponseBody
    public BandScreenResponse bandScreen(HttpServletRequest request, @RequestBody BandScreenRequest bandScreenRequest) {
        BandScreenResponse bandScreenResponse = new BandScreenResponse();
        ScreenPo oldScreenPo = appService.getScreenPoByMacAddress(request, bandScreenRequest.getMacAddress());
        if (oldScreenPo == null) {
            bandScreenResponse.setCode(0);
            return bandScreenResponse;
        } else {
            oldScreenPo.setStockUid(AppUtils.getStockUid(bandScreenRequest.getType(), bandScreenRequest.getVoucherUid()));
            oldScreenPo.setBandStockInfo(bandScreenRequest.getBandStockInfo());
            oldScreenPo.setVoucherAmount(bandScreenRequest.getVoucherAmount());
            oldScreenPo.setVoucherType(bandScreenRequest.getType());
            bandScreenResponse.setCode(1);
            bandScreenResponse.setScreenPos(appService.updateScreenPoByMacAddress(request, oldScreenPo));
            appService.sendMsg(request, "");
            return bandScreenResponse;
        }
    }

    @RequestMapping("/screen-stock-list")
    @ResponseBody
    public List<ScreenMapperStock> getScreenMapperStocks(HttpServletRequest request) {
        return appService.getScreenMapperStocks(request);
    }

    @RequestMapping("/msgpo-list")
    @ResponseBody
    public List<MsgPo> getMsgPos(HttpServletRequest request, @RequestParam(value = "commonInfo", required = false) String commonInfo) {
        return appService.getMsgPos(request, commonInfo);
    }

    @RequestMapping("/send-msg")
    @ResponseBody
    public Boolean sendMsg(HttpServletRequest request, @RequestParam(value = "commonInfo", required = false) String commonInfo) {
        appService.sendMsg(request, commonInfo);
        return Boolean.TRUE;
    }


}
