package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.SkuCustomer;

public interface SkuCustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Fri Aug 31 11:50:34 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Fri Aug 31 11:50:34 CST 2018
     */
    int insert(SkuCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Fri Aug 31 11:50:34 CST 2018
     */
    int insertSelective(SkuCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Fri Aug 31 11:50:34 CST 2018
     */
    SkuCustomer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Fri Aug 31 11:50:34 CST 2018
     */
    int updateByPrimaryKeySelective(SkuCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Fri Aug 31 11:50:34 CST 2018
     */
    int updateByPrimaryKey(SkuCustomer record);
}