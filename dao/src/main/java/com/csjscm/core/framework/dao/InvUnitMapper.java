package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.InvUnit;

public interface InvUnitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvUnit record);

    int insertSelective(InvUnit record);

    InvUnit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InvUnit record);

    int updateByPrimaryKey(InvUnit record);
}