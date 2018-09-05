package com.csjscm.core.framework.model;

import java.math.BigDecimal;
import java.util.Date;

public class SkuSell {
    private Integer id;

    private String productNo;

    private BigDecimal sellPrice;

    private String description;

    private String createUserId;

    private Date createTime;

    private String editUserId;

    private Date editTime;

    private String optUdf1;

    private String optUdf2;

    private String optUdf3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getOptUdf1() {
        return optUdf1;
    }

    public void setOptUdf1(String optUdf1) {
        this.optUdf1 = optUdf1;
    }

    public String getOptUdf2() {
        return optUdf2;
    }

    public void setOptUdf2(String optUdf2) {
        this.optUdf2 = optUdf2;
    }

    public String getOptUdf3() {
        return optUdf3;
    }

    public void setOptUdf3(String optUdf3) {
        this.optUdf3 = optUdf3;
    }
}