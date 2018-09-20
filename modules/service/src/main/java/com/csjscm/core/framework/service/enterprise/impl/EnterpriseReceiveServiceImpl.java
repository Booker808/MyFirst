package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.common.enums.AttachmentTypeEnum;
import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.EnterpriseReceiveMapper;
import com.csjscm.core.framework.model.EnterpriseReceive;
import com.csjscm.core.framework.service.enterprise.EnterpriseReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 收发信息ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 10:29:29
 */
 
@Service
public class EnterpriseReceiveServiceImpl implements EnterpriseReceiveService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseReceiveServiceImpl.class);
   
    @Autowired
    private EnterpriseReceiveMapper enterpriseReceiveMapper;


    @Override
    public EnterpriseReceive findSelective(Map<String, Object> map) {
        return enterpriseReceiveMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseReceive> listSelective(Map<String, Object> map) {
        return enterpriseReceiveMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return enterpriseReceiveMapper.findCount(map);
    }

    @Override
    public int save(EnterpriseReceive enterpriseReceive) {
        Map<String, Object> map=new HashMap<>();
        map.put("receiverName",  enterpriseReceive.getReceiverName());
        map.put("receiverPhone", enterpriseReceive.getReceiverPhone());
        map.put("receiverAddr", enterpriseReceive.getReceiverAddr());
        map.put("entNumber",enterpriseReceive.getEntNumber());
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        int count = findCount(map);
        if(count>0){
            throw  new BussinessException("已存在这条收货信息");
        }
        return enterpriseReceiveMapper.insertSelective(enterpriseReceive);
    }

    @Override
    public int update(EnterpriseReceive enterpriseReceive) {
        Map<String, Object> map=new HashMap<>();
        map.put("receiverName",  enterpriseReceive.getReceiverName());
        map.put("receiverPhone", enterpriseReceive.getReceiverPhone());
        map.put("receiverAddr", enterpriseReceive.getReceiverAddr());
        map.put("entNumber",enterpriseReceive.getEntNumber());
        map.put("isdelete", DeleteStateEnum.未删除.getState());
        EnterpriseReceive selective = findSelective(map);
        if(selective!=null && selective.getId().intValue()!=enterpriseReceive.getId().intValue()){
            throw  new BussinessException("已存在这条收货信息,无法修改");
        }
        return enterpriseReceiveMapper.updateByPrimaryKeySelective(enterpriseReceive);
    }

    @Override
    public int updateIsdelete(Integer id) {
        EnterpriseReceive enterpriseReceive=new EnterpriseReceive();
        enterpriseReceive.setId(id);
        enterpriseReceive.setIsdelete(DeleteStateEnum.已删除.getState());
        return enterpriseReceiveMapper.updateByPrimaryKeySelective(enterpriseReceive);
    }
}