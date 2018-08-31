package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartner;

import java.util.List;

public interface SkuPartnerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuPartner record);

    int insertSelective(SkuPartner record);

    SkuPartner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuPartner record);

    int updateByPrimaryKey(SkuPartner record);

    List<SkuPartner> selectByExample(SkuPartnerExample example);
}