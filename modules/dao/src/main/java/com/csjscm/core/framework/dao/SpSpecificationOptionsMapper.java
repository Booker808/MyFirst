package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.SpSpecificationOptions;
import java.util.List;
import java.util.Map;

/**
 * 分类选项属性关联表Dao
 * 
 * @author jhp
 * @version 1.0.0
 * @date 2018-11-16 11:08:36
 */


public interface SpSpecificationOptionsMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(SpSpecificationOptions t);

    int insertSelective(SpSpecificationOptions t);

    int updateSelective(SpSpecificationOptions t);

    int update(SpSpecificationOptions t);

    SpSpecificationOptions findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     SpSpecificationOptions findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<SpSpecificationOptions> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
