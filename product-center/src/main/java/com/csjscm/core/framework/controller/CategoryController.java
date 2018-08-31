package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.model.Category;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product/category")
@ResponseBody
public class CategoryController {

    /**
     * 查询分类接口
     *
     * @param condition 查询条件
     * @return
     */
    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public APIResponse queryCategoryList(@RequestParam Map<String,String> condition){
        System.out.println("------------------以条件查询分类列表："+JSON.toJSONString(condition));
        return APIResponse.success();
    }

    /**
     * 查询目标为ID的分类
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/category/{id}",method = RequestMethod.GET)
    public APIResponse queryCategory(@PathVariable Integer id){
        System.out.println("------------------以ID查询单个分类："+id);
        return APIResponse.success();
    }

    /**
     * 创建分类对象
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/category",method = RequestMethod.POST)
    public APIResponse createCategory(@RequestBody Category category){
        System.out.println("------------------创建分类对象："+JSON.toJSONString(category));
        return APIResponse.success();
    }

    /**
     * 更新指定分类
     *
     * @param id 分类ID
     * @param category 分类对象
     * @return
     */
    @RequestMapping(value = "/category/{id}",method = RequestMethod.PUT)
    public APIResponse updateCategory(@PathVariable Integer id,
            @RequestBody Category category){
        System.out.println("------------------修改分类对象：id:"+id+",对象："+JSON.toJSONString(category));
        return APIResponse.success();
    }

    /**
     * 删除指定ID的分类对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/category/{id}",method = RequestMethod.DELETE)
    public APIResponse deleteCategory(@PathVariable Integer id){
        System.out.println("----------删除指定ID分类："+id);
        return APIResponse.success();
    }

    /**
     * 删除指定分类列表
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/category",method = RequestMethod.DELETE)
    public APIResponse deleteCategoryList(@RequestParam List<Integer> ids){
        System.out.println("----------删除指定ID列表的分类："+JSON.toJSONString(ids));
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
}
