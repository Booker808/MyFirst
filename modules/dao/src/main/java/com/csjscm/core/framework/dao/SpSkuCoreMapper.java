package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.SpSkuCore;

import java.util.List;
import java.util.Map;

public interface SpSkuCoreMapper {
    int deleteByPrimaryKey(String productNo);

    int insert(SpSkuCore record);

    int insertSelective(SpSkuCore record);

    SpSkuCore selectByPrimaryKey(String productNo);

    int updateByPrimaryKeySelective(SpSkuCore record);

    int updateByPrimaryKeyWithBLOBs(SpSkuCore record);

    int updateByPrimaryKey(SpSkuCore record);

    List<SpSkuCore> selectByCondition(Map<String, Object> map);

    List<SpSkuCore> selectByProductNoList();
}