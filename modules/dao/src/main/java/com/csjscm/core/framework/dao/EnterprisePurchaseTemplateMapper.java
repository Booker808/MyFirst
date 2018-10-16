package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.EnterprisePurchaseTemplateExample;
import com.csjscm.core.framework.model.EnterprisePurchaseTemplate;
import com.csjscm.core.framework.model.EnterprisePurchaseTemplateEx;

import java.util.List;
import java.util.Map;

public interface EnterprisePurchaseTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterprisePurchaseTemplate record);

    int insertSelective(EnterprisePurchaseTemplate record);

    EnterprisePurchaseTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterprisePurchaseTemplate record);

    int updateByPrimaryKey(EnterprisePurchaseTemplate record);

    int findCount(Map<String, Object> map);

    List<EnterprisePurchaseTemplateEx> selectByExample(EnterprisePurchaseTemplateExample templateExample);

    Integer selectNewId();

    String selectEntNameByTemplateId(Integer id);
}