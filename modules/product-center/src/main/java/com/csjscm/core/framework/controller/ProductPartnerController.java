package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuPartnerEx;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api("供应商商品")
@Controller
@RequestMapping("/product/productPartner")
@ResponseBody
public class ProductPartnerController {
    @Autowired
    private ProductPartnerService productPartnerService;

    @ApiOperation("获取供应商品列表")
    @RequestMapping(value = "/product",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="supplyNo",value="供应商编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="productName",value="产品名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="productNo",value="商品编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandId",value="品牌ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandName",value="品牌名",dataType = "String",paramType = "query")
    })
    public APIResponse<QueryResult<SkuPartnerEx>> queryCoreProduct(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,String> condition){
        SkuPartnerExample skuPartnerExample=new SkuPartnerExample();
        if(condition!=null){
            skuPartnerExample= JSON.parseObject(JSON.toJSONString(condition),SkuPartnerExample.class);
        }
        QueryResult<SkuPartnerEx> result= productPartnerService.queryPartnerProduct(page,rpp,skuPartnerExample);
        return APIResponse.success(result);
    }
}
