package com.csjscm.core.framework.service.enterprise;


import com.csjscm.core.framework.model.EnterpriseFlow;
import com.csjscm.core.framework.vo.EnterpriseFlowModel;

/**
 * 供应商审批流程记录Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-28 09:06:02
 */

public interface EnterpriseFlowService {
    /**
     * 初始提交审核
     * @param enterpriseFlow
     */
    void saveFirstCheck(EnterpriseFlow enterpriseFlow);

    /**
     * 正常流程审核
     * @param enterpriseFlowModel
     */
    void saveNormal(EnterpriseFlowModel enterpriseFlowModel);

}
