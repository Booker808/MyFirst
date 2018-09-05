package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.Spu;

public interface SpuMapper {
    int deleteByPrimaryKey(String stdProductNo);

    int insert(Spu record);

    int insertSelective(Spu record);

    Spu selectByPrimaryKey(String stdProductNo);

    int updateByPrimaryKeySelective(Spu record);

    int updateByPrimaryKeyWithBLOBs(Spu record);

    int updateByPrimaryKey(Spu record);
}