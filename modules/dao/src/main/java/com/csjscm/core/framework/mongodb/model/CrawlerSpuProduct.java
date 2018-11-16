package com.csjscm.core.framework.mongodb.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="crawler_spu_product")
@Data
public class CrawlerSpuProduct {
    @Id
    @NotBlank(message = "id不能为空")
    private String id;
    private String minUnit;
    private String minUnitBack;
    private String categoryNames;
    private String channel;
    private String pushed="false";
    private String brandNameBack;
    private String brandName;
    private String brandId;
    private String stock;
    private String lv1CategoryNo;
    private String lv2CategoryNo;
    private String lv3CategoryNo;
    private String lv1CategoryName;
    private String lv2CategoryName;
    private String lv3CategoryName;
    private String mnemonicCode;
    private String guidancePrice;
    private String pageMainPic;
    private String productInfo;
    private String productName;
    private String productTitle;
    private String weight;
    private String height;
    private String width;
    private String length;
    private JSONArray attrJson;

}
