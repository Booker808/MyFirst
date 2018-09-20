package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseCategory;

import java.util.List;
import java.util.Map;

public interface EnterpriseCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseCategory record);

    int insertSelective(EnterpriseCategory record);

    EnterpriseCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseCategory record);

    int updateByPrimaryKey(EnterpriseCategory record);

    /**
     * 按条件查询单个
     *
     */
    EnterpriseCategory findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseCategory> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);
}