package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SkuCoreExample;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SkuCoreEx;
import com.csjscm.core.framework.service.product.ProductCoreService;
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

@Api("商品核心")
@Controller
@RequestMapping("/product/productCore")
@ResponseBody
public class ProductCoreController {
    @Autowired
    private ProductCoreService productCoreService;

    @ApiOperation("获取核心商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="productName",value="产品名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="productNo",value="商品编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandId",value="品牌ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandName",value="品牌名",dataType = "String",paramType = "query")
    })
    @RequestMapping(value = "/product",method = RequestMethod.GET)
    public APIResponse<QueryResult<SkuCoreEx>> queryCoreProduct(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,String> condition){
        SkuCoreExample skuCoreExample=new SkuCoreExample();
        if(condition!=null){
            skuCoreExample=JSON.parseObject(JSON.toJSONString(condition),SkuCoreExample.class);
        }
        QueryResult<SkuCoreEx> result=productCoreService.queryCoreProduct(page,rpp,skuCoreExample);
        return APIResponse.success(result);
    }
}
