package com.csjscm.core.framework.common.enums;

/**
 * 企业附件表
 * 附件类型(1：营业执照，2：其他)
 */
public enum AttachmentTypeEnum {

    营业执照(1),
    其他(2);

    private final Integer state;

    AttachmentTypeEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
