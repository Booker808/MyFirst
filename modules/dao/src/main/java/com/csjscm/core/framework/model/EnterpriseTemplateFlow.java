package com.csjscm.core.framework.model;

import lombok.Data;

import java.util.Date;

@Data
public class EnterpriseTemplateFlow {
    private Integer id;

    private Integer flowType;

    private Integer flowNode;

    private String opinion;

    private String entNumber;

    private Integer pTemplateId;

    private String attachmentUrl;

    private String userName;

    private Date createTime;

    private Date editTime;

    private Integer custom;

    private String instanceId;
}