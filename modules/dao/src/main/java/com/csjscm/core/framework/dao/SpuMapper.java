package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.model.Spu;
import com.csjscm.core.framework.model.SpuEx;
import com.csjscm.core.framework.model.SpuWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SpuMapper {
    int deleteByPrimaryKey(String stdProductNo);

    int insert(SpuWithBLOBs record);

    int insertSelective(SpuWithBLOBs record);

    SpuWithBLOBs selectByPrimaryKey(String stdProductNo);

    int updateByPrimaryKeySelective(SpuWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SpuWithBLOBs record);

    int updateByPrimaryKey(Spu record);

    List<SpuEx> selectByExample(SpuExample example);

    int selectStockBySpu(String spuNo);

    int updateShelfState(Map<String, Object> map);
}