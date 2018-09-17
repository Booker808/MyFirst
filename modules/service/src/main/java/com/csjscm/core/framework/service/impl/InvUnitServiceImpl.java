package com.csjscm.core.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.InvUnitIsvalidEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.InvUnitMapper;
import com.csjscm.core.framework.model.InvUnit;
import com.csjscm.core.framework.service.InvUnitService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.cxf.Bus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    @Autowired
    private RedisServiceFacade redisServiceFacade;



    @Override
    public List<InvUnit> findListByMap(Map<String, Object> map) {
        return invUnitMapper.listSelective(map);
    }

    @Override
    public void save(InvUnit invUnit) {
        Map<String,Object> map=new HashMap<>();
        map.put("objName",invUnit.getObjName());
        int count = invUnitMapper.findCount(map);
        if(count>0){
            throw  new BussinessException("单位名称已存在");
        }
        invUnitMapper.insertSelective(invUnit);
        reloadRedisInvUnit();
    }

    @Override
    public void update(InvUnit invUnit) {
        if(invUnit.getId()==null){
            throw  new  BussinessException("id不能为空");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("objName",invUnit.getObjName());
        InvUnit selective = invUnitMapper.findSelective(map);
        if(selective!=null && selective.getId().intValue()!=invUnit.getId().intValue()){
            throw  new  BussinessException("单位名称不能重复");
        }
        invUnitMapper.updateSelective(invUnit);
        reloadRedisInvUnit();
    }

    @Override
    public void delete(Integer id) {
        invUnitMapper.deleteByPrimaryKey(id);
        reloadRedisInvUnit();
    }

    @Override
    public void reloadRedisInvUnit() {
        Map<String,Object> map=new HashMap<>();
        map.put("isvalid", InvUnitIsvalidEnum.有效.getState());
        List<InvUnit> invUnits = invUnitMapper.listSelective(map);
        redisServiceFacade.set(Constant.REDIS_KEY_UNIT,  JSONArray.parseArray(JSON.toJSONString(invUnits)));
    }

    @Override
    public void updateIsvalid(Integer id, Integer isvalid) {
        InvUnit byPrimary = invUnitMapper.findByPrimary(id);
        if(byPrimary==null){
            throw  new BussinessException("单位信息不存在,id有误");
        }
        byPrimary.setIsvalid(isvalid);
        invUnitMapper.updateSelective(byPrimary);
        reloadRedisInvUnit();
    }

    @Override
    public QueryResult<InvUnit> findPage(Map<String, Object> map, int current, int pageSize) {
        PageHelper.startPage(current,pageSize);
        Page<InvUnit> invUnits =(Page<InvUnit>) invUnitMapper.listSelective(map);
        return new QueryResult(invUnits);
    }

    @Override
    public InvUnit findByPrimary(Integer id) {
        return invUnitMapper.findByPrimary(id);
    }
}