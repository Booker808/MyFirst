package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterpriseStandardTemplate;

import java.util.List;

public interface EnterpriseStandardTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseStandardTemplate record);

    int insertSelective(EnterpriseStandardTemplate record);

    EnterpriseStandardTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseStandardTemplate record);

    int updateByPrimaryKey(EnterpriseStandardTemplate record);

    List<EnterpriseStandardTemplate> selectAllList();
}