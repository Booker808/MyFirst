package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.model.SpSpecification;
import com.csjscm.core.framework.service.SpSpecificationService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("分类属性")
@Controller
@RequestMapping("/product/spSpecification")
@ResponseBody
public class SpSpecificationController {
    @Autowired
    private SpSpecificationService specificationService;

    /**
     * 查询分类接口
     *
     * @return
     */
    @ApiOperation("分类查询销售属性接口")
    @RequestMapping(value = "/listSpecBySell", method = RequestMethod.GET)
    public APIResponse<List<SpSpecification>> listSpecBySell(@RequestParam(value = "categoryId", required = true) Integer categoryId) {
        List<SpSpecification> sellSpecList = specificationService.listSelectiveBySell(categoryId);
        return APIResponse.success(sellSpecList);
    }

    /**
     * 查询分类接口
     *
     * @return
     */
    @ApiOperation("分类查询扩展属性接口")
    @RequestMapping(value = "/listSpecByExt", method = RequestMethod.GET)
    public APIResponse<List<SpSpecification>> listSpecByExt(@RequestParam(value = "categoryId", required = true) Integer categoryId) {
        List<SpSpecification> extSpecList = specificationService.listSelectiveByExt(categoryId);
        return APIResponse.success(extSpecList);
    }
}
