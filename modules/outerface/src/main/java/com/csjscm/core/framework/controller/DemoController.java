package com.csjscm.core.framework.controller;

import com.csjscm.core.framework.service.demo.DemoService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.mongodb.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/outerface")
@ResponseBody
public class DemoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/test")
    public APIResponse test(@RequestParam("str")String str){
        DB db = mongoTemplate.getDb();
        System.out.println(db);
        return APIResponse.success();
    }
}
