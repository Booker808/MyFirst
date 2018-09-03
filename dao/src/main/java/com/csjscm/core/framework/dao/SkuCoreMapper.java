package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SkuCoreEx;

import java.util.List;

public interface SkuCoreMapper {
    int deleteByPrimaryKey(String productNo);

    int insert(SkuCore record);

    int insertSelective(SkuCore record);

    SkuCore selectByPrimaryKey(String productNo);

    int updateByPrimaryKeySelective(SkuCore record);

    int updateByPrimaryKey(SkuCore record);

    List<SkuCore> selectByExample(SkuCoreExample example);

    List<SkuCoreEx> selectExByExample(SkuCoreExample example);
}