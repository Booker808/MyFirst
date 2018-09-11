package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:02:45
 */

 public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 类型编码
    */
    private String typeCode;

    /**
    * 类型名称
    */
    private String typeName;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 备注
    */
    private String remark;


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
    * 获取类型编码
    *
    * @return 类型编码
    */
    public String getTypeCode(){
        return typeCode;
    }

    /**
    * 设置类型编码
    * 
    * @param typeCode 要设置的类型编码
    */
    public void setTypeCode(String typeCode){
        this.typeCode = typeCode;
    }

    /**
    * 获取类型名称
    *
    * @return 类型名称
    */
    public String getTypeName(){
        return typeName;
    }

    /**
    * 设置类型名称
    * 
    * @param typeName 要设置的类型名称
    */
    public void setTypeName(String typeName){
        this.typeName = typeName;
    }

    /**
    * 获取排序
    *
    * @return 排序
    */
    public Integer getSort(){
        return sort;
    }

    /**
    * 设置排序
    * 
    * @param sort 要设置的排序
    */
    public void setSort(Integer sort){
        this.sort = sort;
    }

    /**
    * 获取备注
    *
    * @return 备注
    */
    public String getRemark(){
        return remark;
    }

    /**
    * 设置备注
    * 
    * @param remark 要设置的备注
    */
    public void setRemark(String remark){
        this.remark = remark;
    }

}