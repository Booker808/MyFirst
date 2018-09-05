package com.csjscm.core.framework.common.enums;

/**
 * 商品核心表
 * 来源渠道（1：手动新增；2：导入；3：来自商城）
 */
public enum SkuCoreChannelEnum {
    手动新增(1),
    导入(2),
    来自商城(3);
    private final Integer state;

    SkuCoreChannelEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
