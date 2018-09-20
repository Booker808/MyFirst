package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.common.enums.ContactTypeEnum;
import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.EnterpriseContactMapper;
import com.csjscm.core.framework.model.EnterpriseContact;
import com.csjscm.core.framework.service.enterprise.EnterpriseContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 联系人信息ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 09:24:25
 */
 
@Service
public class EnterpriseContactServiceImpl implements EnterpriseContactService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseContactServiceImpl.class);
   
    @Autowired
    private EnterpriseContactMapper enterpriseContactMapper;


    @Override
    public EnterpriseContact findSelective(Map<String, Object> map) {
        return enterpriseContactMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseContact> listSelective(Map<String, Object> map) {
        return enterpriseContactMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return enterpriseContactMapper.findCount(map);
    }

    @Override
    public int save(EnterpriseContact enterpriseContact) {
        if(enterpriseContact.getContactType().intValue()== ContactTypeEnum.法人代表.getState().intValue()){
            Map<String, Object> map=new HashMap<>();
            map.put("contactType",ContactTypeEnum.法人代表.getState());
            map.put("entNumber",enterpriseContact.getEntNumber());
            map.put("isdelete", DeleteStateEnum.未删除.getState());
            int count = enterpriseContactMapper.findCount(map);
            if(count>0){
                throw  new BussinessException("只能有一个法人代表");
            }
        }
        return enterpriseContactMapper.insertSelective(enterpriseContact);
    }

    @Override
    public int update(EnterpriseContact enterpriseContact) {
        return enterpriseContactMapper.updateByPrimaryKeySelective(enterpriseContact);
    }

    @Override
    public int updateIsdelete(Integer id) {
        EnterpriseContact enterpriseContact = enterpriseContactMapper.selectByPrimaryKey(id);
        if(enterpriseContact.getContactType().intValue()==ContactTypeEnum.法人代表.getState().intValue()){
            throw  new  BussinessException("无法删除法人代表");
        }
        if(enterpriseContact.getContactType().intValue()==ContactTypeEnum.联系人.getState().intValue()){
            Map<String, Object> map=new HashMap<>();
            map.put("entNumber",enterpriseContact.getEntNumber());
            map.put("isdelete", DeleteStateEnum.未删除.getState());
            map.put("contactType",ContactTypeEnum.联系人.getState());
            int count = findCount(map);
            if(count<2){
                throw  new  BussinessException("必须保留一个联系人");
            }
        }
        enterpriseContact.setIsdelete(DeleteStateEnum.已删除.getState());
        return enterpriseContactMapper.updateByPrimaryKeySelective(enterpriseContact);
    }
}