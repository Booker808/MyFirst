package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseAttachment;
import com.csjscm.core.framework.model.EnterpriseContact;
import com.csjscm.core.framework.service.enterprise.EnterpriseAttachmentService;
import com.csjscm.core.framework.service.enterprise.EnterpriseContactService;
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


@Api("企业附件")
@Controller
@RequestMapping("/enterprise/attachment")
@ResponseBody
public class EnterpriseAttachmentController {
    @Autowired
    private EnterpriseAttachmentService enterpriseAttachmentService;
    /**
     * 查询附件列表
     *
     * @param
     * @return
     */
    @ApiOperation("查询附件列表")
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public APIResponse querylist(@ApiParam(name="entNumber",value="企业编码",required=true) String entNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("entNumber",entNumber);
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        List<EnterpriseAttachment> enterpriseAttachments = enterpriseAttachmentService.listSelective(map);
        return APIResponse.success(enterpriseAttachments);
    }

    /**
     * 新建附件
     *
     * @param
     * @return
     */
    @ApiOperation("新建附件")
    @RequestMapping(value = "saveEnterpriseAttachment",method = RequestMethod.POST)
    public APIResponse saveEnterpriseAttachment(@RequestBody @Valid EnterpriseAttachment enterpriseAttachment){
        enterpriseAttachmentService.save(enterpriseAttachment);
        return APIResponse.success();
    }

    /**
     * 更新附件
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("更新附件")
    @RequestMapping(value = "editEnterpriseAttachment",method = RequestMethod.POST)
    public APIResponse editEnterpriseAttachment(
            @RequestBody  @Valid  EnterpriseAttachment enterpriseAttachment){
        if(enterpriseAttachment.getId()!=null){
            enterpriseAttachmentService.update(enterpriseAttachment);
        }
        return APIResponse.success();
    }
    @ApiOperation("逻辑删除企业信息")
    @RequestMapping(value = "updateIsdelete",method = RequestMethod.GET)
    public APIResponse updateIsdelete(@ApiParam(name="id",value="附件id",required=true) @RequestParam Integer id){
        enterpriseAttachmentService.updateIsdelete(id);
        return APIResponse.success();
    }



    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
