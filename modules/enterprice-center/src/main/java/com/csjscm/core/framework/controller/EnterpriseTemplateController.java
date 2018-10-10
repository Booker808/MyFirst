package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.example.EnterprisePurchaseTemplateExample;
import com.csjscm.core.framework.model.EnterpriseStandardTemplate;
import com.csjscm.core.framework.service.template.EnterpriseTemplateService;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateDetailVo;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateVo;
import com.csjscm.sweet.framework.auth.AuthUtils;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api("采购模板")
@RestController
@RequestMapping("/enterprise/template")
@ResponseBody
public class EnterpriseTemplateController {
    @Autowired
    private EnterpriseTemplateService enterpriseTemplateService;

    @ApiOperation("新增标准模板")
    @RequestMapping(value = "/standardTemplate",method = RequestMethod.POST)
    public APIResponse addStandardTemplate(@RequestBody EnterpriseStandardTemplate template){
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        if(sessionUser!=null){
            template.setCreateUser(sessionUser.getString("name"));
            template.setEditUser(sessionUser.getString("name"));
        }
        enterpriseTemplateService.addStandardTemplate(template);
        return APIResponse.success("新增成功");
    }

    @ApiOperation("更新标准模板")
    @RequestMapping(value = "/standardTemplate",method = RequestMethod.PUT)
    public APIResponse updateStandardTemplate(@RequestBody EnterpriseStandardTemplate template){
        if(template.getId()==null){
            return APIResponse.fail("更新模板ID不能为空");
        }
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        if(sessionUser!=null){
            template.setEditUser(sessionUser.getString("name"));
        }
        enterpriseTemplateService.updateStandardTemplate(template);
        return APIResponse.success("更新成功");
    }

    @ApiOperation("获取标准模板列表")
    @RequestMapping(value = "/standardTemplate",method = RequestMethod.GET)
    public APIResponse<List<EnterpriseStandardTemplate>> queryStandardTemplateList(){
        return APIResponse.success(enterpriseTemplateService.queryStandardTemplate());
    }

    @ApiOperation("获取指定标准模板")
    @RequestMapping(value = "/standardTemplate/{id}",method = RequestMethod.GET)
    public APIResponse<EnterpriseStandardTemplate> queryStandardTemplateById(@PathVariable Integer id){
        return APIResponse.success(enterpriseTemplateService.queryStandardTemplateById(id));
    }

    @ApiOperation("新增采购合同模板")
    @RequestMapping(value = "/purchaseTemplate",method = RequestMethod.POST)
    public APIResponse addPurchaseTemplate(@RequestBody @Valid EnterprisePurchaseTemplateDetailVo templateDetailVo){
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        if(sessionUser!=null){
            templateDetailVo.setCreateUser(sessionUser.getString("name"));
            templateDetailVo.setEditUser(sessionUser.getString("name"));
        }
        enterpriseTemplateService.addPurchaseTemplate(templateDetailVo);
        if(templateDetailVo.getIsSubmit()!=null && templateDetailVo.getIsSubmit() ==1){
            enterpriseTemplateService.submitPurchaseTemplate(templateDetailVo);
        }
        return APIResponse.success();
    }

    @ApiOperation("更新采购合同模板")
    @RequestMapping(value = "/purchaseTemplate",method = RequestMethod.PUT)
    public APIResponse updatePurchaseTemplate(@RequestBody @Valid EnterprisePurchaseTemplateDetailVo templateDetailVo){
        JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
        if(sessionUser!=null){
            templateDetailVo.setEditUser(sessionUser.getString("name"));
        }
        if(templateDetailVo.getId()==null){
            return APIResponse.fail("更新模板ID不能为空");
        }
        enterpriseTemplateService.updatePurchaseTemplate(templateDetailVo);
        if(templateDetailVo.getIsSubmit()!=null && templateDetailVo.getIsSubmit() ==1){
            enterpriseTemplateService.submitPurchaseTemplate(templateDetailVo);
        }
        return APIResponse.success();
    }

    @ApiOperation("获取采购合同列表")
    @RequestMapping(value = "/purchaseTemplate",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="entName",value = "企业名称",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="entNumber",value="企业编码",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "payType",value = "1：票到货到付款 2：预付款",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "templateType",value = "1：A类 2：B类 3：C类",dataType = "String",paramType = "query")
    })
    public APIResponse<QueryResult<EnterprisePurchaseTemplateVo>> queryEnterprisePurchaseTemplate(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,Object> condition){
        EnterprisePurchaseTemplateExample templateExample=new EnterprisePurchaseTemplateExample();
        if(condition!=null){
            templateExample= JSON.parseObject(JSON.toJSONString(condition),EnterprisePurchaseTemplateExample.class);
        }
        QueryResult<EnterprisePurchaseTemplateVo> result=enterpriseTemplateService.queryPurchaseTemplate(page,rpp,templateExample);
        return APIResponse.success(result);
    }

    @ApiOperation("获取指定ID的采购合同")
    @RequestMapping(value = "/purchaseTemplate/{id}",method = RequestMethod.GET)
    public APIResponse<EnterprisePurchaseTemplateDetailVo> queryPurchaseTemplateById(@PathVariable Integer id){
        return APIResponse.success(enterpriseTemplateService.queryPurchaseTemplateById(id));
    }
}
