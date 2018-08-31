package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product/brand")
@ResponseBody
public class BrandController {

    /**
     * 查询品牌接口
     *
     * @param condition 查询条件
     * @return
     */
    @RequestMapping(value = "/brand",method = RequestMethod.GET)
    public APIResponse queryBrandList(@RequestParam Map<String,String> condition){
        System.out.println("------------------以条件查询品牌列表："+ JSON.toJSONString(condition));
        return APIResponse.success();
    }

    /**
     * 查询目标为ID的品牌
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/brand/{id}",method = RequestMethod.GET)
    public APIResponse queryBrand(@PathVariable Integer id){
        System.out.println("------------------以ID查询单个品牌："+id);
        return APIResponse.success();
    }

    /**
     * 创建品牌对象
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "/brand",method = RequestMethod.POST)
    public APIResponse createBrand(@RequestBody BrandMaster brand){
        System.out.println("------------------创建品牌对象："+JSON.toJSONString(brand));
        return APIResponse.success();
    }

    /**
     * 更新指定品牌
     *
     * @param id 品牌ID
     * @param brand 品牌对象
     * @return
     */
    @RequestMapping(value = "/brand/{id}",method = RequestMethod.PUT)
    public APIResponse updateBrand(@PathVariable Integer id,
                                      @RequestBody BrandMaster brand){
        System.out.println("------------------修改品牌对象：id:"+id+",对象："+JSON.toJSONString(brand));
        return APIResponse.success();
    }

    /**
     * 删除指定ID的品牌对象
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/brand/{id}",method = RequestMethod.DELETE)
    public APIResponse deleteBrand(@PathVariable Integer id){
        System.out.println("----------删除指定ID品牌："+id);
        return APIResponse.success();
    }

    /**
     * 删除指定品牌列表
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/brand",method = RequestMethod.DELETE)
    public APIResponse deleteBrandList(@RequestParam List<Integer> ids){
        System.out.println("----------删除指定ID列表的品牌："+JSON.toJSONString(ids));
        return APIResponse.success();
    }

    /**
     * 从文件导入品牌
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public APIResponse importBrand(MultipartFile file){
        System.out.println("---------------从文件导入品牌"+file.getName());
        return APIResponse.success();
    }
}
