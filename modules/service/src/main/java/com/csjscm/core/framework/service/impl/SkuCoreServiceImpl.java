package com.csjscm.core.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.CategoryLevelEnum;
import com.csjscm.core.framework.common.enums.InvUnitIsvalidEnum;
import com.csjscm.core.framework.common.enums.SkuCoreChannelEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.*;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.vo.SkuCoreSCMMolde;
import com.csjscm.core.framework.vo.SkuCoreVo;
import com.csjscm.sweet.framework.redis.RedisDistributedCounterObject;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 商品核心表ServiceImpl
 *
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 15:43:10
 */

@Service
public class SkuCoreServiceImpl implements SkuCoreService {

    private static final Logger logger = LoggerFactory.getLogger(SkuCoreServiceImpl.class);

    @Autowired
    private SkuCoreMapper skuCoreMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMasterMapper brandMasterMapper;
    @Autowired
    private RedisServiceFacade redisServiceFacade;
    @Autowired
    private SkuUomMapper skuUomMapper;
    @Autowired
    private SkuUpcMapper skuUpcMapper;
    @Autowired
    private InvUnitMapper invUnitMapper;



    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;
/*    *//**
     * 最少字段
     *//*
    private final static int MIX_CELL = 8;
    *//**
     * 最多字段
     *//*
    private final static int MAX_CELL = 18;*/


    @Override
    public Map<String,Object> importSkuCoreExcel(MultipartFile file) throws BussinessException {
        //成功条数
        int successCount = 0;
        //失败条数
        int failCount = 0;
        //总条数
        int total = 0;
        //失败信息
        List<String> failList = new ArrayList<>();
        //失败的数据
        List<SkuCoreVo> failSkuCores = new ArrayList<>();

        ExcelUtil excelUtil = new ExcelUtil();
        List<Row> rows = excelUtil.readExcel(file);
        total = rows.size() - READ_START_POS;
        int failRow = 0;
        int failCell = 0;
        for (int i = READ_START_POS; i < rows.size(); i++) {
            String failMsg = "";
            String failMsgStr = "";
            boolean issuccess=true;
            failRow = i+1;
            try {
                SkuCore skuCore    = new SkuCore();
                SkuCoreVo skuCoreVo   = new SkuCoreVo();
                Row row = rows.get(i);
                Integer categoryId = 0;
         /*       if (row.getLastCellNum() < MIX_CELL || row.getLastCellNum() > MAX_CELL) {
                    Map<String, Object> map = new HashMap<>();
                    failMsg = "excel列不在此区间" + MIX_CELL + "-" + MAX_CELL;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    issuccess=false;
                    skuCoreVo.setFailMessage(getFailMsg(failRow, failCell, failMsg));
                    failSkuCores.add(skuCoreVo);
                    continue;
                }*/
                //获取普通字段

              /*  String lv1CategoryNo = getCellValue(row.getCell(0));
                String lv2CategoryNo = getCellValue(row.getCell(1));*/
                String  categoryNo = getCellValue(row.getCell(0)).trim();
               // String  productNo = getCellValue(row.getCell(1));
                String  productName = getCellValue(row.getCell(1)).trim();
                String  brandName = getCellValue(row.getCell(2)).trim();
                String rule = getCellValue(row.getCell(3)).trim();
               // String size = getCellValue(row.getCell(4)).trim();

                String minUint = getCellValue(row.getCell(4)).trim();
                String  refrencePrice = getCellValue(row.getCell(5)).trim();
                String  recentEnquiry = getCellValue(row.getCell(6)).trim();
                String  ean13Code = getCellValue(row.getCell(7)).trim();
                String mnemonicCode = getCellValue(row.getCell(8)).trim();
                String  description = getCellValue(row.getCell(9)).trim();

                //校验分类编码 categoryNo
                if (StringUtils.isBlank(categoryNo)) {
                    failMsg = "分类编码不能为空";
                    failCell = 1;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }else {
                    row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                    categoryNo = getCellValue(row.getCell(0));
                }
                Map<String, Object> categoryNomap = new HashMap<>();
                categoryNomap.put("levelNum", CategoryLevelEnum.三级.getState());
                categoryNomap.put("classCode", categoryNo);
                Category category = categoryMapper.findSelective(categoryNomap);
                if (category == null) {
                    failMsg = "分类编码不存在";
                    failCell = 1;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }else {
                     categoryId = category.getId();
                }
                //校验商品名称
                if (StringUtils.isBlank(productName) || productName.length() > 256) {
                    failMsg = "商品名称不能为空或者字段长度超过256";
                    failCell = 2;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }else {
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    productName = getCellValue(row.getCell(1));
                }

                //校验品牌
                if(StringUtils.isBlank(brandName) || brandName.length()>255){
                    failMsg = "品牌名称不能为空或者字段长度超过255";
                    failCell = 3;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }else {
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
                    brandName = getCellValue(row.getCell(2));
                }
                Map<String, Object> brandNamemap = new HashMap<>();
                brandNamemap.put("brandName", brandName);
              //  brandNamemap.put("categoryId",categoryId);
                List<BrandMaster> brandMasters = brandMasterMapper.listSelective(brandNamemap);
                if (brandMasters.size()==0) {
                    failMsg = "品牌名称不存在";
                    failCell = 3;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }
               //校验规格
                if(StringUtils.isBlank(rule) ||rule.length()>255){
                        failMsg = "规格长度超过255或为空";
                        failCell = 4;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                        issuccess=false;
                }else{
                    row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
                    rule = getCellValue(row.getCell(3));
                }
         /*       if(StringUtils.isNotBlank(size)){
                    row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
                    size = getCellValue(row.getCell(4));
                    if(size.length()>255){
                        failMsg = "规格长度超过255";
                        failCell = 4;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                        issuccess=false;
                    }
                }*/
                //校验规格型号 最小库存单位
                if(StringUtils.isBlank(minUint) || minUint.length()>255){
                    failMsg = "库存单位不能为空或者字段长度超过255";
                    failCell = 5;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }else {
                    row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
                    minUint = getCellValue(row.getCell(4));
                }

                Map<String, Object> minUintMap = new HashMap<>();
                minUintMap.put("objName",minUint);
                minUintMap.put("isvalid", InvUnitIsvalidEnum.有效.getState());
                int count = invUnitMapper.findCount(minUintMap);
                if(count<1){
                    failMsg = "最小单位有误";
                    failCell = 5;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }
                BigDecimal refrencePrice1=new BigDecimal("0");
                //校验进价成本
                if(StringUtils.isNotBlank(refrencePrice)){
                   try {
                       BigDecimal a =new BigDecimal(refrencePrice);
                       refrencePrice1 = a.setScale(2,BigDecimal.ROUND_HALF_UP);
                   }catch (Exception e){
                       failMsg = "进价成本字段有误";
                       failCell = 6;
                       failList.add(getFailMsg(failRow, failCell, failMsg));
                       failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                       issuccess=false;
                   }

                }
                BigDecimal recentEnquiry1=new BigDecimal("0");
                //校验近期询价
                if(StringUtils.isNotBlank(recentEnquiry)){
                   try {
                       BigDecimal a =new BigDecimal(recentEnquiry);
                       recentEnquiry1 = a.setScale(2,BigDecimal.ROUND_HALF_UP);
                   }catch (Exception e){
                       failMsg = "近期询价字段有误";
                       failCell = 7;
                       failList.add(getFailMsg(failRow, failCell, failMsg));
                       failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                       issuccess=false;
                   }

                }
                //校验69码（EAN13码）助记码
                if(StringUtils.isNotBlank(ean13Code)){
                    row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
                    ean13Code = getCellValue(row.getCell(7));
                    if(ean13Code.length()>255){
                        failMsg = "69码（EAN13码）段长度超过255";
                        failCell = 8;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                        issuccess=false;
                    }
                }

                if(StringUtils.isNotBlank(mnemonicCode)){
                    row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
                    mnemonicCode = getCellValue(row.getCell(8));
                    if(mnemonicCode.length()>255){
                        failMsg = "助记码段长度超过255";
                        failCell = 9;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                        issuccess=false;
                    }
                }

                if(StringUtils.isNotBlank(description)){
                    row.getCell(9).setCellType(HSSFCell.CELL_TYPE_STRING);
                    description = getCellValue(row.getCell(9));
                    if(description.length()>255){
                        failMsg = "商品文字描述长度超过255";
                        failCell = 10;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                        issuccess=false;
                    }
                }
                Map<String, Object> productNamemap = new HashMap<>();
                productNamemap.put("productName", productName);
                productNamemap.put("minUint", minUint);
                productNamemap.put("brandName", brandName);
                productNamemap.put("rule", rule);
               // productNamemap.put("sizes", size);
                int productCount = skuCoreMapper.findCount(productNamemap);
                if(productCount>0 ){
                    failMsg = "商品已存在";
                    failCell = 0;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }
                //组装成功数据
                if(issuccess){
                    skuCore.setCategoryId(category.getId());

                    category = categoryMapper.findByPrimary(category.getParentClass());
                    //获取2级分类
                    skuCore.setLv2CategoryNo(category.getClassCode());
                    skuCore.setLv2CategoryId(category.getId());
                    //获取一级分类
                    category = categoryMapper.findByPrimary(category.getParentClass());
                    skuCore.setLv1CategoryNo(category.getClassCode());
                    skuCore.setLv1CategoryId(category.getId());

                    skuCore.setCategoryNo(categoryNo);
                    skuCore.setBrandId(brandMasters.get(0).getId());
                    skuCore.setBrandName(brandName);
                    skuCore.setEan13Code(ean13Code);
                    skuCore.setMinUint(minUint);
                    skuCore.setMnemonicCode(mnemonicCode);
                    skuCore.setProductName(productName);
                    skuCore.setRule(rule);
                   // skuCore.setSize(size);
                    // 获取商品编码
                    Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_PRODUCT_NO + skuCore.getCategoryNo()), 1);
                    String  increment =increase.toString();
                    String str="";
                    for(int j=0;j<5-increment.length();j++){
                        str+="0";
                    }
                    str+=increment;
                    skuCore.setProductNo(skuCore.getCategoryNo()+str);
                    skuCore.setChannel(SkuCoreChannelEnum.导入.getState());
                    skuCore.setCreateTime(new Date());
                    skuCore.setDescription(description);
                    skuCore.setRecentEnquiry(recentEnquiry1);
                    skuCore.setRefrencePrice(refrencePrice1);
                   // skuCore.setProductPrice(recentEnquiry1);
                    skuCoreMapper.insertSelective(skuCore);
                    successCount++;
                }else {
                    skuCoreVo.setCategoryNo(categoryNo);
                    skuCoreVo.setBrandName(brandName);
                    skuCoreVo.setEan13Code(ean13Code);
                    skuCoreVo.setMinUint(minUint);
                    skuCoreVo.setMnemonicCode(mnemonicCode);
                    skuCoreVo.setProductName(productName);
                    skuCoreVo.setRule(rule);
                    //skuCoreVo.setSize(size);
                    skuCoreVo.setDescription(description);
                    skuCoreVo.setRecentEnquiry(recentEnquiry);
                    skuCoreVo.setRefrencePrice(refrencePrice);
                    //skuCoreVo.setProductNo(productNo);
                    skuCoreVo.setFailMessage(failMsgStr);
                  //  skuCoreVo.setLv1CategoryNo(lv1CategoryNo);
                    //skuCoreVo.setLv2CategoryNo(lv2CategoryNo);
                    failSkuCores.add(skuCoreVo);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                failMsg = "未知异常";
                failList.add(getFailMsg(failRow, failCell, failMsg));
                SkuCoreVo skuCoreVo   = new SkuCoreVo();
                skuCoreVo.setFailMessage(getFailMsg(failRow, failCell, failMsg));
                failSkuCores.add(skuCoreVo);
                continue;
            }
        }
        failCount=total-successCount;
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("failData",failSkuCores);
        resultMap.put("failCount",failCount);
        resultMap.put("successCount",successCount);
        resultMap.put("failList",failList);
        resultMap.put("total",total);
        return resultMap;
    }

    @Override
    @Transactional
    public void saveImportSkuCore(String jsonData) {
        JSONArray jsonArray = JSON.parseArray(jsonData);
        for (int i=0;i<jsonArray.size();i++) {
            SkuCore skuCore = JSONObject.toJavaObject(jsonArray.getJSONObject(i), SkuCore.class);
            // 获取商品编码
            RedisTemplate redisTemplate = redisServiceFacade.getRedisTemplate();
            String  increment = redisTemplate.opsForValue().increment(Constant.REDIS_KEY_PRODUCT_NO + skuCore.getCategoryNo(), 1).toString();
            String str="";
            for(int j=0;j<5-increment.length();j++){
                str+="0";
            }
            str+=increment;
            skuCore.setProductNo(skuCore.getCategoryNo()+str);
            skuCore.setSize(skuCore.getRule());
            skuCore.setChannel(SkuCoreChannelEnum.导入.getState());
            skuCore.setCreateTime(new Date());
            skuCoreMapper.insertSelective(skuCore);
        }
    }

    private String getFailMsg(int failRow, int failCell, String failMsg) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("失败：行=》 ").append(failRow).append(",").append("列=》").append(failCell).append(",原因=》").append(failMsg).append("...");
        return stringBuffer.toString();
    }

    /***
     * 读取单元格的值
     * @param cell
     * @return
     */
    public static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat df = new DecimalFormat("#.##");
    private String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        result = fmt.format(cell.getDateCellValue());
                    } else {
                        result = df.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString().trim();
    }

    @Override
    public List<SkuCore> selectByProductNoList() {
        return skuCoreMapper.selectByProductNoList();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void insertSelective(SkuCore skuCore) {
        /**校验商品名称,规格,型号,品牌,单位*/
        Map<String, Object> query = new HashMap<>();
        query.put("productName",skuCore.getProductName());
        query.put("rule",skuCore.getRule());
       // query.put("sizes",skuCore.getSize());
        query.put("brandName",skuCore.getBrandName());
        query.put("minUint",skuCore.getMinUint());
        List<SkuCore> skuCoreList = skuCoreMapper.listSelective(query);
        if (skuCoreList.size()>0) {
            throw  new BussinessException("商品已存在");
        }
        /**Redis获取商品编码*/
        String  count = String.valueOf(redisServiceFacade.increase(new RedisDistributedCounterObject("category_" + skuCore.getCategoryNo())));
        String str = "";
        for(int i = 0; i < 5 - count.length(); i++){
            str += "0";
        }
        str += count;
        skuCore.setProductNo(skuCore.getCategoryNo() + str);
        skuCoreMapper.insertSelective(skuCore);
/*        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("skuUom"));
        if (null != jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                SkuUom skuUom = JSONObject.parseObject(object.toJSONString(), SkuUom.class);
                try {
                    skuUomMapper.insertSelective(skuUom);
                } catch (Exception e) {
                    throw  new BussinessException("包装规格为空");
                }
            }
        }
        jsonArray = new JSONArray(jsonObject.getJSONArray("skuUpc"));
        if (null != jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                SkuUpc skuUpc = JSONObject.parseObject(object.toJSONString(), SkuUpc.class);
                try {
                    skuUpcMapper.insertSelective(skuUpc);
                } catch (Exception e) {
                    throw  new BussinessException("商品识别码为空");
                }
            }
        }*/
    }

    @Override
    public SkuCore findSelective(Map<String, Object> map) {
        return skuCoreMapper.findSelective(map);
    }

    @Override
    public List<SkuCore> listSelective(Map<String, Object> map) {
        return skuCoreMapper.listSelective(map);
    }

    @Override
    public SkuCoreSCMMolde saveSCMSkuCore(SkuCoreSCMMolde skuCoreSMMolde) {
        Map<String, Object> map = new HashMap<>();
        map.put("levelNum", CategoryLevelEnum.三级.getState());
        map.put("classCode", skuCoreSMMolde.getCategoryNo());
        Category category = categoryMapper.findSelective(map);
        if(category==null){
            throw  new  BussinessException("分类编码有误，不存在该编码");
        }
        map.clear();
        map.put("brandName", skuCoreSMMolde.getBrandName());
        List<BrandMaster> brandMasters = brandMasterMapper.listSelective(map);
        if (brandMasters.size()==0 ) {
            throw  new  BussinessException("品牌名称不存在");
        }
        map.clear();
        map.put("objName",skuCoreSMMolde.getMinUint());
        map.put("isvalid", InvUnitIsvalidEnum.有效.getState());
        int count = invUnitMapper.findCount(map);
        if(count<1){
            throw  new  BussinessException("不存在最小单位");
        }
        Map<String, Object> productNamemap = new HashMap<>();
        productNamemap.put("productName", skuCoreSMMolde.getProductName());
        productNamemap.put("minUint", skuCoreSMMolde.getMinUint());
        productNamemap.put("brandName", skuCoreSMMolde.getBrandName());
        productNamemap.put("rule", skuCoreSMMolde.getRule());
       // productNamemap.put("size", skuCoreSMMolde.getSize());
        int productCount = skuCoreMapper.findCount(productNamemap);
        if(productCount>0){
            throw  new  BussinessException("商品已存在");
        }
        SkuCore skuCore=new SkuCore();
        //获取三级分类
        skuCore.setCategoryId(category.getId());
        skuCore.setLv2CategoryId(category.getParentClass());
        //获取二级分类
        category=categoryMapper.findByPrimary(skuCore.getLv2CategoryId());
        skuCore.setLv2CategoryNo(category.getClassCode());
        skuCore.setLv1CategoryId(category.getParentClass());
        //获取一级分类
        category=categoryMapper.findByPrimary(skuCore.getLv1CategoryId());
        skuCore.setLv1CategoryNo(category.getClassCode());
        skuCore.setCategoryNo(skuCoreSMMolde.getCategoryNo());
        skuCore.setBrandId(brandMasters.get(0).getId());
        skuCore.setBrandName(skuCoreSMMolde.getBrandName());
        skuCore.setEan13Code(skuCoreSMMolde.getEan13Code());
        skuCore.setMinUint(skuCoreSMMolde.getMinUint());
        skuCore.setMnemonicCode(skuCoreSMMolde.getMnemonicCode());
        skuCore.setProductName(skuCoreSMMolde.getProductName());
        skuCore.setRule(skuCoreSMMolde.getRule());
        skuCore.setSize(skuCoreSMMolde.getSize());
        // 获取商品编码
        Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_PRODUCT_NO + skuCore.getCategoryNo()), 1);
        String  increment =increase.toString();
        String str="";
        for(int j=0;j<5-increment.length();j++){
            str+="0";
        }
        str+=increment;
        skuCore.setProductNo(skuCore.getCategoryNo()+str);
        skuCore.setChannel(SkuCoreChannelEnum.来自scm.getState());
        skuCore.setCreateTime(new Date());
        skuCoreMapper.insertSelective(skuCore);
        skuCoreSMMolde.setProductNo(skuCore.getCategoryNo()+str);
        return skuCoreSMMolde;
    }

    @Override
    public void updateProduct(SkuCore skuCore) {
        /**校验商品名称,规格,型号,品牌,单位*/
        Map<String, Object> query = new HashMap<>();
        query.put("productName",skuCore.getProductName());
        query.put("rule",skuCore.getRule());
        //query.put("sizes",skuCore.getSize());
        query.put("brandName",skuCore.getBrandName());
        query.put("minUint",skuCore.getMinUint());
        List<SkuCore> skuCoreList = skuCoreMapper.listSelective(query);
        if (skuCoreList.size()>0 && !skuCoreList.get(0).getProductNo().equals(skuCore.getProductNo())) {
            throw  new BussinessException("修改之后的商品已存在");
        }
        skuCoreMapper.updateByPrimaryKeySelective(skuCore);
    }
}