package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.example.TaxCategoryExample;
import com.csjscm.core.framework.example.TaxVersionExample;
import com.csjscm.core.framework.model.TaxCategory;
import com.csjscm.core.framework.model.TaxVersion;
import com.csjscm.core.framework.service.tax.TaxService;
import com.csjscm.sweet.framework.auth.AuthUtils;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Api("税务API")
@RequestMapping("/product/tax")
@RestController
@ResponseBody
public class TaxController {
    @Autowired
    private TaxService taxService;

    @ApiOperation("税务版本号列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="version",value="版本号",dataType = "String",paramType = "query")
    })
    @RequestMapping(value = "/taxVersion",method = RequestMethod.GET)
    public APIResponse<QueryResult<TaxVersion>> queryTaxVersionList(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,String> condition){
        TaxVersionExample example=new TaxVersionExample();
        if(condition!=null&&condition.containsKey("version")){
            example.setVersion(condition.get("version").trim());
        }
        QueryResult<TaxVersion> result=taxService.queryTaxVersion(page,rpp,example);
        return APIResponse.success(result);
    }

    @ApiOperation("税务版本号根据id查询")
    @RequestMapping(value = "/taxVersion/{id}",method = RequestMethod.GET)
    public APIResponse<TaxVersion> queryTaxVersionById(@PathVariable Integer id){
        return APIResponse.success(taxService.queryTaxVersionById(id));
    }

    @ApiOperation("新增税务版本号")
    @RequestMapping(value = "/taxVersion",method = RequestMethod.POST)
    public APIResponse addTaxVersion(@RequestBody TaxVersion taxVersion){
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        String userName=null;
        if(sessionUser!=null){
            userName=sessionUser.getString("name");
        }
        taxVersion.setCreateUser(userName);
        taxVersion.setEditUser(userName);
        taxService.addTaxVersion(taxVersion);
        return APIResponse.success();
    }

    @ApiOperation("修改税务版本号")
    @RequestMapping(value = "/taxVersion/{id}",method = RequestMethod.PUT)
    public APIResponse updateTaxVersion(
            @RequestBody TaxVersion taxVersion,
            @PathVariable Integer id){
        taxVersion.setId(id);
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        String userName=null;
        if(sessionUser!=null){
            userName=sessionUser.getString("name");
        }
        taxVersion.setEditUser(userName);
        taxService.updateTaxVersion(taxVersion);
        return APIResponse.success();
    }

    @ApiOperation("复制税务版本号")
    @RequestMapping(value = "/taxVersion/{id}/_copy",method = RequestMethod.POST)
    public APIResponse copyTaxVersion(@PathVariable Integer id){
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        String userName=null;
        if(sessionUser!=null){
            userName=sessionUser.getString("name");
        }
        taxService.copyTaxVersion(id,userName);
        return APIResponse.success("复制成功");
    }

    @ApiOperation("导入税务分类Excel")
    @RequestMapping(value = "/taxVersion/{versionId}/taxCategory/_import",method = RequestMethod.POST)
    public APIResponse importTaxCategoryExcel(
            @PathVariable Integer versionId,
            @ApiParam(name = "file",value = "excel文件")@RequestParam MultipartFile file){
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        String userName=null;
        if(sessionUser!=null){
            userName=sessionUser.getString("name");
        }
        taxService.importTaxCategoryExcel(userName,versionId,file);
        return APIResponse.success("导入成功");
    }

    @ApiOperation("根据税务版本ID获取税收编码树(全部编码)")
    @RequestMapping(value = "/taxVersion/{versionId}/taxCategory/_all",method = RequestMethod.GET)
    public APIResponse<List<TaxCategory>> queryTaxCategoryAll(@PathVariable Integer versionId){
        List<TaxCategory> result=taxService.queryTaxCategoryAll(versionId);
        return APIResponse.success(result);
    }

    @ApiOperation("根据条件查询税务编码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionId",value = "税务版本Id",dataType = "Integer",paramType = "path"),
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="taxCategoryName",value="分类名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "taxCode",value = "税务编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "parentCode",value = "父类编码",dataType="String",paramType = "query")
    })
    @RequestMapping(value = "/taxVersion/{versionId}/taxCategory",method = RequestMethod.GET)
    public APIResponse<QueryResult<TaxCategory>> queryTaxCategoryList(@PathVariable Integer versionId,
                                                                      @RequestParam(required = false,defaultValue = "1")int page,
                                                                      @RequestParam(required = false,defaultValue = "10")int rpp,
                                                                      @ApiIgnore @RequestParam Map<String,String> condition){
        TaxCategoryExample example=new TaxCategoryExample();
        if(condition!=null){
            example= JSON.parseObject(JSON.toJSONString(condition),TaxCategoryExample.class);
        }
        example.setVersionId(versionId);
        QueryResult<TaxCategory> result=taxService.queryTaxCategoryList(page,rpp,example);
        return APIResponse.success(result);
    }
}
