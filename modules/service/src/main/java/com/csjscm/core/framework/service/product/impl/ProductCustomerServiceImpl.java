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
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.impl.SkuCoreServiceImpl;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.core.framework.vo.*;
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

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ProductCustomerServiceImpl implements ProductCustomerService {
    private static final Logger logger = LoggerFactory.getLogger(ProductCustomerServiceImpl.class);
    @Autowired
    private SkuCustomerMapper skuCustomerMapper;
    @Autowired
    private SkuCoreMapper skuCoreMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMasterMapper brandMasterMapper;
    @Autowired
    private InvUnitMapper invUnitMapper;
    @Autowired
    private RedisServiceFacade redisServiceFacade;
    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;

    @Override
    public QueryResult<SkuCustomerEx> queryCustomerProduct(int page, int rpp, SkuCustomerExample example) {
        PageHelper.startPage(page,rpp);
        List<SkuCustomerEx> skuCoreList=skuCustomerMapper.selectExByExample(example);
        PageInfo<SkuCustomerEx> pageInfo=new PageInfo<>(skuCoreList);
        QueryResult<SkuCustomerEx> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }

    @Override
    public QueryResult<SkuCustomerPageVo> findPage(int page, int rpp, Map<String, Object> map) {
        PageHelper.startPage(page,rpp);
        List<SkuCustomerPageVo> skuCoreList=skuCustomerMapper.selectPage(map);
        PageInfo<SkuCustomerPageVo> pageInfo=new PageInfo<>(skuCoreList);
        QueryResult<SkuCustomerPageVo> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }

    @Override
    public Map<String, Object> importCustomerExcel(MultipartFile file,String customerNo) throws BussinessException {
        //成功条数
        int successCount = 0;
        //失败条数
        int failCount = 0;
        //总条数
        int total = 0;
        //失败信息
        List<String> failList = new ArrayList<>();
        //失败的数据
        List<SkuCustomerVo> failSkuCustomer = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();
        List<Row> rows = excelUtil.readExcel(file);
        total = rows.size() - READ_START_POS;
        int failRow = 0;
        int failCell = 0;
        for(int i=READ_START_POS;i<rows.size();i++){
            String failMsg = "";
            String failMsgStr = "";
            boolean issuccess=true;
            failRow = i+1;
            try {
                SkuCustomerVo skuCustomerVo =new SkuCustomerVo();
                SkuCustomer skuCustomer=new SkuCustomer();
                Row row = rows.get(i);
                //获取普通字段

                //String lv1CategoryNo = ExcelUtil.getCellValue(row.getCell(0));
                //String lv2CategoryNo = ExcelUtil.getCellValue(row.getCell(1));
                String categoryNo = ExcelUtil.getCellValue(row.getCell(0)).trim();
                String  customerPdNo = ExcelUtil.getCellValue(row.getCell(1));
                String  customerPdName = ExcelUtil.getCellValue(row.getCell(2)).trim();
                String  brandName = ExcelUtil.getCellValue(row.getCell(3)).trim();
                String  customerPdRule = ExcelUtil.getCellValue(row.getCell(4)).trim();
                String customerPdSize = ExcelUtil.getCellValue(row.getCell(5)).trim();
                String invUnit = ExcelUtil.getCellValue(row.getCell(6)).trim();
                String ean13Code = ExcelUtil.getCellValue(row.getCell(7)).trim();
                String mnemonicCode = ExcelUtil.getCellValue(row.getCell(8)).trim();
                String  productNo = ExcelUtil.getCellValue(row.getCell(9)).trim();
                Category category = new Category();
                Integer categoryId = 0;

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
                //校验customerPdNo
                Map<String, Object> customermap = new HashMap<>();
                if(StringUtils.isNotBlank(customerPdNo)){
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    customerPdNo = ExcelUtil.getCellValue(row.getCell(1));
                    if(customerPdNo.length()>20){
                        failCell = 2;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "客户物料编码长度大于20");
                        failList.add(failMsg);
                        failMsgStr+=failMsg;
                        issuccess=false;
                    }
                    Map<String, Object> customerNomap = new HashMap<>();
                    customerNomap.put("customerNo", customerNo);
                    customerNomap.put("customerPdNo", customerPdNo);
                    int count = skuCustomerMapper.findCount(customerNomap);
                    if(count>0){
                        failCell = 2;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "客户物料编码已存在");
                        failList.add(failMsg);
                        failMsgStr+=failMsg;
                        issuccess=false;
                    }
                }
                //检验 customerPdName  customerPdSize
                if(StringUtils.isBlank(customerPdName)  ||StringUtils.isBlank(customerPdSize) || customerPdName.length()>256  ||customerPdSize.length()>256){
                    failCell = 3;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "客户物料名称或规格为空或者长度大于256");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }else {
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
                    customerPdName = ExcelUtil.getCellValue(row.getCell(2));
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
                    customerPdSize = ExcelUtil.getCellValue(row.getCell(5));
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
                //校验规格
                if (StringUtils.isNotBlank(customerPdRule)) {
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
                    customerPdRule = ExcelUtil.getCellValue(row.getCell(5));
                    if (customerPdRule.length() > 255) {
                        failCell = 6;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "规格长度不能超过256");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
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

                if (StringUtils.isNotBlank(ean13Code)) {
                    row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
                    ean13Code = ExcelUtil.getCellValue(row.getCell(7));
                    if (ean13Code.length() > 255) {
                        failCell = 8;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "条形码69码（EAN13码）段长度超过255");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
                //校验69码（EAN13码）助记码
                if (StringUtils.isNotBlank(mnemonicCode)) {
                    row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
                    mnemonicCode = ExcelUtil.getCellValue(row.getCell(8));
                    if (mnemonicCode.length() > 255) {
                        failCell = 9;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品简码助记码段长度超过255");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
                //校验productNo
                if(StringUtils.isNotBlank(productNo)){
                    row.getCell(9).setCellType(HSSFCell.CELL_TYPE_STRING);
                    productNo = ExcelUtil.getCellValue(row.getCell(9));
                    SkuCore skuCore = skuCoreMapper.selectByPrimaryKey(productNo);
                    if(skuCore==null){
                        failCell = 10;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "川商品编码错误");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }
      /*          if (StringUtils.isNotBlank(productNo)) {
                    row.getCell(9).setCellType(HSSFCell.CELL_TYPE_STRING);
                    productNo = ExcelUtil.getCellValue(row.getCell(9));
                    if (productNo.length() > 20) {
                        failCell = 10;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码长度大于20");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    } else {
                        Map<String, Object> productNomap = new HashMap<>();
                        productNomap.put("productNo", productNo);
                        int count = skuCoreMapper.findCount(productNomap);
                        if (count < 1) {
                            failCell = 10;
                            failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码不存在");
                            failList.add(failMsg);
                            failMsgStr += failMsg;
                            issuccess = false;
                        }
                    }
                }*/
                customermap.put("customerNo",customerNo);
                customermap.put("customerPdName",customerPdName);
                customermap.put("brandName", brandName);
                customermap.put("minUint", invUnit);
                customermap.put("customerPdRule",customerPdRule);
                customermap.put("customerPdSize",customerPdSize);
                int count = skuCustomerMapper.findCount(customermap);
                if(count>0){
                    failCell = 0;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "客户物料商品已存在");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }
                //组装成功数据
                if(issuccess){
                    if (StringUtils.isBlank(productNo)) {
                        Map<String, Object> productNamemap = new HashMap<>();
                        productNamemap.put("productName", customerPdName);
                        productNamemap.put("minUint", invUnit);
                        productNamemap.put("brandName", brandName);
                        productNamemap.put("rule",customerPdRule);
                        productNamemap.put("sizes", customerPdSize);
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
                            skuCore.setRule(customerPdRule);
                            skuCore.setSize(customerPdSize);
                            skuCore.setProductName(customerPdName);
                            skuCore.setBrandName(brandName);
                            skuCore.setBrandId(brandMasters.get(0).getId());
                            skuCore.setCategoryId(categoryId);
                            skuCore.setMinUint(invUnit);
                            skuCore.setCategoryNo(categoryNo);
                            skuCore.setEan13Code(ean13Code);
                            skuCore.setMnemonicCode(mnemonicCode);
                            skuCoreMapper.insertSelective(skuCore);
                        } else {
                            productNo = selective.getProductNo();
                        }
                    }
                    if(StringUtils.isBlank(customerPdNo)){
                        skuCustomer.setCustomerPdNo(productNo);
                    }else {
                        skuCustomer.setCustomerPdNo(customerPdNo);
                    }
                    skuCustomer.setCreateTime(new Date());
                    skuCustomer.setCustomerNo(customerNo);
                    skuCustomer.setCustomerPdName(customerPdName);
                    skuCustomer.setCustomerPdRule(customerPdRule);
                    skuCustomer.setCustomerPdSize(customerPdSize);
                    skuCustomer.setProductNo(productNo);
                    skuCustomer.setBrandId(brandMasters.get(0).getId().toString());
                    skuCustomer.setBrandName(brandName);
                    skuCustomer.setMinUint(invUnit);
                    skuCustomerMapper.insertSelective(skuCustomer);
                    successCount++;
                }else {
                    skuCustomerVo.setCustomerPdName(customerPdName);
                    skuCustomerVo.setCustomerPdNo(customerPdNo);
                    skuCustomerVo.setCustomerPdRule(customerPdRule);
                    skuCustomerVo.setCustomerPdSize(customerPdSize);
                    skuCustomerVo.setProductNo(productNo);
                    skuCustomerVo.setFailMessage(failMsgStr);
                    //skuCustomerVo.setLv1CategoryNo(lv1CategoryNo);
                    //skuCustomerVo.setLv2CategoryNo(lv2CategoryNo);
                    skuCustomerVo.setBrandName(brandName);
                    skuCustomerVo.setEan13Code(ean13Code);
                    skuCustomerVo.setMinUint(invUnit);
                    skuCustomerVo.setMnemonicCode(mnemonicCode);
                    failSkuCustomer.add(skuCustomerVo);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                failMsg = ExcelUtil.getFailMsg(failRow, failCell, "未知异常");
                failList.add(failMsg);
                SkuCustomerVo vo = new SkuCustomerVo();
                vo.setFailMessage(failMsg);
                failSkuCustomer.add(vo);
                continue;
            }
        }
        failCount=total-successCount;
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("failData",failSkuCustomer);
        resultMap.put("failCount",failCount);
        resultMap.put("successCount",successCount);
        resultMap.put("failList",failList);
        resultMap.put("total",total);
        return resultMap;
    }

    @Override
    @Transactional
    public void save(SkuCustomerVo skuCustomerVo) {
        SkuCustomer skuCustomer=new SkuCustomer();
        BeanutilsCopy.copyProperties(skuCustomerVo,skuCustomer);
        skuCustomer.setCreateTime(new Date());
        Map<String, Object> customermap = new HashMap<>();
        customermap.put("customerNo",skuCustomer.getCustomerNo());
        customermap.put("customerPdName",skuCustomer.getCustomerPdName());
        customermap.put("customerPdRule",skuCustomer.getCustomerPdRule());
        customermap.put("customerPdSize",skuCustomer.getCustomerPdSize());
        customermap.put("customerPdNo",skuCustomer.getCustomerPdNo());
        customermap.put("brandName",skuCustomer.getBrandName());
        customermap.put("minUint",skuCustomer.getMinUint());
        int count = skuCustomerMapper.findCount(customermap);
        if(count>0){
            throw  new  BussinessException("客户商品已存在");
        }
        Map<String, Object> productNomap = new HashMap<>();
        productNomap.put("brandName", skuCustomer.getBrandName());
        productNomap.put("minUint", skuCustomer.getMinUint());
        productNomap.put("sizes", skuCustomer.getCustomerPdSize());
        productNomap.put("rule",skuCustomer.getCustomerPdRule());
        productNomap.put("productName", skuCustomer.getCustomerPdName());
        SkuCore skuCore = skuCoreMapper.findSelective(productNomap);
        String productNo="";
        if(skuCore==null){
             skuCore = new SkuCore();
            BeanutilsCopy.copyProperties(skuCustomerVo,skuCore);
            // 获取商品编码
            Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_PRODUCT_NO + skuCore.getCategoryNo()), 1);
            String increment = increase.toString();
            String str = "";
            for (int j = 0; j < 5 - increment.length(); j++) {
                str += "0";
            }
            str += increment;
            productNo = skuCore.getCategoryNo() + str;
            skuCore.setCreateTime(new Date());
            skuCore.setChannel(SkuCoreChannelEnum.手动新增.getState());
            skuCore.setProductNo(productNo);
            skuCore.setRule(skuCustomer.getCustomerPdRule());
            skuCore.setSize(skuCustomer.getCustomerPdSize());
            skuCore.setProductName(skuCustomer.getCustomerPdName());
            skuCoreMapper.insertSelective(skuCore);
        }else {
            productNo=skuCore.getProductNo();
        }
        if(StringUtils.isBlank(skuCustomer.getCustomerPdNo())){
            skuCustomer.setCustomerPdNo(productNo);
        }
        skuCustomer.setProductNo(productNo);
        skuCustomerMapper.insertSelective(skuCustomer);
    }

    @Override
    public List<SkuCustomer> listSelective(Map<String, Object> map) {
        return skuCustomerMapper.listSelective(map);
    }

    @Override
    public ScmCustomerVo saveSCMSkuCustomer(String json){
        ScmCustomerVo vo=new ScmCustomerVo();
        List<SkuCustomerSCMMolde> skuCustomerSCMMoldes = null;
        try {
            skuCustomerSCMMoldes = JSON.parseArray(json, SkuCustomerSCMMolde.class);
        } catch (Exception e) {
            throw  new  BussinessException("json转换异常,请检查数据格式");
        }
        int total=skuCustomerSCMMoldes.size();
        int fail=0;
        List<ScmFailMsgVo>  failList=new ArrayList<>();
        List<ScmSuccessMsgVo>  successList=new ArrayList<>();

        for(SkuCustomerSCMMolde sc:skuCustomerSCMMoldes){
            ScmSuccessMsgVo successVo=new ScmSuccessMsgVo();
            ScmFailMsgVo failMsgVo=new ScmFailMsgVo();
            try {
                BeanValidator.validate(sc);
            } catch (Exception e) {
                fail++;
                failMsgVo.setOutId(sc.getOutId());
                failMsgVo.setFailMsg(e.getMessage());
                failList.add(failMsgVo);
                continue;
            }
            Map<String, Object> brandNamemap = new HashMap<>();
            brandNamemap.put("brandName", sc.getBrandName());
            List<BrandMaster> brandMasters = brandMasterMapper.listSelective(brandNamemap);
            if (brandMasters.size() != 1) {
                fail++;
                failMsgVo.setOutId(sc.getOutId());
                failMsgVo.setFailMsg("品牌名称不存在或者品牌不唯一");
                failList.add(failMsgVo);
                continue;
            }

            Map<String, Object> minUintMap = new HashMap<>();
            minUintMap.put("objName", sc.getMinUint());
            minUintMap.put("isvalid", InvUnitIsvalidEnum.有效.getState());
            int count = invUnitMapper.findCount(minUintMap);
            if (count < 1) {
                fail++;
                failMsgVo.setOutId(sc.getOutId());
                failMsgVo.setFailMsg("最小单位有误");
                failList.add(failMsgVo);
                continue;
            }

            Map<String, Object> categoryNomap = new HashMap<>();
            categoryNomap.put("levelNum", CategoryLevelEnum.三级.getState());
            categoryNomap.put("classCode", sc.getCategoryNo());
            Category category = categoryMapper.findSelective(categoryNomap);
            if (category == null) {
                fail++;
                failMsgVo.setOutId(sc.getOutId());
                failMsgVo.setFailMsg("细分类编码有误");
                failList.add(failMsgVo);
                continue;
            }

            Map<String, Object> customermap = new HashMap<>();
            customermap.put("customerNo",sc.getCustomerNo());
            customermap.put("customerPdName",sc.getCustomerPdName());
            customermap.put("customerPdRule",sc.getCustomerPdRule());
            customermap.put("customerPdSize",sc.getCustomerPdSize());
            customermap.put("brandName",sc.getBrandName());
            customermap.put("minUint",sc.getMinUint());
            SkuCustomer selective = skuCustomerMapper.findSelective(customermap);
            if(selective!=null){
                successVo.setCustomerNo(sc.getCustomerNo());
                successVo.setOutId(sc.getOutId());
                successVo.setProductNo(selective.getProductNo());
                successVo.setCustomerPdNo(selective.getCustomerPdNo());
                successList.add(successVo);
                continue;
            }
            SkuCustomer skuCustomer=new SkuCustomer();
            BeanutilsCopy.copyProperties(sc,skuCustomer);
            try {
                skuCustomer = insertSkuCoreAndCustomer(sc, skuCustomer, category, brandMasters.get(0).getId());
            } catch (Exception e) {
                e.printStackTrace();
                fail++;
                failMsgVo.setOutId(sc.getOutId());
                failMsgVo.setFailMsg("新增时异常，内部错误");
                failList.add(failMsgVo);
                continue;
            }
            successVo.setCustomerNo(sc.getCustomerNo());
            successVo.setOutId(sc.getOutId());
            successVo.setProductNo(skuCustomer.getProductNo());
            successVo.setCustomerPdNo(skuCustomer.getCustomerPdNo());
            successList.add(successVo);
        }
        vo.setFailCount(fail);
        vo.setSuccessCount(total-fail);
        vo.setTotal(total);
        vo.setScmFailMsgVoList(failList);
        vo.setScmSuccessMsgVoList(successList);
        return vo;
    }
    @Transactional
    public SkuCustomer insertSkuCoreAndCustomer(SkuCustomerSCMMolde skuCustomerSCMMolde,SkuCustomer skuCustomer,Category category,Integer brandId){
        String productNo="";
        Map<String, Object> productNamemap = new HashMap<>();
        productNamemap.put("productName", skuCustomerSCMMolde.getCustomerPdName());
        productNamemap.put("minUint", skuCustomerSCMMolde.getMinUint());
        productNamemap.put("brandName", skuCustomerSCMMolde.getBrandName());
        productNamemap.put("rule", skuCustomerSCMMolde.getCustomerPdRule());
        productNamemap.put("sizes", skuCustomerSCMMolde.getCustomerPdSize());
        SkuCore selective = skuCoreMapper.findSelective(productNamemap);
        if(selective==null){
            SkuCore skuCore=new SkuCore();
            skuCore.setCategoryNo(category.getClassCode());
            skuCore.setCategoryId(category.getId());
            //获取三级分类
            skuCore.setLv2CategoryId(category.getParentClass());
            //获取二级分类
            category=categoryMapper.findByPrimary(skuCore.getLv2CategoryId());
            skuCore.setLv2CategoryNo(category.getClassCode());
            skuCore.setLv1CategoryId(category.getParentClass());
            //获取一级分类
            category=categoryMapper.findByPrimary(skuCore.getLv1CategoryId());
            skuCore.setLv1CategoryNo(category.getClassCode());
            skuCore.setBrandId(brandId);
            skuCore.setBrandName(skuCustomerSCMMolde.getBrandName());
            skuCore.setMinUint(skuCustomerSCMMolde.getMinUint());
            skuCore.setProductName(skuCustomerSCMMolde.getCustomerPdName());
            skuCore.setRule(skuCustomerSCMMolde.getCustomerPdRule());
            skuCore.setSize(skuCustomerSCMMolde.getCustomerPdSize());
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
            productNo=skuCore.getCategoryNo()+str;
            skuCoreMapper.insertSelective(skuCore);
        }else {
            productNo=selective.getProductNo();
        }
        skuCustomer.setProductNo(productNo);
        if(StringUtils.isNotBlank(skuCustomerSCMMolde.getCustomerPdNo())){
            skuCustomer.setCustomerPdNo(skuCustomerSCMMolde.getCustomerPdNo());
        }else {
            skuCustomer.setCustomerPdNo(productNo);
        }
        skuCustomerMapper.insertSelective(skuCustomer);
        return skuCustomer;
    }

    @Override
    public List<SkuCustomer> listSelectiveSCM(Map<String, Object> map) {
        return skuCustomerMapper.listSelectiveSCM(map);
    }

    @Override
    public void update(SkuCustomer skuCustomer) {
        Map<String, Object> customermap = new HashMap<>();
        customermap.put("customerNo",skuCustomer.getCustomerNo());
        customermap.put("customerPdName",skuCustomer.getCustomerPdName());
        customermap.put("customerPdRule",skuCustomer.getCustomerPdRule());
        customermap.put("customerPdSize",skuCustomer.getCustomerPdSize());
        customermap.put("customerPdNo",skuCustomer.getCustomerPdNo());
        customermap.put("brandName",skuCustomer.getBrandName());
        customermap.put("minUint",skuCustomer.getMinUint());
        SkuCustomer selective = skuCustomerMapper.findSelective(customermap);
        if(selective!=null && selective.getId().intValue()!= skuCustomer.getId().intValue()){
            throw  new BussinessException("已存在修改过后的商品");
        }
        skuCustomerMapper.updateByPrimaryKeySelective(skuCustomer);
    }
}
