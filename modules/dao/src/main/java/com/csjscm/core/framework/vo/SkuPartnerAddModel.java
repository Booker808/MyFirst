package com.csjscm.core.framework.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 保存供应商以及关联的数据model
 */
public class SkuPartnerAddModel {

    @ApiModelProperty("供应商编码")
    @NotBlank(message = "供应商编码不能为空")
    private String supplyNo;

    @ApiModelProperty("供应商商品编码")
    private String supplyPdNo;

    @ApiModelProperty("供应商商品名称")
    @NotBlank(message = "供应商商品名称不能为空")
    private String supplyPdName;

    @ApiModelProperty("供应商商品规格")
    private String supplyPdRule;

    @ApiModelProperty("供应商商品型号")
    private String supplyPdSize;

    @ApiModelProperty("品牌名")
    @NotBlank(message = "品牌名不能为空")
    private String brandName;

    @ApiModelProperty("品牌Id")
    @NotNull(message = "品牌id不能为空")
    private Integer brandId;

    @ApiModelProperty("参考售价")
    private BigDecimal refrencePrice;

    @ApiModelProperty("最近询价")
    private BigDecimal recentEnquiry;
    @ApiModelProperty("商品编码")
    private String productNo;
    @ApiModelProperty("分类编码")
    private String classCode;
    @ApiModelProperty("分类Id")
    private Integer classId;
    @ApiModelProperty("最小单位")
    private String minUint;

    private String uomStr;


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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getMinUint() {
        return minUint;
    }

    public void setMinUint(String minUint) {
        this.minUint = minUint;
    }

    public String getUomStr() {
        return uomStr;
    }

    public void setUomStr(String uomStr) {
        this.uomStr = uomStr;
    }
}
