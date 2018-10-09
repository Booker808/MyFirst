package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.EnterprisePurchaseTemplate;

public interface EnterprisePurchaseTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterprisePurchaseTemplate record);

    int insertSelective(EnterprisePurchaseTemplate record);

    EnterprisePurchaseTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterprisePurchaseTemplate record);

    int updateByPrimaryKey(EnterprisePurchaseTemplate record);
}