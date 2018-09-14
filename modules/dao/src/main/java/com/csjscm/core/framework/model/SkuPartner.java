package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkuPartner {
    private Integer id;
    private  String uuid;
    @ApiModelProperty("商品编码")
    @NotBlank(message = "商品编码不能为空")
    private String productNo;

    @ApiModelProperty("供应商编码")
    @NotBlank(message = "供应商编码不能为空")
    private String supplyNo;

    @ApiModelProperty("供应商商品编码")
    @NotBlank(message = "供应商商品编码不能为空")
    private String supplyPdNo;

    @ApiModelProperty("供应商名称")
    @NotBlank(message = "供应商商品名称不能为空")
    private String supplyPdName;

    @ApiModelProperty("供应商商品规格")
    private String supplyPdRule;

    @ApiModelProperty("供应商商品型号")
    private String supplyPdSize;

    @ApiModelProperty("品牌ID")
    @NotBlank(message = "品牌ID不能为空")
    private String brandId;

    @ApiModelProperty("品牌名")
    @NotBlank(message = "品牌名不能为空")
    private String brandName;

    @ApiModelProperty("参考售价")
    private BigDecimal refrencePrice;

    @ApiModelProperty("最近询价")
    private BigDecimal recentEnquiry;

    @ApiModelProperty("创建人ID")
    private String createUserId;

    @ApiModelProperty("创建时间戳")
    private Date createTime;

    @ApiModelProperty("编辑人ID")
    private String editUserId;

    @ApiModelProperty("编辑时间戳")
    private Date editTime;

    private String udf1;

    private String udf2;

    private String udf3;

    private String udf4;

    private String udf5;

    private String udf6;

    private String udf7;

    private String udf8;

    private String udf9;

    private String udf10;

    private String udf11;

    private String udf12;

    private String udf13;

    private String udf14;

    private String udf15;

    private String udf16;

    private String udf17;

    private String udf18;

    private String udf19;

    private String udf20;
}