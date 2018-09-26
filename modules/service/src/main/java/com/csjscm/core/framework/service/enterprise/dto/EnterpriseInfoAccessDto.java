package com.csjscm.core.framework.service.enterprise.dto;

import com.csjscm.core.framework.model.EnterpriseCategory;
import com.csjscm.core.framework.model.EnterpriseProtocol;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class EnterpriseInfoAccessDto extends EnterpriseInfoDto {
    @ApiModelProperty("其他信息")
    private EnterpriseCategory enterpriseCategory;

    @ApiModelProperty("框架协议信息")
    private EnterpriseProtocol enterpriseProtocol;
}
