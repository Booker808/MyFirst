package com.csjscm.core.framework.common.enums;

public enum SpecTypeEnum {
    销售(1),
    扩展(2);

    private final Integer state;

    SpecTypeEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
