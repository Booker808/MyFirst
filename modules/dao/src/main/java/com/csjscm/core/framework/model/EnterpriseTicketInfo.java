package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 开票信息表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:03:11
 */

 public class EnterpriseTicketInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 纳税人识别号
    */
    private String taxpayerId;

    /**
    * 开户银行
    */
    private String bankName;

    /**
    * 开户银行账号
    */
    private String bankCardNo;

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
    * @param
    */
    public void setId(Integer id){
        this.id = id;
    }

    /**
    * 获取纳税人识别号
    *
    * @return 纳税人识别号
    */
    public String getTaxpayerId(){
        return taxpayerId;
    }

    /**
    * 设置纳税人识别号
    * 
    * @param taxpayerId 要设置的纳税人识别号
    */
    public void setTaxpayerId(String taxpayerId){
        this.taxpayerId = taxpayerId;
    }

    /**
    * 获取开户银行
    *
    * @return 开户银行
    */
    public String getBankName(){
        return bankName;
    }

    /**
    * 设置开户银行
    * 
    * @param bankName 要设置的开户银行
    */
    public void setBankName(String bankName){
        this.bankName = bankName;
    }

    /**
    * 获取开户银行账号
    *
    * @return 开户银行账号
    */
    public String getBankCardNo(){
        return bankCardNo;
    }

    /**
    * 设置开户银行账号
    * 
    * @param bankCardNo 要设置的开户银行账号
    */
    public void setBankCardNo(String bankCardNo){
        this.bankCardNo = bankCardNo;
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