package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExportExcel;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.model.SkuPartnerEx;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.vo.SkuCustomerVo;
import com.csjscm.core.framework.vo.SkuPartnerAddModel;
import com.csjscm.core.framework.vo.SkuPartnerVo;
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

    @ApiOperation("导入供应商商品excel")
    @RequestMapping(value = "importSkuPartnerExcel",method = RequestMethod.POST)
    public APIResponse importExcel(@ApiParam(name = "file",value = "excel文件") @RequestParam(value="file") MultipartFile file, @ApiParam(name = "customerNo",value = "supplyNo") @RequestParam(value="supplyNo")String supplyNo){
        Map<String, Object> map = productPartnerService.importPartnerExcel(file,supplyNo);
        return  APIResponse.success(map);
    }
    @ApiOperation("下载供应商商品失败数据模板")
    @RequestMapping(value = "downloadFailPartner")
    public void downloadFailCustomer(@ApiParam(name = "jsonData",value = "失败的数据") @RequestParam(value="jsonData") String  jsonData, HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<SkuPartnerVo> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(jsonData);
        for (int i=0;i<jsonArray.size();i++) {
            SkuPartnerVo skuPartnerVo = JSONObject.toJavaObject(jsonArray.getJSONObject(i), SkuPartnerVo.class);
            list.add(skuPartnerVo);
        }
        ExportExcel<SkuPartnerVo> ex = new ExportExcel<SkuPartnerVo>();
        String[] header =
                { "失败原因","企业商品编码", "商品名称","品牌","规格", "型号","参数描述","川商品编码","川小类编码","最小库存单位"};
        String[] line =
                {"failMessage", "supplyPdNo","supplyPdName", "brandName","supplyPdRule","supplyPdSize","free","productNo","categoryNo","minUint"};
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
     * 新增供应商商品
     * @param skuPartnerAddModel
     * @return
     */
    @ApiOperation("新增客户商品")
    @RequestMapping(value = "/saveSkuPartner",method = RequestMethod.POST)
    public APIResponse saveSkuPartner(@RequestBody @Valid SkuPartnerAddModel skuPartnerAddModel){
        productPartnerService.save(skuPartnerAddModel);
        return APIResponse.success();
    }
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
