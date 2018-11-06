package com.csjscm.core.framework.example;

import lombok.Data;

import java.util.List;

@Data
public class EnterpriseInfoExample {
    private String entNumber;
    private String entName;
    private List<Integer> entType;
    private Integer channel;
    private String purchasePrincipal;
    private String businessPrincipal;
}
