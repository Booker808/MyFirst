package com.csjscm.core.framework.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 保存，编辑企业会员的数据model
 */
public class EnterpriseMemberModel {
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 企业编号
     */
    private String entNumber;

    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称")
    private String entName;

    /**
     * 法人
     */
    @NotBlank(message = "法人")
    private String legalPerson;

    /**
     * 注册地址
     */

    private String registerAddress;

    /**
     * 营业地址
     */
    private String businessAddress;

    /**
     * 官网地址
     */
    private String webAddress;

    /**
     * 营业执照图片路径
     */
    private String businessImg;

    /**
     * 核准状态 1备用
     */
    private Integer checkState;

    /**
     * 交易类型 1供应商  2采购商 3供应商&采购商
     */
    private Integer tradeType;

    /**
     * 供应商类型 1标准型  2战略型  3独家/客户指定型
     */
    private Integer partnerType;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系人电话
     */
    private String linkmanPhone;

    /**
     * 注册资本(万)
     */
    private String registerMoney;

    /**
     * 默认仓库
     */
    private String defaultEntrepot;

    /**
     * 已开通业务-采购  0：未勾选  1：已勾选
     */
    private Integer purchase;

    /**
     * 已开通业务-销售  0：未勾选  1：已勾选
     */
    private Integer sell;

    /**
     * 已开通业务-招标  0：未勾选  1：已勾选
     */
    private Integer tender;

    /**
     * 已开通业务-竞标  0：未勾选  1：已勾选
     */
    private Integer bid;

    /**
     * 付款条件 1：货到票到
     */
    private Integer paymentClause;

    /**
     * 天数
     */
    private Integer days;

    /**
     * 结算方式 1:银行转账
     */
    private Integer clearingType;

    /**
     * 预付比例
     */
    private String advanceRatio;


    /**
     * 纳税人识别号
     */
    private String taxpayerId;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 开户银行账号
     */
    private String bankCardNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntNumber() {
        return entNumber;
    }

    public void setEntNumber(String entNumber) {
        this.entNumber = entNumber;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getBusinessImg() {
        return businessImg;
    }

    public void setBusinessImg(String businessImg) {
        this.businessImg = businessImg;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Integer partnerType) {
        this.partnerType = partnerType;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

    public String getRegisterMoney() {
        return registerMoney;
    }

    public void setRegisterMoney(String registerMoney) {
        this.registerMoney = registerMoney;
    }

    public String getDefaultEntrepot() {
        return defaultEntrepot;
    }

    public void setDefaultEntrepot(String defaultEntrepot) {
        this.defaultEntrepot = defaultEntrepot;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public Integer getTender() {
        return tender;
    }

    public void setTender(Integer tender) {
        this.tender = tender;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getPaymentClause() {
        return paymentClause;
    }

    public void setPaymentClause(Integer paymentClause) {
        this.paymentClause = paymentClause;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getClearingType() {
        return clearingType;
    }

    public void setClearingType(Integer clearingType) {
        this.clearingType = clearingType;
    }

    public String getAdvanceRatio() {
        return advanceRatio;
    }

    public void setAdvanceRatio(String advanceRatio) {
        this.advanceRatio = advanceRatio;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
}
