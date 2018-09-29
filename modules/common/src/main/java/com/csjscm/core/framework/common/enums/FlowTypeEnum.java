package com.csjscm.core.framework.common.enums;

/**
 * 供应商审批流程记录
 * 流程类型
 */
public enum FlowTypeEnum {

    正常流程(1),
    特批流程(2),
    状态变更流程(3);

    private final Integer state;

    FlowTypeEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
