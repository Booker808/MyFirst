package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Api("商品核心表")
@Controller
@RequestMapping("/product/product")
@ResponseBody
public class SkuCoreController {

    @Autowired
    private SkuCoreService skuCoreService;


    @ApiOperation("导入商品excel")
    @RequestMapping(value = "importSkuCoreExcel",method = RequestMethod.POST)
    public APIResponse importExcel(@ApiParam(name = "file",value = "excel文件") @RequestParam(value="file") MultipartFile  file){
        Map<String, Object> map = skuCoreService.importSkuCoreExcel(file);
        return  APIResponse.success(map);
    }

    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
