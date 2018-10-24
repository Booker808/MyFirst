package com.csjscm.core.framework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class EnterprisePurchaseTemplateEx extends EnterprisePurchaseTemplate{
    private String entName;
    private String accountName;
    private String bankName;
    private String bankNo;
    private String mainContract;
}