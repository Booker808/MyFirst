package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.common.enums.AccountTypeEnum;
import com.csjscm.core.framework.common.enums.AttachmentTypeEnum;
import com.csjscm.core.framework.common.enums.DeleteStateEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.EnterpriseAttachmentMapper;
import com.csjscm.core.framework.model.EnterpriseAttachment;
import com.csjscm.core.framework.service.enterprise.EnterpriseAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 企业附件ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-19 09:58:12
 */
 
@Service
public class EnterpriseAttachmentServiceImpl implements EnterpriseAttachmentService {
	
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseAttachmentServiceImpl.class);
   
    @Autowired
    private EnterpriseAttachmentMapper enterpriseAttachmentMapper;


    @Override
    public EnterpriseAttachment findSelective(Map<String, Object> map) {
        return enterpriseAttachmentMapper.findSelective(map);
    }

    @Override
    public List<EnterpriseAttachment> listSelective(Map<String, Object> map) {
        return enterpriseAttachmentMapper.listSelective(map);
    }

    @Override
    public int save(EnterpriseAttachment enterpriseAttachment) {
        if(enterpriseAttachment.getAttachmentType().intValue()== AttachmentTypeEnum.营业执照.getState().intValue()){
            Map<String,Object> map=new HashMap<>();
            map.put("attachmentType",  AttachmentTypeEnum.营业执照.getState());
            map.put("entNumber",enterpriseAttachment.getEntNumber());
            map.put("isdelete", DeleteStateEnum.未删除.getState());
            int count = enterpriseAttachmentMapper.findCount(map);
            if(count>0){
                throw  new BussinessException("只能有一个营业执照");
            }
        }

        return enterpriseAttachmentMapper.insertSelective(enterpriseAttachment);
    }

    @Override
    public int update(EnterpriseAttachment enterpriseAttachment) {
        return enterpriseAttachmentMapper.updateByPrimaryKeySelective(enterpriseAttachment);
    }

    @Override
    public int updateIsdelete(Integer id) {
        EnterpriseAttachment enterpriseAttachment = enterpriseAttachmentMapper.selectByPrimaryKey(id);
        if(enterpriseAttachment.getAttachmentType().intValue()==AttachmentTypeEnum.营业执照.getState().intValue()){
            throw  new  BussinessException("无法删除营业执照");
        }
        enterpriseAttachment.setIsdelete(DeleteStateEnum.已删除.getState());
        return enterpriseAttachmentMapper.updateByPrimaryKeySelective(enterpriseAttachment);
    }
}