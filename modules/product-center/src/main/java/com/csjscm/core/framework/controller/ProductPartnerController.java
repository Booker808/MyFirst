package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExportExcel;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.service.SpCategoryService;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.vo.SkuPartnerAddModel;
import com.csjscm.core.framework.vo.SkuPartnerDetailsModel;
import com.csjscm.core.framework.vo.SkuPartnerVo;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("供应商商品")
@Controller
@RequestMapping("/product/productPartner")
@ResponseBody
public class ProductPartnerController {
    @Autowired
    private ProductPartnerService productPartnerService;
    @Autowired
    private SkuCoreService skuCoreService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpCategoryService spCategoryService;
    @ApiOperation("获取供应商品列表")
    @RequestMapping(value = "/product",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="supplyNo",value="供应商编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="productName",value="产品名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="productNo",value="商品编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandId",value="品牌ID",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="brandName",value="品牌名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="supplyPdSize",value="供应商商品型号",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="lv1CategoryId",value="一级分类ID",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="lv2CategoryId",value="二级分类ID",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="categoryId",value="三级分类ID",dataType = "Integer",paramType = "query"),

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

    @ApiOperation("获取供应商商品详情")
    @RequestMapping(value = "/product/{id}",method = RequestMethod.GET)
    public APIResponse<SkuPartnerEx> queryCoreProductDetail(@PathVariable Integer id){
        SkuPartnerEx result=productPartnerService.queryPartnerProductDetail(id);
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
        ExportExcel<SkuPartnerVo> ex = new ExportExcel<>();
        String[] header =
                { "失败原因","三级分类编码","供应商商品编码","商品名称", "品牌", "规格", "型号", "最小单位", "进价成本", "近期询价", "条形码", "商品简码", "川商品编码"};
        String[] line =
                {"failMessage","categoryNo","supplyPdNo","productName","brandName","rule","size","minUint","refrencePrice","recentEnquiry","ean13Code","mnemonicCode","productNo"};
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
    @RequestMapping(value = "/product",method = RequestMethod.POST)
    public APIResponse saveSkuPartner(@RequestBody @Valid SkuPartnerAddModel skuPartnerAddModel){
        productPartnerService.save(skuPartnerAddModel);
        return APIResponse.success();
    }

    @ApiOperation("更新供应商商品")
    @RequestMapping(value = "/product/{id}",method = RequestMethod.PUT)
    public APIResponse updateSkuPartner(@RequestBody SkuPartner skuPartner,
                                        @PathVariable Integer id){
        skuPartner.setId(id);
        productPartnerService.updateSkuPartner(skuPartner);
        return APIResponse.success();
    }
    @ApiOperation("获取供应商商品详情")
    @RequestMapping(value = "skuPartnerDetails",method = RequestMethod.GET)
    public APIResponse skuPartnerDetails(@ApiParam(name = "id" ,value = "id") @RequestParam(value = "id") String id){
        Map<String, Object> map=new HashMap<>();
        map.put("id",id);

        SkuPartnerDetailsModel skuPartnerModel = productPartnerService.getSkuPartnerModel(map);
        if(skuPartnerModel!=null && StringUtils.isNotBlank(skuPartnerModel.getProductNo())){
            map.clear();
            map.put("productNo",skuPartnerModel.getProductNo());
            SkuCore skuCore = skuCoreService.findSelective(map);
            if(skuCore!=null){
                skuPartnerModel.setMinUint(skuCore.getMinUint());
                if(StringUtils.isNotBlank(skuCore.getCategoryNo())){
                    map.clear();
                    map.put("classCode",skuCore.getCategoryNo());
                    Category category = categoryService.findSelective(map);
                    if(category!=null){
                        skuPartnerModel.setClassCode(category.getClassCode());
                        skuPartnerModel.setClassName(category.getClassName());
                    }
                }else if(StringUtils.isNotBlank(skuCore.getCategorySpNo())){
                    map.clear();
                    map.put("classCode",skuCore.getCategorySpNo());
                    SpCategory spCategory = spCategoryService.findSelective(map);
                    if(spCategory!=null){
                        skuPartnerModel.setClassCode(spCategory.getClassCode());
                        skuPartnerModel.setClassName(spCategory.getClassName());
                    }
                }
            }
        }
        return APIResponse.success(skuPartnerModel);
    }


    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
