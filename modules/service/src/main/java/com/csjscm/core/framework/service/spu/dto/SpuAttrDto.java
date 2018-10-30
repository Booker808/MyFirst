package com.csjscm.core.framework.service.spu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpuAttrDto {
    @ApiModelProperty("属性分组")
    private String group;
    @ApiModelProperty("属性名称")
    private String name;
    @ApiModelProperty("当前值")
    private String currentValue;
}
