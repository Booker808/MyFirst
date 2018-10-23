package com.csjscm.core.framework.common.enums;

/**
 * 商品分类表
 * 分类层级
 */
public enum TradeTypeEnum {

    供应商(1),
    采购商(2),
    供应商采购商(3),
    服务商(4);

    private final Integer state;

    TradeTypeEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
