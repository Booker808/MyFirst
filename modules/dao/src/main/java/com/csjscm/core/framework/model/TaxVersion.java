package com.csjscm.core.framework.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaxVersion {
    private Integer id;

    private String version;

    private Integer enable;

    private String createUser;

    private Date createTime;

    private String editUser;

    private Date editTime;
}