package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.List;
import java.util.Map;

/**
 * Created by zjc on 2018/8/31.
 */

public interface BrandMasterService {
    /**
     * 查询品牌接口(分页)
     * @param map
     * @param current
     * @param pageSize
     * @return
     */
    QueryResult<BrandMaster> queryBrandMasterList(Map<String, Object> map, int current, int pageSize);

    /**
     * 查询品牌名称
     * @param brandName
     * @return
     */
    List<BrandMaster> selectByBrandName(String brandName);

    /**
     * 查询品牌名称列表
     * @return
     */
    List<BrandMaster> selectByBrandNameSky();
    /**
     * 查询目标为ID的品牌
     * @param id
     * @return
     */
    BrandMaster selectByPrimaryKey(Integer id);

    /**
     * 创建品牌对象
     * @param record
     * @return
     */
    Map<String, Object> insertSelective(BrandMaster record);

    /**
     * 更新指定品牌
     * @param record
     * @return
     */
    Map<String, Object> updateByPrimaryKeySelective(BrandMaster record);

    /**
     * 删除指定品牌列表
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @param ids
     */
    void deleteByIds(String ids);
}
