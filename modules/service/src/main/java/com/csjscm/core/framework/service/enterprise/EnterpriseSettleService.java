package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.model.EnterpriseSettle;

import java.util.List;
import java.util.Map;

/**
 * 结算信息Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 11:03:25
 */

public interface EnterpriseSettleService {

    /**
     * 按条件查询单个
     *
     */
    EnterpriseSettle findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseSettle> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    int save(EnterpriseSettle enterpriseSettle);
    int update(EnterpriseSettle enterpriseSettle);

}
