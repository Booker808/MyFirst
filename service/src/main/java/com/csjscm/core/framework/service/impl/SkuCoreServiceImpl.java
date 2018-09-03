package com.csjscm.core.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.enums.CategoryLevelEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.BrandMasterMapper;
import com.csjscm.core.framework.dao.CategoryMapper;
import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.SkuCore;
import com.csjscm.core.framework.service.SkuCoreService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        //成功的数据
        List<SkuCore> skuCores = new ArrayList<>();
        //商品名称数据
        List<String> productNameList = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();
        List<Row> rows = excelUtil.readExcel(file);
        total = rows.size() - READ_START_POS;
        String failMsg = "";
        int failRow = 0;
        int failCell = 0;
        for (int i = READ_START_POS; i < rows.size(); i++) {
            failRow = i;
            try {
                SkuCore skuCore    = new SkuCore();
                Row row = rows.get(i);
                if (row.getLastCellNum() < MIX_CELL || row.getLastCellNum() > MAX_CELL) {
                    Map<String, Object> map = new HashMap<>();
                    failMsg = "excel列不在此区间[" + MIX_CELL + "," + MAX_CELL + "]";
                    failList.add(getFailMsg(failRow, failCell, failMsg));
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
                //是否继续执行
                boolean flag = true;
                for (int j = MIX_CELL; j < row.getLastCellNum(); j++) {
                    index++;
                    String sufKey = "suf" + index;
                    String sufVaule = getCellValue(row.getCell(j));
                    if (sufVaule.length() > 256) {
                        failMsg = "字段长度不能大于256";
                        failCell = j + 1;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        flag = false;
                        break;
                    }
                    sufMap.put(sufKey, sufVaule);
                }
                if (!flag) {
                    continue;
                }
                //设置suf
                if(!sufMap.isEmpty()){
                    for (Map.Entry<String, String> entry : sufMap.entrySet()) {
                        try {
                            Field field = SkuCore.class.getDeclaredField(entry.getKey());
                            field.setAccessible(true);
                            field.set(skuCore, entry.getValue());
                        } catch (Exception e) {
                            failMsg = "设置suf时异常";
                            failList.add(getFailMsg(failRow, failCell, failMsg));
                            flag = false;
                            break;
                        }
                    }
                }
                if (!flag) {
                    continue;
                }
                //校验分类编码 categoryNo
                if (StringUtils.isBlank(categoryNo)) {
                    failMsg = "分类编码不能为空";
                    failCell = 1;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    continue;
                }
                Map<String, Object> categoryNomap = new HashMap<>();
                categoryNomap.put("levelNum", CategoryLevelEnum.三级.getState());
                categoryNomap.put("classCode", categoryNo);
                Category category = categoryMapper.findSelective(categoryNomap);
                if (category == null) {
                    failMsg = "分类编码不存在";
                    failCell = 1;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    continue;
                }
                //todo 校验商品编码

                //校验商品名称
                if (StringUtils.isBlank(productName) || productName.length() > 256) {
                    failMsg = "商品名称不能为空或者字段长度超过256";
                    failCell = 3;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    continue;
                }
                Map<String, Object> productNamemap = new HashMap<>();
                productNamemap.put("productName", productName);
                int productCount = skuCoreMapper.findCount(productNamemap);
                if(productCount>0 || productNameList.contains(productName)){
                    failMsg = "商品名称重复";
                    failCell = 3;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    continue;
                }
                productNameList.add(productName);
                //校验品牌
                if(StringUtils.isBlank(brandName) || brandName.length()>255){
                    failMsg = "品牌名称不能为空或者字段长度超过255";
                    failCell = 4;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    continue;
                }
                Map<String, Object> brandNamemap = new HashMap<>();
                brandNamemap.put("brandName", brandName);
                BrandMaster brandMaster = brandMasterMapper.findSelective(brandNamemap);
                if (brandMaster == null) {
                    failMsg = "品牌名称不存在";
                    failCell = 4;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    continue;
                }
                //校验规格型号 最小库存单位
                if(StringUtils.isBlank(rule) || rule.length()>255 ||StringUtils.isBlank(minUint) || minUint.length()>255){
                    failMsg = "规格型号、库存单位不能为空或者字段长度超过255";
                    failCell = 5;
                    failList.add(getFailMsg(failRow, failCell, failMsg));
                    continue;
                }
                //校验69码（EAN13码）助记码
                if(StringUtils.isNotBlank(mnemonicCode)){
                    if(mnemonicCode.length()>255){
                        failMsg = "助记码段长度超过255";
                        failCell = 6;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        continue;
                    }
                }
                if(StringUtils.isNotBlank(ean13Code)){
                    if(ean13Code.length()>255){
                        failMsg = "69码（EAN13码）段长度超过255";
                        failCell = 7;
                        failList.add(getFailMsg(failRow, failCell, failMsg));
                        continue;
                    }
                }
                //组装成功数据
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
                skuCore.setProductNo(productNo);
                skuCores.add(skuCore);
                successCount++;
            } catch (Exception e) {
                logger.error(e.getMessage());
                failMsg = "未知异常";
                failList.add(getFailMsg(failRow, failCell, failMsg));
                continue;
            }
        }
        failCount=total-successCount;
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("successData",skuCores);
        resultMap.put("failCount",failCount);
        resultMap.put("successCount",successCount);
        resultMap.put("failList",failList);
        resultMap.put("total",total);
        return resultMap;
    }

    @Override
    public void saveImportSkuCore(String data) {
        JSONArray jsonArray = JSON.parseArray(data);
        for (int i=0;i<jsonArray.size();i++) {
            SkuCore skuCore = JSONObject.toJavaObject(jsonArray.getJSONObject(i), SkuCore.class);
            skuCore.setProductNo("");
            skuCore.setSize(skuCore.getRule());
        }
    }

    private String getFailMsg(int failRow, int failCell, String failMsg) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("失败：行=》 ").append(failRow).append(",").append("列=》").append(failCell).append(",原因=》").append(failMsg);
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
        List<SkuCore> coreList = skuCoreMapper.selectByProductNoList();
        List list = new ArrayList();
        Map map = new HashMap();
        if(null != coreList && !coreList.isEmpty()){
            for (SkuCore skuCore : coreList) {
                map.put("product_no", skuCore.getProductNo());
                list.add(map);
            }
        }
        return list;
    }
}