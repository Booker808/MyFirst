package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.CategoryLevelEnum;
import com.csjscm.core.framework.common.enums.SkuCoreChannelEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.dao.SkuCustomerMapper;
import com.csjscm.core.framework.example.SkuCustomerExample;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.impl.SkuCoreServiceImpl;
import com.csjscm.core.framework.service.product.ProductCustomerService;
import com.csjscm.core.framework.vo.SkuCoreVo;
import com.csjscm.core.framework.vo.SkuCustomerSCMMolde;
import com.csjscm.core.framework.vo.SkuCustomerVo;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisDistributedCounterObject;
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

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ProductCustomerServiceImpl implements ProductCustomerService {
    private static final Logger logger = LoggerFactory.getLogger(ProductCustomerServiceImpl.class);
    @Autowired
    private SkuCustomerMapper skuCustomerMapper;
    @Autowired
    private SkuCoreMapper skuCoreMapper;

    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;
    /**
     * 最少字段
     */
    private final static int MIX_CELL = 5;
    /**
     * 最多字段
     */
    private final static int MAX_CELL = 5;

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
                String  productNo = ExcelUtil.getCellValue(row.getCell(0)).trim();
                String  customerPdNo = ExcelUtil.getCellValue(row.getCell(1));
                String  customerPdName = ExcelUtil.getCellValue(row.getCell(2)).trim();
                String  customerPdRule = ExcelUtil.getCellValue(row.getCell(3)).trim();
                String customerPdSize = ExcelUtil.getCellValue(row.getCell(4)).trim();

                //校验productNo
                if (StringUtils.isBlank(productNo) || productNo.length()>20) {
                    failCell = 1;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码不能为空或者长度大于20");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }else {
                    row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                    productNo = ExcelUtil.getCellValue(row.getCell(0));
                }
                Map<String, Object> productNomap = new HashMap<>();
                productNomap.put("productNo", productNo);
                SkuCore skuCore = skuCoreMapper.findSelective(productNomap);
                if (skuCore == null) {
                    failCell = 1;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品编码不存在");
                    failList.add(failMsg);
                    failMsgStr+=failMsg;
                    issuccess=false;
                }
                //校验customerPdNo
                Map<String, Object> customermap = new HashMap<>();
                if(StringUtils.isNotBlank(customerPdNo)){
                    customermap.put("customerPdNo",customerPdNo);
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
                    int count = skuCustomerMapper.findCount(productNomap);
                    if(count>0){
                        failCell = 2;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "客户物料编码已存在");
                        failList.add(failMsg);
                        failMsgStr+=failMsg;
                        issuccess=false;
                    }
                }

                //检验 customerPdName customerPdRule customerPdSize
                 if(StringUtils.isBlank(customerPdName) ||StringUtils.isBlank(customerPdRule) ||StringUtils.isBlank(customerPdSize) || customerPdName.length()>256 ||customerPdRule.length()>256 ||customerPdSize.length()>256){
                     failCell = 3;
                     failMsg = ExcelUtil.getFailMsg(failRow, failCell, "客户物料名称、型号或规格为空或者长度大于256");
                     failList.add(failMsg);
                     failMsgStr+=failMsg;
                     issuccess=false;
                 }else {
                     row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
                     customerPdName = ExcelUtil.getCellValue(row.getCell(2));
                     row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
                     customerPdRule = ExcelUtil.getCellValue(row.getCell(3));
                     row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
                     customerPdSize = ExcelUtil.getCellValue(row.getCell(4));
                 }
                customermap.put("customerNo",customerNo);
                customermap.put("customerPdName",customerPdName);
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
                    skuCustomer.setCreateTime(new Date());
                    skuCustomer.setCustomerNo(customerNo);
                    skuCustomer.setCustomerPdName(customerPdName);
                    skuCustomer.setCustomerPdNo(customerPdNo);
                    skuCustomer.setCustomerPdRule(customerPdRule);
                    skuCustomer.setCustomerPdSize(customerPdSize);
                    skuCustomer.setProductNo(productNo);
                    skuCustomerMapper.insertSelective(skuCustomer);
                    successCount++;
                }else {
                    skuCustomerVo.setCustomerPdName(customerPdName);
                    skuCustomerVo.setCustomerPdNo(customerPdNo);
                    skuCustomerVo.setCustomerPdRule(customerPdRule);
                    skuCustomerVo.setCustomerPdSize(customerPdSize);
                    skuCustomerVo.setProductNo(productNo);
                    skuCustomerVo.setFailMessage(failMsgStr);
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
    public void save(SkuCustomer skuCustomer) {
        skuCustomer.setCreateTime(new Date());
        Map<String, Object> customermap = new HashMap<>();
        customermap.put("customerNo",skuCustomer.getCustomerNo());
        customermap.put("customerPdName",skuCustomer.getCustomerPdName());
        customermap.put("customerPdRule",skuCustomer.getCustomerPdRule());
        customermap.put("customerPdSize",skuCustomer.getCustomerPdSize());
        customermap.put("customerPdNo",skuCustomer.getCustomerPdNo());
        int count = skuCustomerMapper.findCount(customermap);
        if(count>0){
            throw  new  BussinessException("客户商品已存在");
        }
        Map<String, Object> productNomap = new HashMap<>();
        productNomap.put("productNo", skuCustomer.getProductNo());
        SkuCore skuCore = skuCoreMapper.findSelective(productNomap);
        if(skuCore==null){
           throw  new BussinessException("商品编码不存在");
        }
        skuCustomerMapper.insertSelective(skuCustomer);
    }

    @Override
    public List<SkuCustomer> listSelective(Map<String, Object> map) {
        return skuCustomerMapper.listSelective(map);
    }

    @Override
    public SkuCustomer saveSCMSkuCustomer(SkuCustomerSCMMolde skuCustomerSCMMolde){
        SkuCustomer skuCustomer=new SkuCustomer();
        Map<String, Object> map = new HashMap<>();
        map.put("productNo",skuCustomerSCMMolde.getProductNo());
        SkuCore skuCore = skuCoreMapper.findSelective(map);
        if(skuCore==null){
            throw  new  BussinessException("商品编码有误");
        }
        if(StringUtils.isNotBlank(skuCustomerSCMMolde.getCustomerPdNo())){
            map.clear();
            map.put("customerNo", skuCustomerSCMMolde.getCustomerNo());
            map.put("customerPdNo", skuCustomerSCMMolde.getCustomerPdNo());
            int count = skuCustomerMapper.findCount(map);
            if(count>0){
                throw  new BussinessException("客户商品编码已存在");
            }
            skuCustomer.setCustomerPdNo(skuCustomerSCMMolde.getCustomerPdNo());
        }
        map.clear();
        map.put("productNo",skuCustomerSCMMolde.getProductNo());
        map.put("customerNo",skuCustomerSCMMolde.getCustomerNo());
        int count = skuCustomerMapper.findCount(map);
        if(count>0){
            throw  new  BussinessException("商品编码与该客户编码已有绑定关系");
        }
        skuCustomer.setCreateTime(new Date());
        skuCustomer.setProductNo(skuCustomerSCMMolde.getProductNo());
        skuCustomer.setCustomerNo(skuCustomerSCMMolde.getCustomerNo());
        skuCustomer.setRecentQuotation(skuCustomerSCMMolde.getRecentQuotation());
        skuCustomer.setReferencePrice(skuCustomerSCMMolde.getReferencePrice());
        skuCustomer.setCustomerPdSize(skuCore.getSize());
        skuCustomer.setCustomerPdRule(skuCore.getRule());
        skuCustomer.setCustomerPdName(skuCore.getProductName());
         skuCustomerMapper.insertSelective(skuCustomer);
        return skuCustomer;
    }
}
