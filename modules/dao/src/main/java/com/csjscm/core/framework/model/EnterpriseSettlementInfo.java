package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 结算信息表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:00:44
 */

 public class EnterpriseSettlementInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 付款条件 1：货到票到
    */
    private Integer paymentClause;

    /**
    * 天数
    */
    private Integer days;

    /**
    * 结算方式 1:银行转账
    */
    private Integer clearingType;

    /**
    * 预付比例
    */
    private String advanceRatio;

    /**
    * 
    */
    private Date createTime;

    /**
    * 
    */
    private Date editTime;

    /**
    * 企业编码
    */
    private String entNumber;


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
    * 获取付款条件 1：货到票到
    *
    * @return 付款条件 1：货到票到
    */
    public Integer getPaymentClause(){
        return paymentClause;
    }

    /**
    * 设置付款条件 1：货到票到
    * 
    * @param paymentClause 要设置的付款条件 1：货到票到
    */
    public void setPaymentClause(Integer paymentClause){
        this.paymentClause = paymentClause;
    }

    /**
    * 获取天数
    *
    * @return 天数
    */
    public Integer getDays(){
        return days;
    }

    /**
    * 设置天数
    * 
    * @param days 要设置的天数
    */
    public void setDays(Integer days){
        this.days = days;
    }

    /**
    * 获取结算方式 1:银行转账
    *
    * @return 结算方式 1:银行转账
    */
    public Integer getClearingType(){
        return clearingType;
    }

    /**
    * 设置结算方式 1:银行转账
    * 
    * @param clearingType 要设置的结算方式 1:银行转账
    */
    public void setClearingType(Integer clearingType){
        this.clearingType = clearingType;
    }

    /**
    * 获取预付比例
    *
    * @return 预付比例
    */
    public String getAdvanceRatio(){
        return advanceRatio;
    }

    /**
    * 设置预付比例
    * 
    * @param advanceRatio 要设置的预付比例
    */
    public void setAdvanceRatio(String advanceRatio){
        this.advanceRatio = advanceRatio;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置
    * 
    * @param createTime 要设置的
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getEditTime(){
        return editTime;
    }

    /**
    * 设置
    * 
    * @param editTime 要设置的
    */
    public void setEditTime(Date editTime){
        this.editTime = editTime;
    }

    /**
    * 获取企业编码
    *
    * @return 企业编码
    */
    public String getEntNumber(){
        return entNumber;
    }

    /**
    * 设置企业编码
    * 
    * @param entNumber 要设置的企业编码
    */
    public void setEntNumber(String entNumber){
        this.entNumber = entNumber;
    }

}