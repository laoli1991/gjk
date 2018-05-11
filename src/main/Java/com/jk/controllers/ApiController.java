package com.jk.controllers;

import com.jk.bans.VoucherPo;
import com.jk.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public List<VoucherPo> voucherList(HttpServletRequest request, @RequestParam("type") Integer type) {
        return appService.getVoucherPos(request, type);
    }

    @RequestMapping("/add-voucher")
    @ResponseBody
    public List<VoucherPo> addVoucher(HttpServletRequest request,
                                      @RequestParam("voucher") String voucher,
                                      @RequestParam("amount") Double amount,
                                      @RequestParam("type") Integer type) {
        return appService.addVoucherPo(request, voucher, amount, type);
    }

    @RequestMapping("/remove-voucher")
    @ResponseBody
    public List<VoucherPo> removeVoucher(HttpServletRequest request,
                                         @RequestParam("uId") String uId) {
        return appService.removeVoucherPo(request, uId);
    }


}
