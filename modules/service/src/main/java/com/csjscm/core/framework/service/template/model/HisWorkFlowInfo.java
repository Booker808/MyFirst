package com.csjscm.core.framework.service.template.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class HisWorkFlowInfo {

    @ApiModelProperty("任务ID")
    private String taskId;

    @ApiModelProperty("节点")
    private String taskName;

    @ApiModelProperty("示例ID")
    private String processInstanceId;

    @ApiModelProperty("操作人")
    private String assignee;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("审批意见")
    private String suggestion;

    @ApiModelProperty(hidden = true)
    private String processDefinitionId;

    private String startUserId;

    @ApiModelProperty("流程状态（0：未开始，1：过程中，2：已结束）")
    private Integer endFlag;

}
