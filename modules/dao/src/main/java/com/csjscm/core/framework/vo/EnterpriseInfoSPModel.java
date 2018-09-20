package com.csjscm.core.framework.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
public class EnterpriseInfoSPModel {
    @NotNull(message = "entType不能为空")
    @Range(min = 1, max = 3, message = "entType企业类型有误,只能输入1、2、3")
    private Integer entType;
    @NotBlank(message = "entName不能为空")
    @Size(
            min = 1,
            max = 20,
            message = "entName长度最大20"
    )
    private String entName;
    @Size(
            min = 0,
            max = 20,
            message = "legalPerson长度最大20"
    )
    private String legalPerson;
    @NotBlank(message = "contactName不能为空")
    @Size(
            min = 0,
            max = 20,
            message = "contactName长度最大20"
    )
    private String contactName;
    @NotBlank(message = "contactPhone不能为空")
    @Size(
            min = 0,
            max = 20,
            message = "contactPhone长度最大20"
    )
    private String contactPhone;
    @Pattern(message = "邮箱有误",regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
    private String contactEmail;
    @NotBlank(message = "registerAddress不能为空")
    private String registerAddress;
    private String bussinessAddress;
    private String webAddress;
    @Size(
            min = 0,
            max = 10,
            message = "registerMoney长度最大10"
    )
    private String registerMoney;

    private String businessImg;

    private String taxpayerId;
    private String bankName;
    private String bankNo;
    @Range(min = 0, max = 1, message = "purchase只能输入0或者1")
    private Integer purchase;
    @Range(min = 0, max = 1, message = "sell只能输入0或者1")
    private Integer sell;
    @Range(min = 0, max = 1, message = "tender只能输入0或者1")
    private Integer tender;
    @Range(min = 0, max = 1, message = "bid只能输入0或者1")
    private Integer bid;

}
