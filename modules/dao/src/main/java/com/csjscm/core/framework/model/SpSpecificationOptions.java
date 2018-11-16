package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类选项属性关联表实体
 * 
 * @author jhp
 * @version 1.0.0
 * @date 2018-11-16 11:08:36
 */

 public class SpSpecificationOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 属性项名称
    */
    private String optionValue;

    /**
    * 分类属性id
    */
    private Integer specId;


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
    * 获取属性项名称
    *
    * @return 属性项名称
    */
    public String getOptionValue(){
        return optionValue;
    }

    /**
    * 设置属性项名称
    * 
    * @param optionValue 要设置的属性项名称
    */
    public void setOptionValue(String optionValue){
        this.optionValue = optionValue;
    }

    /**
    * 获取分类属性id
    *
    * @return 分类属性id
    */
    public Integer getSpecId(){
        return specId;
    }

    /**
    * 设置分类属性id
    * 
    * @param specId 要设置的分类属性id
    */
    public void setSpecId(Integer specId){
        this.specId = specId;
    }

}