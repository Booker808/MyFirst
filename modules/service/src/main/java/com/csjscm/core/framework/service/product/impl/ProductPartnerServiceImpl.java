package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.BrandMasterMapper;
import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.dao.SkuPartnerMapper;
import com.csjscm.core.framework.dao.SpCategoryMapper;
import com.csjscm.core.framework.example.SkuPartnerExample;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.product.ProductPartnerService;
import com.csjscm.core.framework.vo.SkuCustomerVo;
import com.csjscm.core.framework.vo.SkuPartnerModel;
import com.csjscm.core.framework.vo.SkuPartnerVo;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;

    @Override
    public QueryResult<SkuPartnerEx> queryPartnerProduct(int page, int rpp, SkuPartnerExample example) {
        PageHelper.startPage(page,rpp);
        List<SkuPartnerEx> skuPartner=skuPartnerMapper.selectExByExample(example);
        PageInfo<SkuPartnerEx> pageInfo=new PageInfo<>(skuPartner);
        QueryResult<SkuPartnerEx> result=new QueryResult<>();
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
        for(int i=READ_START_POS;i<rows.size();i++){
            String failMsg = "";
            String failMsgStr = "";
            boolean issuccess=true;
            failRow = i+1;
            try {
                SkuPartnerVo skuPartnerVo =new SkuPartnerVo();
                SkuPartner skuPartner=new SkuPartner();
                Row row = rows.get(i);
                //获取普通字段
                String  supplyPdNo = ExcelUtil.getCellValue(row.getCell(0)).trim();
                String  supplyPdName = ExcelUtil.getCellValue(row.getCell(1));
                String  brandName = ExcelUtil.getCellValue(row.getCell(2)).trim();
                String  supplyPdRule = ExcelUtil.getCellValue(row.getCell(3)).trim();
                String supplyPdSize = ExcelUtil.getCellValue(row.getCell(4)).trim();
                String free = ExcelUtil.getCellValue(row.getCell(5)).trim();
                String productNo = ExcelUtil.getCellValue(row.getCell(6)).trim();
                //校验 supplyPdNo
                if(StringUtils.isNotBlank(supplyPdNo)){
                    row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdNo = ExcelUtil.getCellValue(row.getCell(0));
                    if(supplyPdNo.length()>20){
                        failCell = 2;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "供应商商品编码长度大于20");
                        failList.add(failMsg);
                        failMsgStr+=failMsg;
                        issuccess=false;
                    }
                    Map<String, Object> supplyNomap = new HashMap<>();
                    supplyNomap.put("supplyNo", supplyNo);
                    supplyNomap.put("supplyPdNo", supplyPdNo);
                    int count = skuPartnerMapper.findCount(supplyNomap);
                    if(count>0){
                        failCell = 1;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "供应商商品编码已存在");
                        failList.add(failMsg);
                        failMsgStr+=failMsg;
                        issuccess=false;
                    }
                }

                //校验supplyPdName
                if (StringUtils.isBlank(supplyPdName) || supplyPdName.length()>256) {
                    failCell = 2;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品名称不能为空或者长度大于256");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }else {
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdName = ExcelUtil.getCellValue(row.getCell(1));
                }
                //校验品牌
                if(StringUtils.isBlank(brandName) || brandName.length()>255){
                    failCell = 3;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "品牌名称不能为空或者长度大于256");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }else {
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
                    brandName = ExcelUtil.getCellValue(row.getCell(2));
                }
                Map<String, Object> brandNamemap = new HashMap<>();
                brandNamemap.put("brandName", brandName);
                BrandMaster brandMaster = brandMasterMapper.findSelective(brandNamemap);
                if (brandMaster == null) {
                    failCell = 3;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "品牌名称不存在");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }
                //校验supplyPdRule
                if(StringUtils.isNotBlank(supplyPdRule)){
                    row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdRule = ExcelUtil.getCellValue(row.getCell(3));
                    if(supplyPdRule.length()>255){
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "规格长度不能超过256");
                        failList.add(failMsg);
                        failMsgStr+=failMsg;
                        issuccess=false;
                    }
                }
                //校验supplyPdSize
                if(StringUtils.isNotBlank(supplyPdSize)){
                    row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
                    supplyPdSize = ExcelUtil.getCellValue(row.getCell(4));
                    if(supplyPdSize.length()>255){
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "型号长度不能超过256");
                        failList.add(failMsg);
                        failMsgStr+=failMsg;
                        issuccess=false;
                    }
                }
                //校验productNo
                if (StringUtils.isBlank(productNo) || productNo.length()>20) {
                    failCell = 1;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码不能为空或者长度大于20");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }else {
                    row.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
                    productNo = ExcelUtil.getCellValue(row.getCell(6));
                }
                Map<String, Object> productNomap = new HashMap<>();
                productNomap.put("productNo", productNo);
                SkuCore skuCore = skuCoreMapper.findSelective(productNomap);
                if (skuCore == null) {
                    failCell = 7;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码不存在");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }

                //校验供应商商品是否存在
                Map<String, Object> parrnerMap = new HashMap<>();
                parrnerMap.put("supplyNo",supplyNo);
                parrnerMap.put("supplyPdName",supplyPdName);
                parrnerMap.put("brandName",brandName);
                parrnerMap.put("supplyPdRule",supplyPdRule);
                parrnerMap.put("supplyPdSize",supplyPdSize);
                int count = skuPartnerMapper.findCount(parrnerMap);
                if(count>0){
                    failCell = 0;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "该商品已存在");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }
                //组装成功数据
                if(issuccess){
                    skuPartner.setBrandId(brandMaster.getId().toString());
                    skuPartner.setBrandName(brandName);
                    skuPartner.setCreateTime(new Date());
                    skuPartner.setProductNo(productNo);
                    skuPartner.setSupplyNo(supplyNo);
                    skuPartner.setSupplyPdName(supplyPdName);
                    skuPartner.setSupplyPdNo(supplyPdNo);
                    skuPartner.setSupplyPdRule(supplyPdRule);
                    skuPartner.setSupplyPdSize(supplyPdSize);
                    skuPartnerMapper.insertSelective(skuPartner);
                    successCount++;
                }else {
                    skuPartnerVo.setBrandName(brandName);
                    skuPartnerVo.setFailMessage(failMsgStr);
                    skuPartnerVo.setProductNo(productNo);
                    skuPartnerVo.setSupplyPdName(supplyPdName);
                    skuPartnerVo.setSupplyPdNo(supplyPdNo);
                    skuPartnerVo.setSupplyPdRule(supplyPdRule);
                    skuPartnerVo.setSupplyPdSize(supplyPdSize);
                    skuPartnerVo.setFree(free);
                    failSkuPartnerVo.add(skuPartnerVo);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
               failMsg = ExcelUtil.getFailMsg(failRow, failCell, "未知异常");
                failList.add(failMsg);
                SkuPartnerVo skuPartnerVo =new SkuPartnerVo();
                skuPartnerVo.setFailMessage(failMsg);
                failSkuPartnerVo.add(skuPartnerVo);
                continue;
            }
        }
        failCount=total-successCount;
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("failData",failSkuPartnerVo);
        resultMap.put("failCount",failCount);
        resultMap.put("successCount",successCount);
        resultMap.put("failList",failList);
        resultMap.put("total",total);
        return resultMap;
    }

    @Override
    public void save(SkuPartner skuPartner) {
        skuPartner.setCreateTime(new Date());
        //校验供应商商品是否存在
        Map<String, Object> parrnerMap = new HashMap<>();
        parrnerMap.put("supplyNo",skuPartner.getSupplyNo());
        parrnerMap.put("supplyPdName",skuPartner.getSupplyPdName());
        parrnerMap.put("brandName",skuPartner.getBrandName());
        parrnerMap.put("supplyPdRule",skuPartner.getSupplyPdRule());
        parrnerMap.put("supplyPdSize",skuPartner.getSupplyPdSize());
        int count = skuPartnerMapper.findCount(parrnerMap);
        if(count>0){
          throw  new  BussinessException("该商品已存在");
        }
        parrnerMap.clear();
        parrnerMap.put("supplyPdNo",skuPartner.getSupplyPdNo());
        int count1 = skuPartnerMapper.findCount(parrnerMap);
        if(count1>0){
            throw  new  BussinessException("供应商商品编码已存在");
        }
        Map<String, Object> productNomap = new HashMap<>();
        productNomap.put("productNo", skuPartner.getProductNo());
        SkuCore skuCore = skuCoreMapper.findSelective(productNomap);
        if(skuCore==null){
            throw  new  BussinessException("商品编码不存在");
        }
        skuPartnerMapper.insertSelective(skuPartner);
    }

    @Override
    public List<SkuPartner> listSelective(Map<String, Object> map) {
        return skuPartnerMapper.listSelective(map);
    }

    @Override
    public int savePartner(SkuPartnerModel skuPartnerModel) {
        Map<String, Object> parrnerMap = new HashMap<>();
        if(StringUtils.isNotBlank(skuPartnerModel.getSupplyPdNo())){
            parrnerMap.put("supplyPdNo",skuPartnerModel.getSupplyPdNo());
            int count1 = skuPartnerMapper.findCount(parrnerMap);
            if(count1>0){
                throw  new  BussinessException("供应商商品编码已存在");
            }
        }
        parrnerMap.clear();
        parrnerMap.put("brandName", skuPartnerModel.getBrandName());
        int count = brandMasterMapper.findCount(parrnerMap);
        if(count<1){
            throw  new  BussinessException("品牌名称信息有误，不存在该品牌名称");
        }
        parrnerMap.clear();
        parrnerMap.put("classCode",skuPartnerModel.getClassCode());
        spCategoryMapper.findCount(parrnerMap);

        parrnerMap.clear();
        parrnerMap.put("supplyNo",skuPartnerModel.getSupplyNo());
        parrnerMap.put("supplyPdName",skuPartnerModel.getSupplyPdName());
        parrnerMap.put("brandName",skuPartnerModel.getBrandName());
        parrnerMap.put("supplyPdRule",skuPartnerModel.getSupplyPdRule());
        parrnerMap.put("supplyPdSize",skuPartnerModel.getSupplyPdSize());
        int count2 = skuPartnerMapper.findCount(parrnerMap);
        if(count2>0){
            throw  new  BussinessException("该商品已存在");
        }
        Map<String, Object> productNamemap = new HashMap<>();
        productNamemap.put("productName", skuPartnerModel.getSupplyPdName());
        productNamemap.put("brandName", skuPartnerModel.getBrandName());
        productNamemap.put("rule", skuPartnerModel.getSupplyPdRule());
        productNamemap.put("size", skuPartnerModel.getSupplyPdSize());
        List<SkuCore> skuCores = skuCoreMapper.listSelective(productNamemap);
        if(skuCores.size()<1){

        }
        return 0;
    }
}
