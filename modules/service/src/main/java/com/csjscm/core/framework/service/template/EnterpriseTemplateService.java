package com.csjscm.core.framework.service.template;

import com.csjscm.core.framework.example.EnterprisePurchaseTemplateExample;
import com.csjscm.core.framework.model.EnterpriseStandardTemplate;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateDetailVo;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateVo;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.List;

public interface EnterpriseTemplateService {
    /**
     * 添加标准模板
     *
     * @param template
     */
    void addStandardTemplate(EnterpriseStandardTemplate template);

    /**
     * 更新标准模板
     *
     * @param template
     */
    void updateStandardTemplate(EnterpriseStandardTemplate template);

    /**
     * 查找标准模板（列表）
     *
     * @return
     */
    List<EnterpriseStandardTemplate> queryStandardTemplate();

    /**
     * 查找标准模板（指定ID）
     *
     * @param id
     * @return
     */
    EnterpriseStandardTemplate queryStandardTemplateById(Integer id);

    /**
     * 添加采购合同模板
     *
     * @param templateDetailVo
     */
    void addPurchaseTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo);

    /**
     * 更新采购合同模板
     *
     * @param templateDetailVo
     */
    void updatePurchaseTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo);

    /**
     * 分页查询采购模板
     *
     * @param page
     * @param rpp
     * @param templateExample
     * @return
     */
    QueryResult<EnterprisePurchaseTemplateVo> queryPurchaseTemplate(int page, int rpp, EnterprisePurchaseTemplateExample templateExample);

    /**
     * 根据ID查询采购合同模板详情
     *
     * @param id
     * @return
     */
    EnterprisePurchaseTemplateDetailVo queryPurchaseTemplateById(Integer id);

    String getPurchaseTemplateUrl(Integer id);

    void updateArchiveTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo);
}
