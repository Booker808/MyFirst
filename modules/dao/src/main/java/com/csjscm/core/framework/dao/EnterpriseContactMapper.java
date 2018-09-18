package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseContact;

public interface EnterpriseContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseContact record);

    int insertSelective(EnterpriseContact record);

    EnterpriseContact selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseContact record);

    int updateByPrimaryKey(EnterpriseContact record);
}