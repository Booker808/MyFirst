package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseCategory;

public interface EnterpriseCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseCategory record);

    int insertSelective(EnterpriseCategory record);

    EnterpriseCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseCategory record);

    int updateByPrimaryKey(EnterpriseCategory record);
}