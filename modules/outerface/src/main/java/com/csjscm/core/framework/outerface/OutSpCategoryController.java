package com.csjscm.core.framework.outerface;

import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.sweet.framework.core.cloud.annotation.CloudServiceResource;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * DESC:
 * created with com.csjscm.core.framework.outerface
 * Created by paean
 * Email:875931563@qq.com
 * Date: 2018/9/11.
 */

@RestController
@Slf4j
@RequestMapping("/outerface/category")
@CloudServiceResource(scope = CloudServiceResource.Scope.Global)
public class OutSpCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分类接口
     * TODO
     *
     * @return
     */
    @ApiOperation("商品查询分类接口")
    @RequestMapping(value = "/categoryPage", method = RequestMethod.GET)
    public APIResponse queryCategoryList(
            @ApiParam(name = "parentClass", value = "上级分类id", required = false) @RequestParam(value = "parentClass", required = false) String parentClass,
            @ApiParam(name = "current", value = "当前页", required = true) @RequestParam(value = "current") int current,
            @ApiParam(name = "pageSize", value = "页面大小", required = true) @RequestParam(value = "pageSize") int pageSize) {
        if (StringUtils.isBlank(parentClass)) {
            parentClass = "0";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("parentClass", parentClass);
        QueryResult<Category> page = categoryService.findPage(map, current, pageSize);
        return APIResponse.success(page);
    }
}
