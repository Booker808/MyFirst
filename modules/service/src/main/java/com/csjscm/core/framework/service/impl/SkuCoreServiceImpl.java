package com.csjscm.core.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.CategoryLevelEnum;
import com.csjscm.core.framework.common.enums.SkuCoreChannelEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.*;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.SkuCoreService;
import com.csjscm.core.framework.vo.SkuCoreVo;
import com.csjscm.sweet.framework.redis.RedisDistributedCounterObject;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
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



    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;
    /**
     * 最少字段
     */
    private final static int MIX_CELL = 8;
    /**
     * 最多字段
     */
    private final static int MAX_CELL = 18;


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
                if (row.getLastCellNum() < MIX_CELL || row.getLastCellNum() > MAX_CELL) {
                    Map<String, Object> map = new HashMap<>();
                    failMsg = "excel列不在此区间" + MIX_CELL + "-" + MAX_CELL;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    issuccess=false;
                    skuCoreVo.setFailMessage(getFailMsg(failRow, failCell, failMsg));
                    failSkuCores.add(skuCoreVo);
                    continue;
                }
                //获取普通字段
                String productNo = "";
                String productName = "";
                String brandName = "";
                String minUint = "";
                String rule = "";
                String ean13Code = "";
                String mnemonicCode = "";
                String categoryNo = "";
                categoryNo = getCellValue(row.getCell(0)).trim();
                productNo = getCellValue(row.getCell(1));
                productName = getCellValue(row.getCell(2)).trim();
                brandName = getCellValue(row.getCell(3)).trim();
                rule = getCellValue(row.getCell(4)).trim();
                minUint = getCellValue(row.getCell(5)).trim();
                mnemonicCode = getCellValue(row.getCell(6)).trim();
                ean13Code = getCellValue(row.getCell(7)).trim();
                //suf
                Map<String, String> sufMap = new HashMap<>();
                // 获取suf
                int index = 0;
                for (int j = MIX_CELL; j < row.getLastCellNum(); j++) {
                    index++;
                    String sufKey = "suf" + index;
                    String sufVaule = getCellValue(row.getCell(j));
                    if (sufVaule.length() > 256) {
                        failMsg = "字段长度不能大于256";
                        failCell = j + 1;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                        issuccess=false;
                    }
                    sufMap.put(sufKey, sufVaule);
                }

                //设置suf
                if(!sufMap.isEmpty()){
                    for (Map.Entry<String, String> entry : sufMap.entrySet()) {
                        try {
                            Field field = SkuCore.class.getDeclaredField(entry.getKey());
                            field.setAccessible(true);
                            field.set(skuCore, entry.getValue());

                            Field fieldvo = SkuCoreVo.class.getDeclaredField(entry.getKey());
                            fieldvo.setAccessible(true);
                            fieldvo.set(skuCoreVo, entry.getValue());
                        } catch (Exception e) {
                            failMsg = "设置"+entry.getKey()+"自定义属性时异常";
                            failList.add(getFailMsg(failRow, failCell, failMsg));
                            failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                            issuccess=false;
                        }
                    }
                }

                //校验分类编码 categoryNo
                if (StringUtils.isBlank(categoryNo)) {
                    failMsg = "分类编码不能为空";
                    failCell = 1;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
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
                }
                //校验商品名称
                if (StringUtils.isBlank(productName) || productName.length() > 256) {
                    failMsg = "商品名称不能为空或者字段长度超过256";
                    failCell = 3;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }

                //校验品牌
                if(StringUtils.isBlank(brandName) || brandName.length()>255){
                    failMsg = "品牌名称不能为空或者字段长度超过255";
                    failCell = 4;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }
                Map<String, Object> brandNamemap = new HashMap<>();
                brandNamemap.put("brandName", brandName);
                BrandMaster brandMaster = brandMasterMapper.findSelective(brandNamemap);
                if (brandMaster == null) {
                    failMsg = "品牌名称不存在";
                    failCell = 4;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }
                //校验规格型号 最小库存单位
                if(StringUtils.isBlank(rule) || rule.length()>255 ||StringUtils.isBlank(minUint) || minUint.length()>255){
                    failMsg = "规格型号、库存单位不能为空或者字段长度超过255";
                    failCell = 5;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                    issuccess=false;
                }
                //校验69码（EAN13码）助记码
                if(StringUtils.isNotBlank(mnemonicCode)){
                    if(mnemonicCode.length()>255){
                        failMsg = "助记码段长度超过255";
                        failCell = 6;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        failMsgStr+=getFailMsg(failRow, failCell, failMsg);
                        issuccess=false;
                    }
                }
                if(StringUtils.isNotBlank(ean13Code)){
                    if(ean13Code.length()>255){
                        failMsg = "69码（EAN13码）段长度超过255";
                        failCell = 7;
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
                    skuCore.setCategoryNo(categoryNo);
                    skuCore.setBrandId(brandMaster.getId());
                    skuCore.setBrandName(brandName);
                    skuCore.setCategoryId(category.getId());
                    skuCore.setEan13Code(ean13Code);
                    skuCore.setMinUint(minUint);
                    skuCore.setMnemonicCode(mnemonicCode);
                    skuCore.setProductName(productName);
                    skuCore.setRule(rule);
                    skuCore.setSize(rule);
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
                    skuCoreVo.setProductNo(productNo);
                    skuCoreVo.setFailMessage(failMsgStr);
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
    private String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
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
    public Map<String, Object> insertSelective(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        SkuCore skuCore = JSON.parseObject(jsonObject.toJSONString(), SkuCore.class);
        /**校验productName,brandName,rule,size,minUint,categoryNo*/
        List<String> listMsg = new ArrayList<>();
        String message = "";
        Map<String, Object> response = new HashMap<>();
        if (StringUtils.isBlank(skuCore.getProductName())) {
            message = "商品名称为空";
            listMsg.add(message);
        }
        if (StringUtils.isBlank(skuCore.getCategoryNo())) {
            message = "分类编码为空";
            listMsg.add(message);
        }
        if (StringUtils.isBlank(skuCore.getBrandName())) {
            message = "品牌名称为空";
            listMsg.add(message);
        }
        if (null == skuCore.getBrandId()) {
            message = "品牌ID为空";
            listMsg.add(message);
        }
        if (StringUtils.isBlank(skuCore.getRule())) {
            message = "规格为空";
            listMsg.add(message);
        }
        if (StringUtils.isBlank(skuCore.getSize())) {
            message = "型号为空";
            listMsg.add(message);
        }
        if (StringUtils.isBlank(skuCore.getMinUint())) {
            message = "最小库存单位为空";
            listMsg.add(message);
        }
        if (null != listMsg && !listMsg.isEmpty()) {
            response.put("code", "fail");
            response.put("message",listMsg);
            return response;
        }
        Map<String, Object> query = new HashMap<>();
        query.put("productName",skuCore.getProductName());
        query.put("rule",skuCore.getRule());
        query.put("sizes",skuCore.getSize());
        query.put("brandName",skuCore.getBrandName());
        query.put("minUint",skuCore.getMinUint());
        List<SkuCore> skuCoreList = skuCoreMapper.listSelective(query);
        if (null != skuCoreList && !skuCoreList.isEmpty()) {
            response.put("code", "fail");
            response.put("message", "商品已存在");
            return response;
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
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("skuUom"));
        if (null != jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                SkuUom skuUom = JSONObject.parseObject(object.toJSONString(), SkuUom.class);
                skuUomMapper.insertSelective(skuUom);
            }
        }
        jsonArray = new JSONArray(jsonObject.getJSONArray("skuUpc"));
        if (null != jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                SkuUpc skuUpc = JSONObject.parseObject(object.toJSONString(), SkuUpc.class);
                skuUpcMapper.insertSelective(skuUpc);
            }
        }
        response.put("code", "success");
        return response;
    }
}