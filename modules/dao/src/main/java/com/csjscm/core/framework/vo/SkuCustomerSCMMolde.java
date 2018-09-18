package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * 来源scm 新增客户  model
 */
public class SkuCustomerSCMMolde {
    @NotBlank(message = "客户编码不能为空")
    private String customerNo;
    @NotBlank(message = "平台商品编码不能为空")
    private String productNo;
    private String customerPdNo;
    @ApiModelProperty("参考售价")
    private BigDecimal referencePrice;
    @ApiModelProperty("近期报价")
    private BigDecimal recentQuotation;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getCustomerPdNo() {
        return customerPdNo;
    }

    public void setCustomerPdNo(String customerPdNo) {
        this.customerPdNo = customerPdNo;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public BigDecimal getRecentQuotation() {
        return recentQuotation;
    }

    public void setRecentQuotation(BigDecimal recentQuotation) {
        this.recentQuotation = recentQuotation;
    }
}
