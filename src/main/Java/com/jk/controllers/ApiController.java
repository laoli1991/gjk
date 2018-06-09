package com.jk.controllers;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.jk.bean.AddScreenReponse;
import com.jk.bean.AddScreenRequest;
import com.jk.bean.BandScreenRequest;
import com.jk.bean.BandScreenResponse;
import com.jk.bean.MsgPo;
import com.jk.bean.ScreenMapperStock;
import com.jk.bean.ScreenPo;
import com.jk.bean.StockPo;
import com.jk.bean.StockRequest;
import com.jk.bean.StockResponse;
import com.jk.bean.VoucherPo;
import com.jk.bean.VoucherRequest;
import com.jk.bean.VoucherVo;
import com.jk.enums.OperateRequestEnums;
import com.jk.enums.OperateResponseEnums;
import com.jk.service.AppService;
import com.jk.utils.AppUtils;
import com.jk.utils.StockUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
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
    public List<VoucherVo> voucherList(HttpServletRequest request, @RequestParam(value = "type", required = false) Integer type) {
        List<VoucherPo> voucherPoList = appService.getVoucherPos(request, type);
        List<VoucherVo> voucherVoList = Lists.newArrayList();
        for (VoucherPo po : voucherPoList) {
            VoucherVo vo = new VoucherVo();
            BeanUtils.copyProperties(po, vo);
            String unitInfo = "";
            if (po.getType() == 1) {
                unitInfo = "1箱 = " + po.getXiang2Kun() + "捆";
                unitInfo += "</br>1袋 = " + po.getDai2Kun() + "捆";
                unitInfo += "</br>1捆 = " + po.getKun2Ba() + "把";
                unitInfo += "</br>1把 = " + po.getBa2Zhang() + "张";
            } else {
                unitInfo += "1箱 = " + po.getXiang2He() + "盒";
                unitInfo += "</br>1盒 = " + po.getHe2Mei() + "枚";
            }
            vo.setUnitInfo(unitInfo);
            voucherVoList.add(vo);
        }
        return voucherVoList;
    }

    @RequestMapping("/add-voucher")
    @ResponseBody
    public Integer addVoucher(HttpServletRequest request,
                              @RequestBody VoucherRequest voucherRequest) {
        VoucherPo voucherPo = new VoucherPo();
        BeanUtils.copyProperties(voucherRequest, voucherPo);
        voucherPo.setuId(AppUtils.generateUId());
        return appService.addVoucherPo(request, voucherPo);
    }

    @RequestMapping("/remove-voucher")
    @ResponseBody
    public List<VoucherVo> removeVoucher(HttpServletRequest request, @RequestParam("uId") String uId) {
        appService.removeVoucherPo(request, uId);
        List<VoucherPo> voucherPoList = appService.getVoucherPos(request, null);
        List<VoucherVo> voucherVoList = Lists.newArrayList();
        for (VoucherPo po : voucherPoList) {
            VoucherVo vo = new VoucherVo();
            BeanUtils.copyProperties(po, vo);
            String unitInfo = "";
            if (po.getType() == 1) {
                unitInfo = "1箱 = " + po.getXiang2Kun() + "捆";
                unitInfo += "</br>1袋 = " + po.getDai2Kun() + "捆";
                unitInfo += "</br>1捆 = " + po.getKun2Ba() + "把";
                unitInfo += "</br>1把 = " + po.getBa2Zhang() + "张";
            } else {
                unitInfo += "1箱 = " + po.getXiang2He() + "盒";
                unitInfo += "</br>1盒 = " + po.getHe2Mei() + "枚";
            }
            vo.setUnitInfo(unitInfo);
            voucherVoList.add(vo);
        }
        return voucherVoList;
    }

    @RequestMapping("/add-stock")
    @ResponseBody
    public StockResponse addStockPo(HttpServletRequest request,
                                    @RequestBody StockRequest stockRequest) {
        StockPo stockPo = new StockPo();
        BeanUtils.copyProperties(stockRequest, stockPo);
        if (Ints.compare(OperateRequestEnums.RUKU_ADD.getOperate(), stockRequest.getOperation()) == 0) {//入库 新增
            StockResponse stockResponse = new StockResponse();
            stockResponse.setCode(OperateResponseEnums.RUKU_SUCCESS.getCode()); //入库成功
            StockPo oldStockPo = appService.getStockPoByStock(request, stockPo);
            if (oldStockPo == null) {
                stockResponse.setStockPos(appService.addStockPo(request, stockPo));
            } else {
                StockPo curStockPo = StockUtils.addStockPo(oldStockPo, stockPo);
                stockResponse.setStockPos(appService.addStockPo(request, curStockPo));
            }
            appService.sendMsg(request, "");
            return stockResponse;
        }
        if (Ints.compare(OperateRequestEnums.RUKU_COVER.getOperate(), stockRequest.getOperation()) == 0) {//入库 覆盖
            StockResponse stockResponse = new StockResponse();
            stockResponse.setCode(OperateResponseEnums.RUKU_SUCCESS.getCode()); //入库成功
            stockResponse.setStockPos(appService.addStockPo(request, stockPo));
            appService.sendMsg(request, "");
            return stockResponse;
        }
        if (Ints.compare(OperateRequestEnums.CHUKU_TRY.getOperate(), stockRequest.getOperation()) == 0) {//尝试出库
            StockResponse stockResponse = new StockResponse();
            StockPo oldStockPo = appService.getStockPoByStock(request, stockPo);
            if (oldStockPo == null) {
                stockResponse.setCode(OperateResponseEnums.CHUKU_FAILED_NOT_FIND_RUKU.getCode()); //出库失败，找不到入库记录
            } else {
                if (new BigInteger(oldStockPo.getAllCount()).compareTo(new BigInteger(stockRequest.getAllCount())) < 0) {//找到入库记录,但是出库比入库多
                    stockResponse.setCode(OperateResponseEnums.CHUKU_FAILED_BIGGER_THEN_RUKU.getCode());
                } else {//合法出库
                    stockResponse.setCode(OperateResponseEnums.CHUKU_TRY_SUCCESS.getCode());
                    StockPo curStockPo = StockUtils.subStockPo(oldStockPo, stockPo);
                    stockResponse.setCurStockPo(curStockPo);
                }
            }
            return stockResponse;
        }
        if (Ints.compare(OperateRequestEnums.CHUKU_CONFIRM.getOperate(), stockRequest.getOperation()) == 0) {//确认出库
            StockResponse stockResponse = new StockResponse();
            StockPo oldStockPo = appService.getStockPoByStock(request, stockPo);
            if (oldStockPo == null) {
                stockResponse.setCode(OperateResponseEnums.CHUKU_FAILED_NOT_FIND_RUKU.getCode()); //出库失败，找不到入库记录
            } else {
                if (new BigInteger(oldStockPo.getAllCount()).compareTo(new BigInteger(stockRequest.getAllCount())) < 0) {//找到入库记录,但是出库比入库多
                    stockResponse.setCode(OperateResponseEnums.CHUKU_FAILED_BIGGER_THEN_RUKU.getCode());
                } else {//合法出库
                    stockResponse.setCode(OperateResponseEnums.CHUKU_SUCCESS.getCode());
                    StockPo curStockPo = StockUtils.subStockPo(oldStockPo, stockPo);
                    stockResponse.setCurStockPo(curStockPo);
                    stockResponse.setStockPos(appService.addStockPo(request, curStockPo));
                    appService.sendMsg(request, "");
                }
            }
            return stockResponse;
        }
        return null;
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
