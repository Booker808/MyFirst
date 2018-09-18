package com.csjscm.core.framework.service.enterprise.dto;

import lombok.Data;

@Data
public class EnterpriseInfoDto {
    private String entNumber;
    private Integer entType;
    private String entName;
    private String legalPerson;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
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
    private Integer checkState;
    private Integer isvalid;
}
