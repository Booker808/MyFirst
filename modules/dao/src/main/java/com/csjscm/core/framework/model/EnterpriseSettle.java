package com.csjscm.core.framework.model;

import java.util.Date;

public class EnterpriseSettle {
    private Integer id;

    private String entNumber;

    private Integer paymentClause;

    private Integer days;

    private Integer advanceRatio;

    private Integer settleType;

    private Date createTime;

    private Date editTime;

    private String editUser;

    private Integer isdelete;

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

    public Integer getAdvanceRatio() {
        return advanceRatio;
    }

    public void setAdvanceRatio(Integer advanceRatio) {
        this.advanceRatio = advanceRatio;
    }

    public Integer getSettleType() {
        return settleType;
    }

    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}