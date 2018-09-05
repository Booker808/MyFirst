package com.csjscm.core.framework.model;

import java.math.BigDecimal;
import java.util.Date;

public class UnitConvert {
    private Integer id;

    private String unit;

    private String objName;

    private BigDecimal fzNum;

    private Integer fmNum;

    private String isvalid;

    private String memo;

    private String createUserId;

    private Date createTime;

    private String editUserId;

    private Date editTime;

    private String requestId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public BigDecimal getFzNum() {
        return fzNum;
    }

    public void setFzNum(BigDecimal fzNum) {
        this.fzNum = fzNum;
    }

    public Integer getFmNum() {
        return fmNum;
    }

    public void setFmNum(Integer fmNum) {
        this.fmNum = fmNum;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}