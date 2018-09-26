package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class EnterpriseAttachment {
    private Integer id;

    private String entNumber;

    private String attachmentName;

    private Integer attachmentType;

    private String attachmentInfo;

    private String attachmentUrl;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Date editTime;

    @ApiModelProperty(hidden = true)
    private String editUser;

    @ApiModelProperty(hidden = true)
    private Integer isdelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntNumber() {
        return entNumber;
    }

    public void setEntNumber(String entNumber) {
        this.entNumber = entNumber;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Integer getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachmentInfo() {
        return attachmentInfo;
    }

    public void setAttachmentInfo(String attachmentInfo) {
        this.attachmentInfo = attachmentInfo;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
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

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}