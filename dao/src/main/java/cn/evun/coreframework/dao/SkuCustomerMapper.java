package cn.evun.coreframework.dao;

import cn.evun.coreframework.model.SkuCustomer;
import cn.evun.coreframework.model.SkuCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SkuCustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int countByExample(SkuCustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int deleteByExample(SkuCustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int insert(SkuCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int insertSelective(SkuCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    List<SkuCustomer> selectByExample(SkuCustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    SkuCustomer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByExampleSelective(@Param("record") SkuCustomer record, @Param("example") SkuCustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByExample(@Param("record") SkuCustomer record, @Param("example") SkuCustomerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByPrimaryKeySelective(SkuCustomer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_customer
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByPrimaryKey(SkuCustomer record);
}