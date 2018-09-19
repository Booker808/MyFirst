package com.csjscm.core.framework.service.enterprise;


import com.csjscm.core.framework.model.EnterpriseContract;

import java.util.List;
import java.util.Map;

/**
 * 合同模板Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 11:25:02
 */

public interface EnterpriseContractService {

    /**
     * 按条件查询单个
     *
     */
    EnterpriseContract findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseContract> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    int save(EnterpriseContract enterpriseContract);
    int update(EnterpriseContract enterpriseContract);
}
