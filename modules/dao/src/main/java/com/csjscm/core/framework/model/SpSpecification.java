package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类属性表实体
 * 
 * @author jhp
 * @version 1.0.0
 * @date 2018-11-16 11:07:51
 */

 public class SpSpecification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 属性名称
    */
    private String specName;

    /**
    * 是否启用
    */
    private Integer status;

    /**
    * 属性类型（1：销售属性，2：扩展属性）
    */
    private Integer type;

    /**
    * 三级分类id
    */
    private Integer categoryId;


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
    * 获取属性名称
    *
    * @return 属性名称
    */
    public String getSpecName(){
        return specName;
    }

    /**
    * 设置属性名称
    * 
    * @param specName 要设置的属性名称
    */
    public void setSpecName(String specName){
        this.specName = specName;
    }

    /**
    * 获取是否启用
    *
    * @return 是否启用
    */
    public Integer getStatus(){
        return status;
    }

    /**
    * 设置是否启用
    * 
    * @param status 要设置的是否启用
    */
    public void setStatus(Integer status){
        this.status = status;
    }

    /**
    * 获取属性类型（1：销售属性，2：扩展属性）
    *
    * @return 属性类型（1：销售属性，2：扩展属性）
    */
    public Integer getType(){
        return type;
    }

    /**
    * 设置属性类型（1：销售属性，2：扩展属性）
    * 
    * @param type 要设置的属性类型（1：销售属性，2：扩展属性）
    */
    public void setType(Integer type){
        this.type = type;
    }

    /**
    * 获取三级分类id
    *
    * @return 三级分类id
    */
    public Integer getCategoryId(){
        return categoryId;
    }

    /**
    * 设置三级分类id
    * 
    * @param categoryId 要设置的三级分类id
    */
    public void setCategoryId(Integer categoryId){
        this.categoryId = categoryId;
    }

}