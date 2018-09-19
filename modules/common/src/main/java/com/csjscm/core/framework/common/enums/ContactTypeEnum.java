package com.csjscm.core.framework.common.enums;

/**
 * 联系人信息表
 * 人员类型(1:法人代表，2：联系人)',
 */
public enum ContactTypeEnum {

    法人代表(1),
    联系人(2);

    private final Integer state;

    ContactTypeEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
