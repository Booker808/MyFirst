package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseTemplateFlow;

public interface EnterpriseTemplateFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseTemplateFlow record);

    int insertSelective(EnterpriseTemplateFlow record);

    EnterpriseTemplateFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseTemplateFlow record);

    int updateByPrimaryKey(EnterpriseTemplateFlow record);
}