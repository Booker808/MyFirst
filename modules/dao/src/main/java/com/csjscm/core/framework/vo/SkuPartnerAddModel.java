package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 保存供应商以及关联的数据model
 */
@Data
public class SkuPartnerAddModel {

    @ApiModelProperty("供应商编码")
    @NotBlank(message = "供应商编码不能为空")
    private String supplyNo;

    @ApiModelProperty("供应商商品编码")
    private String supplyPdNo;

    @ApiModelProperty("供应商商品名称")
    @NotBlank(message = "供应商商品名称不能为空")
    private String supplyPdName;

    @ApiModelProperty("供应商商品规格")
    private String supplyPdRule;

    @ApiModelProperty("供应商商品型号")
    private String supplyPdSize;

    @ApiModelProperty("品牌名")
    @NotBlank(message = "品牌名不能为空")
    private String brandName;

    @ApiModelProperty("品牌Id")
    @NotNull(message = "品牌id不能为空")
    private Integer brandId;

    @ApiModelProperty("参考售价")
    private BigDecimal refrencePrice;

    @ApiModelProperty("最近询价")
    private BigDecimal recentEnquiry;
    @ApiModelProperty("商品编码")
    private String productNo;
    @ApiModelProperty("分类编码")
    private String classCode;
    @ApiModelProperty("分类Id")
    private Integer classId;
    @ApiModelProperty("最小单位")
    private String minUint;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

    @ApiModelProperty("商品条码")
    private String ean13Code;

    private String uomStr;
}
