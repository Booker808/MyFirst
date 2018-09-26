package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 框架协议信息实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-26 08:40:38
 */

 public class EnterpriseProtocol implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    private String entNumber;

    /**
    * 合同类型 1框架协议 2其他/无
    */
    private Integer type;

    /**
    * 合同编号
    */
    private String number;

    /**
    * 我方合同主体
    */
    private String mainContract;

    /**
    * 合作有效期开始时间
    */
    private Date startTime;

    /**
    * 合作有效期结束时间
    */
    private Date endTime;

    /**
    * 附件地址
    */
    private String attachmentUrl;

    /**
    * 
    */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
    * 
    */
    @ApiModelProperty(hidden = true)
    private Date editTime;


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
    * 获取合同类型 1框架协议 2其他/无
    *
    * @return 合同类型 1框架协议 2其他/无
    */
    public Integer getType(){
        return type;
    }

    /**
    * 设置合同类型 1框架协议 2其他/无
    * 
    * @param type 要设置的合同类型 1框架协议 2其他/无
    */
    public void setType(Integer type){
        this.type = type;
    }

    /**
    * 获取合同编号
    *
    * @return 合同编号
    */
    public String getNumber(){
        return number;
    }

    /**
    * 设置合同编号
    * 
    * @param number 要设置的合同编号
    */
    public void setNumber(String number){
        this.number = number;
    }

    /**
    * 获取我方合同主体
    *
    * @return 我方合同主体
    */
    public String getMainContract(){
        return mainContract;
    }

    /**
    * 设置我方合同主体
    * 
    * @param mainContract 要设置的我方合同主体
    */
    public void setMainContract(String mainContract){
        this.mainContract = mainContract;
    }

    /**
    * 获取合作有效期开始时间
    *
    * @return 合作有效期开始时间
    */
    public Date getStartTime(){
        return startTime;
    }

    /**
    * 设置合作有效期开始时间
    * 
    * @param startTime 要设置的合作有效期开始时间
    */
    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    /**
    * 获取合作有效期结束时间
    *
    * @return 合作有效期结束时间
    */
    public Date getEndTime(){
        return endTime;
    }

    /**
    * 设置合作有效期结束时间
    * 
    * @param endTime 要设置的合作有效期结束时间
    */
    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    /**
    * 获取附件地址
    *
    * @return 附件地址
    */
    public String getAttachmentUrl(){
        return attachmentUrl;
    }

    /**
    * 设置附件地址
    * 
    * @param attachmentUrl 要设置的附件地址
    */
    public void setAttachmentUrl(String attachmentUrl){
        this.attachmentUrl = attachmentUrl;
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

    public String getEntNumber() {
        return entNumber;
    }

    public void setEntNumber(String entNumber) {
        this.entNumber = entNumber;
    }
}