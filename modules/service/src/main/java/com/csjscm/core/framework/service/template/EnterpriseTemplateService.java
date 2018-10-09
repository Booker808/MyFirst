package com.csjscm.core.framework.service.template;

import com.csjscm.core.framework.model.EnterpriseStandardTemplate;

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
}
