package com.csjscm.core.framework.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TaxCategory {
    private Integer id;

    @NotBlank
    private String taxCode;

    @NotBlank
    private String taxCategoryName;

    @NotNull
    private Integer level;

    private String parentCode;

    private BigDecimal taxRate;

    private String description;

    @NotNull
    private Integer versionId;
}