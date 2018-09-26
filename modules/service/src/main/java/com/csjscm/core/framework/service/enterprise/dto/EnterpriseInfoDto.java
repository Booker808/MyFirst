package com.csjscm.core.framework.service.enterprise.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class EnterpriseInfoDto {
    @ApiModelProperty("企业编码")
    private String entNumber;

    @ApiModelProperty("企业类型：1供应商2采购商3供应商&采购商")
    private Integer entType;

    @ApiModelProperty("企业名称")
    private String entName;

    @ApiModelProperty("法人")
    private String legalPerson;

    @ApiModelProperty("联系人")
    private String contactName;

    @ApiModelProperty("联系人电话")
    private String contactPhone;

    @ApiModelProperty("联系人邮箱")
    private String contactEmail;

    @ApiModelProperty("注册地址")
    private String registerAddress;

    @ApiModelProperty("营业地址")
    private String bussinessAddress;

    @ApiModelProperty("官网地址")
    private String webAddress;

    @ApiModelProperty("注册资金")
    private String registerMoney;

    @ApiModelProperty("营业执照地址")
    private String businessImg;

    @ApiModelProperty("税务识别号")
    private String taxpayerId;

    @ApiModelProperty("开户银行")
    private String bankName;

    @ApiModelProperty("开户银行账号")
    private String bankNo;

    @ApiModelProperty("采购0未勾选1已勾选")
    private Integer purchase;

    @ApiModelProperty("销售0未勾选1已勾选")
    private Integer sell;

    @ApiModelProperty("招标0未勾选1已勾选")
    private Integer tender;

    @ApiModelProperty("竞标0未勾选1已勾选")
    private Integer bid;

    @ApiModelProperty("核准状态")
    private String checkState;

    @ApiModelProperty("是否启用0未启用1已启用")
    private Integer isvalid;
}
