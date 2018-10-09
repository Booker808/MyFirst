package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "供应商商品对象")
public class SkuPartnerEx extends SkuPartner {

    @ApiModelProperty("商品条码")
    private String ean13Code;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

    @ApiModelProperty(value = "最小分类编码")
    private String categoryNo;

    @ApiModelProperty(value = "二级分类编码")
    private String lv2CategoryNo;

    @ApiModelProperty(value = "一级分类编码")
    private String lv1CategoryNo;

    @ApiModelProperty(value = "最小分类编码")
    private String categorySpNo;

    @ApiModelProperty(value = "二级分类编码")
    private String lv2CategorySpNo;

    @ApiModelProperty(value = "一级分类编码")
    private String lv1CategorySpNo;

    @ApiModelProperty(value = "来源")
    private Integer channel;

    @ApiModelProperty(value = "供应商名称")
    private String entName;
}
