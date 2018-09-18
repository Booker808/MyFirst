package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseSettle;

public interface EnterpriseSettleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseSettle record);

    int insertSelective(EnterpriseSettle record);

    EnterpriseSettle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseSettle record);

    int updateByPrimaryKey(EnterpriseSettle record);
}