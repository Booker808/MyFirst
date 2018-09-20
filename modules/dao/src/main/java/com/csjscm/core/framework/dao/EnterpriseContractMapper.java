package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseContract;

import java.util.List;
import java.util.Map;

public interface EnterpriseContractMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseContract record);

    int insertSelective(EnterpriseContract record);

    EnterpriseContract selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseContract record);

    int updateByPrimaryKey(EnterpriseContract record);

    /**
     * 按条件查询单个
     *
     */
    EnterpriseContract findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseContract> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);
}