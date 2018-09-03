package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "供应商商品对象")
public class SkuPartnerEx extends SkuPartner {
    @ApiModelProperty("最小库存单位")
    private String minUint;

    @ApiModelProperty("识别码")
    private String identifyingCode;
}
