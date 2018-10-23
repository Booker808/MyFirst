package com.csjscm.core.framework.service.tax.impl;

import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.TaxCategoryMapper;
import com.csjscm.core.framework.dao.TaxVersionMapper;
import com.csjscm.core.framework.example.TaxCategoryExample;
import com.csjscm.core.framework.example.TaxVersionExample;
import com.csjscm.core.framework.model.TaxCategory;
import com.csjscm.core.framework.model.TaxVersion;
import com.csjscm.core.framework.service.tax.TaxService;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
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
    @Autowired
    private RedisServiceFacade redisServiceFacade;

    private static final int READ_START_POS=1;
    @Override
    @Transactional
    public void importTaxCategoryExcel(String userName, Integer versionId, MultipartFile file) {
        ExcelUtil excelUtil=new ExcelUtil();
        List<Row> rows=excelUtil.readExcel(file);
        Stack<TaxCategory> stack=new Stack<>();
        TaxCategory lastTaxCategory;
        int notZeroLength;
        for(int i=READ_START_POS;i<rows.size();i++){
            try{
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
                for(notZeroLength=taxCode.length()-1;notZeroLength>=0;notZeroLength--){
                    if(taxCode.charAt(notZeroLength)!='0'){
                        taxCategory.setLevel((notZeroLength+2)/2);
                        break;
                    }
                }
                if(taxCategory.getLevel()==null){
                    taxCategory.setLevel(1);
                }
                if(StringUtils.isNotEmpty(taxRate)){
                    taxCategory.setTaxRate(BigDecimal.valueOf(Double.parseDouble(taxRate)));
                }
                while(!stack.isEmpty()){
                    lastTaxCategory=stack.peek();
                    if(taxCategory.getLevel()>lastTaxCategory.getLevel()){
                        taxCategory.setParentCode(lastTaxCategory.getTaxCode());
                        taxCategory.setLevel(stack.size()+1);
                        if(isExists(taxCategory)){
                            log.info("versionId:{},{}行,{}税务code重复",versionId,i,taxCategory);
                        }else{
                            taxCategoryMapper.insertSelective(taxCategory);
                        }
                        stack.push(taxCategory);
                        break;
                    }
                    stack.pop();
                }
                if(stack.isEmpty()){
                    if(isExists(taxCategory)){
                        log.info("versionId:{},{}行,{}税务code重复",versionId,i,taxCategory);
                    }else{
                        taxCategoryMapper.insertSelective(taxCategory);
                    }
                    stack.push(taxCategory);
                }
            }catch(Exception e){
                log.error("导入税务分类Excel失败",e);
                throw new BusinessException(String.format("第%d行数据存在异常",i));
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

    @Override
    public List<TaxCategory> queryTaxCategoryAll(Integer versionId) {
        Map<String,Object> map=Maps.newHashMap();
        map.put("versionId",versionId);
        return taxCategoryMapper.selectByCondition(map);
    }

    @Override
    public QueryResult<TaxCategory> queryTaxCategoryList(int page, int rpp, TaxCategoryExample example) {
        PageHelper.startPage(page,rpp);
        List<TaxCategory> taxCategoryList=taxCategoryMapper.selectByExample(example);
        PageInfo<TaxCategory> pageInfo=new PageInfo<>(taxCategoryList);
        QueryResult<TaxCategory> result=new QueryResult<>();
        result.setItems(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public Integer addTaxCategory(String userName, TaxCategory taxCategory) {
        if(isExists(taxCategory)){
            throw new BusinessException("该版本已存在相应的税务code");
        }
        int result=taxCategoryMapper.insertSelective(taxCategory);
        TaxVersion version=taxVersionMapper.selectByPrimaryKey(taxCategory.getVersionId());
        version.setEditUser(userName);
        version.setEditTime(null);
        taxVersionMapper.updateByPrimaryKeySelective(version);
        return result;
    }

    @Override
    public Integer updateTaxCategory(String userName, TaxCategory taxCategory) {
        int result=taxCategoryMapper.updateByPrimaryKeySelective(taxCategory);

        TaxVersion version=taxVersionMapper.selectByPrimaryKey(taxCategory.getVersionId());
        version.setEditUser(userName);
        version.setEditTime(null);
        taxVersionMapper.updateByPrimaryKeySelective(version);
        return null;
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
