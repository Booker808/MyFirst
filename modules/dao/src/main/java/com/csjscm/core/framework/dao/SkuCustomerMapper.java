package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuCustomerEx;
import com.csjscm.core.framework.vo.SkuCustomerPageVo;

import java.util.List;
import java.util.Map;

public interface SkuCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SkuCustomer record);

    int insertSelective(SkuCustomer record);

    SkuCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SkuCustomer record);

    int updateByPrimaryKey(SkuCustomer record);

    List<SkuCustomer> selectByExample(SkuCustomerExample example);

    List<SkuCustomerEx> selectExByExample(SkuCustomerExample example);

    List<SkuCustomerPageVo> selectPage(Map<String,Object> map);

    /**
     * 按条件查询单个
     *
     */
    SkuCustomer findSelective(Map<String,Object> map);
    /**
     * 按条件查询list
     *
     */
    List<SkuCustomer> listSelective(Map<String,Object> map);
    /**
     * 按条件查询条数
     *
     */
    int findCount(Map<String,Object> map);

    List<SkuCustomer> listSelectiveSCM(Map<String,Object> map);


}