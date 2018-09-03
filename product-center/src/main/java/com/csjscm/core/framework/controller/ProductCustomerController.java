package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomerEx;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api("客户商品")
@Controller
@RequestMapping("/product/product")
@ResponseBody
public class ProductCustomerController {
    @Autowired
    private ProductCustomerService productCustomerService;

    @ApiOperation("获取客户商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="customerNo",value="客户编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="productName",value="产品名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="productNo",value="商品编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandId",value="品牌ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandName",value="品牌名",dataType = "String",paramType = "query")
    })
    @RequestMapping(value = "/customer",method = RequestMethod.GET)
    public APIResponse<QueryResult<SkuCustomerEx>> queryCoreProduct(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,String> condition){
        SkuCustomerExample skuCustomerExample=new SkuCustomerExample();
        if(condition!=null){
            skuCustomerExample= JSON.parseObject(JSON.toJSONString(condition),SkuCustomerExample.class);
        }
        QueryResult<SkuCustomerEx> result=productCustomerService.queryCustomerProduct(page,rpp,skuCustomerExample);
        return APIResponse.success(result);
    }
}
