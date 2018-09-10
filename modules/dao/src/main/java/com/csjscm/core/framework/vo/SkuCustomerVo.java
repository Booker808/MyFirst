package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;

public class SkuCustomerVo {
    private  String failMessage;
    @ApiModelProperty("商品编码")
    private String productNo;
    @ApiModelProperty("客户商品编码")
    private String customerPdNo;

    @ApiModelProperty("客户商品名")
    private String customerPdName;

    @ApiModelProperty("客户商品规格")
    private String customerPdRule;

    @ApiModelProperty("客户商品型号")
    private String customerPdSize;

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
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

    public String getCustomerPdName() {
        return customerPdName;
    }

    public void setCustomerPdName(String customerPdName) {
        this.customerPdName = customerPdName;
    }

    public String getCustomerPdRule() {
        return customerPdRule;
    }

    public void setCustomerPdRule(String customerPdRule) {
        this.customerPdRule = customerPdRule;
    }

    public String getCustomerPdSize() {
        return customerPdSize;
    }

    public void setCustomerPdSize(String customerPdSize) {
        this.customerPdSize = customerPdSize;
    }
}
