package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.SysDictDetailMapper;
import com.csjscm.core.framework.model.SysDictDetail;
import com.csjscm.core.framework.service.SysDictDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 字典表数据明细ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:33:38
 */
 
@Service
public class SysDictDetailServiceImpl implements SysDictDetailService {
	
    private static final Logger logger = LoggerFactory.getLogger(SysDictDetailServiceImpl.class);
   
    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;


    @Override
    public List<SysDictDetail> findListByMap(Map<String, Object> map) {
        return sysDictDetailMapper.listSelective(map);
    }
}