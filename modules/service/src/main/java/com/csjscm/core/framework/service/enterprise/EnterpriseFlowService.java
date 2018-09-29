package com.csjscm.core.framework.service.enterprise;


import com.csjscm.core.framework.model.EnterpriseFlow;
import com.csjscm.core.framework.vo.EnterpriseFlowModel;

import java.util.List;
import java.util.Map;

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

    EnterpriseFlow findByPrimary(Integer id);
    /**
     * 按条件查询单个
     *
     */
    EnterpriseFlow findSelective(Map<String, Object> map);
    /**
     * 按条件查询list
     *
     */
    List<EnterpriseFlow> listSelective(Map<String, Object> map);

}
