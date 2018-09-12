package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * 对外接口保存供应商的数据model
 */
public class SkuPartnerModel {

    @ApiModelProperty("供应商编码")
    @NotBlank(message = "供应商编码不能为空")
    private String supplyNo;

    @ApiModelProperty("供应商商品编码")
    @NotBlank(message = "供应商商品编码不能为空")
    private String supplyPdNo;

    @ApiModelProperty("供应商名称")
    @NotBlank(message = "供应商商品名称不能为空")
    private String supplyPdName;

    @ApiModelProperty("供应商商品规格")
    private String supplyPdRule;

    @ApiModelProperty("供应商商品型号")
    private String supplyPdSize;

    @ApiModelProperty("品牌名")
    @NotBlank(message = "品牌名不能为空")
    private String brandName;

    @ApiModelProperty("参考售价")
    private BigDecimal refrencePrice;

    @ApiModelProperty("最近询价")
    private BigDecimal recentEnquiry;

    @ApiModelProperty("商城分类编码")
    private  String classCode;

    public String getSupplyNo() {
        return supplyNo;
    }

    public void setSupplyNo(String supplyNo) {
        this.supplyNo = supplyNo;
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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
