package com.csjscm.core.framework.controller;

import com.csjscm.sweet.framework.auth.AuthUtils;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Api("权限")
@Controller
@RequestMapping("/system/auth")
@ResponseBody
public class AuthController {

    @ApiOperation("退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public APIResponse logout() {
        if (AuthUtils.getSessionUser() != null) {
            AuthUtils.logout();
        }
        return APIResponse.success();
    }
    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public APIResponse getUserInfo() {
        APIResponse apiResponse=new  APIResponse();
        apiResponse.setData(AuthUtils.getSessionUser());
        apiResponse.setMessage(System.getProperty("sweet.framework.scm.domain"));
        apiResponse.setCode("success");
        return apiResponse;
    }
}
