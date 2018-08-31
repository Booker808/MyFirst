package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/product/brand")
@ResponseBody
public class BrandController {

    @Autowired
    private BrandMasterService brandMasterService;
    /**
     * 查询品牌接口
     *
     * @param condition 查询条件
     * @return
     */
    @RequestMapping(value = "/brand",method = RequestMethod.GET)
    public APIResponse queryBrandList(@ApiParam(name="condition",value="品牌名称",required=true)@RequestParam String condition,
                                      @ApiParam(name="current",value="当前页",required=true) @RequestParam(value = "current") int current,
                                      @ApiParam(name="current",value="页面大小",required=true) @RequestParam(value = "pageSize") int pageSize){
        QueryResult<BrandMaster> page = brandMasterService.queryBrandList(condition, current, pageSize);
        return APIResponse.success(page);
    }

    /**
     * 查询目标为ID的品牌
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/brand/{id}",method = RequestMethod.GET)
    public APIResponse queryBrand(@ApiParam(name="id",value="主键id",required=true) @PathVariable Integer id){
        BrandMaster brandMaster = brandMasterService.selectByPrimaryKey(id);
        return APIResponse.success(brandMaster);
    }

    /**
     * 创建品牌对象
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "/brand",method = RequestMethod.POST)
    public APIResponse createBrand(@Valid BrandMaster brand){
        brandMasterService.insertSelective(brand);
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
    public APIResponse updateBrand(@RequestBody BrandMaster brand){
        if (null != brand.getId()) {
            brandMasterService.updateByPrimaryKeySelective(brand);
        }
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
        brandMasterService.deleteByPrimaryKey(id);
        return APIResponse.success();
    }

    /**
     * 删除指定品牌列表
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/brand",method = RequestMethod.DELETE)
    public APIResponse deleteBrandList(@ApiParam(name="ids",value="要删除的id，多个以逗号隔开",required=true) @RequestParam String ids){
        brandMasterService.deleteByIds(ids);
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
