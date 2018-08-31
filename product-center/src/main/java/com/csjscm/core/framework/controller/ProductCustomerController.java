package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/product/product")
public class ProductCustomerController {
    @Autowired
    private ProductCustomerService productCustomerService;

    @RequestMapping(value = "/customer",method = RequestMethod.GET)
    public APIResponse<QueryResult<SkuCustomer>> queryCoreProduct(@RequestParam(required = false,defaultValue = "1")int page,
                                        @RequestParam(required = false,defaultValue = "10")int rpp,
                                        @RequestParam Map<String,String> condition){
        SkuCustomerExample skuCustomerExample=new SkuCustomerExample();
        if(condition!=null){
            skuCustomerExample= JSON.parseObject(JSON.toJSONString(condition),SkuCustomerExample.class);
        }
        QueryResult<SkuCustomer> result=productCustomerService.queryCustomerProduct(page,rpp,skuCustomerExample);
        return APIResponse.success(result);
    }
}
