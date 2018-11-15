package com.csjscm.core.framework.mongodb.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product")
@Data
public class XiyuProduct {
    private  String saleUom;
    private  String[]  categoryNames;
    private  String brandName;
    private  String marketPrice;
    private  String salePrice;
    private  String productName;
    private  String content;
    private  JSONArray attrGroup;
    private  JSONArray packParaM;
    private JSONArray specParam;
    private String skuCode;
    private String pictureUrl;
    private String stock;


}
