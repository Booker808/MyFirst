package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseAccount;

public interface EnterpriseAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseAccount record);

    int insertSelective(EnterpriseAccount record);

    EnterpriseAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseAccount record);

    int updateByPrimaryKey(EnterpriseAccount record);
}