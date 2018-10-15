package com.csjscm.core.framework.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 来源sm 新增 供应商商品 model
 */
public class SkuPartnerSCMMolde {
    @NotBlank(message = "平台商品编码不能为空")
    private String productNo;
    @NotBlank(message = "供应商编码不能为空")
    private String supplyNo;
    @NotBlank(message = "最小单位不能为空")
    private String minUint;
    @NotBlank(message = "供应商商品名称不能为空")
    private String supplyPdName;
    @NotBlank(message = "规格不能为空")
    private String supplyPdRule;
    @NotBlank(message = "型号不能为空")
    private String supplyPdSize;
    @NotBlank(message = "品牌名不能为空")
    private String brandName;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getSupplyNo() {
        return supplyNo;
    }

    public void setSupplyNo(String supplyNo) {
        this.supplyNo = supplyNo;
    }

    public String getMinUint() {
        return minUint;
    }

    public void setMinUint(String minUint) {
        this.minUint = minUint;
    }

    public String getSupplyPdName() {
        return supplyPdName;
    }

    public void setSupplyPdName(String supplyPdName) {
        this.supplyPdName = supplyPdName;
    }

    public String getSupplyPdRule() {
        return supplyPdRule;
    }

    public void setSupplyPdRule(String supplyPdRule) {
        this.supplyPdRule = supplyPdRule;
    }

    public String getSupplyPdSize() {
        return supplyPdSize;
    }

    public void setSupplyPdSize(String supplyPdSize) {
        this.supplyPdSize = supplyPdSize;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
