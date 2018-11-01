package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.vo.BrandMasterModel;
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
    int insertSelective(BrandMasterModel record);

    /**
     * 更新指定品牌
     * @param
     * @return
     */
    int updateByPrimaryKeySelective(BrandMasterModel brand);

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

    /**
     * 刷新 品牌 redis
     */
    void reloadBrandList();

    BrandMaster findSelective(Map<String,Object> map);

    /**
     * 按条件查询list
     *
     */
    List<BrandMaster> listSelective(Map<String,Object> map);

    /**
     * 根据商城三级分类id 查询 该分类底下的品牌
     * @param lv3CategoryId
     * @return
     */
    List<BrandMaster> findBrandByLv3CategoryId(Integer lv3CategoryId);

}
