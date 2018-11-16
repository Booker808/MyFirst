package com.csjscm.core.framework.mongodb.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="standard_sku_product")
@Data
public class StdSkuProduct {
   private String productNo;
}
