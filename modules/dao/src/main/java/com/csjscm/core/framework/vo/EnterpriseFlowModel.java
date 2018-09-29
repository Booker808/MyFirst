package com.csjscm.core.framework.vo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class EnterpriseFlowModel {
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 1正常流程 2特批流程 3状态变更流程
     */
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

    @NotBlank(message = "taskId不能为空")
    private String taskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public Integer getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(Integer flowNode) {
        this.flowNode = flowNode;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getEntNumber() {
        return entNumber;
    }

    public void setEntNumber(String entNumber) {
        this.entNumber = entNumber;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Integer getCustom() {
        return custom;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
