package com.csjscm.core.framework.service;

import com.csjscm.core.framework.model.EnterpriseTicketInfo;

import java.util.Map;

/**
 * 开票信息Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:29:40
 */

public interface EnterpriseTicketInfoService {
    /**
     * 按条件查询单个
     *
     */
    EnterpriseTicketInfo findSelective(Map<String, Object> map);
}
