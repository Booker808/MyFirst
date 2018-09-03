package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.BrandMaster;

import java.util.List;
import java.util.Map;

public interface BrandMasterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrandMaster record);

    int insertSelective(BrandMaster record);

    BrandMaster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrandMaster record);

    int updateByPrimaryKey(BrandMaster record);

    List<BrandMaster> selectByBrandName (String brandName);

    List<BrandMaster> selectByBrandNameSky ();

    List<BrandMaster> selectByBrand (Map<String, Object> map);
}