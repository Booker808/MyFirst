package com.csjscm.core.framework.service.spu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpSkuCoreDto {
    @ApiModelProperty("sku编码")
    private String productNo;

    @ApiModelProperty("spu编码")
    private String stdProductNo;

    private String productName;

    @ApiModelProperty("销售价")
    private BigDecimal productPrice;

    @ApiModelProperty("是否可用，0可用1不可用")
    private Integer isvalidate;

    @ApiModelProperty("商品图片")
    private String productPic;

    @ApiModelProperty("条形码")
    private String ean13Code;

    @ApiModelProperty("参考成本价")
    private BigDecimal refrencePrice;

    @ApiModelProperty("规格属性")
    private String rule;;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("重量")
    private Double weight;
}
