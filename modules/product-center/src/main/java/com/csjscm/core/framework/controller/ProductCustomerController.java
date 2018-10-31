package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExportExcel;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuCustomerEx;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.core.framework.vo.SkuCustomerPageVo;
import com.csjscm.core.framework.vo.SkuCustomerVo;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api("客户商品")
@Controller
@RequestMapping("/product/productCustomer")
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
    @RequestMapping(value = "/product",method = RequestMethod.GET)
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
    @RequestMapping(value = "/productPage",method = RequestMethod.GET)
    public APIResponse<QueryResult<SkuCustomerPageVo>> productPage(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,Object> condition){
        QueryResult<SkuCustomerPageVo> result = productCustomerService.findPage(page, rpp, condition);
        return APIResponse.success(result);
    }
    @ApiOperation("导入客户物料excel")
    @RequestMapping(value = "importCustomerExcel",method = RequestMethod.POST)
    public APIResponse importExcel(@ApiParam(name = "file",value = "excel文件") @RequestParam(value="file") MultipartFile  file,@ApiParam(name = "customerNo",value = "customerNo") @RequestParam(value="customerNo")String customerNo){
        Map<String, Object> map = productCustomerService.importCustomerExcel(file,customerNo);
        return  APIResponse.success(map);
    }
    @ApiOperation("下载客户物料失败数据模板")
    @RequestMapping(value = "downloadFailCustomer")
    public void downloadFailCustomer(@ApiParam(name = "jsonData",value = "失败的数据") @RequestParam(value="jsonData") String  jsonData, HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<SkuCustomerVo> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(jsonData);
        for (int i=0;i<jsonArray.size();i++) {
            SkuCustomerVo skuCustomerVo = JSONObject.toJavaObject(jsonArray.getJSONObject(i), SkuCustomerVo.class);
            list.add(skuCustomerVo);
        }
        ExportExcel<SkuCustomerVo> ex = new ExportExcel<SkuCustomerVo>();
        String[] header =
                { "失败原因", "川商品编码","客户商品编码","三级分类编码","商品名称", "品牌", "规格", "最小单位",  "条形码", "商品简码"};
        String[] line =
                {"failMessage","productNo", "customerPdNo","categoryNo","customerPdName","brandName","customerPdRule","minUint","ean13Code","mnemonicCode"};
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

    /**
     * 新增客户商品
     * @param skuCustomerVo
     * @return
     */
    @ApiOperation("新增客户商品")
    @RequestMapping(value = "/saveCustomer",method = RequestMethod.POST)
    public APIResponse createBrand(@RequestBody @Valid SkuCustomerVo skuCustomerVo){
        productCustomerService.save(skuCustomerVo);
        return APIResponse.success();
    }
    @ApiOperation("更新客户商品")
    @RequestMapping(value = "/updateCustomer",method = RequestMethod.POST)
    public APIResponse updateCustomer(@RequestBody @Valid SkuCustomer skuCustomer){
        productCustomerService.update(skuCustomer);
        return APIResponse.success();
    }
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
