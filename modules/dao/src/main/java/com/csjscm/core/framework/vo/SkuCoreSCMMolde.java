package com.csjscm.core.framework.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 来源sm 新增 平台商品 model
 */
public class SkuCoreSCMMolde {
    @NotBlank(message = "第三级分类编码不能为空")
    private String categoryNo;
    @NotBlank(message = "商品名称不能为空")
    private String productName;
    @NotBlank(message = "品牌名称不能为空")
    private String brandName;
    @NotBlank(message = "规格不能为空")
    private String rule;
    @NotBlank(message = "型号不能为空")
    private String size;
    @NotBlank(message = "最小单位不能为空")
    private String minUint;

    private String mnemonicCode;
    private String ean13Code;

    private String productNo;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMinUint() {
        return minUint;
    }

    public void setMinUint(String minUint) {
        this.minUint = minUint;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    public String getEan13Code() {
        return ean13Code;
    }

    public void setEan13Code(String ean13Code) {
        this.ean13Code = ean13Code;
    }
}
