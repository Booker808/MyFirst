package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.vo.*;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/outerface/scm/")
@ResponseBody
//@CloudServiceResource(scope = CloudServiceResource.Scope.Group)
public class OutScmController {

    @Autowired
    private SkuCoreService skuCoreService;
    @Autowired
    private ProductPartnerService productPartnerService;
    @Autowired
    private ProductCustomerService productCustomerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisServiceFacade redisServiceFacade;

    @Autowired
    private BrandMasterService brandMasterService;
    /**
     * 查询品牌名称列表
     * @return
     */
    @ApiOperation("查询品牌名称列表")
    @RequestMapping(value = "/brandNameList",method = RequestMethod.GET)
    public APIResponse queryBrandNameListSky(){
        if(!redisServiceFacade.exists(Constant.REDIS_KEY_JSONSTR_BRAND)){
            List<BrandMaster> brandList = brandMasterService.selectByBrandNameSky();
            redisServiceFacade.set(Constant.REDIS_KEY_JSONSTR_BRAND,  JSONArray.parseArray(JSON.toJSONString(brandList)));
        }
        return APIResponse.success(redisServiceFacade.get(Constant.REDIS_KEY_JSONSTR_BRAND,JSONArray.class));
    }
    /**
     * 获取分类json
     *
     * @return
     */
    @RequestMapping(value = "/getJsonCategory",method = RequestMethod.GET)
    public APIResponse getJsonSpCategory(){
        if(!redisServiceFacade.exists(Constant.REDIS_KEY_JSON_CATEGORY)){
            categoryService.getJsonCategory();
        }
        return APIResponse.success(redisServiceFacade.get(Constant.REDIS_KEY_JSON_CATEGORY, JSONArray.class));
    }

    /**
     * 根据parentClass查询分类
     * @param parentClass
     * @return
     */
    @RequestMapping(value = "/getCategory",method = RequestMethod.GET)
    public APIResponse queryCategoryList(String parentClass,String levelNum,String classCode,String className,String id){
        Map<String,Object> map=new HashMap<>();
        map.put("parentClass",parentClass);
        map.put("levelNum",levelNum);
        map.put("classCode",classCode);
        map.put("className",className);
        map.put("id",id);
        List<Category> spCategories = categoryService.listSelective(map);
        return APIResponse.success(spCategories);
    }

    /**
     * 新增来自scm的平台商品
     * @param skuCoreSMMolde
     * @return
     */
/*    @RequestMapping(value = "createSkuCore",method = RequestMethod.POST)
    public APIResponse createSkuCore(SkuCoreSCMMolde skuCoreSMMolde){
        BeanValidator.validate(skuCoreSMMolde);
        SkuCoreSCMMolde skuCoreSMMolde1 = skuCoreService.saveSCMSkuCore(skuCoreSMMolde);
        return APIResponse.success(skuCoreSMMolde1);
    }*/
    /**
     * 新增来自scm的 供应商商品数据
     * @param json
     * @return
     */
    @RequestMapping(value = "createSkuPartner",method = RequestMethod.POST)
    public APIResponse createSkuPartner(String json){
        ScmPartnerVo scmPartnerVo = productPartnerService.saveSCMSkuPartner(json);
        return APIResponse.success(scmPartnerVo);
    }
    /**
     * 新增来自scm的 客户数据
     * @param json
     * @return
     */
    @RequestMapping(value = "createSkuCustomer",method = RequestMethod.POST)
    public APIResponse createSkuCustomer(@RequestParam(name = "json") String json){
        ScmCustomerVo scmCustomerVo = productCustomerService.saveSCMSkuCustomer(json);
        return APIResponse.success(scmCustomerVo);
    }

    /**
     * 搜索平台商品
     * @param productName
     * @param brandName
     * @param rule
     * @param minUint
     * @param size
     * @return
     */
    @RequestMapping(value = "searchSkuCore",method = RequestMethod.POST)
    public APIResponse searchSkuCore(String productName,String brandName,String rule,String minUint,String size ,String productNo){
        Map<String,Object> map=new HashMap<>();
        map.put("productName",productName);
        map.put("brandName",brandName);
        map.put("rule",rule);
        map.put("minUint",minUint);
        map.put("productNo",productNo);
        map.put("sizes",size);
        boolean flag=false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue()!=null && StringUtils.isNotBlank(entry.getValue().toString())){
                flag=true;
                break;
            }
        }
        if(!flag){
            return  APIResponse.fail("必须要有一个搜索条件");
        }
        List<SkuCore> skuCores = skuCoreService.listSelective(map);
        return APIResponse.success(skuCores);
    }

    /**
     * 搜索供应商商品
     * @param productName
     * @param brandName
     * @param rule
     * @param size
     * @param supplyNo
     * @return
     */
    @RequestMapping(value = "searchSkuPartner",method = RequestMethod.POST)
    public APIResponse searchSkuPartner(String productName,String brandName,String rule,String size,String supplyNo,String entName){
        Map<String,Object> map=new HashMap<>();
        map.put("supplyPdName",productName);
        map.put("brandName",brandName);
        map.put("supplyPdRule",rule);
        map.put("supplyPdSize",size);
        map.put("supplyNo",supplyNo);
        map.put("entName",entName);
        boolean flag=false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue()!=null && StringUtils.isNotBlank(entry.getValue().toString())){
                flag=true;
                break;
            }
        }
        if(!flag){
            return  APIResponse.fail("必须要有一个搜索条件");
        }
        List<SkuPartner> skuPartners = productPartnerService.listSelectiveSCM(map);
        return APIResponse.success(skuPartners);
    }

    /**
     * 搜索客户商品
     * @param productName
     * @param rule
     * @param size
     * @param customerNo
     * @return
     */
    @RequestMapping(value = "searchSkuCustomer",method = RequestMethod.POST)
    public APIResponse searchSkuCustomer(String productName,String rule,String size,String customerNo,String entName){
        Map<String,Object> map=new HashMap<>();
        map.put("customerPdName",productName);
        map.put("customerPdRule",rule);
        map.put("customerPdSize",size);
        map.put("customerNo",customerNo);
        map.put("entName",entName);
        boolean flag=false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue()!=null && StringUtils.isNotBlank(entry.getValue().toString())){
                flag=true;
                break;
            }
        }
        if(!flag){
            return  APIResponse.fail("必须要有一个搜索条件");
        }
        List<SkuCustomer> skuCustomers = productCustomerService.listSelectiveSCM(map);
        return APIResponse.success(skuCustomers);
    }



    /**
     * 自定义异常捕获
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
    @ExceptionHandler(ValidationException.class)
    public APIResponse validationExp(ValidationException e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
