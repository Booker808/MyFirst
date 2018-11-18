package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.model.SpSpecification;
import com.csjscm.core.framework.service.SpSpecificationService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @Autowired
    private SpSpecificationService spSpecificationService;

    /**
     * 新增销售属性接口
     *
     * @return
     */
    @ApiOperation("新增三级分类下的销售属性")
    @RequestMapping(value = "/insertSpecOnSell", method = RequestMethod.POST)
    public APIResponse listSpecBySell(@ApiParam(name = "spec", value = "属性自带三级categoryId")
                                      @RequestBody SpSpecification spec,
                                      @ApiParam(name = "options", value = "属性值的集合") List<String> options) {
        specificationService.insertSellSpecByLv3Id(spec, options);
        //
        return APIResponse.success();
    }

    /**
     * 新增扩展属性接口
     *
     * @return
     */
    @ApiOperation("新增三级分类下的扩展属性")
    @RequestMapping(value = "/insertSpecOnExt", method = RequestMethod.POST)
    public APIResponse listSpecByExt(@ApiParam(name = "spec", value = "属性自带三级categoryId")
                                     @RequestBody SpSpecification spec,
                                     @ApiParam(name = "options", value = "属性值的集合") List<String> options) {
        specificationService.insertExtSpecByLv3Id(spec, options);
        return APIResponse.success();
    }

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
