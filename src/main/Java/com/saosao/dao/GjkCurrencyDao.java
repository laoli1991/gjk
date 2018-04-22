package com.saosao.dao;

import com.saosao.po.GjkCurrency;

import java.util.List;

public interface GjkCurrencyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GjkCurrency record);

    int insertSelective(GjkCurrency record);

    GjkCurrency selectByPrimaryKey(Integer id);

    List<GjkCurrency> selectList() ;

    int updateByPrimaryKeySelective(GjkCurrency record);

    int updateByPrimaryKey(GjkCurrency record);
}