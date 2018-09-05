package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.UnitConvert;

public interface UnitConvertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UnitConvert record);

    int insertSelective(UnitConvert record);

    UnitConvert selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UnitConvert record);

    int updateByPrimaryKey(UnitConvert record);
}