package com.csjscm.core.framework.common.enums;

public enum TemplateCheckStatusEnum {

    待提交申请(0),
    审核过程中(1),
    审核流程结束(2);
    private final Integer status;

    TemplateCheckStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
