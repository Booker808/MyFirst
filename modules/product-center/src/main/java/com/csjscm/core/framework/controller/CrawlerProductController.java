package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.Page;
import com.csjscm.core.framework.mongodb.model.CrawlerSkuProduct;
import com.csjscm.core.framework.mongodb.model.CrawlerSpuProduct;
import com.csjscm.core.framework.service.product.CrawlerProductService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("爬虫商品库")
@Controller
@RequestMapping("/product/crawler")
@ResponseBody
public class CrawlerProductController {
    @Autowired
    private CrawlerProductService crawlerProductService;
/***********************************  spu  ********************************************/
    @ApiOperation("网爬spu分页列表")
    @RequestMapping(value = "/spuProductPage",method = RequestMethod.GET)
    public APIResponse spuProductPage(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,Object> condition){
        Page<CrawlerSpuProduct> spuPage = crawlerProductService.findSpuPage(page, rpp, condition);
        return APIResponse.success(spuPage);
    }
    /**
     * 查询目标为ID网爬spu详情
     * @param id
     * @return
     */
    @ApiOperation("根据id查询网爬spu详情")
    @RequestMapping(value = "getSpuProduct",method = RequestMethod.GET)
    public APIResponse getSpuProduct(@ApiParam(name="id",value="主键id",required=true) String id){
        return APIResponse.success(crawlerProductService.findSpuById(id));
    }
    @ApiOperation("编辑网爬spu")
    @RequestMapping(value = "/editSpuProduct",method = RequestMethod.POST)
    public APIResponse editSpuProduct(@RequestBody CrawlerSpuProduct crawlerSpuProduct){
        BeanValidator.validate(crawlerSpuProduct);
        crawlerProductService.updateSpu(crawlerSpuProduct);
        return APIResponse.success();
    }

    /***********************************  sku  ********************************************/
    @ApiOperation("根据spu_id查询sku列表")
    @RequestMapping(value = "/skuProductList",method = RequestMethod.GET)
    public APIResponse skuProductList(@ApiParam(name="spuId",value="spu_id",required=true) String spuId){
        Map<String,Object> map=new HashMap<>();
        map.put("spuId",spuId);
        List<CrawlerSkuProduct> skuList = crawlerProductService.findSkuList(map);
        return APIResponse.success(skuList);
    }
    @ApiOperation("根据sku id查询sku详情")
    @RequestMapping(value = "/getSkuProduct",method = RequestMethod.GET)
    public APIResponse updateInvUnit(@ApiParam(name="id",value="id",required=true) String id){
        CrawlerSkuProduct skuById = crawlerProductService.findSkuById(id);
        return APIResponse.success(skuById);
    }
    @ApiOperation("编辑网爬sku")
    @RequestMapping(value = "/editSkuProduct",method = RequestMethod.POST)
    public APIResponse editSkuProduct(@RequestBody CrawlerSkuProduct crawlerSkuProduct){
        crawlerProductService.updateSku(crawlerSkuProduct);
        return APIResponse.success();
    }



    /**
     * 自定义异常捕获
     *
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public APIResponse validationExp(ValidationException e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
