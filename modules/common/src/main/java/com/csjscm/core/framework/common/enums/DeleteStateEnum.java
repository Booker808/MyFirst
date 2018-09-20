package com.csjscm.core.framework.common.enums;

/**
 * 计量单位定义表
 * 是否有效，1-有效，0-失效',
 */
public enum DeleteStateEnum {

    未删除(0),
    已删除(1);

    private final Integer state;

    DeleteStateEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
