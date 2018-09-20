package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.model.EnterpriseCategory;

import java.util.List;
import java.util.Map;

/**
 * 供应商分类Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 11:16:27
 */

public interface EnterpriseCategoryService {

    /**
     * 按条件查询单个
     *
     */
    EnterpriseCategory findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseCategory> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    int  save (EnterpriseCategory enterpriseCategory);
    int  update (EnterpriseCategory enterpriseCategory);

}
