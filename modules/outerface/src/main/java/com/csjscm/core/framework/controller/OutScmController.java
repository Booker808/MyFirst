package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.model.SkuCustomer;
import com.csjscm.core.framework.model.SkuPartner;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.vo.SkuCoreSCMMolde;
import com.csjscm.core.framework.vo.SkuCustomerSCMMolde;
import com.csjscm.core.framework.vo.SkuPartnerSCMMolde;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
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

    /**
     * 新增来自scm的平台商品
     * @param skuCoreSMMolde
     * @return
     */
    @RequestMapping(value = "createSkuCore",method = RequestMethod.POST)
    public APIResponse createSkuCore(SkuCoreSCMMolde skuCoreSMMolde){
        BeanValidator.validate(skuCoreSMMolde);
        SkuCoreSCMMolde skuCoreSMMolde1 = skuCoreService.saveSCMSkuCore(skuCoreSMMolde);
        return APIResponse.success(skuCoreSMMolde1);
    }
    /**
     * 新增来自scm的 供应商商品数据
     * @param skuPartnerSCMMolde
     * @return
     */
    @RequestMapping(value = "createSkuPartner",method = RequestMethod.POST)
    public APIResponse createSkuPartner(SkuPartnerSCMMolde skuPartnerSCMMolde){
        BeanValidator.validate(skuPartnerSCMMolde);
        SkuPartner skuPartner = productPartnerService.saveSCMSkuPartner(skuPartnerSCMMolde);
        return APIResponse.success();
    }
    /**
     * 新增来自scm的 客户数据
     * @param skuCustomerSCMMolde
     * @return
     */
    @RequestMapping(value = "createSkuCustomer",method = RequestMethod.POST)
    public APIResponse createSkuCustomer(SkuCustomerSCMMolde skuCustomerSCMMolde){
        BeanValidator.validate(skuCustomerSCMMolde);
        SkuCustomer skuCustomer = productCustomerService.saveSCMSkuCustomer(skuCustomerSCMMolde);
        return APIResponse.success();
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
    public APIResponse searchSkuCore(@RequestParam(name = "productName",required = true) String productName,String brandName,String rule,String minUint,String size){
        Map<String,Object> map=new HashMap<>();
        map.put("productName",productName);
        map.put("brandName",brandName);
        map.put("rule",rule);
        map.put("minUint",minUint);
        map.put("size",size);
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
    public APIResponse searchSkuPartner(@RequestParam(name = "productName",required = true) String productName,String brandName,String rule,String size,String supplyNo){
        Map<String,Object> map=new HashMap<>();
        map.put("supplyPdName",productName);
        map.put("brandName",brandName);
        map.put("supplyPdRule",rule);
        map.put("supplyPdSize",size);
        map.put("supplyNo",supplyNo);
        List<SkuPartner> skuPartners = productPartnerService.listSelective(map);
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
    public APIResponse searchSkuCustomer(@RequestParam(name = "productName",required = true) String productName,String rule,String size,String customerNo){
        Map<String,Object> map=new HashMap<>();
        map.put("customerPdName",productName);
        map.put("customerPdRule",rule);
        map.put("customerPdSize",size);
        map.put("customerNo",customerNo);
        List<SkuCustomer> skuCustomers = productCustomerService.listSelective(map);
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
