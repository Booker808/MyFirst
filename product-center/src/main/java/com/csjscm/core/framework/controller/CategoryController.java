package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Api("商品分类")
@Controller
@RequestMapping("/product/category")
@ResponseBody
public class CategoryController{
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分类接口
     *
     * @return
     */
    @ApiOperation("商品查询分类接口")
    @RequestMapping(value = "/categoryPage",method = RequestMethod.GET)
    public APIResponse<QueryResult<Category>> queryCategoryList(@ApiParam(name="parentClass",value="上级分类id",required=false) @RequestParam(value = "parentClass",required = false) String parentClass,
                                         @ApiParam(name="current",value="当前页",required=true) @RequestParam(value = "current") int current,
                                         @ApiParam(name="pageSize",value="页面大小",required=true) @RequestParam(value = "pageSize") int pageSize){
        if(StringUtils.isBlank(parentClass)){
           parentClass="0";
       }
        Map<String,Object> map=new HashMap<>();
        map.put("parentClass",parentClass);
        QueryResult<Category> page = categoryService.findPage(map, current, pageSize);
        return APIResponse.success(page);
    }

    /**
     * 查询目标为ID的分类
     *
     * @param id
     * @return
     */
    @ApiOperation("查询目标为ID的分类")
    @RequestMapping(value = "/category/{id}",method = RequestMethod.GET)
    public APIResponse queryCategory(@ApiParam(name="id",value="主键id",required=true) @PathVariable Integer id){
        return APIResponse.success(categoryService.findByPrimary(id));
    }

    /**
     * 创建分类对象
     *
     * @param category
     * @return
     */
    @ApiOperation("创建分类对象")
    @RequestMapping(value = "/category",method = RequestMethod.POST)
    public APIResponse createCategory(@Valid Category category){
        categoryService.save(category);
        return APIResponse.success();
    }

    /**
     * 更新指定分类
     *
     * @param
     * @param category 分类对象
     * @return
     */
    @RequestMapping(value = "/category/{id}",method = RequestMethod.PUT)
    public APIResponse updateCategory(
            @RequestBody Category category){
        if(category.getId()!=null){
            categoryService.update(category);
        }
        return APIResponse.success();
    }

    /**
     * 删除指定ID的分类对象
     *
     * @param id
     * @return
     */
/*    @RequestMapping(value = "/category/{id}",method = RequestMethod.DELETE)
    public APIResponse deleteCategory(@PathVariable Integer id){
        System.out.println("----------删除指定ID分类："+id);
        return APIResponse.success();
    }*/

    /**
     * 删除指定分类列表
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/category",method = RequestMethod.DELETE)
    public APIResponse deleteCategoryList(@ApiParam(name="ids",value="要删除的id，多个以逗号隔开",required=true)@RequestParam String ids){
        categoryService.deleteByIds(ids);
        return APIResponse.success();
    }

    /**
     * 从文件导入分类
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public APIResponse importCategory(MultipartFile file){
        System.out.println("---------------从文件导入分类"+file.getName());
        return APIResponse.success();
    }

    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
