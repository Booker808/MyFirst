package com.csjscm.core.framework.common.enums;

/**
 * 商品核心表
 * 来源渠道（1：平台手动新增；2：平台导入；3：来自西域； 4：来自商城； 5：来自scm）
 */
public enum SkuCoreChannelEnum {
    手动新增(1),
    导入(2),
    来自西域(3),
    来自商城(4),
    来自scm(5);
    private final Integer state;

    SkuCoreChannelEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
