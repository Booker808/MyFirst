package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.model.SpCategory;
import com.csjscm.core.framework.service.SpCategoryService;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
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
     * @param type
     * @return
     */
    @RequestMapping(value = "/checkEnterpriseName",method = RequestMethod.GET)
    public APIResponse checkPartnerName(@RequestParam(value = "name",required =true) String name,@RequestParam(value = "type",required =true) Integer type){
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.checkPartnerName(name, type);
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
     * 创建供应商 客户企业
     * @return
     */
    @RequestMapping(value = "/createEnterprise",method = RequestMethod.POST)
    public APIResponse createEnterprise(EnterpriseInfoSPModel enterpriseInfoSPModel){
        BeanValidator.validate(enterpriseInfoSPModel);
        return APIResponse.success(enterpriseInfoService.saveSPEnterpriseInfo(enterpriseInfoSPModel));
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
