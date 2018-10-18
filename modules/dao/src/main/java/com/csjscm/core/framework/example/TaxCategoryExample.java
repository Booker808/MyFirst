package com.csjscm.core.framework.example;

import lombok.Data;

@Data
public class TaxCategoryExample {
    private Integer versionId;
    private String taxCategoryName;
    private String taxCode;
    private String parentCode;
}
