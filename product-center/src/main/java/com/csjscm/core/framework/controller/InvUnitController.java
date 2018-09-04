package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.InvUnitIsvalidEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.InvUnit;
import com.csjscm.core.framework.service.InvUnitService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
        Map<String,Object> map=new HashMap<>();
        map.put("isvalid", InvUnitIsvalidEnum.有效.getState());
        List<InvUnit> invUnits = invUnitService.findListByMap(map);
        RedisTemplate redisTemplate = redisServiceFacade.getRedisTemplate();
        redisTemplate.opsForList().rightPush(Constant.REDIS_KEY_UNIT,invUnits);
        return APIResponse.success(invUnits);
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
