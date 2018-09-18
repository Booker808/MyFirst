package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseContract;

public interface EnterpriseContractMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseContract record);

    int insertSelective(EnterpriseContract record);

    EnterpriseContract selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseContract record);

    int updateByPrimaryKey(EnterpriseContract record);
}