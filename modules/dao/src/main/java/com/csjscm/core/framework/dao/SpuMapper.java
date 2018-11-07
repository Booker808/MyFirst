package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.model.Spu;
import com.csjscm.core.framework.model.SpuEx;

import java.util.List;
import java.util.Map;

public interface SpuMapper {
    int deleteByPrimaryKey(String stdProductNo);

    int insert(Spu record);

    int insertSelective(Spu record);

    Spu selectByPrimaryKey(String stdProductNo);

    int updateByPrimaryKeySelective(Spu record);

    int updateByPrimaryKeyWithBLOBs(Spu record);

    int updateByPrimaryKey(Spu record);

    List<SpuEx> selectByExample(SpuExample example);

    Integer selectStockBySpu(String spuNo);

    int updateShelfState(Map<String, Object> map);

    List<Spu> selectBySpuNoList();

    int findCount(Map<String, Object> condition);
}