package com.csjscm.core.framework.vo;

import java.util.List;

public class ScmPartnerVo {
    private  int successCount;
    private  int failCount;
    private  int total;
    private List<ScmPartnerFailVo> scmPartnerFailVos;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ScmPartnerFailVo> getScmPartnerFailVos() {
        return scmPartnerFailVos;
    }

    public void setScmPartnerFailVos(List<ScmPartnerFailVo> scmPartnerFailVos) {
        this.scmPartnerFailVos = scmPartnerFailVos;
    }
}
