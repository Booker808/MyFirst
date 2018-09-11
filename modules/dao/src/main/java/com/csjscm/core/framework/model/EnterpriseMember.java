package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业-会员表实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 15:01:53
 */

 public class EnterpriseMember implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String entName;

    /**
    * 法人
    */
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
    * 
    */
    private Date createTime;

    /**
    * 
    */
    private Date editTime;


    /**
    * 获取主键Id
    *
    * @return id
    */
    public Integer getId(){
        return id;
    }

    /**
    * 设置主键Id
    * 
    * @param
    */
    public void setId(Integer id){
        this.id = id;
    }

    /**
    * 获取企业编号
    *
    * @return 企业编号
    */
    public String getEntNumber(){
        return entNumber;
    }

    /**
    * 设置企业编号
    * 
    * @param entNumber 要设置的企业编号
    */
    public void setEntNumber(String entNumber){
        this.entNumber = entNumber;
    }

    /**
    * 获取企业名称
    *
    * @return 企业名称
    */
    public String getEntName(){
        return entName;
    }

    /**
    * 设置企业名称
    * 
    * @param entName 要设置的企业名称
    */
    public void setEntName(String entName){
        this.entName = entName;
    }

    /**
    * 获取法人
    *
    * @return 法人
    */
    public String getLegalPerson(){
        return legalPerson;
    }

    /**
    * 设置法人
    * 
    * @param legalPerson 要设置的法人
    */
    public void setLegalPerson(String legalPerson){
        this.legalPerson = legalPerson;
    }

    /**
    * 获取注册地址
    *
    * @return 注册地址
    */
    public String getRegisterAddress(){
        return registerAddress;
    }

    /**
    * 设置注册地址
    * 
    * @param registerAddress 要设置的注册地址
    */
    public void setRegisterAddress(String registerAddress){
        this.registerAddress = registerAddress;
    }

    /**
    * 获取营业地址
    *
    * @return 营业地址
    */
    public String getBusinessAddress(){
        return businessAddress;
    }

    /**
    * 设置营业地址
    * 
    * @param businessAddress 要设置的营业地址
    */
    public void setBusinessAddress(String businessAddress){
        this.businessAddress = businessAddress;
    }

    /**
    * 获取官网地址
    *
    * @return 官网地址
    */
    public String getWebAddress(){
        return webAddress;
    }

    /**
    * 设置官网地址
    * 
    * @param webAddress 要设置的官网地址
    */
    public void setWebAddress(String webAddress){
        this.webAddress = webAddress;
    }

    /**
    * 获取营业执照图片路径
    *
    * @return 营业执照图片路径
    */
    public String getBusinessImg(){
        return businessImg;
    }

    /**
    * 设置营业执照图片路径
    * 
    * @param businessImg 要设置的营业执照图片路径
    */
    public void setBusinessImg(String businessImg){
        this.businessImg = businessImg;
    }

    /**
    * 获取核准状态 1备用
    *
    * @return 核准状态 1备用
    */
    public Integer getCheckState(){
        return checkState;
    }

    /**
    * 设置核准状态 1备用
    * 
    * @param checkState 要设置的核准状态 1备用
    */
    public void setCheckState(Integer checkState){
        this.checkState = checkState;
    }

    /**
    * 获取交易类型 1供应商  2采购商 3供应商&采购商
    *
    * @return 交易类型 1供应商  2采购商 3供应商&采购商
    */
    public Integer getTradeType(){
        return tradeType;
    }

    /**
    * 设置交易类型 1供应商  2采购商 3供应商&采购商
    * 
    * @param tradeType 要设置的交易类型 1供应商  2采购商 3供应商&采购商
    */
    public void setTradeType(Integer tradeType){
        this.tradeType = tradeType;
    }

    /**
    * 获取供应商类型 1标准型  2战略型  3独家/客户指定型
    *
    * @return 供应商类型 1标准型  2战略型  3独家/客户指定型
    */
    public Integer getPartnerType(){
        return partnerType;
    }

    /**
    * 设置供应商类型 1标准型  2战略型  3独家/客户指定型
    * 
    * @param partnerType 要设置的供应商类型 1标准型  2战略型  3独家/客户指定型
    */
    public void setPartnerType(Integer partnerType){
        this.partnerType = partnerType;
    }

    /**
    * 获取联系人
    *
    * @return 联系人
    */
    public String getLinkman(){
        return linkman;
    }

    /**
    * 设置联系人
    * 
    * @param linkman 要设置的联系人
    */
    public void setLinkman(String linkman){
        this.linkman = linkman;
    }

    /**
    * 获取联系人电话
    *
    * @return 联系人电话
    */
    public String getLinkmanPhone(){
        return linkmanPhone;
    }

    /**
    * 设置联系人电话
    * 
    * @param linkmanPhone 要设置的联系人电话
    */
    public void setLinkmanPhone(String linkmanPhone){
        this.linkmanPhone = linkmanPhone;
    }

    /**
    * 获取注册资本(万)
    *
    * @return 注册资本(万)
    */
    public String getRegisterMoney() {
        return registerMoney;
    }
    /**
     * 设置注册资本(万)
     *
     * @param registerMoney 要设置的注册资本(万)
     */
    public void setRegisterMoney(String registerMoney) {
        this.registerMoney = registerMoney;
    }
    /**
    * 获取默认仓库
    *
    * @return 默认仓库
    */
    public String getDefaultEntrepot(){
        return defaultEntrepot;
    }

    /**
    * 设置默认仓库
    * 
    * @param defaultEntrepot 要设置的默认仓库
    */
    public void setDefaultEntrepot(String defaultEntrepot){
        this.defaultEntrepot = defaultEntrepot;
    }

    /**
    * 获取已开通业务-采购  0：未勾选  1：已勾选
    *
    * @return 已开通业务-采购  0：未勾选  1：已勾选
    */
    public Integer getPurchase(){
        return purchase;
    }

    /**
    * 设置已开通业务-采购  0：未勾选  1：已勾选
    * 
    * @param purchase 要设置的已开通业务-采购  0：未勾选  1：已勾选
    */
    public void setPurchase(Integer purchase){
        this.purchase = purchase;
    }

    /**
    * 获取已开通业务-销售  0：未勾选  1：已勾选
    *
    * @return 已开通业务-销售  0：未勾选  1：已勾选
    */
    public Integer getSell(){
        return sell;
    }

    /**
    * 设置已开通业务-销售  0：未勾选  1：已勾选
    * 
    * @param sell 要设置的已开通业务-销售  0：未勾选  1：已勾选
    */
    public void setSell(Integer sell){
        this.sell = sell;
    }

    /**
    * 获取已开通业务-招标  0：未勾选  1：已勾选
    *
    * @return 已开通业务-招标  0：未勾选  1：已勾选
    */
    public Integer getTender(){
        return tender;
    }

    /**
    * 设置已开通业务-招标  0：未勾选  1：已勾选
    * 
    * @param tender 要设置的已开通业务-招标  0：未勾选  1：已勾选
    */
    public void setTender(Integer tender){
        this.tender = tender;
    }

    /**
    * 获取已开通业务-竞标  0：未勾选  1：已勾选
    *
    * @return 已开通业务-竞标  0：未勾选  1：已勾选
    */
    public Integer getBid(){
        return bid;
    }

    /**
    * 设置已开通业务-竞标  0：未勾选  1：已勾选
    * 
    * @param bid 要设置的已开通业务-竞标  0：未勾选  1：已勾选
    */
    public void setBid(Integer bid){
        this.bid = bid;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置
    * 
    * @param createTime 要设置的
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getEditTime(){
        return editTime;
    }

    /**
    * 设置
    * 
    * @param editTime 要设置的
    */
    public void setEditTime(Date editTime){
        this.editTime = editTime;
    }

}