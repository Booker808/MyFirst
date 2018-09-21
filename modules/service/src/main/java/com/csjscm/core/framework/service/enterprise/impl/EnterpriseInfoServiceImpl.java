package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.AccountTypeEnum;
import com.csjscm.core.framework.common.enums.AttachmentTypeEnum;
import com.csjscm.core.framework.common.enums.ContactTypeEnum;
import com.csjscm.core.framework.common.enums.TradeTypeEnum;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.EnterpriseAccountMapper;
import com.csjscm.core.framework.dao.EnterpriseAttachmentMapper;
import com.csjscm.core.framework.dao.EnterpriseContactMapper;
import com.csjscm.core.framework.dao.EnterpriseInfoMapper;
import com.csjscm.core.framework.example.EnterpriseInfoExample;
import com.csjscm.core.framework.model.EnterpriseAccount;
import com.csjscm.core.framework.model.EnterpriseAttachment;
import com.csjscm.core.framework.model.EnterpriseContact;
import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoDto;
import com.csjscm.core.framework.vo.EnterpriseInfoSPModel;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
        switch (enterpriseInfoDto.getEntType()){
            //采购商
            case 2:
                result=checkCustomerEmpty(enterpriseInfoDto);
                break;
            //供应商
            case 1:
            //供应商&采购商
            case 3:
                result=checkSupplyEmpty(enterpriseInfoDto);
                break;
            default:
                return "企业类型错误";
        }
        if(StringUtils.isNotEmpty(result)){
            return result;
        }
        if(StringUtils.isNotEmpty(getEpNoByEpName(enterpriseInfoDto.getEntName()))){
            return "该企业已存在";
        }

        EnterpriseInfo enterpriseInfo=new EnterpriseInfo();
        enterpriseInfo.setBid(enterpriseInfoDto.getBid());
        enterpriseInfo.setBussinessAddress(enterpriseInfoDto.getBussinessAddress());
        enterpriseInfo.setCheckState(enterpriseInfoDto.getCheckState());
        enterpriseInfo.setEntName(enterpriseInfoDto.getEntName());
        enterpriseInfo.setEntNumber(enterpriseInfoDto.getEntNumber());
        enterpriseInfo.setEntType(enterpriseInfoDto.getEntType());
        enterpriseInfo.setIsvalid(enterpriseInfoDto.getIsvalid());
        enterpriseInfo.setPurchase(enterpriseInfoDto.getPurchase());
        enterpriseInfo.setRegisterAddress(enterpriseInfoDto.getRegisterAddress());
        enterpriseInfo.setRegisterMoney(enterpriseInfoDto.getRegisterMoney());
        enterpriseInfo.setSell(enterpriseInfoDto.getSell());
        enterpriseInfo.setTaxpayerId(enterpriseInfoDto.getTaxpayerId());
        enterpriseInfo.setTender(enterpriseInfoDto.getTender());
        enterpriseInfo.setWebAddress(enterpriseInfoDto.getWebAddress());

        EnterpriseContact legalPerson=new EnterpriseContact(),contactPerson=new EnterpriseContact();
        legalPerson.setEntNumber(enterpriseInfoDto.getEntNumber());
        legalPerson.setContactType(1);
        legalPerson.setName(enterpriseInfoDto.getLegalPerson());

        contactPerson.setEntNumber(enterpriseInfoDto.getEntNumber());
        contactPerson.setContactType(2);
        contactPerson.setName(enterpriseInfoDto.getContactName());
        contactPerson.setPhone(enterpriseInfoDto.getContactPhone());
        contactPerson.setEmail(enterpriseInfoDto.getContactEmail());

        EnterpriseAttachment attachment=new EnterpriseAttachment();
        attachment.setEntNumber(enterpriseInfoDto.getEntNumber());
        attachment.setAttachmentName("营业执照");
        attachment.setAttachmentType(1);
        attachment.setAttachmentUrl(enterpriseInfoDto.getBusinessImg());

        EnterpriseAccount account=new EnterpriseAccount();
        account.setEntNumber(enterpriseInfoDto.getEntNumber());
        account.setAccountType(1);
        account.setBankNo(enterpriseInfoDto.getBankNo());
        account.setBankName(enterpriseInfoDto.getBankName());

        int count=enterpriseInfoMapper.insertSelective(enterpriseInfo);
        if(count<=0)
            return "Info表插入失败";
        List<String> resultList= Lists.newLinkedList();
        if(StringUtils.isNotEmpty(legalPerson.getName())){
            count=enterpriseContactMapper.insertSelective(legalPerson);
            if(count<=0)
                resultList.add("法人");
        }
        count=enterpriseContactMapper.insertSelective(contactPerson);
        if(count<=0)
            resultList.add("联系人");

        if(StringUtils.isNotEmpty(attachment.getAttachmentUrl())){
            count=enterpriseAttachmentMapper.insertSelective(attachment);
            if(count<=0)
                resultList.add("附件");
        }

        if(StringUtils.isNotEmpty(account.getBankName())||StringUtils.isNotEmpty(account.getBankNo())){
            count=enterpriseAccountMapper.insertSelective(account);
            if(count<=0)
                resultList.add("企业账户");
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
            enterpriseInfoDto.setEntNumber(info.getEntNumber());
            enterpriseInfoDto.setEntType(info.getEntType());
            enterpriseInfoDto.setEntName(info.getEntName());
            enterpriseInfoDto.setRegisterAddress(info.getRegisterAddress());
            enterpriseInfoDto.setBussinessAddress(info.getBussinessAddress());
            enterpriseInfoDto.setWebAddress(info.getWebAddress());
            enterpriseInfoDto.setRegisterMoney(info.getRegisterMoney());
            enterpriseInfoDto.setTaxpayerId(info.getTaxpayerId());
            enterpriseInfoDto.setPurchase(info.getPurchase());
            enterpriseInfoDto.setSell(info.getSell());
            enterpriseInfoDto.setTender(info.getTender());
            enterpriseInfoDto.setBid(info.getBid());
            enterpriseInfoDto.setCheckState(info.getCheckState());
            enterpriseInfoDto.setIsvalid(info.getIsvalid());

            contactList=enterpriseContactMapper.selectByEpNumber(info.getEntNumber());
            attachmentList=enterpriseAttachmentMapper.selectByEpNumber(info.getEntNumber());
            accountList=enterpriseAccountMapper.selectByEpNumber(info.getEntNumber());

            if(!contactList.isEmpty()){
                for(EnterpriseContact contact:contactList){
                    if(contact.getContactType().equals(1)&&StringUtils.isEmpty(enterpriseInfoDto.getLegalPerson())){
                        enterpriseInfoDto.setLegalPerson(contact.getName());
                    }
                    if(contact.getContactType().equals(2)&&StringUtils.isEmpty(enterpriseInfoDto.getContactName())){
                        enterpriseInfoDto.setContactName(contact.getName());
                        enterpriseInfoDto.setContactPhone(contact.getPhone());
                        enterpriseInfoDto.setContactEmail(contact.getEmail());
                    }
                }
            }
            if(!attachmentList.isEmpty()){
                for(EnterpriseAttachment attachment:attachmentList){
                    if(attachment.getAttachmentType().equals(1)){
                        enterpriseInfoDto.setBusinessImg(attachment.getAttachmentUrl());
                        break;
                    }
                }
            }
            if(!accountList.isEmpty()){
                for(EnterpriseAccount account:accountList){
                    if(account.getAccountType().equals(1)){
                        enterpriseInfoDto.setBankName(account.getBankName());
                        enterpriseInfoDto.setBankNo(account.getBankNo());
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
    public List<EnterpriseInfo> listSelective(Map<String, Object> map) {
        return enterpriseInfoMapper.listSelective(map);
    }

    /**
     * 校验供应商必填项是否为空
     *
     * @param enterpriseInfoDto
     * @return
     */
    private String checkSupplyEmpty(EnterpriseInfoDto enterpriseInfoDto){
        String result=checkCustomerEmpty(enterpriseInfoDto);
        if(StringUtils.isNotEmpty(result)){
            return result;
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getLegalPerson())){
            return "法人不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getBusinessImg())){
            return "营业执照不能为空";
        }

        if(StringUtils.isEmpty(enterpriseInfoDto.getTaxpayerId())){
            return "纳税人识别号不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getBankName())){
            return "基本开户银行不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getBankNo())){
            return "基本开户账号不能为空";
        }
        return "";
    }

    /**
     * 校验采购商必填项是否为空
     *
     * @param enterpriseInfoDto
     * @return
     */
    private String checkCustomerEmpty(EnterpriseInfoDto enterpriseInfoDto){
        if(StringUtils.isEmpty(enterpriseInfoDto.getEntNumber())){
            return "企业名称不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getEntName())){
            return "企业名称不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getContactName())){
            return "联系人不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getContactPhone())){
            return "联系人电话不能为空";
        }
        if(StringUtils.isEmpty(enterpriseInfoDto.getRegisterAddress())){
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
