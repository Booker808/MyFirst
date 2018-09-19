package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseContact;

import java.util.List;
import java.util.Map;

public interface EnterpriseContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseContact record);

    int insertSelective(EnterpriseContact record);

    EnterpriseContact selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseContact record);

    int updateByPrimaryKey(EnterpriseContact record);

    List<EnterpriseContact> selectByEpNumber(String entNumber);
    /**
     * 按条件查询单个
     *
     */
    EnterpriseContact findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseContact> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);
}