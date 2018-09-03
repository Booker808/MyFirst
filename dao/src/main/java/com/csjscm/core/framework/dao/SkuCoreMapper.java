package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SkuCoreEx;

import java.util.List;
import java.util.Map;

public interface SkuCoreMapper {
    int deleteByPrimaryKey(String productNo);

    int insert(SkuCore record);

    int insertSelective(SkuCore record);

    SkuCore selectByPrimaryKey(String productNo);

    int updateByPrimaryKeySelective(SkuCore record);

    int updateByPrimaryKey(SkuCore record);

    List<SkuCore> selectByExample(SkuCoreExample example);

    /**
     * 按条件查询单个
     *
     */
    SkuCore findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<SkuCore> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);


    List<SkuCoreEx> selectExByExample(SkuCoreExample example);

}