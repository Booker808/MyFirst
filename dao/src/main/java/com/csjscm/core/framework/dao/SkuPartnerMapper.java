package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.SkuPartner;

public interface SkuPartnerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuPartner record);

    int insertSelective(SkuPartner record);

    SkuPartner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuPartner record);

    int updateByPrimaryKey(SkuPartner record);
}