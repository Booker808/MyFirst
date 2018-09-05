package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 计量单位定义表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-03 10:01:53
 */

 public class InvUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 单位名称
    */
    private String objName;

    /**
    * 分类类型，1-数量  2-重量  3-长度 4-体积
    */
    private Integer objType;

    /**
    * 是否有效，1-有效，0-失效
    */
    private Integer isvalid;

    /**
    * 备注
    */
    private String memo;

    /**
    * 原requestId
    */
    private String requestId;


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
    * 获取单位名称
    *
    * @return 单位名称
    */
    public String getObjName(){
        return objName;
    }

    /**
    * 设置单位名称
    * 
    * @param objName 要设置的单位名称
    */
    public void setObjName(String objName){
        this.objName = objName;
    }

    /**
    * 获取分类类型，1-数量  2-重量  3-长度 4-体积
    *
    * @return 分类类型，1-数量  2-重量  3-长度 4-体积
    */
    public Integer getObjType(){
        return objType;
    }

    /**
    * 设置分类类型，1-数量  2-重量  3-长度 4-体积
    * 
    * @param objType 要设置的分类类型，1-数量  2-重量  3-长度 4-体积
    */
    public void setObjType(Integer objType){
        this.objType = objType;
    }

    /**
    * 获取是否有效，1-有效，0-失效
    *
    * @return 是否有效，1-有效，0-失效
    */
    public Integer getIsvalid(){
        return isvalid;
    }

    /**
    * 设置是否有效，1-有效，0-失效
    * 
    * @param isvalid 要设置的是否有效，1-有效，0-失效
    */
    public void setIsvalid(Integer isvalid){
        this.isvalid = isvalid;
    }

    /**
    * 获取备注
    *
    * @return 备注
    */
    public String getMemo(){
        return memo;
    }

    /**
    * 设置备注
    * 
    * @param memo 要设置的备注
    */
    public void setMemo(String memo){
        this.memo = memo;
    }

    /**
    * 获取原requestId
    *
    * @return 原requestId
    */
    public String getRequestId(){
        return requestId;
    }

    /**
    * 设置原requestId
    * 
    * @param requestId 要设置的原requestId
    */
    public void setRequestId(String requestId){
        this.requestId = requestId;
    }

}