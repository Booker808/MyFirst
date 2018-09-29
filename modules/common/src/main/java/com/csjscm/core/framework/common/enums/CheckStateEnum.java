package com.csjscm.core.framework.common.enums;

/**
 * 企业基本信息
 * 审批状态1待申请人提交，2待内部审核，3待准入审核，4待执行总经理审核，5待准入确认，6待打印盖章，7待合同预警，8归档，9已驳回申请人，10保存
 */
public enum CheckStateEnum {

    待申请人提交(1),
    待内部审核(2),
    待准入审核(3),
    待执行总经理审核(4),
    待准入确认(5),
    待打印盖章(6),
    待合同预警(7),
    归档(8),
    已驳回申请人(9),
    保存(10),
    已作废(11);
    private final Integer state;

    CheckStateEnum(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
