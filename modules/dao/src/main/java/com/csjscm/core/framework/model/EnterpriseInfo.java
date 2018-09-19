package com.csjscm.core.framework.model;

import lombok.Data;

import java.util.Date;

@Data
public class EnterpriseInfo {
    private String entNumber;

    private Integer entType;

    private String entName;

    private String registerAddress;

    private String bussinessAddress;

    private String webAddress;

    private String registerMoney;

    private String taxpayerId;

    private Integer purchase;

    private Integer sell;

    private Integer tender;

    private Integer bid;

    private Integer checkState;

    private Integer isvalid;

    private Date createTime;

    private Date editTime;

    private String editUser;

    private Integer isdelete;
}