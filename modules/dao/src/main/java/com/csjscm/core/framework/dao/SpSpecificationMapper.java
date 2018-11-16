package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.SpSpecification;
import java.util.List;
import java.util.Map;

/**
 * 分类属性表Dao
 * 
 * @author jhp
 * @version 1.0.0
 * @date 2018-11-16 11:07:51
 */


public interface SpSpecificationMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(SpSpecification t);

    int insertSelective(SpSpecification t);

    int updateSelective(SpSpecification t);

    int update(SpSpecification t);

    SpSpecification findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     SpSpecification findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<SpSpecification> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
