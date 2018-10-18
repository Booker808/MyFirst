package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.TaxCategoryExample;
import com.csjscm.core.framework.model.TaxCategory;

import java.util.List;
import java.util.Map;

public interface TaxCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaxCategory record);

    int insertSelective(TaxCategory record);

    TaxCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaxCategory record);

    int updateByPrimaryKey(TaxCategory record);

    int findCount(Map<String, Object> map);

    TaxCategory findOne(Map<String, Object> map);

    void copy(Map<String,Object> map);

    List<TaxCategory> selectByCondition(Map<String, Object> map);

    List<TaxCategory> selectByExample(TaxCategoryExample example);
}