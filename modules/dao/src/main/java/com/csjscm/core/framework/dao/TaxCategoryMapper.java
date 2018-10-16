package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.TaxCategory;

public interface TaxCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaxCategory record);

    int insertSelective(TaxCategory record);

    TaxCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaxCategory record);

    int updateByPrimaryKey(TaxCategory record);
}