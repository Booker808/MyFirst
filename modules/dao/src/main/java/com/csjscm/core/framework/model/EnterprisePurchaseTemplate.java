package com.csjscm.core.framework.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EnterprisePurchaseTemplate {
    private Integer id;

    private String entNumber;

    private String address;

    private Integer payType;

    private BigDecimal prepayRate;

    private BigDecimal pickRate;

    private BigDecimal warrantyRate;

    private Integer payDate;

    private Integer ticketDate;

    private Integer templateType;

    private String templateUrl;

    private Integer warrantyDate;

    private Integer enable;

    private Integer checkStatus;

    private String createUser;

    private Date createTime;

    private String editUser;

    private Date editTime;
}