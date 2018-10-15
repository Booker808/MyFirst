package com.csjscm.core.framework.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 来源scm 新增客户  model
 */
public class SkuCustomerSCMMolde {
    @NotBlank(message = "客户编码不能为空")
    private String customerNo;
    @NotBlank(message = "品牌名称不能为空")
    private String brandName;
    @NotBlank(message = "最小单位不能为空")
    private String minUint;
    @NotBlank(message = "规格不能为空")
    private String customerPdRule;
    @NotBlank(message = "型号不能为空")
    private String customerPdSize;
    @NotBlank(message = "客户商品名不能为空")
    private String customerPdName;
    @NotBlank(message = "细分类编码不能为空")
    private String categoryNo;
    @NotBlank(message = "outId不能为空")
    private  String outId;

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMinUint() {
        return minUint;
    }

    public void setMinUint(String minUint) {
        this.minUint = minUint;
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

    public String getCustomerPdName() {
        return customerPdName;
    }

    public void setCustomerPdName(String customerPdName) {
        this.customerPdName = customerPdName;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }
}
