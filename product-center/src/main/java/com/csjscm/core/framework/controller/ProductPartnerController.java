package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/product/product")
@ResponseBody
public class ProductPartnerController {
    @Autowired
    private ProductPartnerService productPartnerService;

    @RequestMapping(value = "/partner",method = RequestMethod.GET)
    public APIResponse<QueryResult<SkuPartner>> queryCoreProduct(@RequestParam(required = false,defaultValue = "1")int page,
                                                                 @RequestParam(required = false,defaultValue = "10")int rpp,
                                                                 @RequestParam Map<String,String> condition){
        SkuPartnerExample skuPartnerExample=new SkuPartnerExample();
        if(condition!=null){
            skuPartnerExample= JSON.parseObject(JSON.toJSONString(condition),SkuPartnerExample.class);
        }
        QueryResult<SkuPartner> result= productPartnerService.queryPartnerProduct(page,rpp,skuPartnerExample);
        return APIResponse.success(result);
    }
}
