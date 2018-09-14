package com.csjscm.core.framework.vo;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 企业会员编辑model
 *
 */
public class EnterpriseUpdateModel {
    /**
     * 主键Id
     */
    @NotNull(message = "id不能为空")
    private Integer id;
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
     *
     */
    private Date editTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }
}
