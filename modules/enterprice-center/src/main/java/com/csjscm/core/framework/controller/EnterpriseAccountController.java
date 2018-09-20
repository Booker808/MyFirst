package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseAccount;
import com.csjscm.core.framework.model.EnterpriseAttachment;
import com.csjscm.core.framework.service.enterprise.EnterpriseAccountService;
import com.csjscm.core.framework.service.enterprise.EnterpriseAttachmentService;
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


@Api("企业账户信息")
@Controller
@RequestMapping("/enterprise/account")
@ResponseBody
public class EnterpriseAccountController {
    @Autowired
    private EnterpriseAccountService enterpriseAccountService;
    /**
     * 查询企业账户信息列表
     *
     * @param
     * @return
     */
    @ApiOperation("查询企业账户信息列表")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseAccount> enterpriseAccounts = enterpriseAccountService.listSelective(map);
        return APIResponse.success(enterpriseAccounts);
    }

    /**
     * 新建附件
     *
     * @param
     * @return
     */
    @ApiOperation("新建企业账户信息")
    @RequestMapping(value = "saveEnterpriseAttachment",method = RequestMethod.POST)
    public APIResponse saveEnterpriseAttachment(@RequestBody @Valid EnterpriseAccount enterpriseAccount){
        enterpriseAccountService.save(enterpriseAccount);
        return APIResponse.success();
    }

    /**
     * 更新企业账户
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("更新企业账户信息")
    @RequestMapping(value = "editEnterpriseAttachment",method = RequestMethod.POST)
    public APIResponse editEnterpriseAttachment(
            @RequestBody  @Valid   EnterpriseAccount enterpriseAccount){
        if(enterpriseAccount.getId()!=null){
            enterpriseAccountService.update(enterpriseAccount);
        }
        return APIResponse.success();
    }
    @ApiOperation("逻辑删除企业信息")
    @RequestMapping(value = "updateIsdelete",method = RequestMethod.GET)
    public APIResponse editEnterpriseAttachment(@ApiParam(name="id",value="企业账户信息id",required=true) @RequestParam Integer id){
        enterpriseAccountService.updateIsdelete(id);
        return APIResponse.success();
    }



    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
