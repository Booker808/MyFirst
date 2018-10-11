package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.AccountTypeEnum;
import com.csjscm.core.framework.common.enums.AttachmentTypeEnum;
import com.csjscm.core.framework.common.enums.ContactTypeEnum;
import com.csjscm.core.framework.common.enums.TradeTypeEnum;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.*;
import com.csjscm.core.framework.example.EnterpriseInfoExample;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.service.enterprise.EnterpriseProtocolService;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoAccessDto;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoDto;
import com.csjscm.core.framework.vo.EnterpriseInfoSPModel;
import com.csjscm.core.framework.vo.SkuCustomerPageVo;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {
    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;
    @Autowired
    private EnterpriseContactMapper enterpriseContactMapper;
    @Autowired
    private EnterpriseAttachmentMapper enterpriseAttachmentMapper;
    @Autowired
    private EnterpriseAccountMapper enterpriseAccountMapper;
    @Autowired
    private EnterpriseCategoryMapper enterpriseCategoryMapper;
    @Autowired
    private EnterpriseProtocolMapper enterpriseProtocolMapper;
    @Autowired
    private EnterpriseReceiveMapper enterpriseReceiveMapper;

    @Override
    public String createEnterpriseNo() {
        //暂定 企业编码生成规则
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date())+"0001";
        Long newno=Long.parseLong(format);
        String maxEntNumber = enterpriseInfoMapper.findMaxEntNumber();
        if(maxEntNumber==null|| StringUtils.isEmpty(maxEntNumber)){
            return Constant.ENTNUMBER_INDEX+format;
        }else{
            maxEntNumber=maxEntNumber.replace(Constant.ENTNUMBER_INDEX,"").trim();
        }
        Long oldno=Long.parseLong(maxEntNumber);
        String  entNumber=Constant.ENTNUMBER_INDEX+format;
        if(oldno>=newno){
            Long i = oldno + 1;
            entNumber=Constant.ENTNUMBER_INDEX+i;
        }
        return entNumber;
    }

    @Override
    @Transactional
    public String insertEnterpriseInfo(EnterpriseInfoDto enterpriseInfoDto) {
        String result;
        switch (enterpriseInfoDto.getEnterpriseInfo().getEntType()){
            //采购商
            case 2:
                //供应商&采购商
            case 3:
                result=checkCustomerEmpty(enterpriseInfoDto);
                break;
            //供应商
            case 1:
                result=checkSupplyEmpty(enterpriseInfoDto);
                break;
            default:
                return "企业类型错误";
        }
        if(StringUtils.isNotEmpty(result)){
            return result;
        }
        if(StringUtils.isNotEmpty(getEpNoByEpName(enterpriseInfoDto.getEnterpriseInfo().getEntName()))){
            return "该企业已存在";
        }

        EnterpriseInfo enterpriseInfo=enterpriseInfoDto.getEnterpriseInfo();
//        BeanutilsCopy.copyProperties(enterpriseInfoDto,enterpriseInfo);

        EnterpriseContact legalPerson=enterpriseInfoDto.getLegalPerson(),
                contactPerson=enterpriseInfoDto.getEnterpriseContact();
        if(legalPerson!=null){
            legalPerson.setEntNumber(enterpriseInfoDto.getEnterpriseInfo().getEntNumber());
            legalPerson.setContactType(1);
        }

        if(contactPerson!=null){
            contactPerson.setEntNumber(enterpriseInfoDto.getEnterpriseInfo().getEntNumber());
            contactPerson.setContactType(2);
        }

        EnterpriseAttachment attachment=enterpriseInfoDto.getEnterpriseAttachment();
        if(attachment!=null){
            attachment.setEntNumber(enterpriseInfoDto.getEnterpriseInfo().getEntNumber());
            if(StringUtils.isEmpty(attachment.getAttachmentName())){
                attachment.setAttachmentName("营业执照");
            }
            attachment.setAttachmentType(1);
        }

        EnterpriseAccount account=enterpriseInfoDto.getEnterpriseAccount();
        if(account!=null){
            account.setEntNumber(enterpriseInfoDto.getEnterpriseInfo().getEntNumber());
            account.setAccountType(1);
        }

        int count=enterpriseInfoMapper.insertSelective(enterpriseInfo);
        if(count<=0)
            return "Info表插入失败";
        List<String> resultList= Lists.newLinkedList();
        if(legalPerson!=null && StringUtils.isNotEmpty(legalPerson.getName())){
            count=enterpriseContactMapper.insertSelective(legalPerson);
            if(count<=0)
                resultList.add("法人");
        }
        if(contactPerson!=null){
            count=enterpriseContactMapper.insertSelective(contactPerson);
            if(count<=0)
                resultList.add("联系人");
        }

        if(attachment!=null && StringUtils.isNotEmpty(attachment.getAttachmentUrl())){
            count=enterpriseAttachmentMapper.insertSelective(attachment);
            if(count<=0)
                resultList.add("附件");
        }

        if(account!=null &&
                (StringUtils.isNotEmpty(account.getBankName())||StringUtils.isNotEmpty(account.getBankNo()))){
            count=enterpriseAccountMapper.insertSelective(account);
            if(count<=0)
                resultList.add("企业账户");
        }
        EnterpriseReceive receive=enterpriseInfoDto.getEnterpriseReceive();
        if(receive!=null &&
                (StringUtils.isNotEmpty(receive.getReceiverAddr())
                        ||StringUtils.isNotEmpty(receive.getReceiverName())
                        ||StringUtils.isNotEmpty(receive.getReceiverPhone()))){
            receive.setEntNumber(enterpriseInfoDto.getEnterpriseInfo().getEntNumber());
            count=enterpriseReceiveMapper.insertSelective(receive);
            if(count<=0)
                resultList.add("收货人信息");
        }
        if(resultList.isEmpty())
            return "";
        else
            return StringUtils.join(resultList,',')+"插入失败";
    }

    @Override
    public List<String> queryEnterpriseName(String name) {
        return enterpriseInfoMapper.selectNameByFuzzyName(name);
    }

    @Override
    public QueryResult<EnterpriseInfoDto> queryEnterpriseInfo(int page, int rpp, EnterpriseInfoExample enterpriseInfoExample) {
        PageHelper.startPage(page,rpp);
        List<EnterpriseInfo> infos=enterpriseInfoMapper.selectByExample(enterpriseInfoExample);
        PageInfo<EnterpriseInfo> pageInfo=new PageInfo<>(infos);
        List<EnterpriseInfoDto> dataList=Lists.newArrayList();
        List<EnterpriseContact> contactList;
        List<EnterpriseAttachment> attachmentList;
        List<EnterpriseAccount> accountList;
        for(EnterpriseInfo info:pageInfo.getList()){
            EnterpriseInfoDto enterpriseInfoDto=new EnterpriseInfoDto();
            enterpriseInfoDto.setEnterpriseInfo(info);

            contactList=enterpriseContactMapper.selectByEpNumber(info.getEntNumber());
            attachmentList=enterpriseAttachmentMapper.selectByEpNumber(info.getEntNumber());
            accountList=enterpriseAccountMapper.selectByEpNumber(info.getEntNumber());

            if(!contactList.isEmpty()){
                for(EnterpriseContact contact:contactList){
                    if(contact.getContactType().equals(1)&&enterpriseInfoDto.getLegalPerson()==null){
                        enterpriseInfoDto.setLegalPerson(contact);
                    }
                    if(contact.getContactType().equals(2)&&enterpriseInfoDto.getEnterpriseContact()==null){
                        enterpriseInfoDto.setEnterpriseContact(contact);
                    }
                }
            }
            if(!attachmentList.isEmpty()){
                for(EnterpriseAttachment attachment:attachmentList){
                    if(attachment.getAttachmentType().equals(1)){
                        enterpriseInfoDto.setEnterpriseAttachment(attachment);
                        break;
                    }
                }
            }
            if(!accountList.isEmpty()){
                for(EnterpriseAccount account:accountList){
                    if(account.getAccountType().equals(1)){
                        enterpriseInfoDto.setEnterpriseAccount(account);
                        break;
                    }
                }
            }
            dataList.add(enterpriseInfoDto);
        }

        QueryResult<EnterpriseInfoDto> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(dataList);

        return result;
    }

    @Override
    public String updateEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        int count=enterpriseInfoMapper.updateByPrimaryKeySelective(enterpriseInfo);
        if(count>0)
            return "";
        else
            return "更新失败";
    }

    @Override
    public String updateEnterpriseDetail(EnterpriseInfoAccessDto enterpriseInfoAccessDto){
        String result=checkSupplyEmpty(enterpriseInfoAccessDto);
        if(StringUtils.isNotEmpty(result)){
            return result;
        }
        enterpriseInfoMapper.updateByPrimaryKeySelective(enterpriseInfoAccessDto.getEnterpriseInfo());
        if(enterpriseInfoAccessDto.getLegalPerson().getId()==null){
            enterpriseInfoAccessDto.getLegalPerson().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
            enterpriseInfoAccessDto.getLegalPerson().setContactType(1);
            enterpriseContactMapper.insertSelective(enterpriseInfoAccessDto.getLegalPerson());
        }else{
            enterpriseContactMapper.updateByPrimaryKeySelective(enterpriseInfoAccessDto.getLegalPerson());
        }

        if(enterpriseInfoAccessDto.getEnterpriseContact().getId()==null){
            enterpriseInfoAccessDto.getEnterpriseContact().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
            enterpriseInfoAccessDto.getEnterpriseContact().setContactType(2);
            enterpriseContactMapper.insertSelective(enterpriseInfoAccessDto.getEnterpriseContact());
        }else{
            enterpriseContactMapper.updateByPrimaryKeySelective(enterpriseInfoAccessDto.getEnterpriseContact());
        }

        if(enterpriseInfoAccessDto.getEnterpriseAccount().getId()==null){
            enterpriseInfoAccessDto.getEnterpriseAccount().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
            enterpriseInfoAccessDto.getEnterpriseAccount().setAccountType(1);
            enterpriseAccountMapper.insertSelective(enterpriseInfoAccessDto.getEnterpriseAccount());
        }else{
            enterpriseAccountMapper.updateByPrimaryKeySelective(enterpriseInfoAccessDto.getEnterpriseAccount());
        }

        if(enterpriseInfoAccessDto.getEnterpriseAttachment().getId()==null){
            enterpriseInfoAccessDto.getEnterpriseAttachment().setEntNumber(enterpriseInfoAccessDto.getEnterpriseInfo().getEntNumber());
            enterpriseInfoAccessDto.getEnterpriseAttachment().setAttachmentType(1);
            enterpriseAttachmentMapper.insertSelective(enterpriseInfoAccessDto.getEnterpriseAttachment());
        }else{
            enterpriseAttachmentMapper.updateByPrimaryKeySelective(enterpriseInfoAccessDto.getEnterpriseAttachment());
        }
        return "";
    }

    @Override
    public List<EnterpriseInfo> listSelective(Map<String, Object> map) {
        return enterpriseInfoMapper.listSelective(map);
    }

    @Override
    public EnterpriseInfoAccessDto queryEnterpriseInfoAccess(String entNumber) {
        EnterpriseInfo enterpriseInfo=enterpriseInfoMapper.selectByPrimaryKey(entNumber);
        if(enterpriseInfo==null)
            throw new BusinessException("找不到企业");
        EnterpriseInfoAccessDto result=new EnterpriseInfoAccessDto();
        result.setEnterpriseInfo(enterpriseInfo);

        List<EnterpriseContact> contactList=enterpriseContactMapper.selectByEpNumber(entNumber);
        List<EnterpriseAttachment> attachmentList=enterpriseAttachmentMapper.selectByEpNumber(entNumber);
        List<EnterpriseAccount> accountList=enterpriseAccountMapper.selectByEpNumber(entNumber);

        if(!contactList.isEmpty()){
            for(EnterpriseContact contact:contactList){
                if(contact.getContactType().equals(1)&&result.getLegalPerson()==null){
                    result.setLegalPerson(contact);
                }
                if(contact.getContactType().equals(2)&&result.getEnterpriseContact()==null){
                    result.setEnterpriseContact(contact);
                }
            }
        }
        if(!attachmentList.isEmpty()){
            for(EnterpriseAttachment attachment:attachmentList){
                if(attachment.getAttachmentType().equals(1)){
                    result.setEnterpriseAttachment(attachment);
                    break;
                }
            }
        }
        if(!accountList.isEmpty()){
            for(EnterpriseAccount account:accountList){
                if(account.getAccountType().equals(1)){
                    result.setEnterpriseAccount(account);
                    break;
                }
            }
        }
        Map<String,Object> params= Maps.newHashMap();
        params.put("entNumber",entNumber);
        List<EnterpriseProtocol> protocolList=enterpriseProtocolMapper.listSelective(params);
        params.put("isdelete",0);
        List<EnterpriseCategory> categoryList=enterpriseCategoryMapper.listSelective(params);
        if(categoryList!=null&&!categoryList.isEmpty()){
            result.setEnterpriseCategory(categoryList.get(0));
        }
        if(protocolList!=null&&!protocolList.isEmpty()){
            result.setEnterpriseProtocol(protocolList.get(0));
        }
        return result;
    }

    @Override
    public EnterpriseInfo selectByPrimaryKey(String entNumber) {
        return enterpriseInfoMapper.selectByPrimaryKey(entNumber);
    }

    @Override
    public QueryResult<EnterpriseInfo> selectEnterpriseInfoPage(int page, int rpp, Map<String, Object> map) {
        PageHelper.startPage(page,rpp);
        List<EnterpriseInfo> skuCoreList=enterpriseInfoMapper.listSelective(map);
        PageInfo<EnterpriseInfo> pageInfo=new PageInfo<>(skuCoreList);
        QueryResult<EnterpriseInfo> result=new QueryResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setItems(pageInfo.getList());
        return result;
    }

    /**
     * 校验供应商必填项是否为空
     *
     * @param enterpriseInfoDto
     * @return
     */
    private String checkSupplyEmpty(EnterpriseInfoDto enterpriseInfoDto){
        if(enterpriseInfoDto.getEnterpriseInfo()==null||StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseInfo().getEntNumber())){
            return "企业编码不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseInfo().getEntName())){
            return "企业名称不能为空";
        }
//        if(enterpriseInfoDto.getLegalPerson()==null||StringUtils.isEmpty(enterpriseInfoDto.getLegalPerson().getName())){
//            return "法人不能为空";
//        }
//        if(enterpriseInfoDto.getEnterpriseAttachment()==null||StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseAttachment().getAttachmentUrl())){
//            return "营业执照不能为空";
//        }
//
//        if(StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseInfo().getTaxpayerId())){
//            return "纳税人识别号不能为空";
//        }
//        if(enterpriseInfoDto.getEnterpriseAccount()==null||StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseAccount().getBankName())){
//            return "基本开户银行不能为空";
//        }
//        if(StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseAccount().getBankNo())){
//            return "基本开户账号不能为空";
//        }
        return "";
    }

    /**
     * 校验采购商必填项是否为空
     *
     * @param enterpriseInfoDto
     * @return
     */
    private String checkCustomerEmpty(EnterpriseInfoDto enterpriseInfoDto){
        if(enterpriseInfoDto.getEnterpriseInfo()==null||StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseInfo().getEntNumber())){
            return "企业编码不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseInfo().getEntName())){
            return "企业名称不能为空";
        }
        if(enterpriseInfoDto.getEnterpriseContact()==null||StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseContact().getName())){
            return "联系人不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseContact().getPhone())){
            return "联系人电话不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getEnterpriseInfo().getRegisterAddress())){
            return "注册地址不能为空";
        }
        return "";
    }

    /**
     * 根据企业名获取企业NO
     *
     * @param enterpriseName
     * @return
     */
    private String getEpNoByEpName(String enterpriseName){
        return enterpriseInfoMapper.selectEpNoByEpName(enterpriseName.trim());
    }

    @Override
    public EnterpriseInfo checkPartnerName(String name, Integer type) {
        String entTypeIn="("+ TradeTypeEnum.供应商采购商.getState()+","+type+")";
        Map<String,Object> map=new HashMap<>();
        map.put("entName",name);
        map.put("entTypeIn",entTypeIn);
        EnterpriseInfo selective = enterpriseInfoMapper.findSelective(map);
        return selective;
    }

    @Override
    @Transactional
    public String saveSPEnterpriseInfo(EnterpriseInfoSPModel enterpriseInfoSPModel) {
        EnterpriseInfo e = checkPartnerName(enterpriseInfoSPModel.getEntName(), enterpriseInfoSPModel.getEntType());
        if(e!=null){
            throw  new BussinessException("企业名称已存在");
        }
        String enterpriseNo = createEnterpriseNo();
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        BeanutilsCopy.copyProperties(enterpriseInfoSPModel,enterpriseInfo);
        enterpriseInfo.setEntNumber(enterpriseNo);
        enterpriseInfoMapper.insertSelective(enterpriseInfo);

        EnterpriseContact contactPerson=new EnterpriseContact();
        contactPerson.setEntNumber(enterpriseNo);
        contactPerson.setName(enterpriseInfoSPModel.getContactName());
        contactPerson.setPhone(enterpriseInfoSPModel.getContactPhone());
        contactPerson.setEmail(enterpriseInfoSPModel.getContactEmail());
        contactPerson.setContactType(ContactTypeEnum.联系人.getState());
        enterpriseContactMapper.insertSelective(contactPerson);
        if(StringUtils.isNotBlank(enterpriseInfoSPModel.getLegalPerson())){
            EnterpriseContact legalPerson=new EnterpriseContact();
            legalPerson.setEntNumber(enterpriseNo);
            legalPerson.setContactType(ContactTypeEnum.法人代表.getState());
            legalPerson.setName(enterpriseInfoSPModel.getLegalPerson());
            enterpriseContactMapper.insertSelective(legalPerson);
        }
        if(StringUtils.isNotBlank(enterpriseInfoSPModel.getBankName()) && StringUtils.isNotBlank(enterpriseInfoSPModel.getBankNo())){
            EnterpriseAccount account=new EnterpriseAccount();
            account.setEntNumber(enterpriseNo);
            account.setAccountType(AccountTypeEnum.基本户.getState());
            account.setBankNo(enterpriseInfoSPModel.getBankNo());
            account.setBankName(enterpriseInfoSPModel.getBankName());
            enterpriseAccountMapper.insertSelective(account);
        }
        if(StringUtils.isNotBlank(enterpriseInfoSPModel.getBusinessImg())){
            EnterpriseAttachment attachment=new EnterpriseAttachment();
            attachment.setEntNumber(enterpriseNo);
            attachment.setAttachmentName("营业执照");
            attachment.setAttachmentType(AttachmentTypeEnum.营业执照.getState());
            attachment.setAttachmentUrl(enterpriseInfoSPModel.getBusinessImg());
            enterpriseAttachmentMapper.insertSelective(attachment);
        }
        return enterpriseNo;
    }
}
