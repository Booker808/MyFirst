package com.csjscm.core.framework.common.enums;

public enum IsSpecUsedEnum {
    是(1),
    否(0);

    private final Integer state;

    IsSpecUsedEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
