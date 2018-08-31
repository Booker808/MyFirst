package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

/**
 * Created by zjc on 2018/8/31.
 */

public interface BrandMasterService {
    /**
     *
     * @param condition
     * @param current
     * @param pageSize
     * @return
     */
    QueryResult<BrandMaster> queryBrandList(String condition, int current, int pageSize);
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
    int insertSelective(BrandMaster record);

    /**
     * 更新指定品牌
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(BrandMaster record);

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
