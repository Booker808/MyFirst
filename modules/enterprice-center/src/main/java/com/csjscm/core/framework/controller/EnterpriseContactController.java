package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.EnterpriseContact;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.core.framework.service.enterprise.EnterpriseContactService;
import com.csjscm.core.framework.vo.CategoryModel;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api("联系人")
@Controller
@RequestMapping("/enterprise/contact")
@ResponseBody
public class EnterpriseContactController {
    @Autowired
    private EnterpriseContactService enterpriseContactService;
    /**
     * 查询联系人列表
     *
     * @param
     * @return
     */
    @ApiOperation("查询联系人列表")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseContact> enterpriseContacts = enterpriseContactService.listSelective(map);
        return APIResponse.success(enterpriseContacts);
    }

    /**
     * 新建联系人
     *
     * @param
     * @return
     */
    @ApiOperation("新建联系人")
    @RequestMapping(value = "saveEnterpriseContact",method = RequestMethod.POST)
    public APIResponse createCategory(@RequestBody @Valid EnterpriseContact enterpriseContact){
        enterpriseContactService.save(enterpriseContact);
        return APIResponse.success();
    }

    /**
     * 更新联系人
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("更新联系人")
    @RequestMapping(value = "editEnterpriseContact",method = RequestMethod.POST)
    public APIResponse updateCategory(
            @RequestBody  @Valid  EnterpriseContact enterpriseContact){
        if(enterpriseContact.getId()!=null){
            enterpriseContactService.update(enterpriseContact);
        }
        return APIResponse.success();
    }
    @ApiOperation("逻辑删联系人")
    @RequestMapping(value = "updateIsdelete",method = RequestMethod.GET)
    public APIResponse editEnterpriseAttachment(@ApiParam(name="id",value="联系人id",required=true) @RequestParam Integer id){
        enterpriseContactService.updateIsdelete(id);
        return APIResponse.success();
    }


    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
