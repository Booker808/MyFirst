package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BrandMaster implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String brandId;

    private String brandName;

    private String brandPic;

    private String brandInfo;

    private String brandCa;

    private String createUserId;

    private Date createTime;

    private String editUserId;

    private Date editTime;

    private String requestId;

    private Integer categoryId;

    @ApiModelProperty("是否授权：0未授权，1已授权")
    private Integer isAuthorize;
}