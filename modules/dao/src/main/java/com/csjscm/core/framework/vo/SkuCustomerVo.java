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

    @ApiModelProperty("二级分类编码")
    private String lv2CategoryNo;

    @ApiModelProperty("一级分类编码")
    private String lv1CategoryNo;

    private String categoryNo;
    private String minUint;
    @ApiModelProperty("也称69码")
    private String ean13Code;

    @ApiModelProperty("助记码")
    private String mnemonicCode;
    @ApiModelProperty("品牌名")
    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLv2CategoryNo() {
        return lv2CategoryNo;
    }

    public void setLv2CategoryNo(String lv2CategoryNo) {
        this.lv2CategoryNo = lv2CategoryNo;
    }

    public String getLv1CategoryNo() {
        return lv1CategoryNo;
    }

    public void setLv1CategoryNo(String lv1CategoryNo) {
        this.lv1CategoryNo = lv1CategoryNo;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getMinUint() {
        return minUint;
    }

    public void setMinUint(String minUint) {
        this.minUint = minUint;
    }

    public String getEan13Code() {
        return ean13Code;
    }

    public void setEan13Code(String ean13Code) {
        this.ean13Code = ean13Code;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

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
