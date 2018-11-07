package com.csjscm.core.framework.demo.controller;

import com.csjscm.core.framework.elasticsearch.constant.EsObjEnum;
import com.csjscm.core.framework.elasticsearch.model.EsSpuModel;
import com.csjscm.core.framework.elasticsearch.model.ProductPartner;
import com.csjscm.core.framework.elasticsearch.utils.EsUtil;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/es")
@ResponseBody
@Slf4j
public class EsController {
    @Autowired
    private EsUtil esUtil;

    @RequestMapping("/utilTest/insert/{id}")
    @ApiIgnore
    public APIResponse insertTest(@RequestParam String index,
                                  @RequestParam String type,
                                  @PathVariable(required = false) String id,
                                  @RequestBody Map<String,Object> data){
        try {
            if(StringUtils.isEmpty(id)){
                return APIResponse.success(esUtil.insert(index,type,data));
            }else{
                return APIResponse.success(esUtil.insert(index,type,id,data));
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return APIResponse.fail(e.getMessage());
        }
    }

    @RequestMapping("/utilTest/update/{id}")
    @ApiIgnore
    public APIResponse updateTest(@RequestParam String index,
                                  @RequestParam String type,
                                  @PathVariable String id,
                                  @RequestBody Map<String,Object> data){
        try {
            return APIResponse.success(esUtil.updateById(index,type,id,data));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return APIResponse.fail(e.getMessage());
        }
    }

    @RequestMapping("/utilTest/delete/{id}")
    @ApiIgnore
    public APIResponse deleteTest(@RequestParam String index,
                                  @RequestParam String type,
                                  @PathVariable String id){
        try {
            return APIResponse.success(esUtil.deleteById(index,type,id));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return APIResponse.fail(e.getMessage());
        }
    }

    @RequestMapping("/utilTest/search/{id}")
    @ApiIgnore
    public APIResponse search(@RequestParam String index,
                              @RequestParam String type,
                              @PathVariable String id){
        EsObjEnum objEnum=EsObjEnum.getEsObjEnum(index);
        try{
            if(objEnum!=null){
                return APIResponse.success(esUtil.selectById(objEnum.getIndex(),objEnum.getType(),id,objEnum.getClazz()));
            }
            return APIResponse.success(esUtil.selectById(index,type,id,ProductPartner.class));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return APIResponse.fail(e.getMessage());
        }
    }



    @RequestMapping("/utilTest/search")
    @ApiIgnore
    public APIResponse search(@RequestParam String index,
                              @RequestParam String type,
                              @RequestBody Map<String,String> condition){
        try{
            return APIResponse.success(esUtil.selectByCondition(index,type,condition,ProductPartner.class));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return APIResponse.fail(e.getMessage());
        }
    }



    @RequestMapping(value = "/createTest",method = RequestMethod.POST)
    public APIResponse createTest(){
        return APIResponse.success(esUtil.createIndex(EsSpuModel.class));
    }

    @RequestMapping(value = "/insertTest",method = RequestMethod.POST)
    public APIResponse insertTest(){
        EsSpuModel esSpuModel1=new EsSpuModel();
        esSpuModel1.setBrandName("测试品牌");
        esSpuModel1.setMinUnit("测试单位");
        esSpuModel1.setProductName("测试商品");
        esSpuModel1.setRuleSize("测试规格");
        esSpuModel1.setStdProductNo("ZZZZZZaaaaa");

        try {
            esUtil.insert(esSpuModel1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EsSpuModel esSpuModel2=new EsSpuModel();
        esSpuModel2.setBrandName("测试1品牌");
        esSpuModel2.setMinUnit("测试单1位");
        esSpuModel2.setProductName("测试商1品");
        esSpuModel2.setRuleSize("测试规1格");
        esSpuModel2.setStdProductNo("ZZZZZZaa1aa1a");

        try {
            esUtil.insert(esSpuModel2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIResponse.success();
    }
}
