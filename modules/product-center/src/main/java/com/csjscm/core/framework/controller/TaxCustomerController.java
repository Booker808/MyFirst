package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExportExcel;
import com.csjscm.core.framework.model.TaxCustomer;
import com.csjscm.core.framework.service.tax.TaxCustomerService;
import com.csjscm.core.framework.service.tax.TaxService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("客户商品与税收关联记录参考表")
@RequestMapping("/product/taxCustomer")
@RestController
@ResponseBody
public class TaxCustomerController {
    @Autowired
    private TaxService taxService;
    @Autowired
    private TaxCustomerService taxCustomerService;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public APIResponse<QueryResult<TaxCustomer>> queryTaxVersionList(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,Object> condition){
        QueryResult<TaxCustomer> result = taxCustomerService.findPage(page, rpp, condition);
        return APIResponse.success(result);
    }

    @ApiOperation("导入Excel")
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public APIResponse importTaxCategoryExcel(
            @ApiParam(name = "file",value = "excel文件")@RequestParam MultipartFile file){
        Map<String, Object> map = taxCustomerService.importExcel(file);
        return APIResponse.success(map);
    }
    @ApiOperation("下载供应商商品失败数据模板")
    @RequestMapping(value = "download")
    public void download(@ApiParam(name = "jsonData",value = "失败的数据") @RequestParam(value="jsonData") String  jsonData, HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<Map<String,String>> list = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(jsonData);
        for (int i=0;i<jsonArray.size();i++) {
            Map<String,String> map=new HashMap<>();
            map.put("failMessage",jsonArray.getJSONObject(i).getString("failMessage"));
            map.put("customerPdName",jsonArray.getJSONObject(i).getString("customerPdName"));
            map.put("taxCode",jsonArray.getJSONObject(i).getString("taxCode"));
            list.add(map);
        }
        ExportExcel<Map<String,String>> ex = new ExportExcel<>();
        String[] header =
                { "失败原因","客户商品名称","税收分类编码"};
        String[] line =
                {"failMessage","categoryNo","supplyPdNo","supplyPdName","brandName","supplyPdRule","supplyPdSize","minUint","refrencePrice","recentEnquiry","ean13Code","mnemonicCode","productNo"};
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
    @ApiOperation("新增")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public APIResponse create(@RequestBody  @Valid TaxCustomer taxCustomer){
        taxCustomerService.save(taxCustomer);
        return APIResponse.success();
    }
    @ApiOperation("编辑")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public APIResponse edit(@RequestBody  @Valid TaxCustomer taxCustomer){
        taxCustomerService.update(taxCustomer);
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
