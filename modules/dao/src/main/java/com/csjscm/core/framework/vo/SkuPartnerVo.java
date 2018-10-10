package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class SkuPartnerVo {
    private  String failMessage;
    @ApiModelProperty("商品编码")
    private String productNo;
    @ApiModelProperty("供应商商品编码")
    private String supplyPdNo;

    @ApiModelProperty("供应商名称")
    private String supplyPdName;

    @ApiModelProperty("供应商商品规格")
    private String supplyPdRule;

    @ApiModelProperty("供应商商品型号")
    private String supplyPdSize;

    @ApiModelProperty("品牌名")
    private String brandName;

    private String free;
    private String categoryNo;
    private String minUint;

    @ApiModelProperty("参考售价")
    private String refrencePrice;

    @ApiModelProperty("最近询价")
    private String recentEnquiry;

    @ApiModelProperty("二级分类编码")
    private String lv2CategoryNo;

    @ApiModelProperty("一级分类编码")
    private String lv1CategoryNo;

    @ApiModelProperty("也称69码")
    private String ean13Code;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

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

    public String getRefrencePrice() {
        return refrencePrice;
    }

    public void setRefrencePrice(String refrencePrice) {
        this.refrencePrice = refrencePrice;
    }

    public String getRecentEnquiry() {
        return recentEnquiry;
    }

    public void setRecentEnquiry(String recentEnquiry) {
        this.recentEnquiry = recentEnquiry;
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

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
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

    public String getSupplyPdNo() {
        return supplyPdNo;
    }

    public void setSupplyPdNo(String supplyPdNo) {
        this.supplyPdNo = supplyPdNo;
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
