package com.csjscm.core.framework.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class SkuCore {
    @ApiModelProperty("商品编码")
    private String productNo;

    @ApiModelProperty("标准商品编码")
    private String stdProductNo;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty("分类ID")
    private Integer categoryId;

    @ApiModelProperty("分类编码")
    private String categoryNo;

    @ApiModelProperty("是否有效0：有效，1无效")
    private Integer isvalidate;

    @ApiModelProperty("核准状况(1:已核准;2:待核准;3:不许交易)")
    private Integer approvalStatus;

    @ApiModelProperty("来源渠道（1：手动新增；2：导入；3：来自商城）")
    private Integer channel;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("品牌ID")
    private Integer brandId;

    @ApiModelProperty("包装规格ID")
    private Integer unitId;

    @ApiModelProperty("最小库存单位")
    private String minUint;

    @ApiModelProperty("商品图片")
    private String productPic;

    @ApiModelProperty("也称69码")
    private String ean13Code;

    @ApiModelProperty("助记码")
    private String mnemonicCode;

    @ApiModelProperty("开票类型")
    private String invoiceType;

    @ApiModelProperty("税则码")
    private String txtCode;

    @ApiModelProperty("规格")
    private String rule;

    @ApiModelProperty("型号")
    private String size;

    @ApiModelProperty("创建用户ID")
    private String createUserId;

    @ApiModelProperty("创建时间戳")
    private Date createTime;

    @ApiModelProperty("编辑用户ID")
    private String editUserId;

    @ApiModelProperty("编辑时间戳")
    private Date editTime;

    private String udf1;

    private String udf2;

    private String udf3;

    private String udf4;

    private String udf5;

    private String udf6;

    private String udf7;

    private String udf8;

    private String udf9;

    private String udf10;

    private String suf1;

    private String suf2;

    private String suf3;

    private String suf4;

    private String suf5;

    private String suf6;

    private String suf7;

    private String suf8;

    private String suf9;

    private String suf10;

    @ApiModelProperty(hidden = true)
    private String requestId;

    @ApiModelProperty(hidden = true)
    private String categoryIdOld;

    @ApiModelProperty(hidden = true)
    private String createUserIdOld;

    @ApiModelProperty(hidden = true)
    private String editUserIdOld;

    @ApiModelProperty(hidden = true)
    private String approvalStatusOld;

    @ApiModelProperty(hidden = true)
    private String brandIdOld;

    @ApiModelProperty(hidden = true)
    private String minUintOld;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getStdProductNo() {
        return stdProductNo;
    }

    public void setStdProductNo(String stdProductNo) {
        this.stdProductNo = stdProductNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public Integer getIsvalidate() {
        return isvalidate;
    }

    public void setIsvalidate(Integer isvalidate) {
        this.isvalidate = isvalidate;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getMinUint() {
        return minUint;
    }

    public void setMinUint(String minUint) {
        this.minUint = minUint;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
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

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getTxtCode() {
        return txtCode;
    }

    public void setTxtCode(String txtCode) {
        this.txtCode = txtCode;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditUserId() {
        return editUserId;
    }

    public void setEditUserId(String editUserId) {
        this.editUserId = editUserId;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public String getUdf4() {
        return udf4;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    public String getUdf6() {
        return udf6;
    }

    public void setUdf6(String udf6) {
        this.udf6 = udf6;
    }

    public String getUdf7() {
        return udf7;
    }

    public void setUdf7(String udf7) {
        this.udf7 = udf7;
    }

    public String getUdf8() {
        return udf8;
    }

    public void setUdf8(String udf8) {
        this.udf8 = udf8;
    }

    public String getUdf9() {
        return udf9;
    }

    public void setUdf9(String udf9) {
        this.udf9 = udf9;
    }

    public String getUdf10() {
        return udf10;
    }

    public void setUdf10(String udf10) {
        this.udf10 = udf10;
    }

    public String getSuf1() {
        return suf1;
    }

    public void setSuf1(String suf1) {
        this.suf1 = suf1;
    }

    public String getSuf2() {
        return suf2;
    }

    public void setSuf2(String suf2) {
        this.suf2 = suf2;
    }

    public String getSuf3() {
        return suf3;
    }

    public void setSuf3(String suf3) {
        this.suf3 = suf3;
    }

    public String getSuf4() {
        return suf4;
    }

    public void setSuf4(String suf4) {
        this.suf4 = suf4;
    }

    public String getSuf5() {
        return suf5;
    }

    public void setSuf5(String suf5) {
        this.suf5 = suf5;
    }

    public String getSuf6() {
        return suf6;
    }

    public void setSuf6(String suf6) {
        this.suf6 = suf6;
    }

    public String getSuf7() {
        return suf7;
    }

    public void setSuf7(String suf7) {
        this.suf7 = suf7;
    }

    public String getSuf8() {
        return suf8;
    }

    public void setSuf8(String suf8) {
        this.suf8 = suf8;
    }

    public String getSuf9() {
        return suf9;
    }

    public void setSuf9(String suf9) {
        this.suf9 = suf9;
    }

    public String getSuf10() {
        return suf10;
    }

    public void setSuf10(String suf10) {
        this.suf10 = suf10;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCategoryIdOld() {
        return categoryIdOld;
    }

    public void setCategoryIdOld(String categoryIdOld) {
        this.categoryIdOld = categoryIdOld;
    }

    public String getCreateUserIdOld() {
        return createUserIdOld;
    }

    public void setCreateUserIdOld(String createUserIdOld) {
        this.createUserIdOld = createUserIdOld;
    }

    public String getEditUserIdOld() {
        return editUserIdOld;
    }

    public void setEditUserIdOld(String editUserIdOld) {
        this.editUserIdOld = editUserIdOld;
    }

    public String getApprovalStatusOld() {
        return approvalStatusOld;
    }

    public void setApprovalStatusOld(String approvalStatusOld) {
        this.approvalStatusOld = approvalStatusOld;
    }

    public String getBrandIdOld() {
        return brandIdOld;
    }

    public void setBrandIdOld(String brandIdOld) {
        this.brandIdOld = brandIdOld;
    }

    public String getMinUintOld() {
        return minUintOld;
    }

    public void setMinUintOld(String minUintOld) {
        this.minUintOld = minUintOld;
    }
}