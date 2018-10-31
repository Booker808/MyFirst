package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.service.impl.SkuCoreServiceImpl;
import com.csjscm.sweet.framework.auth.AuthUtils;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("权限")
@RestController
@RequestMapping("/system/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
        Object sessionUser = AuthUtils.getSessionUser();
        if (sessionUser == null) {
            return new APIResponse("user-not-login");
        } else {
            APIResponse apiResponse = APIResponse.success();
            apiResponse.setData(sessionUser);
            apiResponse.setMessage(System.getProperty("sweet.framework.scm.domain"));
            System.out.println();
            logger.info("当前用户权限集合：" + AuthUtils.getSessionAttribute("SWEET-SESSION-USER_PERMISSION"));
            System.out.println();
            return apiResponse;
        }


    }
}
