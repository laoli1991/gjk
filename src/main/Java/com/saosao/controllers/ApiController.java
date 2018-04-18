package com.saosao.controllers;

import com.google.common.collect.Maps;
import com.saosao.dao.StuDao;
import com.saosao.po.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private StuDao stuDao ;

    @RequestMapping("/test")
    @ResponseBody
    public Map<String , String> test(){
        Map<String , String> t = Maps.newHashMap() ;
        t.put("name" , "saosao") ;
        t.put("age" , "100") ;
        return t ;
    }

    @RequestMapping("/mybatis-test")
    @ResponseBody
    public Stu selectByPrimaryKey(){
        return stuDao.selectByPrimaryKey(1655) ;
    }
}
