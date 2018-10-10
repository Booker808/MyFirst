package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class EnterprisePurchaseTemplateDetailVo extends EnterprisePurchaseTemplateVo{

    @ApiModelProperty("卖方户名")
    private String accountName;

    @ApiModelProperty("卖方开户银行")
    private String bankName;

    @ApiModelProperty("卖方账户")
    private String bankNo;

    @ApiModelProperty("是否提交审核：0仅保存，1保存并提交审核")
    private Integer isSubmit;

    @ApiModelProperty(hidden = true)
    private String createUser;

    @ApiModelProperty(hidden = true)
    private String editUser;
}
