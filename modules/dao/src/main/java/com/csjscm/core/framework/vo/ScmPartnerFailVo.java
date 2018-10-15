package com.csjscm.core.framework.vo;

public class ScmPartnerFailVo {
    private  SkuPartnerSCMMolde failData;
    private  String  message;

    public SkuPartnerSCMMolde getFailData() {
        return failData;
    }

    public void setFailData(SkuPartnerSCMMolde failData) {
        this.failData = failData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
