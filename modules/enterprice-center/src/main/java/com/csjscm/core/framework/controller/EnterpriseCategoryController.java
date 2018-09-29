package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseAttachment;
import com.csjscm.core.framework.model.EnterpriseCategory;
import com.csjscm.core.framework.service.enterprise.EnterpriseAttachmentService;
import com.csjscm.core.framework.service.enterprise.EnterpriseCategoryService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api("供应商分类")
@Controller
@RequestMapping("/enterprise/category")
@ResponseBody
public class EnterpriseCategoryController {
    @Autowired
    private EnterpriseCategoryService enterpriseCategoryService;
    /**
     * 供应商分类列表
     *
     * @param
     * @return
     */
    @ApiOperation("供应商分类列表")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseCategory> enterpriseCategories = enterpriseCategoryService.listSelective(map);
        return APIResponse.success(enterpriseCategories);
    }

    /**
     * 新建供应商分类
     *
     * @param
     * @return
     */
    @ApiOperation("新建供应商分类")
    @RequestMapping(value = "saveEnterpriseCategory",method = RequestMethod.POST)
    public APIResponse saveEnterpriseAttachment(@RequestBody @Valid EnterpriseCategory enterpriseCategory){
        enterpriseCategoryService.save(enterpriseCategory);
        return APIResponse.success();
    }

    /**
     * 更新供应商分类
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("更新供应商分类")
    @RequestMapping(value = "editEnterpriseCategory",method = RequestMethod.POST)
    public APIResponse editEnterpriseAttachment(
            @RequestBody  @Valid   EnterpriseCategory enterpriseCategory){
        if(enterpriseCategory.getId()!=null){
            enterpriseCategoryService.update(enterpriseCategory);
        }else {
            return APIResponse.fail("id不能为空");
        }
        return APIResponse.success();
    }
    @ApiOperation("审核时更新供应商分类")
    @RequestMapping(value = "updateState",method = RequestMethod.POST)
    public APIResponse updateState(
            @RequestBody  @Valid   EnterpriseCategory enterpriseCategory){
        if(enterpriseCategory.getId()!=null){
            enterpriseCategoryService.updateState(enterpriseCategory);
        }
        return APIResponse.success();
    }
    @ApiOperation("根据企业编码获取供应商分类信息")
    @RequestMapping(value = "getEnterpriseCategory",method = RequestMethod.GET)
    public APIResponse getEnterpriseCategory(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        EnterpriseCategory selective = enterpriseCategoryService.findSelective(map);
        return APIResponse.success(selective);
    }
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
