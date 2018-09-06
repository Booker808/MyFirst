package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.Category;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.Page;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 商品分类表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */

public interface CategoryService {
    int save(Category t);


    int update(Category t);

    Category findByPrimary(Integer id);
     /**
      * 按条件查询单个
      *
      */
     Category findSelective(Map<String,Object> map);
     /**
      * 按条件查询list
      *
      */
     List<Category> listSelective(Map<String,Object> map);
     /**
      * 按条件查询条数
      *
      */
     int findCount(Map<String,Object> map);

    /**
     * 分页查询
     * @param map
     * @return
     */
    QueryResult<Category> findPage(Map<String,Object> map, int current, int pageSize);

    /**
     * 按id删除 多个id 以逗号隔开
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 更新状态
     * @param ids
     * @param state
     */
    void updateState(List<Integer> ids , Integer state);

    /**
     * 编辑udf
     * @param map
     */
    void updateUdf(Map<String,Object> map);
}
