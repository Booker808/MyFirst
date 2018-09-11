package com.csjscm.core.framework.dao;


import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.SpCategory;
import com.csjscm.core.framework.vo.SpCategoryJsonModel;

import java.util.List;
import java.util.Map;

/**
 * 商品分类表Dao
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */


public interface SpCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SpCategory t);

    int insertSelective(SpCategory t);

    int updateSelective(SpCategory t);

    int update(SpCategory t);

    SpCategory findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     SpCategory findSelective(Map<String, Object> map);
     /**
      * 按条件查询list
      *
      */
     List<SpCategory> listSelective(Map<String, Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String, Object> map);
    /**
     * 编辑udf
     * @param map
     */
    void updateUdf(Map<String, Object> map);

    /**
     * 查询全部
     * @return
     */
    List<SpCategoryJsonModel>  findModelAll();

}
