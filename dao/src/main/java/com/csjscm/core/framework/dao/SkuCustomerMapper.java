package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.SkuCustomer;

public interface SkuCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuCustomer record);

    int insertSelective(SkuCustomer record);

    SkuCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuCustomer record);

    int updateByPrimaryKey(SkuCustomer record);
}