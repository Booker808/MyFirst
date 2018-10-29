package com.csjscm.core.framework.service.spu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpuAttrDetailDto extends SpuAttrDto {
    @ApiModelProperty("属性值，若为多选，则以,分隔")
    private String value;
    @ApiModelProperty("显示类型")
    private String showType;
}
