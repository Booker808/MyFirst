package com.csjscm.core.framework.model;

import lombok.Data;

import java.util.Date;

@Data
public class EnterpriseStandardTemplate {
    private Integer id;

    private Integer templateType;

    private String templateUrl;

    private Integer enable;

    private String createUser;

    private Date createTime;

    private String editUser;

    private Date editTime;
}