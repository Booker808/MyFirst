package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseInfo;

public interface EnterpriseInfoMapper {
    int deleteByPrimaryKey(String entNumber);

    int insert(EnterpriseInfo record);

    int insertSelective(EnterpriseInfo record);

    EnterpriseInfo selectByPrimaryKey(String entNumber);

    int updateByPrimaryKeySelective(EnterpriseInfo record);

    int updateByPrimaryKey(EnterpriseInfo record);

    String findMaxEntNumber();
}