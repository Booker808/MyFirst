package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.SysDictDetailMapper;
import com.csjscm.core.framework.dao.SysDictMapper;
import com.csjscm.core.framework.model.SysDict;
import com.csjscm.core.framework.model.SysDictDetail;
import com.csjscm.core.framework.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据字典表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-11 14:33:08
 */
 
@Service
public class SysDictServiceImpl implements SysDictService {
	
    private static final Logger logger = LoggerFactory.getLogger(SysDictServiceImpl.class);
   
    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;


    @Override
    public SysDict findByCode(String code) {
        Map<String,Object> map=new HashMap<>();
        map.put("typeCode",code);
        return sysDictMapper.findSelective(map);
    }

    @Override
    public List<SysDictDetail> findDetailListByCode(String code) {
        List<SysDictDetail> list=new ArrayList<>();
        SysDict byCode = findByCode(code);
        if(byCode!=null){
            Map<String,Object> map=new HashMap<>();
            map.put("parentId",byCode.getId());
            list= sysDictDetailMapper.listSelective(map);
        }
        return list;
    }
}