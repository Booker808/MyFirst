package com.csjscm.core.framework.common.enums;

/**
 * 企业账户信息
 * 账户类型
 *1：基本户，2：一般户，3：其他
 */
public enum AccountTypeEnum {

    基本户(1),
    一般户(2),
    其他(3);

    private final Integer state;

    AccountTypeEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
