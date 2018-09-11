package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.EnterpriseSettlementInfo;

import java.util.Map;

/**
 * 结算信息表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:29:03
 */

public interface EnterpriseSettlementInfoService {
    /**
     * 按条件查询单个
     *
     */
    EnterpriseSettlementInfo findSelective(Map<String, Object> map);

}
