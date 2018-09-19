package com.csjscm.core.framework.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class EnterpriseInfoSPModel {
    @NotNull(message = "entType不能为空")
    private Integer entType;
    @NotBlank(message = "entName不能为空")
    @Size(
            min = 1,
            max = 20,
            message = "legalPerson长度最大20"
    )
    private String entName;
    @Size(
            min = 0,
            max = 4,
            message = "legalPerson长度最大4"
    )
    private String legalPerson;
    @NotBlank(message = "contactName不能为空")
    private String contactName;
    @NotBlank(message = "contactPhone不能为空")
    private String contactPhone;
    private String contactEmail;
    @NotBlank(message = "registerAddress不能为空")
    private String registerAddress;
    private String bussinessAddress;
    private String webAddress;
    private String registerMoney;

    private String businessImg;

    private String taxpayerId;
    private String bankName;
    private String bankNo;
    private Integer purchase;
    private Integer sell;
    private Integer tender;
    private Integer bid;
}
