package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.TradeTypeEnum;
import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.service.SpCategoryService;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.vo.EnterpriseInfoSPModel;
import com.csjscm.core.framework.vo.SkuPartnerModel;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
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
@RequestMapping("/outerface/shopping/center")
@ResponseBody
public class OutShoppingCenterController {
    @Autowired
    private SpCategoryService spCategoryService;
    @Autowired
    private RedisServiceFacade redisServiceFacade;
    @Autowired
    private ProductPartnerService productPartnerService;
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;
    @Autowired
    private SkuCoreService skuCoreService;
    @Autowired
    private ProductCustomerService productCustomerService;

    /**
     * 获取商城分类json
     *
     * @return
     */
    @RequestMapping(value = "/getJsonSpCategory",method = RequestMethod.GET)
    public APIResponse getJsonSpCategory(){
        if(!redisServiceFacade.exists(Constant.REDIS_KEY_JSON_SP_CATEGORY)){
            spCategoryService.getJsonCategory();
        }
        return APIResponse.success(redisServiceFacade.get(Constant.REDIS_KEY_JSON_SP_CATEGORY, JSONArray.class));
    }

    /**
     * 根据parentClass查询分类
     * @param parentClass
     * @return
     */
    @RequestMapping(value = "/getSpCategory",method = RequestMethod.GET)
    public APIResponse queryCategoryList(@RequestParam(value = "parentClass",required = false) String parentClass){
        if(StringUtils.isBlank(parentClass)){
            parentClass="0";
        }
        Map<String,Object> map=new HashMap<>();
        map.put("parentClass",parentClass);
        List<SpCategory> spCategories = spCategoryService.listSelective(map);
        return APIResponse.success(spCategories);
    }

    /**
     * 校验供应商企业、客户企业是否存在的接口
     * @param name

     * @return
     */
    @RequestMapping(value = "/checkEnterpriseName",method = RequestMethod.GET)
    public APIResponse checkPartnerName(@RequestParam(value = "name",required =true) String name,@RequestParam(value = "type",required =true) Integer type){
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.checkPartnerName(name,type);
        if(enterpriseInfo!=null){
            APIResponse apiResponse=new APIResponse();
            apiResponse.setMessage("企业名称已存在");
            apiResponse.setCode("fail");
            apiResponse.setData(enterpriseInfo);
            return apiResponse;
        }
        return APIResponse.success();
    }

    /**
     *   按类型查询企业
     * @param type
     * @return
     */
    @RequestMapping(value = "/getEnterpriseList",method = RequestMethod.GET)
    public APIResponse getEnterpriseList(@RequestParam(value = "type",required =true) Integer type){
        Map<String,Object> map=new HashMap<>();
        map.put("entType",type);
        List<EnterpriseInfo> enterpriseInfos = enterpriseInfoService.listSelective(map);
        return APIResponse.success(enterpriseInfos);
    }

    /**
     * 创建供应商 客户企业
     * @return
     */
 /*   @RequestMapping(value = "/createEnterprise",method = RequestMethod.POST)
    public APIResponse createEnterprise(EnterpriseInfoSPModel enterpriseInfoSPModel){
        BeanValidator.validate(enterpriseInfoSPModel);
        return APIResponse.success(enterpriseInfoService.saveSPEnterpriseInfo(enterpriseInfoSPModel));
    }*/
    @RequestMapping(value = "/createEnterprise",method = RequestMethod.POST)
    public APIResponse createEnterprise(@RequestParam(value = "name",required =true) String name,@RequestParam(value = "type",required =true) Integer type){
        if(type<1 || type>2){
            return APIResponse.fail("type值为1,2");
        }
        return APIResponse.success(enterpriseInfoService.saveSPEnterpriseInfo(name,type));
    }
    /**
     * 新建供应商商品档案接口
     * @return
     */
    @RequestMapping(value = "/createSkuPartner",method = RequestMethod.POST)
    public APIResponse createSkuPartner(SkuPartnerModel skuPartnerModel){
        BeanValidator.validate(skuPartnerModel);
        return APIResponse.success(productPartnerService.savePartner(skuPartnerModel));
    }
    /**
     * 获取供应商商品列表列表
     * @return
     */
    @RequestMapping(value = "/getSkuPartnerList",method = RequestMethod.GET)
    public APIResponse getSkuPartnerList(@RequestParam(value = "supplyNo",required =true) String supplyNo){
        Map<String,Object> map=new HashMap<>();
        map.put("supplyNo",supplyNo);
        List<SkuPartner> skuPartners = productPartnerService.listSelective(map);
        return APIResponse.success(skuPartners);
    }


    /**
     * 搜索平台商品
     *
     * @param productName
     * @param brandName
     * @param rule
     * @param minUint
     * @param size
     * @return
     */
    @RequestMapping(value = "searchSkuCore", method = RequestMethod.POST)
    public APIResponse searchSkuCore(String productName, String brandName, String rule, String minUint, String size, String productNo,Integer channel) {
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productName);
        map.put("brandName", brandName);
        map.put("rule", rule);
        map.put("minUint", minUint);
        map.put("productNo", productNo);
        map.put("sizes", size);
        map.put("channel", channel);
        boolean flag = false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return APIResponse.fail("必须要有一个搜索条件");
        }
        List<SkuCore> skuCores = skuCoreService.listSelective(map);
        return APIResponse.success(skuCores);
    }

    /**
     * 搜索供应商商品
     *
     * @param productName
     * @param brandName
     * @param rule
     * @param size
     * @param supplyNo
     * @return
     */
    @RequestMapping(value = "searchSkuPartner", method = RequestMethod.POST)
    public APIResponse searchSkuPartner(String productName, String brandName, String rule, String size, String supplyNo, String entName, String minUint) {
        Map<String, Object> map = new HashMap<>();
        map.put("supplyPdName", productName);
        map.put("brandName", brandName);
        map.put("minUint", minUint);
        map.put("supplyPdRule", rule);
        map.put("supplyPdSize", size);
        map.put("supplyNo", supplyNo);
        map.put("entName", entName);
        boolean flag = false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return APIResponse.fail("必须要有一个搜索条件");
        }
        List<SkuPartner> skuPartners = productPartnerService.listSelectiveSCM(map);
        return APIResponse.success(skuPartners);
    }

    /**
     * 搜索客户商品
     *
     * @param productName
     * @param rule
     * @param size
     * @param customerNo
     * @return
     */
    @RequestMapping(value = "searchSkuCustomer", method = RequestMethod.POST)
    public APIResponse searchSkuCustomer(String productName, String rule, String size, String customerNo, String entName,String brandName, String minUint) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerPdName", productName);
        map.put("customerPdRule", rule);
        map.put("brandName", brandName);
        map.put("minUint", minUint);
        map.put("customerPdSize", size);
        map.put("customerNo", customerNo);
        map.put("entName", entName);
        boolean flag = false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return APIResponse.fail("必须要有一个搜索条件");
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
