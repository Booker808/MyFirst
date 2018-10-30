package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.service.SpCategoryService;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.service.tax.TaxCustomerService;
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
@RequestMapping("/outerface/tiger/product")
@ResponseBody
public class OutTigerController {

    @Autowired
    private TaxCustomerService taxCustomerService;

    /**
     *  客户商品与税收关联记录列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getTaxCustomerList",method = RequestMethod.GET)
    public APIResponse getEnterpriseList(@RequestParam(value = "customerPdName",required =false) String customerPdName,@RequestParam(value = "taxCode",required =false) String taxCode,
    @RequestParam(value = "taxCategoryName",required =false) String taxCategoryName){
        Map<String,Object> map=new HashMap<>();
        map.put("customerPdName",customerPdName);
        map.put("taxCode",taxCode);
        map.put("taxCategoryName",taxCategoryName);
        List<TaxCustomer> taxCustomers = taxCustomerService.listSelective(map);
        return APIResponse.success(taxCustomers);
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
