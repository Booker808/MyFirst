package com.csjscm.core.framework.common.enums;

/**
 * 商品分类表
 * 分类层级
 */
public enum CategoryLevelEnum {

    一级(1),
    二级(2),
    三级(3);

    private final Integer state;

    CategoryLevelEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
