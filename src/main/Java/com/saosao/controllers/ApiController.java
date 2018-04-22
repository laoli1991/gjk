package com.saosao.controllers;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.saosao.com.saosao.vo.GjkCurrencyVo;
import com.saosao.dao.GjkCurrencyDao;
import com.saosao.dao.StuDao;
import com.saosao.po.GjkCurrency;
import com.saosao.po.Stu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private GjkCurrencyDao gjkCurrencyDao ;

    @RequestMapping("/test")
    @ResponseBody
    public Map<String , String> test(){
        Map<String , String> t = Maps.newHashMap() ;
        t.put("name" , "saosao") ;
        t.put("age" , "100") ;
        return t ;
    }

    @RequestMapping("/insertGjkCurrency")
    @ResponseBody
    public int insertGjkCurrency(@RequestBody GjkCurrency gjkCurrency){
        return gjkCurrencyDao.insertSelective(gjkCurrency) ;
    }


    @RequestMapping("/selectList")
    @ResponseBody
    public PageInfo<GjkCurrencyVo> selectList(@RequestParam("pageNum") Integer pageNum , @RequestParam("pageSize") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<GjkCurrency> gjkCurrencys = gjkCurrencyDao.selectList();
        PageInfo<GjkCurrency> currencyPageInfo=new PageInfo<GjkCurrency>(gjkCurrencys);

        PageInfo<GjkCurrencyVo> response = new PageInfo<GjkCurrencyVo>();
        BeanUtils.copyProperties(currencyPageInfo , response);
        List<GjkCurrencyVo> gjkCurrencyVos = Lists.newArrayList() ;
        List<GjkCurrency> gjkCurrencies = currencyPageInfo.getList() ;
        for(GjkCurrency gjkCurrency : gjkCurrencies){
            gjkCurrencyVos.add(buildPo2Vo(gjkCurrency)) ;
        }
        response.setList(gjkCurrencyVos);
        return response ;
    }

    private GjkCurrencyVo buildPo2Vo(GjkCurrency gjkCurrency){
        GjkCurrencyVo gjkCurrencyVo = new GjkCurrencyVo() ;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gjkCurrencyVo.setCreateTime(sdf.format(gjkCurrency.getCreateTime()));

        Map<Integer , String> currencyTypelis = Maps.newHashMap() ;
        currencyTypelis.put(1 , "纸币100元") ;
        currencyTypelis.put(2 , "纸币50元") ;
        currencyTypelis.put(3 , "纸币20元") ;
        currencyTypelis.put(4 , "纸币10元") ;
        currencyTypelis.put(5 , "纸币5元") ;
        currencyTypelis.put(6 , "纸币1元") ;
        currencyTypelis.put(7 , "硬币1元") ;
        currencyTypelis.put(8 , "硬币0.5元") ;

        Map<Integer , String> currencyUnitlis = Maps.newHashMap() ;
        currencyUnitlis.put(1 , "箱") ;
        currencyUnitlis.put(2 , "贷") ;
        currencyUnitlis.put(3 , "捆") ;
        currencyUnitlis.put(4 , "张") ;
        currencyUnitlis.put(5 , "枚") ;

        Map<Integer , String> currencyAttributelis = Maps.newHashMap() ;
        currencyAttributelis.put(1 , "完整卷") ;
        currencyAttributelis.put(2 , "残缺卷") ;

        gjkCurrencyVo.setType(currencyTypelis.get(gjkCurrency.getType()));
        gjkCurrencyVo.setUnit(currencyUnitlis.get(gjkCurrency.getUnit()));
        gjkCurrencyVo.setAttribute(currencyAttributelis.get(gjkCurrency.getAttribute()));
        gjkCurrencyVo.setCount(gjkCurrency.getCount());
        gjkCurrencyVo.setOperator(gjkCurrency.getOperator());
        gjkCurrencyVo.setVersion(gjkCurrency.getVersion() + "");
        gjkCurrencyVo.setId(gjkCurrency.getId());
        return gjkCurrencyVo ;
    }

}
