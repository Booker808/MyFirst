package com.csjscm.core.framework.service.tax.impl;

import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.SkuCustomerMapper;
import com.csjscm.core.framework.dao.TaxCategoryMapper;
import com.csjscm.core.framework.dao.TaxCustomerMapper;
import com.csjscm.core.framework.model.TaxCategory;
import com.csjscm.core.framework.model.TaxCustomer;
import com.csjscm.core.framework.service.tax.TaxCustomerService;
import com.csjscm.core.framework.vo.SkuCustomerVo;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 客户商品与税收关联记录参考表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-17 16:12:45
 */
 
@Service
public class TaxCustomerServiceImpl implements TaxCustomerService {
	
    private static final Logger logger = LoggerFactory.getLogger(TaxCustomerServiceImpl.class);
   
    @Autowired
    private TaxCustomerMapper taxCustomerMapper;
    @Autowired
    private SkuCustomerMapper skuCustomerMapper;
    @Autowired
    private TaxCategoryMapper taxCategoryMapper;
    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;

    @Override
    public QueryResult<TaxCustomer> findPage(int page, int rpp, Map<String, Object> map) {
        PageHelper.startPage(page,rpp);
        List<TaxCustomer> taxCustomers = taxCustomerMapper.listSelective(map);
        PageInfo<TaxCustomer> pageInfo=new PageInfo<>(taxCustomers);
        QueryResult<TaxCustomer> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file) {
        //成功条数
        int successCount = 0;
        //失败条数
        int failCount = 0;
        //总条数
        int total = 0;
        //失败信息
        List<String> failList = new ArrayList<>();
        //失败的数据
        List<Map<String,Object>> failSkuCustomer = new ArrayList<>();
        ExcelUtil excelUtil = new ExcelUtil();
        List<Row> rows = excelUtil.readExcel(file);
        total = rows.size() - READ_START_POS;
        int failRow = 0;
        int failCell = 0;
        for(int i=READ_START_POS;i<rows.size();i++){
            Map<String,Object> fail=new HashMap<>();
            String failMsg = "";
            String failMsgStr = "";
            boolean issuccess=true;
            failRow = i+1;
            try {
                Row row = rows.get(i);
                String customerPdName = ExcelUtil.getCellValue(row.getCell(0));
                String  taxCode = ExcelUtil.getCellValue(row.getCell(1));
                if(StringUtils.isNotBlank(customerPdName)){
                    row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                    customerPdName = ExcelUtil.getCellValue(row.getCell(0));
                    Map<String,Object> customerPdNamemap=new HashMap<>();
                    customerPdNamemap.put("customerPdName",customerPdName);
                    int count = skuCustomerMapper.findCount(customerPdNamemap);
                    if(count<1){
                        failCell = 1;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品名称不存在");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }else {
                    failCell = 1;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "商品名称不能为空");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                }
                TaxCategory one=new TaxCategory();
                if(StringUtils.isNotBlank(taxCode)){
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    taxCode = ExcelUtil.getCellValue(row.getCell(1));
                    Map<String,Object> taxCodemap=new HashMap<>();
                    taxCodemap.put("taxCode",taxCode);
                    one = taxCategoryMapper.findOne(taxCodemap);
                    if(one==null){
                        failCell = 2;
                        failMsg = ExcelUtil.getFailMsg(failRow, failCell, "税收分类编码不存在");
                        failList.add(failMsg);
                        failMsgStr += failMsg;
                        issuccess = false;
                    }
                }else {
                    failCell = 2;
                    failMsg = ExcelUtil.getFailMsg(failRow, failCell, "税收分类编码不能为空");
                    failList.add(failMsg);
                    failMsgStr += failMsg;
                    issuccess = false;
                }



                if(issuccess){
                    TaxCustomer taxCustomer=new TaxCustomer();
                    taxCustomer.setCustomerPdName(customerPdName);
                    taxCustomer.setTaxCategoryName(one.getTaxCategoryName());
                    taxCustomer.setTaxCode(taxCode);
                    taxCustomerMapper.insertSelective(taxCustomer);
                }else {
                    fail.put("customerPdName",customerPdName);
                    fail.put("taxCode",taxCode);
                    fail.put("failMessage",failMsgStr);
                    failSkuCustomer.add(fail);
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                failMsg = ExcelUtil.getFailMsg(failRow, failCell, "未知异常");
                failList.add(failMsg);
                Map<String,Object> fail1=new HashMap<>();
                fail1.put("failMessage",failMsg);
                failSkuCustomer.add(fail1);
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
}