package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseAccount;
import com.csjscm.core.framework.model.EnterpriseReceive;
import com.csjscm.core.framework.service.enterprise.EnterpriseAccountService;
import com.csjscm.core.framework.service.enterprise.EnterpriseReceiveService;
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


@Api("收发信息")
@Controller
@RequestMapping("/enterprise/receive")
@ResponseBody
public class EnterpriseReceiveController {
    @Autowired
    private EnterpriseReceiveService enterpriseReceiveService;
    /**
     * 查询收发信息信息列表
     *
     * @param
     * @return
     */
    @ApiOperation("查询收发信息列表")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseReceive> enterpriseReceives = enterpriseReceiveService.listSelective(map);
        return APIResponse.success(enterpriseReceives);
    }

    /**
     * 新建收发
     *
     * @param
     * @return
     */
    @ApiOperation("新建收发信息")
    @RequestMapping(value = "saveEnterpriseAttachment",method = RequestMethod.POST)
    public APIResponse saveEnterpriseAttachment(@RequestBody @Valid EnterpriseReceive enterpriseReceive){
        enterpriseReceiveService.save(enterpriseReceive);
        return APIResponse.success();
    }

    /**
     * 更新收发
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("更新收发信息")
    @RequestMapping(value = "editEnterpriseAttachment",method = RequestMethod.POST)
    public APIResponse editEnterpriseAttachment(
            @RequestBody  @Valid   EnterpriseReceive enterpriseReceive){
        if(enterpriseReceive.getId()!=null){
            enterpriseReceiveService.update(enterpriseReceive);
        }
        return APIResponse.success();
    }
    @ApiOperation("逻辑删除收发信息")
    @RequestMapping(value = "updateIsdelete",method = RequestMethod.GET)
    public APIResponse editEnterpriseAttachment(@ApiParam(name="id",value="收发信息id",required=true) @RequestParam Integer id){
        enterpriseReceiveService.updateIsdelete(id);
        return APIResponse.success();
    }
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
