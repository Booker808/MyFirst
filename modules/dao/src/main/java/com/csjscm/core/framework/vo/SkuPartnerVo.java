package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;

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
