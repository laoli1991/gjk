package com.jk.controllers;

import com.jk.bans.StockPo;
import com.jk.bans.StockRequest;
import com.jk.bans.VoucherPo;
import com.jk.bans.VoucherResponse;
import com.jk.service.AppService;
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
                                      @RequestParam("type") Integer type) {
        return appService.addVoucherPo(request, dec, name, amount, type);
    }

    @RequestMapping("/remove-voucher")
    @ResponseBody
    public List<VoucherPo> removeVoucher(HttpServletRequest request,
                                         @RequestParam("uId") String uId) {
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

}
