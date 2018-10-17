package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.TaxVersionExample;
import com.csjscm.core.framework.model.TaxVersion;

import java.util.List;
import java.util.Map;

public interface TaxVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaxVersion record);

    int insertSelective(TaxVersion record);

    TaxVersion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaxVersion record);

    int updateByPrimaryKey(TaxVersion record);

    List<TaxVersion> selectByExample(TaxVersionExample example);

    int findCount(Map<String, Object> map);
}