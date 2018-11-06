package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EnterpriseInfo {
    private String entNumber;

    private Integer entType;

    private String entName;

    private String registerAddress;

    private String bussinessAddress;

    private String webAddress;

    private String registerMoney;

    private String taxpayerId;

    private Integer purchase;

    private Integer sell;

    private Integer tender;

    private Integer bid;

    private String checkState;
    private String requestId;

    private Integer isvalid;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Date editTime;

    @ApiModelProperty(hidden = true)
    private String editUser;

    @ApiModelProperty(hidden = true)
    private Integer isdelete;


    private  Integer  channel;

    @ApiModelProperty("业务负责人")
    private String businessPrincipal;

    @ApiModelProperty("采购负责人")
    private String purchasePrincipal;
}