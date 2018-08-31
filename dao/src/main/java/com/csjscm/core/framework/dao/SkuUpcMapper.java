package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.SkuUpc;

public interface SkuUpcMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuUpc record);

    int insertSelective(SkuUpc record);

    SkuUpc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuUpc record);

    int updateByPrimaryKey(SkuUpc record);
}