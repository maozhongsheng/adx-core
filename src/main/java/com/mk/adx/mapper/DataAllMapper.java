package com.mk.adx.mapper;

import com.mk.adx.vo.DataAll;
import com.mk.adx.vo.DataAllExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DataAllMapper {
    long countByExample(DataAllExample example);

    int deleteByExample(DataAllExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DataAll record);

    int insertSelective(DataAll record);

    List<DataAll> selectByExample(DataAllExample example);

    DataAll selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DataAll record, @Param("example") DataAllExample example);

    int updateByExample(@Param("record") DataAll record, @Param("example") DataAllExample example);

    int updateByPrimaryKeySelective(DataAll record);

    int updateByPrimaryKey(DataAll record);

    void uppv(Map da);

    void upck(Map da);

    void upvs(Map da);

    void upve(Map da);

    void upds(Map da);

    void upde(Map da);

    void upis(Map da);

    void upie(Map da);

    void updp(Map da);

    void upidp(Map da);

    void update(DataAll dataAll);
}