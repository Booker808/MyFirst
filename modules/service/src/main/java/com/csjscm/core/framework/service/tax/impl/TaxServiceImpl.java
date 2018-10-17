package com.csjscm.core.framework.service.tax.impl;

import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.TaxCategoryMapper;
import com.csjscm.core.framework.dao.TaxVersionMapper;
import com.csjscm.core.framework.example.TaxVersionExample;
import com.csjscm.core.framework.model.TaxCategory;
import com.csjscm.core.framework.model.TaxVersion;
import com.csjscm.core.framework.service.tax.TaxService;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public void importTaxCategoryExcel(String userName, Integer versionId, MultipartFile file) {
        ExcelUtil excelUtil=new ExcelUtil();
        List<Row> rows=excelUtil.readExcel(file);
        Stack<TaxCategory> stack=new Stack<>();
        TaxCategory lastTaxCategory;
        for(int i=READ_START_POS;i<rows.size();i++){
            TaxCategory taxCategory=new TaxCategory();
            Row row=rows.get(i);
            String taxCode=ExcelUtil.getCellValue(row.getCell(0)).trim();
            String taxCategoryName=ExcelUtil.getCellValue(row.getCell(1)).trim();
            String description=ExcelUtil.getCellValue(row.getCell(2)).trim();
            String taxRate=ExcelUtil.getCellValue(row.getCell(3)).trim();
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

            if(isExists(taxCategory)){
                throw new BusinessException("导入失败，该版本已存在相应的税务code");
            }
            while(!stack.isEmpty()){
                lastTaxCategory=stack.peek();
                if(taxCategory.getTaxCode().startsWith(
                        lastTaxCategory.getTaxCode().substring(0,lastTaxCategory.getLevel()*2))){
                    taxCategory.setParentCode(lastTaxCategory.getTaxCode());
                    taxCategory.setLevel(stack.size()+1);
                    taxCategoryMapper.insertSelective(taxCategory);
                    stack.push(taxCategory);
                    break;
                }
                stack.pop();
            }
            if(stack.isEmpty()){
                taxCategory.setLevel(1);
                taxCategoryMapper.insertSelective(taxCategory);
                stack.push(taxCategory);
            }
        }
        TaxVersion taxVersion=taxVersionMapper.selectByPrimaryKey(versionId);
        taxVersion.setEditUser(userName);
        taxVersion.setEditTime(null);
        taxVersionMapper.updateByPrimaryKeySelective(taxVersion);
    }

    @Override
    public QueryResult<TaxVersion> queryTaxVersion(int page, int rpp, TaxVersionExample example) {
        PageHelper.startPage(page,rpp);
        List<TaxVersion> taxVersionList=taxVersionMapper.selectByExample(example);
        PageInfo<TaxVersion> pageInfo=new PageInfo<>(taxVersionList);
        QueryResult<TaxVersion> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }

    @Override
    public TaxVersion queryTaxVersionById(Integer id) {
        return taxVersionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer addTaxVersion(TaxVersion taxVersion) {
        if(taxVersion.getEnable()==1 && isExistsEnableVersion(taxVersion)){
            throw new BusinessException("已存在启用分类");
        }
        return taxVersionMapper.insertSelective(taxVersion);
    }

    @Override
    public Integer updateTaxVersion(TaxVersion taxVersion) {
        if(taxVersion.getEnable()==1 && isExistsEnableVersion(taxVersion)){
            throw new BusinessException("已存在启用分类");
        }
        return taxVersionMapper.updateByPrimaryKeySelective(taxVersion);
    }

    @Override
    @Transactional
    public void copyTaxVersion(Integer id, String userName) {
        TaxVersion oldTaxVersion=taxVersionMapper.selectByPrimaryKey(id);
        TaxVersion newTaxVersion=new TaxVersion();
        newTaxVersion.setEditUser(userName);
        newTaxVersion.setCreateUser(userName);
        newTaxVersion.setEnable(0);
        newTaxVersion.setVersion(oldTaxVersion.getVersion()+"复制");
        taxVersionMapper.insertSelective(newTaxVersion);

        Integer newId=newTaxVersion.getId();
        Map<String,Object> map=Maps.newHashMap();
        map.put("newId",newTaxVersion.getId());
        map.put("oldId",oldTaxVersion.getId());
        taxCategoryMapper.copy(map);
    }

    private boolean isExistsEnableVersion(TaxVersion taxVersion) {
        Map<String,Object> map=Maps.newHashMap();
        map.put("enable",1);
        map.put("notId",taxVersion.getId());
        return taxVersionMapper.findCount(map)>0;
    }

    private boolean isExists(TaxCategory taxCategory) {
        Map<String,Object> map=Maps.newHashMap();
        map.put("versionId",taxCategory.getVersionId());
        map.put("taxCode",taxCategory.getTaxCode());
        return taxCategoryMapper.findCount(map)>0;
    }
}
