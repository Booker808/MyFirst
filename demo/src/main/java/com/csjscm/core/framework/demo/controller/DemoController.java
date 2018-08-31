package com.csjscm.core.framework.demo.controller;

import com.csjscm.core.framework.service.demo.DemoService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
@ResponseBody
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    public APIResponse test(@RequestParam("str")String str){
        return APIResponse.success(demoService.getMd5(str));
    }
}
