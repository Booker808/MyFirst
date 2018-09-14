package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.SkuPartnerUom;
import java.util.List;
import java.util.Map;

/**
 * 供应商商品包装规格Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-14 10:26:29
 */


public interface SkuPartnerUomMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(SkuPartnerUom t);

    int insertSelective(SkuPartnerUom t);

    int updateSelective(SkuPartnerUom t);

    int update(SkuPartnerUom t);

    SkuPartnerUom findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     SkuPartnerUom findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<SkuPartnerUom> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
