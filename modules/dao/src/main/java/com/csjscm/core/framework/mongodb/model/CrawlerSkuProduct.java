package com.csjscm.core.framework.mongodb.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="crawler_sku_product")
@Data
public class CrawlerSkuProduct {
    @Id
    @NotBlank(message = "id不能为空")
    private String id;
    private String spuId;
    private String minUnit;
    private String rule;
    private String minUnitBack;
    private String stock;
    private String mnemonicCode;
    private String sellPrice;
    private String referencePrice;
    private String pageMainPic;
    private String productInfo;
    private String productName;
    private String productTitle;
    private String goodsNo;
    private String weight;
    private String height;
    private String width;
    private String length;
    private JSONArray specParam;
    private JSONArray attrJson;
}
