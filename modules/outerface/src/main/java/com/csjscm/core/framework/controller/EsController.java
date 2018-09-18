package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.dao.SkuCustomerMapper;
import com.csjscm.core.framework.dao.SkuPartnerMapper;
import com.csjscm.core.framework.elasticsearch.model.ProductCore;
import com.csjscm.core.framework.elasticsearch.model.ProductCustomer;
import com.csjscm.core.framework.elasticsearch.model.ProductPartner;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/es")
@ResponseBody
@Slf4j
public class EsController {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private SkuPartnerMapper skuPartnerMapper;
    @Autowired
    private SkuCustomerMapper skuCustomerMapper;
    @Autowired
    private SkuCoreMapper skuCoreMapper;

    @RequestMapping("addPartner")
    public APIResponse addPartner(){
        List<SkuPartner> skuPartnerList=skuPartnerMapper.selectByExample(new SkuPartnerExample());
        skuPartnerList.parallelStream().forEach(skuPartner->{
            ProductPartner partner=new ProductPartner();
            partner.setSupplyPdName(skuPartner.getSupplyPdName());
            partner.setSupplyPdRule(skuPartner.getSupplyPdRule());
            partner.setSupplyPdSize(skuPartner.getSupplyPdSize());
            IndexRequest indexRequest=new IndexRequest("sku_partner","product",skuPartner.getId().toString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                indexRequest.source(mapper.writeValueAsString(partner), XContentType.JSON);
                client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (Exception e) {
                log.error("插入失败，id:"+skuPartner.getId());
                e.printStackTrace();
            }
        });
        return APIResponse.success();
    }



    @RequestMapping("addCustomer")
    public APIResponse addCustomer(){
        List<SkuCustomer> skuCustomerList=skuCustomerMapper.selectByExample(new SkuCustomerExample());
        skuCustomerList.parallelStream().forEach(skuCustomer->{
            ProductCustomer customer=new ProductCustomer();
            customer.setCustomerPdName(skuCustomer.getCustomerPdName());
            customer.setCustomerPdRule(skuCustomer.getCustomerPdRule());
            customer.setCustomerPdSize(skuCustomer.getCustomerPdSize());
            IndexRequest indexRequest=new IndexRequest("sku_customer","product",skuCustomer.getId().toString());
            ObjectMapper mapper = new ObjectMapper();
            try {
                indexRequest.source(mapper.writeValueAsString(customer), XContentType.JSON);
                client.index(indexRequest, RequestOptions.DEFAULT);
            } catch (Exception e) {
                log.error("插入失败，id:"+skuCustomer.getId());
                e.printStackTrace();
            }
        });
        return APIResponse.success();
    }
//
//    @RequestMapping("addCore")
//    public APIResponse addCore(){
//        for(int i=0;i<135729;i=i+5000){
//            log.info("start at:"+i);
//            List<SkuCore> skuCoreList=skuCoreMapper.selectWithSize(i,5000);
//            skuCoreList.parallelStream().forEach(skuCore->{
//            ProductCore core=new ProductCore();
//            core.setProductName(skuCore.getProductName());
//            core.setRule(skuCore.getRule());
//            core.setSize(skuCore.getSize());
//            IndexRequest indexRequest=new IndexRequest("sku_core","product",skuCore.getProductNo());
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                indexRequest.source(mapper.writeValueAsString(core), XContentType.JSON);
//                client.index(indexRequest, RequestOptions.DEFAULT);
//            } catch (Exception e) {
//                log.error("插入失败，id:"+skuCore.getProductNo());
//                e.printStackTrace();
//            }
//        });
//        }
//        return APIResponse.success();
//    }

    @RequestMapping(value = "/EsSkuPartner",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="supplyPdName",value="供应商商品名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="supplyPdSize",value="供应商商品型号",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="supplyPdRule",value="供应商商品类型",dataType = "String",paramType = "query")
    })
    public APIResponse queryEsSkuPartner(@RequestParam Map<String,String> condition){
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        if(condition.containsKey("page")&&condition.containsKey("rpp")
                && StringUtils.isNotEmpty(condition.get("page"))&&StringUtils.isNotEmpty(condition.get("rpp"))){
            int page=Integer.parseInt(condition.get("page"));
            int rpp=Integer.parseInt(condition.get("rpp"));
            sourceBuilder.from((page-1)*rpp);
            sourceBuilder.size(rpp);
        }
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        if(condition.containsKey("supplyPdName")&&StringUtils.isNotEmpty(condition.get("supplyPdName"))){
            boolQueryBuilder.must(
                    QueryBuilders.termQuery("supplyPdName",condition.get("supplyPdName")));
        }
        if(condition.containsKey("supplyPdRule")&&StringUtils.isNotEmpty(condition.get("supplyPdRule"))){
            boolQueryBuilder.must(
                    QueryBuilders.termQuery("supplyPdRule",condition.get("supplyPdRule")));
        }
        if(condition.containsKey("supplyPdSize")&&StringUtils.isNotEmpty(condition.get("supplyPdSize"))){
            boolQueryBuilder.must(
                    QueryBuilders.termQuery("supplyPdSize",condition.get("supplyPdSize")));
        }
        sourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest=new SearchRequest("sku_partner").types("product").source(sourceBuilder);
        try{
            SearchResponse response=client.search(searchRequest);
            return APIResponse.success(response);
        }catch(Exception e){
            e.printStackTrace();
            return APIResponse.fail(e);
        }
    }
}
