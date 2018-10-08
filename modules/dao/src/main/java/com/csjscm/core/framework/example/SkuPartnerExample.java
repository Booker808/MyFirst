package com.csjscm.core.framework.example;

import lombok.Data;

@Data
public class SkuPartnerExample {
    private String productNo;
    private String supplyNo;
    private String supplyPdName;
    private Integer brandId;
    private String brandName;
    private String supplyPdSize;
    private Integer lv1CategoryId;
    private Integer lv2CategoryId;
    private Integer categoryId;
}
