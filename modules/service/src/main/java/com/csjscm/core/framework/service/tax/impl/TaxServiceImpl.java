package com.csjscm.core.framework.service.tax.impl;

import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.TaxCategoryMapper;
import com.csjscm.core.framework.dao.TaxVersionMapper;
import com.csjscm.core.framework.model.TaxCategory;
import com.csjscm.core.framework.model.TaxVersion;
import com.csjscm.core.framework.service.tax.TaxService;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
@Slf4j
public class TaxServiceImpl implements TaxService {
    @Autowired
    private TaxCategoryMapper taxCategoryMapper;
    @Autowired
    private TaxVersionMapper taxVersionMapper;

    private static final int READ_START_POS=1;
    @Override
    @Transactional
    public Map<String, Object> importtaxCategoryExcel(String userName,Integer versionId, MultipartFile file) {
        //成功条数
        int successCount = 0;
        //失败条数
        int failCount;
        //总条数
        int total;
        //失败信息
        List<String> failList = Lists.newArrayList();
        ExcelUtil excelUtil=new ExcelUtil();
        List<Row> rows=excelUtil.readExcel(file);
        total= rows.size()-READ_START_POS;
        int failRow;
        int failCell=0;
        Stack<TaxCategory> stack=new Stack<>();
        TaxCategory lastTaxCategory;
        for(int i=READ_START_POS;i<rows.size();i++){
            String failMsg;
            failRow = i + 1;
//            try{
                TaxCategory taxCategory=new TaxCategory();
                Row row=rows.get(i);
                String levelChar=ExcelUtil.getCellValue(row.getCell(0)).trim();
                String taxCode=ExcelUtil.getCellValue(row.getCell(1)).trim();
                String taxCategoryName=ExcelUtil.getCellValue(row.getCell(2)).trim();
                String description=ExcelUtil.getCellValue(row.getCell(3)).trim();
                String taxRate=ExcelUtil.getCellValue(row.getCell(4)).trim();
                if(StringUtils.isNotEmpty(description)&&description.startsWith("<![CDATA[")){
                    description=description.substring(9,description.length()-3);
                }
                if(StringUtils.isNotEmpty(taxRate)&&taxRate.startsWith("<![CDATA[")){
                    taxRate=taxRate.substring(9,taxRate.length()-4);
                }

                taxCategory.setVersionId(versionId);
                taxCategory.setTaxCode(taxCode);
                taxCategory.setDescription(description);
                taxCategory.setTaxCategoryName(taxCategoryName);
                if(StringUtils.isNotEmpty(taxRate)){
                    taxCategory.setTaxRate(BigDecimal.valueOf(Double.parseDouble(taxRate)));
                }
                taxCategory.setLevel(levelChar.charAt(0)-'A'+1);

                if(isExists(taxCategory)){
                    throw new BusinessException("导入失败，该版本已存在相应的税务code");
                }
                while(!stack.isEmpty()){
                    lastTaxCategory=stack.peek();
                    if(lastTaxCategory.getLevel()<taxCategory.getLevel()){
                        taxCategory.setParentCode(lastTaxCategory.getTaxCode());
                        taxCategoryMapper.insertSelective(taxCategory);
                        stack.push(taxCategory);
                        break;
                    }
                    stack.pop();
                }
                if(stack.isEmpty()){
                    taxCategoryMapper.insertSelective(taxCategory);
                    stack.push(taxCategory);
                }
                successCount++;
//            }catch (Exception e){
//                log.error(e.getMessage());
//                failMsg = ExcelUtil.getFailMsg(failRow, failCell, "未知异常");
//                failList.add(failMsg);
//            }
        }
        TaxVersion taxVersion=new TaxVersion();
        taxVersion.setId(versionId);
        taxVersion.setEditUser(userName);
        taxVersionMapper.updateByPrimaryKeySelective(taxVersion);
        failCount=total-successCount;

        Map<String,Object> resultMap= Maps.newHashMap();
        resultMap.put("failCount",failCount);
        resultMap.put("successCount",successCount);
        resultMap.put("failList",failList);
        resultMap.put("total",total);
        return resultMap;
    }

    private boolean isExists(TaxCategory taxCategory) {
        Map<String,Object> map=Maps.newHashMap();
        map.put("versionId",taxCategory.getVersionId());
        map.put("taxCode",taxCategory.getTaxCode());
        return taxCategoryMapper.findCount(map)>0;
    }
}
