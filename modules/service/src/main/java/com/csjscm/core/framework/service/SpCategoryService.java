package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.SpCategory;
import com.csjscm.core.framework.vo.SpCategoryJsonModel;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.List;
import java.util.Map;

/**
 * 商品分类表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */

public interface SpCategoryService {
    int save(SpCategory t);


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
     * 分页查询
     * @param map
     * @return
     */
    QueryResult<SpCategory> findPage(Map<String, Object> map, int current, int pageSize);

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
    void updateState(List<Integer> ids, Integer state);

    /**
     * 编辑udf
     * @param
     */
    void updateUdf(SpCategory t);

    /**
     * 一个分类接口  三级树状结构 把所有的分类集成起来, 并且缓存到 redis里面
     * @return
     */
    List<SpCategoryJsonModel>  getJsonCategory();
}
