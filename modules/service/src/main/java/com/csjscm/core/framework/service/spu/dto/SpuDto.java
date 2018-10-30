package com.csjscm.core.framework.service.spu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpuDto {
    @ApiModelProperty("spu编码")
    private String stdProductNo;

    @ApiModelProperty("商品名")
    private String productName;

    @ApiModelProperty("平台商品1级分类ID")
    private Integer lv1CategoryId;

    @ApiModelProperty("平台商品1级分类编码")
    private String lv1CategoryNo;

    @ApiModelProperty("平台商品2级分类ID")
    private Integer lv2CategoryId;

    @ApiModelProperty("平台商品2级分类编码")
    private String lv2CategoryNo;

    @ApiModelProperty("平台商品3级分类ID")
    private Integer categoryId;

    @ApiModelProperty("平台商品3级分类编码")
    private String categoryNo;

    @ApiModelProperty("商城商品1级分类ID")
    private Integer lv1CategorySpId;

    @ApiModelProperty("商城商品1级分类编码")
    private String lv1CategorySpNo;

    @ApiModelProperty("商城商品2级分类ID")
    private Integer lv2CategorySpId;

    @ApiModelProperty("商城商品2级分类编码")
    private String lv2CategorySpNo;

    @ApiModelProperty("商城商品3级分类ID")
    private Integer categorySpId;

    @ApiModelProperty("商城商品3级分类编码")
    private String categorySpNo;

    @ApiModelProperty("商品简码")
    private String mnemonicCode;

    @ApiModelProperty("指导价")
    private BigDecimal guidancePrice;

    @ApiModelProperty("商品标题")
    private String pageTitle;

    @ApiModelProperty("商品详情")
    private String distributionCondition;

    @ApiModelProperty("上架：0上架中，1下架中")
    private Integer isvalidate;

    @ApiModelProperty("品牌ID")
    private String brandId;

    private String createUserId;

    private Date createTime;

    private String editUserId;

    private Date editTime;

    private String cdf1;

    private String cdf2;

    private String cdf3;

    private String cdf4;

    private String cdf5;

    private String cdf6;

    private String cdf7;

    private String cdf8;

    private String cdf9;

    private String cdf10;

    private String cdf11;

    private String cdf12;

    private String cdf13;

    private String cdf14;

    private String cdf15;

    private String cdf16;

    private String cdf17;

    private String cdf18;

    private String cdf19;

    private String cdf20;

    private String cdf21;

    private String cdf22;

    private String cdf23;

    private String cdf24;

    private String cdf25;

    private String cdf26;

    private String cdf27;

    private String cdf28;

    private String cdf29;

    private String cdf30;

    @ApiModelProperty("规格型号")
    private String ruleSize;

    @ApiModelProperty("最小单位")
    private String minUnit;

    @ApiModelProperty("商品重量")
    private Double weight;

    @ApiModelProperty("商品高度")
    private Double height;

    @ApiModelProperty("商品宽度")
    private Double width;

    @ApiModelProperty("商品长度")
    private Double length;

    @ApiModelProperty("上架时间")
    private Date shelfTime;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("商品主图")
    private String pageMainPic;

    @ApiModelProperty("商品信息")
    private String productInfo;
}
