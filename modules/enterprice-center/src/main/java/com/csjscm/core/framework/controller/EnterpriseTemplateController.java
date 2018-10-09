package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.model.EnterpriseStandardTemplate;
import com.csjscm.core.framework.service.template.EnterpriseTemplateService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("采购模板")
//@RestController
@RequestMapping("/enterprise/template")
@ResponseBody
public class EnterpriseTemplateController {
    @Autowired
    private EnterpriseTemplateService enterpriseTemplateService;

    @ApiOperation("新增标准模板")
    @RequestMapping(value = "/standardTemplate",method = RequestMethod.POST)
    public APIResponse addStandardTemplate(@RequestBody EnterpriseStandardTemplate template){
        enterpriseTemplateService.addStandardTemplate(template);
        return APIResponse.success("新增成功");
    }

    @ApiOperation("更新标准模板")
    @RequestMapping(value = "/standardTemplate",method = RequestMethod.POST)
    public APIResponse updateStandardTemplate(@RequestBody EnterpriseStandardTemplate template){
        if(template.getId()==null){
            return APIResponse.fail("更新模板ID不能为空");
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
}
