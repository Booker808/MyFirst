package com.csjscm.core.framework.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.sweet.framework.core.mvc.APIResponse;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.csjscm.sweet.framework.storage.StorageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product/brand")
@ResponseBody
public class BrandController {

    @Autowired
    private BrandMasterService brandMasterService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private RedisServiceFacade redisServiceFacade;


    /**
     * 查询品牌名称
     *
     * @param brandName 查询条件
     * @return
     */
    @ApiOperation("查询品牌名称")
    @RequestMapping(value = "/brandName",method = RequestMethod.GET)
    public APIResponse queryBrandNameList(@ApiParam(name="brandName",value="品牌名称",required=true) @RequestParam String brandName){
        List<BrandMaster> brandList = brandMasterService.selectByBrandName(brandName);
        return APIResponse.success(brandList);
    }

    /**
     * 查询品牌名称列表
     * @return
     */
    @ApiOperation("查询品牌名称列表")
    @RequestMapping(value = "/brandNameList",method = RequestMethod.GET)
    public APIResponse queryBrandNameListSky(){
        if(!redisServiceFacade.exists(Constant.REDIS_KEY_JSONSTR_BRAND)){
            List<BrandMaster> brandList = brandMasterService.selectByBrandNameSky();
            redisServiceFacade.set(Constant.REDIS_KEY_JSONSTR_BRAND,  JSONArray.parseArray(JSON.toJSONString(brandList)));
        }
        return APIResponse.success(redisServiceFacade.get(Constant.REDIS_KEY_JSONSTR_BRAND,JSONArray.class));
    }

    /**
     * 查询品牌接口
     * @param
     * @param current
     * @param pageSize
     * @return
     */
    @ApiOperation("查询品牌接口")
    @RequestMapping(value = "/brandPage",method = RequestMethod.GET)
    public APIResponse <QueryResult<BrandMaster>> queryBrandList(@ApiParam(name="brandName",value="品牌名称")@RequestParam(required=false) String brandName,
                                      @ApiParam(name="current",value="当前页",required=true) @RequestParam(value = "current") int current,
                                      @ApiParam(name="pageSize",value="页面大小",required=true) @RequestParam(value = "pageSize") int pageSize){
        Map<String, Object> map = new HashMap<>();
        map.put("brandNameLike",brandName);
        QueryResult<BrandMaster> page = brandMasterService.queryBrandMasterList(map, current, pageSize);
        return APIResponse.success(page);
    }

    /**
     * 查询目标为ID的品牌
     *
     * @param id
     * @return
     */
    @ApiOperation("查询目标为ID的品牌")
    @RequestMapping(value = "/brandID",method = RequestMethod.GET)
    public APIResponse queryBrand(@ApiParam(name="id",value="主键id",required=true) @PathVariable Integer id){
        BrandMaster brandMaster = brandMasterService.selectByPrimaryKey(id);
        return APIResponse.success(brandMaster);
    }

    /**
     * 创建品牌对象
     *
     * @param brand
     * @return
     */
    @ApiOperation("创建品牌对象")
    @RequestMapping(value = "/brandObject",method = RequestMethod.POST)
    public APIResponse createBrand(@RequestBody @Valid BrandMaster brand){
        brandMasterService.insertSelective(brand);
        return APIResponse.success();
    }

    /**
     * 更新指定品牌
     *
     * @param brand 品牌对象
     * @return
     */
    @ApiOperation("更新指定品牌")
    @RequestMapping(value = "brandUpdate",method = RequestMethod.POST)
    public APIResponse updateBrand(@RequestBody  @Valid  BrandMaster brand){
        brandMasterService.updateByPrimaryKeySelective(brand);
        return APIResponse.success();
    }

    /**
     * 删除指定ID的品牌对象
     *
     * @param id
     * @return
     */
    @ApiOperation("删除指定ID的品牌对象")
    @RequestMapping(value = "brandDelete",method = RequestMethod.GET)
    public APIResponse deleteBrand(@ApiParam(name="id",value="要删除的id",required=true)Integer id){
        brandMasterService.deleteByPrimaryKey(id);
        return APIResponse.success();
    }
    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    @ApiOperation("保存文件")
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public APIResponse importBrand(  @ApiParam(name="file",value="保存品牌的图片",required=true) @RequestParam(value = "file")MultipartFile file) throws  Exception{
       String store = storageService.store(file.getOriginalFilename(), file.getInputStream());
        return APIResponse.success(System.getProperty("sweet.framework.storage.fastdfs.tracker-domain")+store);
    }
    @ApiOperation("下载文件")
    @RequestMapping(value = "/downloadFile")
    public APIResponse downloadFile(HttpServletResponse response,String name,String url ) throws  Exception{
        String substring = url.substring(url.lastIndexOf("."), url.length());
        String fileNames=name+substring;
        URL url1 = new URL(url);
        try {
            URLConnection conn = url1.openConnection();
            InputStream inStream = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while( (len=inStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            byte data[] =outStream.toByteArray();
            inStream.close();
            OutputStream out;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName=new String(fileNames.getBytes("UTF-8"),"iso-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename="+fileName);
            out=response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw  new BussinessException("文件下载异常");
        }
        return APIResponse.success();
    }
    @ExceptionHandler({BussinessException.class})
    public APIResponse exceptionHandler(Exception e, HttpServletResponse response) {
        return APIResponse.fail(e.getMessage());
    }
}
