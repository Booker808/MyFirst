package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.service.spu.SpuService;
import com.csjscm.core.framework.service.spu.dto.SpSkuCoreDto;
import com.csjscm.core.framework.service.spu.dto.SpuAttrDetailDto;
import com.csjscm.core.framework.service.spu.dto.SpuAttrDto;
import com.csjscm.core.framework.service.spu.dto.SpuDto;
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
            @ApiImplicitParam(name = "page", value = "当前页码（起始为1）", dataType = "Integer", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "rpp", value = "每页数量", dataType = "Integer", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "productName", value = "商品名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isvalidate", value = "是否上架（0已上架可用，1未上架不可用）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isSoldOut", value = "是否售完（0未售完，1已售完）", dataType = "String", paramType = "query")

    })
    @RequestMapping(value = "/spu", method = RequestMethod.GET)
    public APIResponse<QueryResult<SpuDto>> querySpuList(@RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int rpp,
                                                         @ApiIgnore @RequestParam Map<String, String> condition) {
        SpuExample example = new SpuExample();
        if (condition != null) {
            example = JSON.parseObject(JSON.toJSONString(condition), SpuExample.class);
        }
        QueryResult<SpuDto> result = spuService.querySpuList(page, rpp, example);
        return APIResponse.success(result);
    }

    @ApiOperation("指定spu商品查询")
    @RequestMapping(value = "/spu/{spuNo}", method = RequestMethod.GET)
    public APIResponse<SpuDto> querySpu(@PathVariable String spuNo) {
        SpuDto spuDto = spuService.querySpu(spuNo);
        return APIResponse.success(spuDto);
    }

    @ApiOperation("新建Spu,返回新生成的spu编码")
    @RequestMapping(value = "/spu", method = RequestMethod.POST)
    public APIResponse<String> createSpu(@RequestBody SpuDto spuDto) {
        String spuNo = spuService.createSpu(spuDto);
        return APIResponse.success(spuNo);
    }

    @ApiOperation("更新Spu")
    @RequestMapping(value = "/spu/{spuNo}", method = RequestMethod.PUT)
    public APIResponse updateSpu(@PathVariable String spuNo, @RequestBody SpuDto spuDto) {
        spuDto.setStdProductNo(spuNo);
        spuService.updateSpu(spuDto);
        return APIResponse.success();
    }

    @ApiOperation("spu上下架操作")
    @RequestMapping(value = "/spu/_shelf", method = RequestMethod.PUT)
    public APIResponse updateShelfState(
            @ApiParam("1下架，0上架") @RequestParam Integer shelf,
            @ApiParam("spu列表") @RequestParam List<String> spuList) {
        if (spuList.isEmpty()) {
            throw new BusinessException("请输入spu列表");
        }
        spuService.updateShelfState(shelf, spuList);
        return APIResponse.success();
    }

    @ApiOperation("获取对应spu中对应的sku")
    @RequestMapping(value = "/spu/{spuNo}/sku", method = RequestMethod.GET)
    public APIResponse<List<SpSkuCoreDto>> querySkuListBySpu(@PathVariable String spuNo) {
        List<SpSkuCoreDto> list = spuService.querySkuListBySpu(spuNo, 0);
        return APIResponse.success(list);
    }

    @ApiOperation("批量操作Sku，删除的话修改isvalidate值，并带上对应的sku")
    @RequestMapping(value = "/spu/{spuNo}/sku", method = RequestMethod.POST)
    public APIResponse updateSkuList(@PathVariable String spuNo,
                                     @RequestBody List<SpSkuCoreDto> skuCoreVoList) {
        spuService.updateSpSkuList(spuNo, skuCoreVoList);
        return APIResponse.success();
    }

    @ApiOperation("获取扩展属性")
    @RequestMapping(value = "/spu/{spuNo}/attributes", method = RequestMethod.GET)
    public APIResponse<List<SpuAttrDetailDto>> querySpuAttrList(@PathVariable String spuNo) {
        List<SpuAttrDetailDto> result = spuService.queryAttrList(spuNo);
        return APIResponse.success(result);
    }

    @ApiOperation("保存spu扩展属性")
    @RequestMapping(value = "/spu/{spuNo}/attributes", method = RequestMethod.POST)
    public APIResponse saveSpuAttrList(@PathVariable String spuNo, @RequestBody List<SpuAttrDto> attrList) {
        spuService.saveSpuAttrList(spuNo, attrList);
        return APIResponse.success();
    }

    @ExceptionHandler({BusinessException.class})
    public APIResponse exceptionHandler(Exception e) {
        return APIResponse.fail(e.getMessage());
    }
}
