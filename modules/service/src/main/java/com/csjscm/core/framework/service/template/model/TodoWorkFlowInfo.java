package com.csjscm.core.framework.service.template.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TodoWorkFlowInfo {

    /**
     * 业务参数
     */
    private String bussinessKey;
    /**
     * 申请人
     */
    private String applyer;
    /**
     *申请时间
     */
    private Date applyTime;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    private  String taskName;
    /**
     * 流程实例对象
     */
    private String processInstanceId;
    /**
     * 流程定义ID
     */
    private String processdefid; /**
     * 自定义的业务参数
     */
    Object variables;
    /**
     * 意见对象
     */
    List<Object> suggestList;

}
