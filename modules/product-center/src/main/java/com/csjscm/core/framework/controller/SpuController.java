package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.model.SpSkuCore;
import com.csjscm.core.framework.service.spu.SpuService;
import com.csjscm.core.framework.vo.SpuVo;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Api("SPU相关接口")
@RequestMapping("/product/spu")
@RestController
@Slf4j
@ResponseBody
public class SpuController {
    @Autowired
    private SpuService spuService;

    @ApiOperation("商城商品库查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页码（起始为1）",dataType = "Integer",defaultValue = "1",paramType = "query"),
            @ApiImplicitParam(name="rpp",value = "每页数量",dataType = "Integer",defaultValue = "10",paramType = "query"),
            @ApiImplicitParam(name="productName",value="商品名",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "isvalidate", value = "是否上架（0已上架可用，1未上架不可用）",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "isSoldOut",value = "是否售完（0未售完，1已售完）",dataType = "String",paramType = "query")

    })
    @RequestMapping(value = "/spu",method = RequestMethod.GET)
    public APIResponse<QueryResult<SpuVo>> querySpuList(@RequestParam(required = false,defaultValue = "1")int page,
                                                        @RequestParam(required = false,defaultValue = "10")int rpp,
                                                        @ApiIgnore @RequestParam Map<String,String> condition){
        SpuExample example=new SpuExample();
        if(condition!=null){
            example= JSON.parseObject(JSON.toJSONString(condition),SpuExample.class);
        }
        QueryResult<SpuVo> result=spuService.querySpuList(page,rpp,example);
        return APIResponse.success(result);
    }

    @ApiOperation("spu上下架操作")
    @RequestMapping(value = "/spu/_shelf",method = RequestMethod.PUT)
    public APIResponse updateShelfState(
            @ApiParam("1下架，0上架") @RequestParam Integer shelf,
            @ApiParam("spu列表") @RequestParam List<String> spuList){
        if(spuList.isEmpty()){
            throw new BusinessException("请输入spu列表");
        }
        spuService.updateShelfState(shelf,spuList);
        return APIResponse.success();
    }

    @ApiOperation("获取对应spu中对应的sku")
    @RequestMapping(value = "/spu/{spuNo}/sku",method = RequestMethod.GET)
    public APIResponse<List<SpSkuCore>> querySkuListBySpu(@PathVariable String spuNo){
        List<SpSkuCore> list=spuService.querySkuListBySpu(spuNo,0);
        return APIResponse.success(list);
    }
}
