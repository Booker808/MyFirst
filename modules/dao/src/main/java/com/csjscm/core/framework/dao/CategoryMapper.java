package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.Category;
import java.util.List;
import java.util.Map;

/**
 * 商品分类表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */


public interface CategoryMapper{
    int deleteByPrimaryKey(Integer id);

    int insert(Category t);

    int insertSelective(Category t);

    int updateSelective(Category t);

    int update(Category t);

    Category findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     Category findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<Category> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);


}
