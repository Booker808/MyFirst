package com.csjscm.core.framework.model;

import com.csjscm.core.framework.annotation.Creator;
import com.csjscm.core.framework.annotation.Editor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Spu {
    private String stdProductNo;

    private String productName;

    private Integer lv1CategoryId;

    private String lv1CategoryNo;

    private Integer lv2CategoryId;

    private String lv2CategoryNo;

    private Integer categoryId;

    private String categoryNo;

    private Integer lv1CategorySpId;

    private String lv1CategorySpNo;

    private Integer lv2CategorySpId;

    private String lv2CategorySpNo;

    private Integer categorySpId;

    private String categorySpNo;

    private String mnemonicCode;

    private BigDecimal guidancePrice;

    private String pageTitle;

    private String distributionCondition;

    private Integer isvalidate;

    private String brandId;

    @Creator
    private String createUserId;

    private Date createTime;

    @Editor
    private String editUserId;

    private Date editTime;

    private String cdf1;

    private String cdf2;

    private String cdf3;

    private String cdf4;

    private String cdf5;

    private String cdf6;

    private String cdf7;

    private String cdf8;

    private String cdf9;

    private String cdf10;

    private String cdf11;

    private String cdf12;

    private String cdf13;

    private String cdf14;

    private String cdf15;

    private String cdf16;

    private String cdf17;

    private String cdf18;

    private String cdf19;

    private String cdf20;

    private String cdf21;

    private String cdf22;

    private String cdf23;

    private String cdf24;

    private String cdf25;

    private String cdf26;

    private String cdf27;

    private String cdf28;

    private String cdf29;

    private String cdf30;

    private String ruleSize;

    private String minUnit;

    private Double weight;

    private Double height;

    private Double width;

    private Double length;

    private Date shelfTime;

    private String pageMainPic;

    private String productInfo;
}