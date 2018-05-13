package com.jk.controllers;

import com.google.common.collect.Maps;
import com.jk.bean.BandScreenRequest;
import com.jk.bean.BandScreenResponse;
import com.jk.bean.ScreenPo;
import com.jk.bean.ScreenRequest;
import com.jk.bean.StockPo;
import com.jk.bean.StockRequest;
import com.jk.bean.VoucherPo;
import com.jk.bean.VoucherResponse;
import com.jk.service.AppService;
import com.jk.utils.AppUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
                                      @RequestParam("type") Integer type) {
        return appService.addVoucherPo(request, dec, name, amount, type);
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
    public Map<String, String> addScreen(HttpServletRequest request, @RequestBody ScreenRequest screenRequest) {
        ScreenPo oldScreenPo = appService.getScreenPoByMacAddress(request, screenRequest.getMacAddress());
        if (oldScreenPo == null) {
            appService.addScreenPo(request, screenRequest.getMacAddress(), request.getRemoteAddr(), null);
        } else {
            oldScreenPo.setUpdateTime(AppUtils.getNowStr());
            appService.updateScreenPoByMacAddress(request, oldScreenPo);
        }
        Map<String, String> response = Maps.newHashMap();
        response.put("code", "1");
        response.put("ipAddress", request.getRemoteAddr());
        return response;
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
            bandScreenResponse.setCode(1);
            bandScreenResponse.setScreenPos(appService.updateScreenPoByMacAddress(request, oldScreenPo));
            return bandScreenResponse;
        }
    }

}
