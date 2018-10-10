package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExportExcel;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.vo.SkuCoreVo;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    @ApiOperation("下载失败数据模板")
    @RequestMapping(value = "downloadFailSkuCore")
    public void downloadFailSkuCore(@ApiParam(name = "jsonData",value = "失败的数据") @RequestParam(value="jsonData") String  jsonData, HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<SkuCoreVo> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(jsonData);
        for (int i=0;i<jsonArray.size();i++) {
            SkuCoreVo skuCore = JSONObject.toJavaObject(jsonArray.getJSONObject(i), SkuCoreVo.class);
            list.add(skuCore);
        }
        ExportExcel<SkuCoreVo> ex = new ExportExcel<SkuCoreVo>();
        String[] header =
                { "失败原因","三级分类编码","商品名称", "品牌", "规格", "型号", "最小单位", "进价成本", "近期询价", "条形码", "商品简码", "商品文字描述"};
        String[] line =
                {"failMessage","categoryNo","productName","brandName","rule","rule","minUint","refrencePrice","recentEnquiry","ean13Code","mnemonicCode","description"};
        OutputStream out;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String fileName=new String("失败数据模板.xlsx".getBytes("UTF-8"),"iso-8859-1");
        response.addHeader("Content-Disposition", "attachment;filename="+fileName);
        out = response.getOutputStream();
        ex.exportExcelBigData("失败数据模板",header,line, list, out,"yyyy-MM-dd HH:mm:ss");
        out.flush();
        out.close();
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

    /**
     * 创建商品
     * @param skuCore
     * @return
     */
    @ApiOperation("创建商品对象")
    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public APIResponse createProduct(@RequestBody SkuCore skuCore){
        skuCoreService.insertSelective(skuCore);
        return APIResponse.success();
    }
    /**
     * 创建商品
     * @param skuCore
     * @return
     */
    @ApiOperation("更新商品对象")
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public APIResponse updateProduct(@RequestBody SkuCore skuCore){
        skuCoreService.updateProduct(skuCore);
        return APIResponse.success();
    }
    /**
     * 获取平台商品
     * @param productNo
     * @return
     */
    @ApiOperation("获取平台商品")
    @RequestMapping(value = "/getSkuCoreInfo", method = RequestMethod.GET)
    public APIResponse getSkuCoreInfo(@ApiParam(name = "productNo" ,value = "商品编码",required=true) String productNo){
        Map<String,Object> map=new HashMap<>();
        map.put("productNo",productNo);
        SkuCore selective = skuCoreService.findSelective(map);
        return APIResponse.success(selective);
    }
/*
    */
/**
     * 创建商品
     * @param map
     * @return
     *//*

    @ApiOperation("创建商品对象")
    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public APIResponse createProduct(@ApiParam(name="map",value="商品对象",required=true) @RequestBody Map<String, Object> map){
        skuCoreService.insertSelective(map);
        return APIResponse.success();
    }
*/

}
