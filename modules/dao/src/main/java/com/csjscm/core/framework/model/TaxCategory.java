package com.csjscm.core.framework.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxCategory {
    private Integer id;

    private String taxCode;

    private String taxCategoryName;

    private Integer level;

    private String parentCode;

    private BigDecimal taxRate;

    private String description;

    private Integer versionId;
}