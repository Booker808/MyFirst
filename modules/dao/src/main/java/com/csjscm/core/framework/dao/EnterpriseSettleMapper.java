package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseSettle;

import java.util.List;
import java.util.Map;

public interface EnterpriseSettleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseSettle record);

    int insertSelective(EnterpriseSettle record);

    EnterpriseSettle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseSettle record);

    int updateByPrimaryKey(EnterpriseSettle record);

    /**
     * 按条件查询单个
     *
     */
    EnterpriseSettle findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseSettle> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);
}