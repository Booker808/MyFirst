package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.service.template.EnterpriseTemplateService;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateDetailVo;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outerface/template/")
@ResponseBody
public class OutPurchaseTemplateController {
    @Autowired
    private EnterpriseTemplateService templateService;

    @RequestMapping(value = "/purchaseTemplateDetail",method = RequestMethod.GET)
    public APIResponse<EnterprisePurchaseTemplateDetailVo> getPurchaseTemplateDetail(
            @RequestParam String entNumber){
        return APIResponse.success(templateService.queryPurchaseTemplateByEntNumber(entNumber));
    }
}
