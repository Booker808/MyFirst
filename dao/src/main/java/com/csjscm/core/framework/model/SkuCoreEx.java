package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "商品核心对象")
public class SkuCoreEx extends SkuCore {
    @ApiModelProperty(value = "识别码")
    private String identifyingCode;
    @ApiModelProperty(value = "客户编码")
    private String customerNo;
    @ApiModelProperty(value = "供应商编码")
    private String supplyNo;
    @ApiModelProperty(value = "最小分类名称")
    private String className;
}
