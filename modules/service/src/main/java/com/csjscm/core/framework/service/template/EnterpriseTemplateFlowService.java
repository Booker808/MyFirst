package com.csjscm.core.framework.service.template;

import com.csjscm.core.framework.model.EnterprisePurchaseTemplate;
import com.csjscm.core.framework.service.template.model.CheckTaskVo;
import com.csjscm.core.framework.service.template.model.HisWorkFlowInfo;
import com.csjscm.core.framework.service.template.model.TodoWorkFlowInfo;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateDetailVo;

import java.util.List;

public interface EnterpriseTemplateFlowService {
    /**
     * 提交采购合同模板至流程
     *
     * @param templateDetailVo
     */
    void submitPurchaseTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo);

    /**
     * 根据模板ID，用户名获取代办TaskId
     *
     * @param templateId
     * @param userName
     * @return
     */
    String getToDoTaskId(Integer templateId, String userName);

    List<TodoWorkFlowInfo> getToDoTaskIds(String userName);

    void checkTask(String userName, CheckTaskVo checkTask);

    /**
     * 根据实例ID查询历史流程记录
     *
     * @param instanceId
     * @return
     */
    List<HisWorkFlowInfo> queryHisFlow(String instanceId);

    EnterprisePurchaseTemplate selectByPrimaryKey(Integer id);
}
