package com.csjscm.core.framework.common.enums;

/**
 * 计量单位定义表
 * 是否有效，1-有效，0-失效',
 */
public enum InvUnitIsvalidEnum {

    有效(1),
    失效(0);

    private final Integer state;

    InvUnitIsvalidEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
