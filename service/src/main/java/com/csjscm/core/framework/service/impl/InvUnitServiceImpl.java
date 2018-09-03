package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.InvUnitMapper;
import com.csjscm.core.framework.model.InvUnit;
import com.csjscm.core.framework.service.InvUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 计量单位定义表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-03 10:01:53
 */
 
@Service
public class InvUnitServiceImpl implements InvUnitService {
	
    private static final Logger logger = LoggerFactory.getLogger(InvUnitServiceImpl.class);
   
    @Autowired
    private InvUnitMapper invUnitMapper;


    @Override
    public List<InvUnit> findListByMap(Map<String, Object> map) {
        return invUnitMapper.listSelective(map);
    }
}