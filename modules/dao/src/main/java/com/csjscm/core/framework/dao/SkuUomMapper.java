package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.SkuUom;

public interface SkuUomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuUom record);

    int insertSelective(SkuUom record);

    SkuUom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuUom record);

    int updateByPrimaryKey(SkuUom record);
}