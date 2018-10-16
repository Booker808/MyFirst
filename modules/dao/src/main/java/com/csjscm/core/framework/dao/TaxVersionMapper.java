package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.TaxVersion;

public interface TaxVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaxVersion record);

    int insertSelective(TaxVersion record);

    TaxVersion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaxVersion record);

    int updateByPrimaryKey(TaxVersion record);
}