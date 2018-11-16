package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.Page;
import com.csjscm.core.framework.mongodb.dao.CrawlerSkuProductDao;
import com.csjscm.core.framework.mongodb.dao.CrawlerSpuProductDao;
import com.csjscm.core.framework.mongodb.model.CrawlerSkuProduct;
import com.csjscm.core.framework.mongodb.model.CrawlerSpuProduct;
import com.csjscm.core.framework.mongodb.model.XiyuProduct;
import com.csjscm.core.framework.mongodb.dao.XiyuProductDao;
import com.csjscm.core.framework.service.product.CrawlerProductService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.mongodb.DB;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/outerface")
@ResponseBody
public class DemoController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private XiyuProductDao xiyuProductDao;
    @Autowired
    private CrawlerSkuProductDao crawlerSkuProductDao;
    @Autowired
    private CrawlerSpuProductDao crawlerSpuProductDao;
    @Autowired
    private CrawlerProductService crawlerProductService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public APIResponse list(){
        CrawlerSpuProduct spuById = crawlerProductService.findSpuById("5beb8d14c83b79f0954d4736");
        return APIResponse.success(spuById);
    }
    @RequestMapping("/test")
    public APIResponse test(@RequestParam("str")String str){
        DB db = mongoTemplate.getDb();
        System.out.println(db);
        return APIResponse.success();
    }
    @RequestMapping("/testdata")
    public APIResponse testdata(){
        Query query = new Query();
        query.skip(1).limit(10);
        List<XiyuProduct> list3 = xiyuProductDao.find(query,"product");
        for(XiyuProduct l:list3){
            CrawlerSpuProduct spuProductBack=new CrawlerSpuProduct();
            spuProductBack.setBrandNameBack(l.getBrandName());
            String[] categoryNames = l.getCategoryNames();
            String categorystr="";
            if(categoryNames!=null){
                for(String s:categoryNames){
                    categorystr+=s+",";
                }
                if(StringUtils.isNotBlank(categorystr)){
                    categorystr = categorystr.substring(0, categorystr.length() - 1);
                }
            }
            spuProductBack.setCategoryNames(categorystr);
            spuProductBack.setChannel("西域");
            spuProductBack.setGuidancePrice(l.getMarketPrice());
            JSONArray packParaM = l.getPackParaM();
            if(packParaM!=null){
                for(int i=0;i<packParaM.size();i++){
                    JSONObject jsonObject = packParaM.getJSONObject(i);
                    String paramValue = jsonObject.getString("paramValue");
                    if(jsonObject.getString("paramName").equals("长度(mm)")){
                        spuProductBack.setLength(paramValue);
                    }
                    if(jsonObject.getString("paramName").equals("宽度(mm)")){
                        spuProductBack.setWeight(paramValue);
                    }
                    if(jsonObject.getString("paramName").equals("高度(mm)")){
                        spuProductBack.setHeight(paramValue);
                    }
                    if(jsonObject.getString("paramName").equals("重量(kg)")){
                        spuProductBack.setWeight(paramValue);
                    }
                }
            }
            spuProductBack.setMinUnitBack(l.getSaleUom());
            spuProductBack.setMnemonicCode(l.getSkuCode());
            spuProductBack.setPageMainPic(l.getPictureUrl());

            String content = l.getContent();
            if(StringUtils.isNotBlank(content)){
                JSONObject jsonObject = JSON.parseObject(content);
                if(jsonObject.containsKey("banner")){
                    content=jsonObject.getString("banner");
                }
            }
            spuProductBack.setProductInfo(content);
            spuProductBack.setProductName(l.getProductName());
            spuProductBack.setProductTitle(l.getProductName());
            spuProductBack.setStock(l.getStock());
            spuProductBack.setAttrJson(l.getSpecParam());
          //  mongoTemplate.insert(spuProductBack,"spu_product_back");

            CrawlerSkuProduct skuProductBack=new CrawlerSkuProduct();

            BeanutilsCopy.copyProperties(spuProductBack,skuProductBack);
            skuProductBack.setId(null);
            JSONArray array=new JSONArray();
            JSONArray attrGroup = l.getAttrGroup();
            if(attrGroup!=null){
                for(int j=0;j<attrGroup.size();j++){
                    JSONObject jsonObject = attrGroup.getJSONObject(j);
                    JSONObject json=new JSONObject();
                    json.put("key",jsonObject.getString("attrName"));
                    json.put("value",jsonObject.getString("attrValue"));
                    array.add(json);
                }
            }
            skuProductBack.setSpecParam(array);
            skuProductBack.setAttrJson(l.getSpecParam());
            skuProductBack.setReferencePrice(l.getMarketPrice());
            skuProductBack.setSellPrice(l.getSalePrice());
          //  mongoTemplate.insert(skuProductBack,"sku_product_back");
        }
        return APIResponse.success();
    }
}
