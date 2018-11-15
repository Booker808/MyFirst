package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.Page;
import com.csjscm.core.framework.mongodb.model.CrawlerSpuProduct;
import com.csjscm.core.framework.service.product.CrawlerProductService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api("爬虫商品库")
@Controller
@RequestMapping("/product/crawler")
@ResponseBody
public class CrawlerProductController {
    @Autowired
    private CrawlerProductService crawlerProductService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public APIResponse list(){
        Page<CrawlerSpuProduct> spuPage = crawlerProductService.findSpuPage();
        return APIResponse.success(spuPage);
    }
}
