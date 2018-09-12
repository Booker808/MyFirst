package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.SpCategory;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.core.framework.service.SpCategoryService;
import com.csjscm.core.framework.vo.CategoryModel;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/shopping/center")
@ResponseBody
public class ShoppingCenterController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpCategoryService spCategoryService;
    @Autowired
    private RedisServiceFacade redisServiceFacade;

    /**
     * 获取商城分类json
     *
     * @return
     */
    @RequestMapping(value = "/getJsonSpCategory",method = RequestMethod.GET)
    public APIResponse getJsonSpCategory(){
        return APIResponse.success(redisServiceFacade.get(Constant.REDIS_KEY_JSON_SP_CATEGORY));
    }
/*    @RequestMapping(value = "/getJsonSpCategoryList",method = RequestMethod.GET)
    public APIResponse getJsonSpCategoryList(){
        return APIResponse.success(spCategoryService.getJsonCategory());
    }*/

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
     * 校验
     * @param name
     * @param type
     * @return
     */
    @RequestMapping(value = "/checkEnterpriseName",method = RequestMethod.GET)
    public APIResponse checkPartnerName(@RequestParam(value = "name",required =true) String name,@RequestParam(value = "type",required =true) String type){

        return APIResponse.success();
    }
    @RequestMapping(value = "/createEnterprise",method = RequestMethod.POST)
    public APIResponse createEnterprise(){

        return APIResponse.success();
    }


    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
          return APIResponse.fail(e.getMessage());
    }
    @ExceptionHandler(ValidationException.class)
    public APIResponse validationExp(ValidationException e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
