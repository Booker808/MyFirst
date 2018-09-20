package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.common.enums.AccountTypeEnum;
import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.EnterpriseAccountMapper;
import com.csjscm.core.framework.model.EnterpriseAccount;
import com.csjscm.core.framework.service.enterprise.EnterpriseAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 企业账户信息ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 10:14:35
 */
 
@Service
public class EnterpriseAccountServiceImpl implements EnterpriseAccountService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseAccountServiceImpl.class);
   
    @Autowired
    private EnterpriseAccountMapper enterpriseAccountMapper;


    @Override
    public EnterpriseAccount findSelective(Map<String, Object> map) {
        return enterpriseAccountMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseAccount> listSelective(Map<String, Object> map) {
        return enterpriseAccountMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return enterpriseAccountMapper.findCount(map);
    }

    @Override
    public int save(EnterpriseAccount enterpriseAccount) {
        if(enterpriseAccount.getAccountType().intValue()== AccountTypeEnum.基本户.getState().intValue()){
            Map<String,Object> map=new HashMap<>();
            map.put("accountType",AccountTypeEnum.基本户.getState());
            map.put("entNumber",enterpriseAccount.getEntNumber());
            map.put("isdelete", DeleteStateEnum.未删除.getState());
            int count = enterpriseAccountMapper.findCount(map);
            if(count>0){
                throw  new BussinessException("只能有一个基本基本户");
            }
        }
        return enterpriseAccountMapper.insertSelective(enterpriseAccount);
    }

    @Override
    public int update(EnterpriseAccount enterpriseAccount) {
        return enterpriseAccountMapper.updateByPrimaryKeySelective(enterpriseAccount);
    }

    @Override
    public EnterpriseAccount selectByPrimaryKey(Integer id) {
        return enterpriseAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateIsdelete(Integer id) {
        EnterpriseAccount enterpriseAccount = enterpriseAccountMapper.selectByPrimaryKey(id);
        if(enterpriseAccount.getAccountType().intValue()==AccountTypeEnum.基本户.getState().intValue()){
            throw  new  BussinessException("无法删除基本账户");
        }
        enterpriseAccount.setIsdelete(DeleteStateEnum.已删除.getState());
        return enterpriseAccountMapper.updateByPrimaryKeySelective(enterpriseAccount);
    }
}