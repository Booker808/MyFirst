package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.SkuSell;

public interface SkuSellMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuSell record);

    int insertSelective(SkuSell record);

    SkuSell selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuSell record);

    int updateByPrimaryKey(SkuSell record);
}