package com.csjscm.core.framework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpuEx extends Spu {
    private Integer stock;
}
