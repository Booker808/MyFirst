package com.csjscm.core.framework.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 供应商审批流程记录实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-28 09:06:02
 */

 public class EnterpriseFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 1正常流程 2特批流程 3状态变更流程
    */
    @NotNull(message = "custom不能为空")
    @Range(min = 1,max = 3,message = "只能输入1,2,3")
    private Integer flowType;

    /**
    * 当前流程节点
    */
    private Integer flowNode;

    /**
    * 备注 意见
    */
    private String opinion;

    /**
    * 供应商编码
    */
    @NotBlank(message = "entNumber不能为空")
    private String entNumber;

    /**
    * 附件地址
    */
    private String attachmentUrl;

    /**
    * 操作人姓名
    */
    private String userName;

    /**
    * 
    */
    private Date createTime;

    /**
    * 
    */
    private Date editTime;

    /**
    * 自定义参数 1 通过 2不通过 3表示开始发起审核
    */
    @NotNull(message = "custom不能为空")
    @Range(min = 1,max = 2,message = "只能输入1,2")
    private Integer custom;

    /**
    * 实例id
    */
    private String instanceId;


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
    * 获取1正常流程 2特批流程 3状态变更流程
    *
    * @return 1正常流程 2特批流程 3状态变更流程
    */
    public Integer getFlowType(){
        return flowType;
    }

    /**
    * 设置1正常流程 2特批流程 3状态变更流程
    * 
    * @param flowType 要设置的1正常流程 2特批流程 3状态变更流程
    */
    public void setFlowType(Integer flowType){
        this.flowType = flowType;
    }

    /**
    * 获取当前流程节点
    *
    * @return 当前流程节点
    */
    public Integer getFlowNode(){
        return flowNode;
    }

    /**
    * 设置当前流程节点
    * 
    * @param flowNode 要设置的当前流程节点
    */
    public void setFlowNode(Integer flowNode){
        this.flowNode = flowNode;
    }

    /**
    * 获取备注 意见
    *
    * @return 备注 意见
    */
    public String getOpinion(){
        return opinion;
    }

    /**
    * 设置备注 意见
    * 
    * @param opinion 要设置的备注 意见
    */
    public void setOpinion(String opinion){
        this.opinion = opinion;
    }

    /**
    * 获取供应商编码
    *
    * @return 供应商编码
    */
    public String getEntNumber(){
        return entNumber;
    }

    /**
    * 设置供应商编码
    * 
    * @param entNumber 要设置的供应商编码
    */
    public void setEntNumber(String entNumber){
        this.entNumber = entNumber;
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
    * 获取操作人姓名
    *
    * @return 操作人姓名
    */
    public String getUserName(){
        return userName;
    }

    /**
    * 设置操作人姓名
    * 
    * @param userName 要设置的操作人姓名
    */
    public void setUserName(String userName){
        this.userName = userName;
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
    * 获取自定义参数 1 通过 2不通过 3表示开始发起审核
    *
    * @return 自定义参数 1 通过 2不通过 3表示开始发起审核
    */
    public Integer getCustom(){
        return custom;
    }

    /**
    * 设置自定义参数 1 通过 2不通过 3表示开始发起审核
    * 
    * @param custom 要设置的自定义参数 1 通过 2不通过 3表示开始发起审核
    */
    public void setCustom(Integer custom){
        this.custom = custom;
    }

    /**
    * 获取实例id
    *
    * @return 实例id
    */
    public String getInstanceId(){
        return instanceId;
    }

    /**
    * 设置实例id
    * 
    * @param instanceId 要设置的实例id
    */
    public void setInstanceId(String instanceId){
        this.instanceId = instanceId;
    }

}