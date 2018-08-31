package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.service.product.ProductCoreService;
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
public class ProductCoreController {
    @Autowired
    private ProductCoreService productCoreService;

    @RequestMapping(value = "/core",method = RequestMethod.GET)
    public APIResponse<QueryResult<SkuCore>> queryCoreProduct(@RequestParam(required = false,defaultValue = "1")int page,
                                        @RequestParam(required = false,defaultValue = "10")int rpp,
                                        @RequestParam Map<String,String> condition){
        SkuCoreExample skuCoreExample=new SkuCoreExample();
        if(condition!=null){
            skuCoreExample=JSON.parseObject(JSON.toJSONString(condition),SkuCoreExample.class);
        }
        QueryResult<SkuCore> result=productCoreService.queryCoreProduct(page,rpp,skuCoreExample);
        return APIResponse.success(result);
    }
}
