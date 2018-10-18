package com.csjscm.core.framework.vo;

public class TaxCustomerImportFailVo {
    private  String failMessage;
    private  String customerPdName;
    private  String taxCode;

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getCustomerPdName() {
        return customerPdName;
    }

    public void setCustomerPdName(String customerPdName) {
        this.customerPdName = customerPdName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}
