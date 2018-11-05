package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.TaxCategory;
import com.csjscm.core.framework.model.TaxCustomer;
import com.csjscm.core.framework.service.tax.TaxCustomerService;
import com.csjscm.core.framework.service.tax.TaxService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @Autowired
    private TaxService taxService;

    /**
     *  客户商品与税收关联记录列表
     * @param
     * @return
     */
    @RequestMapping(value = "/getTaxCustomerList")
    public APIResponse getEnterpriseList(@RequestParam(value = "customerPdName",required =false) String customerPdName,@RequestParam(value = "taxCode",required =false) String taxCode,
    @RequestParam(value = "taxCategoryName",required =false) String taxCategoryName, @RequestParam(value = "customerPdNameList",required =false) String customerPdNameList){
        Map<String,Object> map=new HashMap<>();
        map.put("customerPdName",customerPdName);
        map.put("taxCode",taxCode);
        map.put("taxCategoryName",taxCategoryName);
        map.put("customerPdNameList",customerPdNameList);
        List<TaxCustomer> taxCustomers = taxCustomerService.listSelective(map);
        return APIResponse.success(taxCustomers);
    }

    /**
     * 获取启用的税收分类分页
     * @param page
     * @param rpp
     * @param condition
     * @return
     */
    @RequestMapping(value = "/taxCategoryPage")
    public APIResponse<QueryResult<TaxCategory>> queryTaxVersionList(
            @RequestParam(required = false,defaultValue = "1")int page,
            @RequestParam(required = false,defaultValue = "10")int rpp,
            @ApiIgnore @RequestParam Map<String,Object> condition){
        QueryResult<TaxCategory> pageByEnableTax = taxService.findPageByEnableTax(page, rpp, condition);
        return APIResponse.success(pageByEnableTax);
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
