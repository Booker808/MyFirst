package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.TradeTypeEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.EnterpriseMemberMapper;
import com.csjscm.core.framework.dao.EnterpriseSettlementInfoMapper;
import com.csjscm.core.framework.dao.EnterpriseTicketInfoMapper;
import com.csjscm.core.framework.model.EnterpriseMember;
import com.csjscm.core.framework.model.EnterpriseSettlementInfo;
import com.csjscm.core.framework.model.EnterpriseTicketInfo;
import com.csjscm.core.framework.service.EnterpriseMemberService;
import com.csjscm.core.framework.vo.EnterpriseMemberModel;
import com.csjscm.core.framework.vo.EnterpriseUpdateModel;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 企业-会员表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:28:14
 */
 
@Service
public class EnterpriseMemberServiceImpl implements EnterpriseMemberService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseMemberServiceImpl.class);
   
    @Autowired
    private EnterpriseMemberMapper enterpriseMemberMapper;
    @Autowired
    private EnterpriseSettlementInfoMapper enterpriseSettlementInfoMapper;
    @Autowired
    private EnterpriseTicketInfoMapper enterpriseTicketInfoMapper;


    @Override
    @Transactional
    public int saveEnterpriseMember(EnterpriseMemberModel enterpriseMemberModel) {
         //暂定 企业编码生成规则
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date())+"0001";
        Long newno=Long.parseLong(format);
        String maxEntNumber = enterpriseMemberMapper.findMaxEntNumber().replace(Constant.ENTNUMBER_INDEX,"").trim();
        Long oldno=Long.parseLong(maxEntNumber);
        String  entNumber=Constant.ENTNUMBER_INDEX+format;
        if(oldno>=newno){
            Long i = oldno + 1;
            entNumber=Constant.ENTNUMBER_INDEX+i;
        }
        if(TradeTypeEnum.供应商采购商.getState().intValue()==enterpriseMemberModel.getTradeType().intValue() ||enterpriseMemberModel.getTradeType().intValue()==TradeTypeEnum.供应商.getState().intValue()){
            if(enterpriseMemberModel.getPartnerType()==null){
                throw  new BussinessException("供应商类型不能为空");
            }
        }
       //校验企业名称是否存在
       Map<String,Object> map=new HashMap<>();
        String tradeTypeIn="("+enterpriseMemberModel.getTradeType()+","+TradeTypeEnum.供应商采购商.getState()+")";
        map.put("entName",enterpriseMemberModel.getEntName());
        map.put("tradeTypeIn",tradeTypeIn);
        int count = enterpriseMemberMapper.findCount(map);
        if(count>0){
            throw  new BussinessException("企业名称重复");
        }
        Date date=new Date();
        // 企业-会员表实体
        EnterpriseMember enterpriseMember=new EnterpriseMember();
        enterpriseMember.setCreateTime(date);
        enterpriseMember.setBid(enterpriseMemberModel.getBid());
        enterpriseMember.setBusinessAddress(enterpriseMemberModel.getBusinessAddress());
        enterpriseMember.setBusinessImg(enterpriseMemberModel.getBusinessImg());
        enterpriseMember.setCheckState(enterpriseMemberModel.getCheckState());
        enterpriseMember.setDefaultEntrepot(enterpriseMemberModel.getDefaultEntrepot());
        enterpriseMember.setEntName(enterpriseMemberModel.getEntName());
        enterpriseMember.setEntNumber(entNumber);
        enterpriseMember.setLegalPerson(enterpriseMemberModel.getLegalPerson());
        enterpriseMember.setLinkman(enterpriseMemberModel.getLinkman());
        enterpriseMember.setLinkmanPhone(enterpriseMemberModel.getLinkmanPhone());
        enterpriseMember.setPartnerType(enterpriseMemberModel.getPartnerType());
        enterpriseMember.setPurchase(enterpriseMemberModel.getPurchase());
        enterpriseMember.setRegisterAddress(enterpriseMemberModel.getRegisterAddress());
        enterpriseMember.setRegisterMoney(enterpriseMemberModel.getRegisterMoney());
        enterpriseMember.setSell(enterpriseMemberModel.getSell());
        enterpriseMember.setRegisterMoney(enterpriseMemberModel.getRegisterMoney());
        enterpriseMember.setTender(enterpriseMemberModel.getTender());
        enterpriseMember.setTradeType(enterpriseMemberModel.getTradeType());
        enterpriseMember.setWebAddress(enterpriseMemberModel.getWebAddress());
        //结算信息表实体
        EnterpriseSettlementInfo enterpriseSettlementInfo=new EnterpriseSettlementInfo();
        enterpriseSettlementInfo.setAdvanceRatio(enterpriseMemberModel.getAdvanceRatio());
        enterpriseSettlementInfo.setClearingType(enterpriseMemberModel.getClearingType());
        enterpriseSettlementInfo.setCreateTime(date);
        enterpriseSettlementInfo.setDays(enterpriseMemberModel.getDays());
        enterpriseSettlementInfo.setEntNumber(entNumber);
        enterpriseSettlementInfo.setPaymentClause(enterpriseMemberModel.getPaymentClause());
        //开票信息表实体
        EnterpriseTicketInfo enterpriseTicketInfo=new EnterpriseTicketInfo();
        enterpriseTicketInfo.setBankCardNo(enterpriseMemberModel.getBankCardNo());
        enterpriseTicketInfo.setBankName(enterpriseMemberModel.getBankName());
        enterpriseTicketInfo.setCreateTime(date);
        enterpriseTicketInfo.setEntNumber(entNumber);
        enterpriseTicketInfo.setTaxpayerId(enterpriseMemberModel.getTaxpayerId());
        int i = enterpriseMemberMapper.insertSelective(enterpriseMember);
        enterpriseSettlementInfoMapper.insertSelective(enterpriseSettlementInfo);
        enterpriseTicketInfoMapper.insertSelective(enterpriseTicketInfo);
        return i;
    }

    @Override
    public int deleteEnterpriseMember(String ids) {
        return 0;
    }

    @Override
    public QueryResult<EnterpriseMember> findPage(Map<String, Object> map, int current, int pageSize) {
        PageHelper.startPage(current,pageSize);
        Page<EnterpriseMember> enterpriseMembers =(Page<EnterpriseMember>)enterpriseMemberMapper.listSelective(map);
        return new QueryResult(enterpriseMembers);
    }

    @Override
    public EnterpriseMember findByPrimary(Integer id) {
        return enterpriseMemberMapper.findByPrimary(id);
    }

    @Override
    @Transactional
    public int updateEnterpriseMember(EnterpriseMemberModel enterpriseMemberModel) {


        if(enterpriseMemberModel.getId()==null){
            throw  new BussinessException("id不能为空");
        }
        EnterpriseMember byPrimary = enterpriseMemberMapper.findByPrimary(enterpriseMemberModel.getId());
        if(byPrimary==null){
            throw  new BussinessException("id有误");
        }
        Date date=new Date();
        // 企业-会员表实体
        EnterpriseMember enterpriseMember=new EnterpriseMember();
        enterpriseMember.setId(enterpriseMemberModel.getId());
        enterpriseMember.setEditTime(date);
        enterpriseMember.setBid(enterpriseMemberModel.getBid());
        enterpriseMember.setBusinessAddress(enterpriseMemberModel.getBusinessAddress());
        enterpriseMember.setBusinessImg(enterpriseMemberModel.getBusinessImg());
        enterpriseMember.setCheckState(enterpriseMemberModel.getCheckState());
        enterpriseMember.setDefaultEntrepot(enterpriseMemberModel.getDefaultEntrepot());
        enterpriseMember.setEntName(enterpriseMemberModel.getEntName());
        enterpriseMember.setLegalPerson(enterpriseMemberModel.getLegalPerson());
        enterpriseMember.setLinkman(enterpriseMemberModel.getLinkman());
        enterpriseMember.setLinkmanPhone(enterpriseMemberModel.getLinkmanPhone());
        enterpriseMember.setPartnerType(enterpriseMemberModel.getPartnerType());
        enterpriseMember.setPurchase(enterpriseMemberModel.getPurchase());
        enterpriseMember.setRegisterAddress(enterpriseMemberModel.getRegisterAddress());
        enterpriseMember.setRegisterMoney(enterpriseMemberModel.getRegisterMoney());
        enterpriseMember.setSell(enterpriseMemberModel.getSell());
        enterpriseMember.setRegisterMoney(enterpriseMemberModel.getRegisterMoney());
        enterpriseMember.setTender(enterpriseMemberModel.getTender());
        enterpriseMember.setTradeType(enterpriseMemberModel.getTradeType());
        enterpriseMember.setWebAddress(enterpriseMemberModel.getWebAddress());
        //结算信息表实体
        Map<String, Object> map=new HashMap<>();
        map.put("entNumber",byPrimary.getEntNumber());
        EnterpriseSettlementInfo infoMapperSelective = enterpriseSettlementInfoMapper.findSelective(map);

        EnterpriseSettlementInfo enterpriseSettlementInfo=new EnterpriseSettlementInfo();
        enterpriseSettlementInfo.setId(infoMapperSelective.getId());
        enterpriseSettlementInfo.setAdvanceRatio(enterpriseMemberModel.getAdvanceRatio());
        enterpriseSettlementInfo.setClearingType(enterpriseMemberModel.getClearingType());
        enterpriseSettlementInfo.setEditTime(date);
        enterpriseSettlementInfo.setDays(enterpriseMemberModel.getDays());
        enterpriseSettlementInfo.setPaymentClause(enterpriseMemberModel.getPaymentClause());
        //开票信息表实体
        EnterpriseTicketInfo ticketInfoMapperSelective = enterpriseTicketInfoMapper.findSelective(map);

        EnterpriseTicketInfo enterpriseTicketInfo=new EnterpriseTicketInfo();
        enterpriseTicketInfo.setId(ticketInfoMapperSelective.getId());
        enterpriseTicketInfo.setBankCardNo(enterpriseMemberModel.getBankCardNo());
        enterpriseTicketInfo.setBankName(enterpriseMemberModel.getBankName());
        enterpriseTicketInfo.setEditTime(date);
        enterpriseTicketInfo.setTaxpayerId(enterpriseMemberModel.getTaxpayerId());
        int i = enterpriseMemberMapper.updateSelective(enterpriseMember);
        enterpriseSettlementInfoMapper.updateSelective(enterpriseSettlementInfo);
        enterpriseTicketInfoMapper.updateSelective(enterpriseTicketInfo);
        return i;
    }

    @Override
    public int updateEnterpriseModel(EnterpriseUpdateModel enterpriseUpdateModel) {
        Date date=new Date();
        // 企业-会员表实体
        EnterpriseMember enterpriseMember=new EnterpriseMember();
        enterpriseMember.setId(enterpriseUpdateModel.getId());
        enterpriseMember.setEditTime(date);
        enterpriseMember.setBusinessAddress(enterpriseUpdateModel.getBusinessAddress());
        enterpriseMember.setDefaultEntrepot(enterpriseUpdateModel.getDefaultEntrepot());
        enterpriseMember.setLegalPerson(enterpriseUpdateModel.getLegalPerson());
        enterpriseMember.setLinkman(enterpriseUpdateModel.getLinkman());
        enterpriseMember.setLinkmanPhone(enterpriseUpdateModel.getLinkmanPhone());
        enterpriseMember.setRegisterAddress(enterpriseUpdateModel.getRegisterAddress());
        enterpriseMember.setRegisterMoney(enterpriseUpdateModel.getRegisterMoney());
        enterpriseMember.setRegisterMoney(enterpriseUpdateModel.getRegisterMoney());
        enterpriseMember.setWebAddress(enterpriseUpdateModel.getWebAddress());
        return enterpriseMemberMapper.updateSelective(enterpriseMember);
    }
    @Override
    public boolean checkPartnerName(String name, Integer type) {
        //校验企业名称是否存在
        Map<String,Object> map=new HashMap<>();
        String tradeTypeIn="("+type+","+TradeTypeEnum.供应商采购商.getState()+")";
        map.put("entName",name);
        map.put("tradeTypeIn",tradeTypeIn);
        int count = enterpriseMemberMapper.findCount(map);
        if(count>0){
            return true;
        }
        return false;
    }
}