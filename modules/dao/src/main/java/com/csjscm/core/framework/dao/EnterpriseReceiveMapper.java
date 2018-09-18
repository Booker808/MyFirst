package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseReceive;

public interface EnterpriseReceiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseReceive record);

    int insertSelective(EnterpriseReceive record);

    EnterpriseReceive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseReceive record);

    int updateByPrimaryKey(EnterpriseReceive record);
}