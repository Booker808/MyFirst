package com.csjscm.core.framework.service.template.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CheckTaskVo {
    @ApiModelProperty("模板ID")
    @NotNull
    private Integer templateId;

    @ApiModelProperty("任务ID")
    @NotBlank
    private String taskId;

    @ApiModelProperty("审核状态（true通过false驳回）")
    @NotNull
    private Boolean suggestionStatus;

    @ApiModelProperty("审批意见")
    private String opinion;
}
