package com.csjscm.core.framework.vo;

import java.util.List;

public class ScmCustomerVo {
    private  int successCount;
    private  int failCount;
    private  int total;
    private List<ScmFailMsgVo> scmFailMsgVoList;
    private List<ScmSuccessMsgVo> scmSuccessMsgVoList;

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

    public List<ScmFailMsgVo> getScmFailMsgVoList() {
        return scmFailMsgVoList;
    }

    public void setScmFailMsgVoList(List<ScmFailMsgVo> scmFailMsgVoList) {
        this.scmFailMsgVoList = scmFailMsgVoList;
    }

    public List<ScmSuccessMsgVo> getScmSuccessMsgVoList() {
        return scmSuccessMsgVoList;
    }

    public void setScmSuccessMsgVoList(List<ScmSuccessMsgVo> scmSuccessMsgVoList) {
        this.scmSuccessMsgVoList = scmSuccessMsgVoList;
    }
}
