package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuCustomerEx;

import java.util.List;

public interface SkuCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuCustomer record);

    int insertSelective(SkuCustomer record);

    SkuCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuCustomer record);

    int updateByPrimaryKey(SkuCustomer record);

    List<SkuCustomer> selectByExample(SkuCustomerExample example);

    List<SkuCustomerEx> selectExByExample(SkuCustomerExample example);
}