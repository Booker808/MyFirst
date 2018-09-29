package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.InvUnitIsvalidEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.InvUnit;
import com.csjscm.core.framework.service.InvUnitService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api("计量单位定义表")
@Controller
@RequestMapping("/product/invUnit")
@ResponseBody
public class InvUnitController {
    @Autowired
    private InvUnitService invUnitService;
    @Autowired
    private RedisServiceFacade redisServiceFacade;

    /**
     * 查询可用的计量单位列表
     *
     * @return
     */
    @ApiOperation("查询可用的计量单位列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public APIResponse queryList(){
        if(!redisServiceFacade.exists(Constant.REDIS_KEY_UNIT)){
            invUnitService.reloadRedisInvUnit();
        }
        return APIResponse.success(redisServiceFacade.get(Constant.REDIS_KEY_UNIT, JSONArray.class));
    }
    @ApiOperation("新增单位")
    @RequestMapping(value = "/saveInvUnit",method = RequestMethod.POST)
    public APIResponse saveInvUnit(@RequestBody  @Valid InvUnit invUnit){
        invUnitService.save(invUnit);
        return APIResponse.success();
    }
    @ApiOperation("删除单位")
    @RequestMapping(value = "deleteInvUnit",method = RequestMethod.GET)
    public APIResponse deleteInvUnit(@ApiParam(name="id",value="要删除的id",required=true) @RequestParam(value = "id") Integer id){
        invUnitService.delete(id);
        return APIResponse.success();
    }
    @ApiOperation("单位状态变更")
    @RequestMapping(value = "/updateIsvalid",method = RequestMethod.GET)
    public APIResponse updateIsvalid(@ApiParam(name="id",value="主键id",required=true) @RequestParam(value = "id") Integer id,
                                     @ApiParam(name="isvalid",value="是否有效，1-有效，0-失效",required=true) @RequestParam(value = "isvalid") Integer isvalid){
        invUnitService.updateIsvalid(id,isvalid);
        return APIResponse.success();
    }
    @ApiOperation("编辑单位")
    @RequestMapping(value = "/updateInvUnit",method = RequestMethod.POST)
    public APIResponse updateInvUnit(@RequestBody  @Valid InvUnit invUnit){
        invUnitService.update(invUnit);
        return APIResponse.success();
    }
    @ApiOperation("单位查询分类接口")
    @RequestMapping(value = "InvUnitPage",method = RequestMethod.GET)
    public APIResponse<QueryResult<InvUnit>> queryCategoryList(@ApiParam(name="objName",value="单位名称",required=false) @RequestParam(value = "objName",required = false) String objName,
                                                                @ApiParam(name = "objType",value = "单位类型",required = false) @RequestParam(value = "objType",required = false) Integer objType,
                                                                @ApiParam(name = "isvalid",value = "是否有效",required = false) @RequestParam(value = "isvalid",required = false) Integer isvalid,
                                                                @ApiParam(name="current",value="当前页",required=true) @RequestParam(value = "current") int current,
                                                                @ApiParam(name="pageSize",value="页面大小",required=true) @RequestParam(value = "pageSize") int pageSize){
        Map<String,Object> map=new HashMap<>();
        if(StringUtils.isNotBlank(objName)){
            map.put("objName",objName);
        }
        if(objType!=null){
            map.put("objType",objType);
        }
        if(isvalid!=null){
            map.put("isvalid",isvalid);
        }
        QueryResult<InvUnit> page = invUnitService.findPage(map, current, pageSize);
        return APIResponse.success(page);
    }
    @ApiOperation("获取单位")
    @RequestMapping(value = "getInvUnit",method = RequestMethod.GET)
    public APIResponse getInvUnit(@ApiParam(name="id",value="主键id",required=true) @RequestParam(value = "id") Integer id){
        InvUnit byPrimary = invUnitService.findByPrimary(id);
        return APIResponse.success(byPrimary);
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
}
