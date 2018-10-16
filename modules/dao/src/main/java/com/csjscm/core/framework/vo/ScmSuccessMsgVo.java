package com.csjscm.core.framework.vo;

public class ScmSuccessMsgVo {
    private  String outId;
    private  String productNo;
    private  String customerNo;
    private  String customerPdNo;

    public String getCustomerPdNo() {
        return customerPdNo;
    }

    public void setCustomerPdNo(String customerPdNo) {
        this.customerPdNo = customerPdNo;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}
