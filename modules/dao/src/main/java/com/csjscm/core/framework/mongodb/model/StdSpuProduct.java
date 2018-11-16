package com.csjscm.core.framework.mongodb.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="standard_spu_product")
@Data
public class StdSpuProduct {
  private String stdProductNo;
  private String spu;
  private String productName;
  private String mnemonicCode;
  private String guidancePrice;
  private String lv1CategoryNo;
  private String lv2CategoryNo;
  private String lv3CategoryNo;
  private String lv1CategoryName;
  private String lv2CategoryName;
  private String lv3CategoryName;
  private String brandName;
  private String brandId;
  private String minUnit;
  private String productTile;
  private String channel;
  private String pictureUrl;
  private String productInfo;
  private JSONArray basicAttr;
  private String added="未上架";
  private String shelfTime;
}
