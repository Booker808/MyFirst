package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel
public class EnterprisePurchaseTemplateVo {
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("企业编码")
    @NotBlank(message = "entNumber不能为空")
    private String entNumber;

    @ApiModelProperty("企业名称（保存时只用企业编码即可）")
    private String entName;

    @ApiModelProperty("买方指定地点")
    @NotBlank(message = "address不能为空")
    private String address;

    @ApiModelProperty("结算方式1票到货到付款2预付款")
    @NotNull(message = "payType不能为空")
    private Integer payType;

    @ApiModelProperty("预付比例%")
    private BigDecimal prepayRate;

    @ApiModelProperty("提货比例%")
    private BigDecimal pickRate;

    @ApiModelProperty("质保金比例%")
    private BigDecimal warrantyRate;

    @ApiModelProperty("支付/付款工作日")
    private Integer payDate;

    @ApiModelProperty("开票工作日")
    private Integer ticketDate;

    @ApiModelProperty("模板类型：1:A类,2:B类,3:C类")
    @NotNull(message = "templateType不能为空")
    private Integer templateType;

    @ApiModelProperty("模板地址")
    private String templateUrl;

    @ApiModelProperty("质保期（年）")
    private Integer warrantyDate;

    @ApiModelProperty("是否启用")
    @NotNull(message = "enable不能为空")
    private Integer enable;

    @ApiModelProperty("（暂废弃）审批状态(保存时此项为空) 取工作流接口返回值，1待申请人提交，2待采购部负责人审批，3待法务专员审批，4待执行总经理审批5归档6已驳回申请人7已作废")
    private Integer checkStatus;

    @ApiModelProperty("实例ID，审核接口会用到")
    private String instanceId;

    @ApiModelProperty("审核描述")
    private String checkDescription;
}
