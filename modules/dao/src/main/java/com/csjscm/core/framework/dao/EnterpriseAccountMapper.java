package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseAccount;

import java.util.List;
import java.util.Map;

public interface EnterpriseAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseAccount record);

    int insertSelective(EnterpriseAccount record);

    EnterpriseAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseAccount record);

    int updateByPrimaryKey(EnterpriseAccount record);

    List<EnterpriseAccount> selectByEpNumber(String entNumber);

    /**
     * 按条件查询单个
     *
     */
    EnterpriseAccount findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseAccount> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);
}