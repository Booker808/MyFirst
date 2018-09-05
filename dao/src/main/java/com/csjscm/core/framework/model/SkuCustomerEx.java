package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "客户商品对象")
public class SkuCustomerEx extends SkuCustomer {
    @ApiModelProperty("最小库存单位")
    private String minUint;

    @ApiModelProperty("识别码")
    private String identifyingCode;

    @ApiModelProperty("品牌ID")
    private String brandId;

    @ApiModelProperty("品牌名")
    private String brandName;

    @ApiModelProperty(value = "最小分类名称")
    private String className;
}
