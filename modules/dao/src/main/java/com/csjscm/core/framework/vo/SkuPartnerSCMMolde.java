package com.csjscm.core.framework.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * 来源sm 新增 供应商商品 model
 */
public class SkuPartnerSCMMolde {
    @NotBlank(message = "供应商编码不能为空")
    private String supplyNo;
    @NotBlank(message = "平台商品编码不能为空")
    private String productNo;
    private String supplyPdNo;
    private BigDecimal refrencePrice;
    private BigDecimal recentEnquiry;

    public String getSupplyNo() {
        return supplyNo;
    }

    public void setSupplyNo(String supplyNo) {
        this.supplyNo = supplyNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getSupplyPdNo() {
        return supplyPdNo;
    }

    public void setSupplyPdNo(String supplyPdNo) {
        this.supplyPdNo = supplyPdNo;
    }

    public BigDecimal getRefrencePrice() {
        return refrencePrice;
    }

    public void setRefrencePrice(BigDecimal refrencePrice) {
        this.refrencePrice = refrencePrice;
    }

    public BigDecimal getRecentEnquiry() {
        return recentEnquiry;
    }

    public void setRecentEnquiry(BigDecimal recentEnquiry) {
        this.recentEnquiry = recentEnquiry;
    }
}
