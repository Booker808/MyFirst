package com.csjscm.core.framework.model;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户商品与税收关联记录参考表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-17 16:12:45
 */

 public class TaxCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 客户商品名称
    */
    @NotBlank(message = "customerPdName不能为空")
    private String customerPdName;

    /**
    * 税务编码
    */
    @NotBlank(message = "taxCode不能为空")
    private String taxCode;

    /**
    * 税务分类名
    */
    @NotBlank(message = "taxCategoryName不能为空")
    private String taxCategoryName;


    /**
    * 获取主键Id
    *
    * @return id
    */
    public Integer getId(){
        return id;
    }

    /**
    * 设置主键Id
    * 
    * @param 要设置的主键Id
    */
    public void setId(Integer id){
        this.id = id;
    }

    /**
    * 获取客户商品名称
    *
    * @return 客户商品名称
    */
    public String getCustomerPdName(){
        return customerPdName;
    }

    /**
    * 设置客户商品名称
    * 
    * @param customerPdName 要设置的客户商品名称
    */
    public void setCustomerPdName(String customerPdName){
        this.customerPdName = customerPdName;
    }

    /**
    * 获取税务编码
    *
    * @return 税务编码
    */
    public String getTaxCode(){
        return taxCode;
    }

    /**
    * 设置税务编码
    * 
    * @param taxCode 要设置的税务编码
    */
    public void setTaxCode(String taxCode){
        this.taxCode = taxCode;
    }

    /**
    * 获取税务分类名
    *
    * @return 税务分类名
    */
    public String getTaxCategoryName(){
        return taxCategoryName;
    }

    /**
    * 设置税务分类名
    * 
    * @param taxCategoryName 要设置的税务分类名
    */
    public void setTaxCategoryName(String taxCategoryName){
        this.taxCategoryName = taxCategoryName;
    }

}