package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌分类关联表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-29 14:49:28
 */

 public class BrandCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 品牌id
    */
    private Integer brandId;

    /**
    * 一级分类id
    */
    private Integer lv1CategoryId;

    /**
    * 二级分类id
    */
    private Integer lv2CategoryId;

    /**
    * 三级分类id
    */
    private Integer lv3CategoryId;

    /**
    * 1平台 2商城
    */
    private Integer type;


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
    * @param
    */
    public void setId(Integer id){
        this.id = id;
    }

    /**
    * 获取品牌id
    *
    * @return 品牌id
    */
    public Integer getBrandId(){
        return brandId;
    }

    /**
    * 设置品牌id
    * 
    * @param brandId 要设置的品牌id
    */
    public void setBrandId(Integer brandId){
        this.brandId = brandId;
    }

    /**
    * 获取一级分类id
    *
    * @return 一级分类id
    */
    public Integer getLv1CategoryId(){
        return lv1CategoryId;
    }

    /**
    * 设置一级分类id
    * 
    * @param lv1CategoryId 要设置的一级分类id
    */
    public void setLv1CategoryId(Integer lv1CategoryId){
        this.lv1CategoryId = lv1CategoryId;
    }

    /**
    * 获取二级分类id
    *
    * @return 二级分类id
    */
    public Integer getLv2CategoryId(){
        return lv2CategoryId;
    }

    /**
    * 设置二级分类id
    * 
    * @param lv2CategoryId 要设置的二级分类id
    */
    public void setLv2CategoryId(Integer lv2CategoryId){
        this.lv2CategoryId = lv2CategoryId;
    }

    /**
    * 获取三级分类id
    *
    * @return 三级分类id
    */
    public Integer getLv3CategoryId(){
        return lv3CategoryId;
    }

    /**
    * 设置三级分类id
    * 
    * @param lv3CategoryId 要设置的三级分类id
    */
    public void setLv3CategoryId(Integer lv3CategoryId){
        this.lv3CategoryId = lv3CategoryId;
    }

    /**
    * 获取1平台 2商城
    *
    * @return 1平台 2商城
    */
    public Integer getType(){
        return type;
    }

    /**
    * 设置1平台 2商城
    * 
    * @param type 要设置的1平台 2商城
    */
    public void setType(Integer type){
        this.type = type;
    }

}