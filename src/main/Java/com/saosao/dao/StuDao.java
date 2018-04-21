package com.saosao.dao;

import com.saosao.po.Stu;

import java.util.List;

public interface StuDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Stu record);

    int insertBatch(List<Stu> stus);

    int insertSelective(Stu record);

    Stu selectByPrimaryKey(Integer id);

    List<Stu> selectList() ;

    int updateByPrimaryKeySelective(Stu record);

    int updateByPrimaryKey(Stu record);
}