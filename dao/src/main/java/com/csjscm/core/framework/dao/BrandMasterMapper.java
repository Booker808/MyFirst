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


    List<BrandMaster> selectByBrand (String condition);
    /**
     * 按条件查询单个
     *
     */
    BrandMaster findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<BrandMaster> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    List<BrandMaster> selectByBrandName (String brandName);

    List<BrandMaster> selectByBrandNameSky ();

    List<BrandMaster> selectByBrand (Map<String, Object> map);

}