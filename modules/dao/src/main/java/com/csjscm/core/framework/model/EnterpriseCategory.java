package com.csjscm.core.framework.model;

import java.util.Date;

public class EnterpriseCategory {
    private Integer id;

    private String entNumber;

    private Integer supplyState;

    private Integer supplyBusiness;

    private Date supplyStartTime;

    private Date supplyEndTime;

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

    public Integer getSupplyState() {
        return supplyState;
    }

    public void setSupplyState(Integer supplyState) {
        this.supplyState = supplyState;
    }

    public Integer getSupplyBusiness() {
        return supplyBusiness;
    }

    public void setSupplyBusiness(Integer supplyBusiness) {
        this.supplyBusiness = supplyBusiness;
    }

    public Date getSupplyStartTime() {
        return supplyStartTime;
    }

    public void setSupplyStartTime(Date supplyStartTime) {
        this.supplyStartTime = supplyStartTime;
    }

    public Date getSupplyEndTime() {
        return supplyEndTime;
    }

    public void setSupplyEndTime(Date supplyEndTime) {
        this.supplyEndTime = supplyEndTime;
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