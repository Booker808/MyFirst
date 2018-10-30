package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.BrandCategory;
import com.csjscm.core.framework.vo.BrandCategoryVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-29 14:49:28
 */


public interface BrandCategoryMapper{
    int deleteByPrimaryKey(Integer id);

    int deleteByBrandId(Integer brandId);

    int insert(BrandCategory t);

    int insertSelective(BrandCategory t);

    int updateSelective(BrandCategory t);

    int update(BrandCategory t);

    BrandCategory findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     BrandCategory findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<BrandCategory> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);

     List<String> findCategoryNameList(Map<String, Object> map);

     List<BrandCategoryVo> findBrandCategoryVo(Integer brandId);

}
