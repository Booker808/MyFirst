package com.csjscm.core.framework.service.product.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.CategoryLevelEnum;
import com.csjscm.core.framework.common.enums.InvUnitIsvalidEnum;
import com.csjscm.core.framework.common.enums.SkuCoreChannelEnum;
import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.*;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.vo.*;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisDistributedCounterObject;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductPartnerServiceImpl implements ProductPartnerService {
    private static final Logger logger = LoggerFactory.getLogger(ProductPartnerServiceImpl.class);
    @Autowired
    private SkuPartnerMapper skuPartnerMapper;
    @Autowired
    private BrandMasterMapper brandMasterMapper;
    @Autowired
    private SkuCoreMapper skuCoreMapper;
    @Autowired
    private SpCategoryMapper spCategoryMapper;
    @Autowired
    private RedisServiceFacade redisServiceFacade;
    @Autowired
    private SkuUomMapper skuUomMapper;
    @Autowired
    private SkuPartnerUomMapper skuPartnerUomMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private InvUnitMapper invUnitMapper;
    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;

    @Override
    public QueryResult<SkuPartnerEx> queryPartnerProduct(int page, int rpp, SkuPartnerExample example) {
        PageHelper.startPage(page, rpp);
        if (example.getSupplyPdSize() != null) {
            example.setSupplyPdSize(example.getSupplyPdSize().trim());
        }
        List<SkuPartnerEx> skuPartner = skuPartnerMapper.selectExByExample(example);
        PageInfo<SkuPartnerEx> pageInfo = new PageInfo<>(skuPartner);
        QueryResult<SkuPartnerEx> result = new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }

    @Override
    public Map<String, Object> importPartnerExcel(MultipartFile file, String supplyNo) throws BussinessException {
        //成功条数
        int successCount = 0;
        //失败条数
        int failCount = 0;
        //总条数
        int total = 0;
        //失败信息
        List<String> failList = new ArrayList<>();
        //失败的数据
        List<SkuPartnerVo> failSkuPartnerVo = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();
        List<Row> rows = excelUtil.readExcel(file);
        total = rows.size() - READ_START_POS;
        int failRow = 0;
        int failCell = 0;
        for (int i = READ_START_POS; i < rows.size(); i++) {
            String failMsg = "";
            String failMsgStr = "";
            boolean issuccess = true;
            failRow = i + 1;
            try {
                SkuPartnerVo skuPartnerVo = new SkuPartnerVo();
                SkuPartner skuPartner = new SkuPartner();
                Row row = rows.get(i);
                //获取普通字段
                /*String lv1CategoryNo = ExcelUtil.getCellValue(row.getCell(0));
                String lv2CategoryNo = ExcelUtil.getCellValue(row.getCell(1));*/
                String categoryNo = ExcelUtil.getCellValue(row.getCell(0)).trim();

                String supplyPdNo = ExcelUtil.getCellValue(row.getCell(1)).trim();
                String supplyPdName = ExcelUtil.getCellValue(row.getCell(2));
                String brandName = ExcelUtil.getCellValue(row.getCell(3)).trim();
                String supplyPdSize = ExcelUtil.getCellValue(row.getCell(4)).trim();
                String supplyPdRule = ExcelUtil.getCellValue(row.getCell(5)).trim();
                String invUnit = ExcelUtil.getCellValue(row.getCell(6)).trim();
                String refrencePrice = ExcelUtil.getCellValue(row.getCell(7)).trim();
                String recentEnquiry = ExcelUtil.getCellValue(row.getCell(8)).trim();
                String ean13Code = ExcelUtil.getCellValue(row.getCell(9)).trim();
                String mnemonicCode = ExcelUtil.getCellValue(row.getCell(10)).trim();
                String productNo = ExcelUtil.getCellValue(row.getCell(11)).trim();
                Integer categoryId = 0;
                Category category = new Category();
                if (StringUtils.isBlank(categoryNo) || categoryNo.length() > 255) {
                    failCell = 1;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "分类编码为空或者长度超过255");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                } else {
                    row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                    categoryNo = ExcelUtil.getCellValue(row.getCell(0));
                    Map<String, Object> categoryNomap = new HashMap<>();
                    categoryNomap.put("levelNum", CategoryLevelEnum.三级.getState());
                    categoryNomap.put("classCode", categoryNo);
                    category = categoryMapper.findSelective(categoryNomap);
                    if (category == null) {
                        failCell = 1;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "分类编码不存在");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    } else {
                        categoryId = category.getId();
                    }
                }

                //校验 supplyPdNo
                if (StringUtils.isNotBlank(supplyPdNo)) {
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdNo = ExcelUtil.getCellValue(row.getCell(1));
                    if (supplyPdNo.length() > 20) {
                        failCell = 2;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "供应商商品编码长度大于20");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                    Map<String, Object> supplyNomap = new HashMap<>();
                    supplyNomap.put("supplyNo", supplyNo);
                    supplyNomap.put("supplyPdNo", supplyPdNo);
                    int count = skuPartnerMapper.findCount(supplyNomap);
                    if (count > 0) {
                        failCell = 2;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "供应商商品编码已存在");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
                //校验supplyPdName
                if (StringUtils.isBlank(supplyPdName) || supplyPdName.length() > 256) {
                    failCell = 3;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品名称不能为空或者长度大于256");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                } else {
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdName = ExcelUtil.getCellValue(row.getCell(2));
                }
                //校验品牌
                if (StringUtils.isBlank(brandName) || brandName.length() > 255) {
                    failCell = 4;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "品牌名称不能为空或者长度大于256");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                } else {
                    row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
                    brandName = ExcelUtil.getCellValue(row.getCell(3));
                }
                Map<String, Object> brandNamemap = new HashMap<>();
                brandNamemap.put("brandName", brandName);
                List<BrandMaster> brandMasters = brandMasterMapper.listSelective(brandNamemap);
                if (brandMasters.size() != 1) {
                    failCell = 4;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "品牌名称不存在或者品牌不唯一");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                }
                //校验supplyPdRule
                if (StringUtils.isNotBlank(supplyPdRule)) {
                    row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdRule = ExcelUtil.getCellValue(row.getCell(4));
                    if (supplyPdRule.length() > 255) {
                        failCell = 5;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "规格长度不能超过256");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
                //校验supplyPdSize
                if (StringUtils.isBlank(supplyPdSize) || supplyPdSize.length() > 255) {
                    failCell = 6;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "型号长度不能为空或超过256");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                } else {
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdSize = ExcelUtil.getCellValue(row.getCell(5));
                }

                if (StringUtils.isBlank(invUnit) || invUnit.length() > 256) {
                    failCell = 7;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "最小单位为空或者长度大于256");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                } else {
                    row.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
                    invUnit = ExcelUtil.getCellValue(row.getCell(6));
                    Map<String, Object> minUintMap = new HashMap<>();
                    minUintMap.put("objName", invUnit);
                    minUintMap.put("isvalid", InvUnitIsvalidEnum.有效.getState());
                    int count = invUnitMapper.findCount(minUintMap);
                    if (count < 1) {
                        failCell = 7;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "最小单位有误");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
                //校验进价成本
                BigDecimal refrencePrice1 = new BigDecimal("0");
                if (StringUtils.isNotBlank(refrencePrice)) {
                    try {
                        BigDecimal a = new BigDecimal(refrencePrice);
                        refrencePrice1 = a.setScale(2, BigDecimal.ROUND_HALF_UP);
                    } catch (Exception e) {
                        failCell = 8;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "进价成本字段有误");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }

                }
                BigDecimal recentEnquiry1 = new BigDecimal("0");
                //校验近期询价
                if (StringUtils.isNotBlank(recentEnquiry)) {
                    try {
                        BigDecimal a = new BigDecimal(recentEnquiry);
                        recentEnquiry1 = a.setScale(2, BigDecimal.ROUND_HALF_UP);
                    } catch (Exception e) {
                        failCell = 9;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "近期询价字段有误");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
                //校验69码（EAN13码）助记码
                if (StringUtils.isNotBlank(ean13Code)) {
                    row.getCell(9).setCellType(HSSFCell.CELL_TYPE_STRING);
                    ean13Code = ExcelUtil.getCellValue(row.getCell(9));
                    if (ean13Code.length() > 255) {
                        failCell = 10;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "条形码69码（EAN13码）段长度超过255");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }

                if (StringUtils.isNotBlank(mnemonicCode)) {
                    row.getCell(10).setCellType(HSSFCell.CELL_TYPE_STRING);
                    mnemonicCode = ExcelUtil.getCellValue(row.getCell(10));
                    if (mnemonicCode.length() > 255) {
                        failCell = 11;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品简码助记码段长度超过255");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }

                //校验productNo
                if(StringUtils.isNotBlank(productNo)){
                    row.getCell(11).setCellType(HSSFCell.CELL_TYPE_STRING);
                    productNo = ExcelUtil.getCellValue(row.getCell(11));
                    SkuCore skuCore = skuCoreMapper.selectByPrimaryKey(productNo);
                    if(skuCore==null){
                        failCell = 12;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "川商品编码错误");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
          /*      if (StringUtils.isNotBlank(productNo)) {
                    row.getCell(11).setCellType(HSSFCell.CELL_TYPE_STRING);
                    productNo = ExcelUtil.getCellValue(row.getCell(11));
                    if (productNo.length() > 20) {
                        failCell = 12;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码长度大于20");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    } else {
                        Map<String, Object> productNomap = new HashMap<>();
                        productNomap.put("productNo", productNo);
                        int count = skuCoreMapper.findCount(productNomap);
                        if (count < 1) {
                            failCell = 14;
                            failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码不存在");
                            failList.add(failMsg);
                            failMsgStr += failMsg;
                            issuccess = false;
                        }
                    }
                }*/
                //校验供应商商品是否存在
                Map<String, Object> parrnerMap = new HashMap<>();
                parrnerMap.put("supplyNo", supplyNo);
                parrnerMap.put("supplyPdName", supplyPdName);
                parrnerMap.put("brandName", brandName);
                parrnerMap.put("minUint", invUnit);
                parrnerMap.put("supplyPdRule", supplyPdRule);
                parrnerMap.put("supplyPdSize", supplyPdSize);
                int count = skuPartnerMapper.findCount(parrnerMap);
                if (count > 0) {
                    failCell = 0;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "该商品已存在");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                }
                //组装成功数据
                if (issuccess) {
                    if (StringUtils.isBlank(productNo)) {
                        Map<String, Object> productNamemap = new HashMap<>();
                        productNamemap.put("productName", supplyPdName);
                        productNamemap.put("minUint", invUnit);
                        productNamemap.put("brandName", brandName);
                        productNamemap.put("rule", supplyPdRule);
                        productNamemap.put("sizes", supplyPdSize);
                        SkuCore selective = skuCoreMapper.findSelective(productNamemap);
                        if (selective == null) {
                            // 获取商品编码
                            Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_PRODUCT_NO + categoryNo), 1);
                            String increment = increase.toString();
                            String str = "";
                            for (int j = 0; j < 5 - increment.length(); j++) {
                                str += "0";
                            }

                            str += increment;
                            productNo = categoryNo + str;
                            SkuCore skuCore = new SkuCore();

                            category = categoryMapper.findByPrimary(category.getParentClass());
                            //获取2级分类
                            skuCore.setLv2CategoryNo(category.getClassCode());
                            skuCore.setLv2CategoryId(category.getId());
                            //获取一级分类
                            category = categoryMapper.findByPrimary(category.getParentClass());
                            skuCore.setLv1CategoryNo(category.getClassCode());
                            skuCore.setLv1CategoryId(category.getId());

                            skuCore.setCreateTime(new Date());
                            skuCore.setChannel(SkuCoreChannelEnum.手动新增.getState());
                            skuCore.setProductNo(productNo);
                            skuCore.setRule(supplyPdRule);
                            skuCore.setSize(supplyPdSize);
                            skuCore.setProductName(supplyPdName);
                            skuCore.setBrandName(brandName);
                            skuCore.setBrandId(brandMasters.get(0).getId());
                            skuCore.setCategoryId(categoryId);
                            skuCore.setMinUint(invUnit);
                            skuCore.setCategoryNo(categoryNo);
                            skuCore.setMnemonicCode(mnemonicCode);
                            skuCore.setEan13Code(ean13Code);
                            skuCore.setRecentEnquiry(recentEnquiry1);
                            skuCore.setRefrencePrice(refrencePrice1);
                            skuCoreMapper.insertSelective(skuCore);
                        } else {
                            productNo = selective.getProductNo();
                        }
                    }
                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    skuPartner.setBrandId(brandMasters.get(0).getId().toString());
                    skuPartner.setBrandName(brandName);
                    skuPartner.setCreateTime(new Date());
                    skuPartner.setProductNo(productNo);
                    skuPartner.setSupplyNo(supplyNo);
                    skuPartner.setSupplyPdName(supplyPdName);
                    if(StringUtils.isBlank(supplyPdNo)){
                        skuPartner.setSupplyPdNo(productNo);
                    }else {
                        skuPartner.setSupplyPdNo(supplyPdNo);
                    }
                    skuPartner.setSupplyPdRule(supplyPdRule);
                    skuPartner.setSupplyPdSize(supplyPdSize);
                    skuPartner.setUuid(uuid);
                    skuPartner.setRecentEnquiry(recentEnquiry1);
                    skuPartner.setRefrencePrice(refrencePrice1);
                    skuPartner.setMinUint(invUnit);
                    skuPartnerMapper.insertSelective(skuPartner);
                    successCount++;
                } else {
                    skuPartnerVo.setBrandName(brandName);
                    skuPartnerVo.setFailMessage(failMsgStr);
                    skuPartnerVo.setProductNo(productNo);
                    skuPartnerVo.setSupplyPdName(supplyPdName);
                    skuPartnerVo.setSupplyPdNo(supplyPdNo);
                    skuPartnerVo.setSupplyPdRule(supplyPdRule);
                    skuPartnerVo.setSupplyPdSize(supplyPdSize);
                    skuPartnerVo.setCategoryNo(categoryNo);
                    skuPartnerVo.setMinUint(invUnit);
                    skuPartnerVo.setRecentEnquiry(recentEnquiry);
                    skuPartnerVo.setRefrencePrice(refrencePrice);
                   // skuPartnerVo.setLv1CategoryNo(lv1CategoryNo);
                    //skuPartnerVo.setLv2CategoryNo(lv2CategoryNo);
                    skuPartnerVo.setEan13Code(ean13Code);
                    skuPartnerVo.setMnemonicCode(mnemonicCode);
                    failSkuPartnerVo.add(skuPartnerVo);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                failMsg = ExcelUtil.getFailMsg(failRow, failCell, "未知异常");
                failList.add(failMsg);
                SkuPartnerVo skuPartnerVo = new SkuPartnerVo();
                skuPartnerVo.setFailMessage(failMsg);
                failSkuPartnerVo.add(skuPartnerVo);
                continue;
            }
        }
        failCount = total - successCount;
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("failData", failSkuPartnerVo);
        resultMap.put("failCount", failCount);
        resultMap.put("successCount", successCount);
        resultMap.put("failList", failList);
        resultMap.put("total", total);
        return resultMap;
    }

    @Override
    @Transactional
    public void save(SkuPartnerAddModel skuPartnerAddModel) {
        Map<String, Object> productNomap = new HashMap<>();
        String productNo = "";
        if (StringUtils.isNotBlank(skuPartnerAddModel.getProductNo())) {
            productNomap.put("productNo", skuPartnerAddModel.getProductNo());
            int count1 = skuCoreMapper.findCount(productNomap);
            if (count1 < 1) {
                throw new BussinessException("商品编码不存在");
            }
            productNomap.clear();
            productNomap.put("productNo", skuPartnerAddModel.getProductNo());
            productNomap.put("supplyNo", skuPartnerAddModel.getSupplyNo());
            productNomap.put("supplyPdName", skuPartnerAddModel.getSupplyPdName());
            productNomap.put("brandName", skuPartnerAddModel.getBrandName());
            productNomap.put("supplyPdRule", skuPartnerAddModel.getSupplyPdRule());
            productNomap.put("supplyPdSize", skuPartnerAddModel.getSupplyPdSize());
            productNomap.put("minUnit", skuPartnerAddModel.getMinUint());
            int count = skuPartnerMapper.findCount(productNomap);
            if (count > 0) {
                throw new BussinessException("商品编码已绑定供应商商品");
            }
            productNo = skuPartnerAddModel.getProductNo();
        } else {
            if (StringUtils.isBlank(skuPartnerAddModel.getMinUint()) || StringUtils.isBlank(skuPartnerAddModel.getClassCode())) {
                throw new BussinessException("单位或者分类编码不能为空");
            }
            productNomap.put("supplyNo", skuPartnerAddModel.getSupplyNo());
            productNomap.put("supplyPdName", skuPartnerAddModel.getSupplyPdName());
            productNomap.put("brandName", skuPartnerAddModel.getBrandName());
            productNomap.put("supplyPdRule", skuPartnerAddModel.getSupplyPdRule());
            productNomap.put("supplyPdSize", skuPartnerAddModel.getSupplyPdSize());
            productNomap.put("minUnit", skuPartnerAddModel.getMinUint());
            int count = skuPartnerMapper.findCount(productNomap);
            if (count > 0) {
                throw new BussinessException("该商品已存在");
            }
            productNomap.clear();
            productNomap.put("productName", skuPartnerAddModel.getSupplyNo());
            productNomap.put("brandName", skuPartnerAddModel.getBrandName());
            productNomap.put("rule", skuPartnerAddModel.getSupplyPdRule());
            productNomap.put("sizes", skuPartnerAddModel.getSupplyPdSize());
            productNomap.put("mnemonicCode", skuPartnerAddModel.getMnemonicCode());
            productNomap.put("ean13Code", skuPartnerAddModel.getEan13Code());
            List<SkuCore> skuCores = skuCoreMapper.listSelective(productNomap);
            if (skuCores.size() > 0) {
                productNo = skuCores.get(0).getProductNo();
            }
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
//        if(StringUtils.isNotBlank(skuPartnerAddModel.getUomStr())){
//            List<SkuPartnerUom> skuPartnerUoms = JSONArray.parseArray(skuPartnerAddModel.getUomStr(), SkuPartnerUom.class);
//            for(SkuPartnerUom uom:skuPartnerUoms){
//                uom.setPartnerUuid(uuid);
//                uom.setCreateTime(new Date());
//                skuPartnerUomMapper.insertSelective(uom);
//            }
//        }
        if (StringUtils.isBlank(productNo)) {
            // 获取商品编码
            Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_PRODUCT_NO + skuPartnerAddModel.getClassCode()), 1);
            String increment = increase.toString();
            String str = "";
            for (int j = 0; j < 5 - increment.length(); j++) {
                str += "0";
            }
            str += increment;
            productNo = skuPartnerAddModel.getClassCode() + str;
            SkuCore skuCore = new SkuCore();
            skuCore.setCreateTime(new Date());
            skuCore.setChannel(SkuCoreChannelEnum.手动新增.getState());
            skuCore.setProductNo(productNo);
            skuCore.setRule(skuPartnerAddModel.getSupplyPdRule());
            skuCore.setSize(skuPartnerAddModel.getSupplyPdSize());
            skuCore.setProductName(skuPartnerAddModel.getSupplyPdName());
            skuCore.setBrandName(skuPartnerAddModel.getBrandName());
            skuCore.setBrandId(skuPartnerAddModel.getBrandId());
            skuCore.setCategoryId(skuPartnerAddModel.getClassId());
            skuCore.setCategoryNo(skuPartnerAddModel.getClassCode());
            skuCore.setMnemonicCode(skuPartnerAddModel.getMnemonicCode());
            skuCore.setEan13Code(skuPartnerAddModel.getEan13Code());
            skuCore.setMinUint(skuPartnerAddModel.getMinUint());
            try {
                if (skuCore.getCategoryId() != null) {
                    //获取三级分类
                    Category category = categoryMapper.findByPrimary(skuCore.getCategoryId());
                    skuCore.setLv2CategoryId(category.getParentClass());
                    //获取二级分类
                    category = categoryMapper.findByPrimary(skuCore.getLv2CategoryId());
                    skuCore.setLv2CategoryNo(category.getClassCode());
                    skuCore.setLv1CategoryId(category.getParentClass());
                    //获取一级分类
                    category = categoryMapper.findByPrimary(skuCore.getLv1CategoryId());
                    skuCore.setLv1CategoryNo(category.getClassCode());
                }
            } catch (Exception e) {
                logger.error("新增供应商商品库时分类异常", e);
                throw new BusinessException("分类异常");
            }

            skuCoreMapper.insertSelective(skuCore);
//            if(StringUtils.isNotBlank(skuPartnerAddModel.getUomStr())){
//                List<SkuUom> skuUoms = JSONArray.parseArray(skuPartnerAddModel.getUomStr(), SkuUom.class);
//                for(SkuUom uom:skuUoms){
//                    uom.setProductNo(productNo);
//                    uom.setCreateTime(new Date());
//                    skuUomMapper.insertSelective(uom);
//                }
//            }
        }
        SkuPartner skuPartner = new SkuPartner();
        BeanutilsCopy.copyProperties(skuPartnerAddModel, skuPartner);
        skuPartner.setUuid(uuid);
        skuPartner.setCreateTime(new Date());
        skuPartner.setProductNo(productNo);
        if(StringUtils.isBlank(skuPartner.getSupplyPdNo())){
            skuPartner.setSupplyPdNo(productNo);
        }
        skuPartnerMapper.insertSelective(skuPartner);
    }

    @Override
    public List<SkuPartner> listSelective(Map<String, Object> map) {
        return skuPartnerMapper.listSelective(map);
    }

    @Override
    @Transactional
    public Integer savePartner(SkuPartnerModel skuPartnerModel) {
        Map<String, Object> parrnerMap = new HashMap<>();
        if (StringUtils.isNotBlank(skuPartnerModel.getSupplyPdNo())) {
            parrnerMap.put("supplyPdNo", skuPartnerModel.getSupplyPdNo());
            parrnerMap.put("supplyNo", skuPartnerModel.getSupplyNo());
            int count1 = skuPartnerMapper.findCount(parrnerMap);
            if (count1 > 0) {
                throw new BussinessException("供应商商品编码已存在");
            }
        }
        parrnerMap.clear();
        parrnerMap.put("brandName", skuPartnerModel.getBrandName());
        List<BrandMaster> brandMasters = brandMasterMapper.listSelective(parrnerMap);
        if (brandMasters.size() == 0) {
            throw new BussinessException("品牌名称信息有误，不存在该品牌名称");
        }
        if (brandMasters.size() > 1) {
            throw new BussinessException("存在多个品牌名称，无法识别");
        }
        parrnerMap.clear();
        parrnerMap.put("classCode", skuPartnerModel.getClassCode());
        List<SpCategory> spCategories = spCategoryMapper.listSelective(parrnerMap);
        if (spCategories.size() == 0) {
            throw new BussinessException("分类编码有误，未查到对应编码分类信息");
        }
        if (spCategories.size() > 1) {
            throw new BussinessException("存在多个分类编码");
        }
        parrnerMap.clear();
        parrnerMap.put("supplyNo", skuPartnerModel.getSupplyNo());
        parrnerMap.put("supplyPdName", skuPartnerModel.getSupplyPdName());
        parrnerMap.put("brandName", skuPartnerModel.getBrandName());
        parrnerMap.put("supplyPdRule", skuPartnerModel.getSupplyPdRule());
        parrnerMap.put("supplyPdSize", skuPartnerModel.getSupplyPdSize());
        parrnerMap.put("minUint", skuPartnerModel.getMinUint());
        int count2 = skuPartnerMapper.findCount(parrnerMap);
        if (count2 > 0) {
            throw new BussinessException("该商品已存在");
        }
        Map<String, Object> productNamemap = new HashMap<>();
        productNamemap.put("productName", skuPartnerModel.getSupplyPdName());
        productNamemap.put("brandName", skuPartnerModel.getBrandName());
        productNamemap.put("rule", skuPartnerModel.getSupplyPdRule());
        productNamemap.put("sizes", skuPartnerModel.getSupplyPdSize());
        productNamemap.put("minUint", skuPartnerModel.getMinUint());
        List<SkuCore> skuCores = skuCoreMapper.listSelective(productNamemap);
        String productNo = "";
        if (skuCores.size() < 1) {
            // 获取商品编码
            Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_PRODUCT_NO + skuPartnerModel.getClassCode()), 1);
            String increment = increase.toString();
            String str = "";
            for (int j = 0; j < 5 - increment.length(); j++) {
                str += "0";
            }
            str += increment;
            productNo = skuPartnerModel.getClassCode() + str;
            SkuCore skuCore = new SkuCore();
            skuCore.setCreateTime(new Date());
            skuCore.setChannel(SkuCoreChannelEnum.来自商城.getState());
            skuCore.setProductNo(productNo);
            skuCore.setRule(skuPartnerModel.getSupplyPdRule());
            skuCore.setSize(skuPartnerModel.getSupplyPdSize());
            skuCore.setProductName(skuPartnerModel.getSupplyPdName());
            skuCore.setBrandName(skuPartnerModel.getBrandName());
            skuCore.setBrandId(brandMasters.get(0).getId());
            skuCore.setCategorySpId(spCategories.get(0).getId());
            skuCore.setCategorySpNo(spCategories.get(0).getClassCode());
            skuCore.setMinUint(skuPartnerModel.getMinUint());
            skuCore.setRefrencePrice(skuPartnerModel.getRefrencePrice());
            skuCore.setRecentEnquiry(skuPartnerModel.getRecentEnquiry());
            skuCoreMapper.insertSelective(skuCore);
        } else {
            productNo = skuCores.get(0).getProductNo();
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        SkuPartner skuPartner = new SkuPartner();
        skuPartner.setCreateTime(new Date());
        skuPartner.setUuid(uuid);
        skuPartner.setSupplyPdSize(skuPartnerModel.getSupplyPdSize());
        skuPartner.setSupplyPdRule(skuPartnerModel.getSupplyPdRule());
        skuPartner.setSupplyPdNo(skuPartnerModel.getSupplyPdNo());
        skuPartner.setSupplyPdName(skuPartnerModel.getSupplyPdName());
        skuPartner.setProductNo(productNo);
        skuPartner.setSupplyNo(skuPartnerModel.getSupplyNo());
        skuPartner.setBrandName(skuPartnerModel.getBrandName());
        skuPartner.setBrandId(brandMasters.get(0).getId().toString());
        skuPartner.setRecentEnquiry(skuPartnerModel.getRecentEnquiry());
        skuPartner.setRefrencePrice(skuPartnerModel.getRefrencePrice());
        skuPartner.setMinUint(skuPartnerModel.getMinUint());
        skuPartnerMapper.insertSelective(skuPartner);
        return skuPartner.getId();
    }

    @Override
    public SkuPartnerDetailsModel getSkuPartnerModel(Map<String, Object> map) {
        return skuPartnerMapper.getSkuPartnerModel(map);
    }

    @Override
    public ScmPartnerVo saveSCMSkuPartner(String json) {
        ScmPartnerVo vo=new ScmPartnerVo();
        List<SkuPartnerSCMMolde> skuPartnerSCMMoldes=null;
        try {
            skuPartnerSCMMoldes= JSON.parseArray(json,SkuPartnerSCMMolde.class);
        } catch (Exception e) {
            throw  new  BussinessException("json解析异常，请检查数据格式");
        }
        int total=skuPartnerSCMMoldes.size();
        int fail=0;
        List<ScmPartnerFailVo> scmPartnerFailVos=new ArrayList<>();
        for(SkuPartnerSCMMolde ps:skuPartnerSCMMoldes){
            ScmPartnerFailVo failVo=new ScmPartnerFailVo();
            try {
                BeanValidator.validate(ps);
            } catch (Exception e) {
                fail++;
                failVo.setFailData(ps);
                failVo.setMessage(e.getMessage());
                scmPartnerFailVos.add(failVo);
                continue;
            }

            Map<String, Object> brandNamemap = new HashMap<>();
            brandNamemap.put("brandName", ps.getBrandName());
            List<BrandMaster> brandMasters = brandMasterMapper.listSelective(brandNamemap);
            if (brandMasters.size() != 1) {
                fail++;
                failVo.setFailData(ps);
                failVo.setMessage("品牌名称不存在或者品牌不唯一");
                scmPartnerFailVos.add(failVo);
                continue;
            }
            Map<String, Object> minUintMap = new HashMap<>();
            minUintMap.put("objName", ps.getMinUint());
            minUintMap.put("isvalid", InvUnitIsvalidEnum.有效.getState());
            int count = invUnitMapper.findCount(minUintMap);
            if (count < 1) {
                fail++;
                failVo.setFailData(ps);
                failVo.setMessage("最小单位有误");
                scmPartnerFailVos.add(failVo);
                continue;
            }
            SkuCore skuCore = skuCoreMapper.selectByPrimaryKey(ps.getProductNo());
            if(skuCore==null){
                fail++;
                failVo.setFailData(ps);
                failVo.setMessage("平台商品编码有误");
                scmPartnerFailVos.add(failVo);
                continue;
            }
            Map<String, Object> parrnerMap = new HashMap<>();
            parrnerMap.put("supplyNo", ps.getSupplyNo());
            parrnerMap.put("supplyPdName", ps.getSupplyPdName());
            parrnerMap.put("brandName", ps.getBrandName());
            parrnerMap.put("supplyPdRule", ps.getSupplyPdRule());
            parrnerMap.put("supplyPdSize", ps.getSupplyPdSize());
            parrnerMap.put("minUint", ps.getMinUint());
            parrnerMap.put("productNo", ps.getProductNo());
            int count1 = skuPartnerMapper.findCount(parrnerMap);
            if(count1>0){
             /*   fail++;
                failVo.setFailData(ps);
                failVo.setMessage("已存在该供应商商品");
                scmPartnerFailVos.add(failVo);*/
                continue;
            }
            SkuPartner skuPartner = new SkuPartner();
            BeanutilsCopy.copyProperties(ps,skuPartner);
            skuPartnerMapper.insertSelective(skuPartner);
        }
        vo.setFailCount(fail);
        vo.setScmPartnerFailVos(scmPartnerFailVos);
        vo.setTotal(total);
        vo.setSuccessCount(total-fail);
        return vo;
    }

    @Override
    public List<SkuPartner> listSelectiveSCM(Map<String, Object> map) {
        return skuPartnerMapper.listSelectiveSCM(map);
    }

    @Override
    public SkuPartnerEx queryPartnerProductDetail(Integer id) {
        return skuPartnerMapper.selectExByPrimaryKey(id);
    }

    @Override
    public void updateSkuPartner(SkuPartner skuPartner) {
        skuPartnerMapper.updateByPrimaryKeySelective(skuPartner);
    }
}
