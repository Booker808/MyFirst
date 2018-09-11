package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.SpCategory;
import com.csjscm.core.framework.service.SpCategoryService;
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


@Api("sp商品分类")
@Controller
@RequestMapping("/product/spCategory")
@ResponseBody
public class SpCategoryController {
    @Autowired
    private SpCategoryService spCategoryService;

    /**
     * 查询分类接口
     *
     * @return
     */
    @ApiOperation("sp商品查询分类接口")
    @RequestMapping(value = "/categoryPage",method = RequestMethod.GET)
    public APIResponse<QueryResult<SpCategory>> queryCategoryList(@ApiParam(name="parentClass",value="上级分类id",required=false) @RequestParam(value = "parentClass",required = false) String parentClass,
                                                                  @ApiParam(name="current",value="当前页",required=true) @RequestParam(value = "current") int current,
                                                                  @ApiParam(name="pageSize",value="页面大小",required=true) @RequestParam(value = "pageSize") int pageSize){
        if(StringUtils.isBlank(parentClass)){
           parentClass="0";
       }
        Map<String,Object> map=new HashMap<>();
        map.put("parentClass",parentClass);
        QueryResult<SpCategory> page = spCategoryService.findPage(map, current, pageSize);
        return APIResponse.success(page);
    }

    /**
     * 查询目标为ID的分类
     *
     * @param id
     * @return
     */
    @ApiOperation("sp查询目标为ID的分类")
    @RequestMapping(value = "getCategory",method = RequestMethod.GET)
    public APIResponse queryCategory(@ApiParam(name="id",value="主键id",required=true) Integer id){
        return APIResponse.success(spCategoryService.findByPrimary(id));
    }

    /**
     * 创建分类对象
     *
     * @param category
     * @return
     */
    @ApiOperation("sp新增分类对象")
    @RequestMapping(value = "saveCategory",method = RequestMethod.POST)
    public APIResponse createCategory(@RequestBody @Valid SpCategory category){
        spCategoryService.save(category);
        return APIResponse.success();
    }

    /**
     * 更新指定分类
     *
     * @param
     * @param category 分类对象
     * @return
     */
    @ApiOperation("sp编辑分类接口")
    @RequestMapping(value = "editCategory",method = RequestMethod.POST)
    public APIResponse updateCategory(
            @RequestBody  @Valid  SpCategory category){
        if(category.getId()!=null){
            spCategoryService.update(category);
        }
        return APIResponse.success();
    }

    /**
     * 删除指定分类列表
     *
     * @param ids
     * @return
     */
    @ApiOperation("删除分类接口")
    @RequestMapping(value = "deleteCategory",method = RequestMethod.GET)
    public APIResponse deleteCategoryList(@ApiParam(name="ids",value="要删除的id，多个以逗号隔开",required=true) @RequestParam(value = "ids") String ids){
        spCategoryService.deleteByIds(ids);
        return APIResponse.success();
    }
    /**
     * 启用停用分类接口
     *
     * @param
     * @return
     */
    @ApiOperation("sp启用停用分类接口")
    @RequestMapping(value = "updateState",method = RequestMethod.POST)
    public APIResponse updateState(@RequestBody CategoryModel categoryModel){
        List<Integer> idList=new ArrayList<>();
        String[] strings=categoryModel.getIds().split(",");
        for (String s : strings) {
            idList.add(Integer.parseInt(s));
        }
        spCategoryService.updateState(idList,categoryModel.getState());
        return APIResponse.success();
    }
    @ApiOperation("sp编辑修改删除ufd")
    @RequestMapping(value = "editUdf",method = RequestMethod.POST)
    public APIResponse editUdf(@RequestBody Map<String,Object> mnp){
        spCategoryService.updateUdf(mnp);
        return APIResponse.success();
    }
    
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
}
