package com.csjscm.core.framework.service.template.impl;

import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.dao.EnterpriseAccountMapper;
import com.csjscm.core.framework.dao.EnterprisePurchaseTemplateMapper;
import com.csjscm.core.framework.dao.EnterpriseStandardTemplateMapper;
import com.csjscm.core.framework.example.EnterprisePurchaseTemplateExample;
import com.csjscm.core.framework.model.EnterpriseAccount;
import com.csjscm.core.framework.model.EnterprisePurchaseTemplate;
import com.csjscm.core.framework.model.EnterprisePurchaseTemplateEx;
import com.csjscm.core.framework.model.EnterpriseStandardTemplate;
import com.csjscm.core.framework.service.template.EnterpriseTemplateService;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateDetailVo;
import com.csjscm.core.framework.vo.EnterprisePurchaseTemplateVo;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EnterpriseTemplateServiceImpl implements EnterpriseTemplateService{
    @Autowired
    private EnterpriseStandardTemplateMapper standardTemplateMapper;
    @Autowired
    private EnterprisePurchaseTemplateMapper purchaseTemplateMapper;
    @Autowired
    private EnterpriseAccountMapper accountMapper;

    @Override
    public void addStandardTemplate(EnterpriseStandardTemplate template) {
        if(template.getEnable()==1 && isStandardTemplateExists(template)){
            throw new BusinessException("已存在同类型且开启的模板");
        }
        standardTemplateMapper.insertSelective(template);
    }

    @Override
    public void updateStandardTemplate(EnterpriseStandardTemplate template) {
        if(template.getEnable()==1 && isStandardTemplateExists(template)){
            throw new BusinessException("已存在同类型且开启的模板");
        }
        standardTemplateMapper.updateByPrimaryKeySelective(template);
    }

    @Override
    public List<EnterpriseStandardTemplate> queryStandardTemplate() {
        return standardTemplateMapper.selectAllList();
    }

    @Override
    public EnterpriseStandardTemplate queryStandardTemplateById(Integer id) {
        return standardTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void addPurchaseTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo) {
        if(isPurchaseTemplateExists(templateDetailVo.getEntNumber(),null)){
            throw new BusinessException("此供应商模版已存在");
        }
        EnterprisePurchaseTemplate purchaseTemplate = new EnterprisePurchaseTemplate();
        BeanutilsCopy.copyProperties(templateDetailVo,purchaseTemplate);
        //校验合同模板信息是否合法
        checkPurchaseTemplateValid(purchaseTemplate);
        //插入采购合同模板
        purchaseTemplate.setId(null);
//        purchaseTemplate.setCheckStatus(TemplateCheckStatusEnum.待申请人提交.getStatus());
        Integer templateId=purchaseTemplateMapper.selectNewId();
        if(templateId==null){
            templateId=1;
        }else{
            templateId++;
        }
        purchaseTemplate.setId(templateId);
        templateDetailVo.setId(templateId);
        purchaseTemplateMapper.insertSelective(purchaseTemplate);
        EnterpriseAccount account=new EnterpriseAccount();
        BeanutilsCopy.copyProperties(templateDetailVo,account);
        account.setId(null);
        upsertBasicAccount(account);
    }

    @Override
    @Transactional
    public void updatePurchaseTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo) {
        EnterprisePurchaseTemplate purchaseTemplate = purchaseTemplateMapper.selectByPrimaryKey(templateDetailVo.getId());
        if(purchaseTemplate==null){
            throw new BusinessException("此ID不存在");
        }
        if(isPurchaseTemplateExists(templateDetailVo.getEntNumber(),templateDetailVo.getId())){
            throw new BusinessException("此供应商已有其他启用的模板");
        }
//        if(!purchaseTemplate.getCheckStatus().equals(TemplateCheckStatusEnum.待申请人提交.getStatus())){
//            throw new BusinessException("非待申请人提交状态下不可修改");
//        }
        BeanutilsCopy.copyProperties(templateDetailVo,purchaseTemplate);
        //校验合同模板信息是否合法
        checkPurchaseTemplateValid(purchaseTemplate);
        //插入采购合同模板
//        purchaseTemplate.setCheckStatus(TemplateCheckStatusEnum.待申请人提交.getStatus());
        purchaseTemplateMapper.updateByPrimaryKeySelective(purchaseTemplate);
        EnterpriseAccount account=new EnterpriseAccount();
        BeanutilsCopy.copyProperties(templateDetailVo,account);
        account.setId(null);
        upsertBasicAccount(account);
    }

    @Override
    public QueryResult<EnterprisePurchaseTemplateVo> queryPurchaseTemplate(int page, int rpp, EnterprisePurchaseTemplateExample templateExample) {
        PageHelper.startPage(page,rpp);
        List<EnterprisePurchaseTemplateEx> exList=purchaseTemplateMapper.selectByExample(templateExample);
        PageInfo<EnterprisePurchaseTemplateEx> pageInfo=new PageInfo<>(exList);
        List<EnterprisePurchaseTemplateVo> items= Lists.newLinkedList();
        for(EnterprisePurchaseTemplateEx templateEx:pageInfo.getList()){
            EnterprisePurchaseTemplateVo item=new EnterprisePurchaseTemplateVo();
            BeanutilsCopy.copyProperties(templateEx,item);
            items.add(item);
        }
        QueryResult<EnterprisePurchaseTemplateVo> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(items);
        return result;
    }

    @Override
    public EnterprisePurchaseTemplateDetailVo queryPurchaseTemplateById(Integer id) {
        EnterprisePurchaseTemplateExample example=new EnterprisePurchaseTemplateExample();
        example.setId(id);
        List<EnterprisePurchaseTemplateEx> list=purchaseTemplateMapper.selectByExample(example);
        if(list==null || list.isEmpty()){
            return null;
        }
        EnterprisePurchaseTemplateDetailVo detailVo=new EnterprisePurchaseTemplateDetailVo();
        BeanutilsCopy.copyProperties(list.get(0),detailVo);
        detailVo.setTemplateUrl(getPurchaseTemplateUrl(id));
        return detailVo;
    }

    /**
     * 根据采购模板ID获取模板URL
     *
     * @param id
     * @return
     */
    @Override
    public String getPurchaseTemplateUrl(Integer id) {
        EnterprisePurchaseTemplate purchaseTemplate=purchaseTemplateMapper.selectByPrimaryKey(id);
        switch(purchaseTemplate.getTemplateType()){
            case 1:
            case 2:
                EnterpriseStandardTemplate standardTemplate=standardTemplateMapper.selectCurrentTemplate(purchaseTemplate.getTemplateType());
                if(standardTemplate!=null){
                    return standardTemplate.getTemplateUrl();
                }else{
                    return "";
                }
            case 3:
                return purchaseTemplate.getTemplateUrl();
            default:
                return "";
        }
    }

    @Override
    public void updateArchiveTemplate(EnterprisePurchaseTemplateDetailVo templateDetailVo) {
        EnterprisePurchaseTemplate purchaseTemplate = purchaseTemplateMapper.selectByPrimaryKey(templateDetailVo.getId());
        if(purchaseTemplate==null){
            throw new BusinessException("此ID不存在");
        }
        if(isPurchaseTemplateExists(templateDetailVo.getEntNumber(),templateDetailVo.getId())){
            throw new BusinessException("此供应商已有其他启用的模板");
        }
        purchaseTemplate=new EnterprisePurchaseTemplate();
        purchaseTemplate.setId(templateDetailVo.getId());
        purchaseTemplate.setEnable(templateDetailVo.getEnable());
        purchaseTemplate.setEditUser(templateDetailVo.getEditUser());
        purchaseTemplateMapper.updateByPrimaryKeySelective(purchaseTemplate);
    }

    private boolean isPurchaseTemplateExists(String entNumber,Integer templateId){
        Map<String,Object> map= Maps.newHashMap();
        map.put("notId",templateId);
        map.put("entNumber",entNumber);
        map.put("enable",1);
        return purchaseTemplateMapper.findCount(map)>0;
    }

    /**
     * 检验是否存在同类型的已启用的标准模板
     *
     * @param template
     * @return
     */
    private boolean isStandardTemplateExists(EnterpriseStandardTemplate template) {
        Map<String,Object> map=Maps.newHashMap();
        map.put("notId",template.getId());
        map.put("templateType",template.getTemplateType());
        map.put("enable",1);
        return standardTemplateMapper.findCount(map)>0;
    }

    /**
     * 校验采购模板是否合法
     *
     * @param purchaseTemplate
     * @return
     */
    private void checkPurchaseTemplateValid(EnterprisePurchaseTemplate purchaseTemplate) throws BusinessException{
        switch(purchaseTemplate.getPayType()){
            case 1:
                if(purchaseTemplate.getPayDate()==null){
                    throw new BusinessException("付款工作日不得为空");
                }
                break;
            case 2:
                if(purchaseTemplate.getPrepayRate()==null){
                    throw new BusinessException("预付比例不得为空");
                }
                if(purchaseTemplate.getPickRate()==null){
                    throw new BusinessException("提货比例不得为空");
                }
                if(purchaseTemplate.getWarrantyRate()==null){
                    throw new BusinessException("质保金比例不得为空");
                }
                break;
            default:
                throw new BusinessException("结算方式存在异常");
        }
        switch(purchaseTemplate.getTemplateType()){
            case 1:
            case 2:
                purchaseTemplate.setTemplateUrl("");
                return;
            case 3:
                if(StringUtils.isBlank(purchaseTemplate.getTemplateUrl())){
                    throw new BusinessException("合同模板不得为空");
                }
                return;
            default:
                throw new BusinessException("合同类型异常");
        }
    }

    private void upsertBasicAccount(EnterpriseAccount account){
        //判断账户信息是否全为空
        if(StringUtils.isNotBlank(account.getBankNo())
                ||StringUtils.isNotBlank(account.getBankName())
                ||StringUtils.isNotBlank(account.getAccountName())){
            //若已存在则修改基本户，否则新增基本户
            if(isBasicBankExists(account)){
                accountMapper.updateBasicBankByEntNo(account);
            }else{
                account.setAccountType(1);
                accountMapper.insertSelective(account);
            }
        }
    }

    private boolean isBasicBankExists(EnterpriseAccount account){
        Map<String,Object> map=Maps.newHashMap();
        map.put("entNumber",account.getEntNumber());
        map.put("accountType",1);
        map.put("isdelete",0);
        return accountMapper.findCount(map)>0;
    }

}
