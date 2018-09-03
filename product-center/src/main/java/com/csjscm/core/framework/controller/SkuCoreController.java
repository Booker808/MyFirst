package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    /**
     * 查询商品最小分类的最大编码
     * @return
     */
    @ApiOperation("查询商品最小分类的最大编码")
    @RequestMapping(value = "/productNO",method = RequestMethod.GET)
    public APIResponse sortData(){
        List<SkuCore> coreList = skuCoreService.selectByProductNoList();
        return APIResponse.success(coreList);
    }
}
