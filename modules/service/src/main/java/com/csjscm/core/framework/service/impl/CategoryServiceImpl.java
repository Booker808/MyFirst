package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.CategoryLevelEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.CategoryMapper;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisDistributedCounterObject;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品分类表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */
 
@Service
public class CategoryServiceImpl implements CategoryService {
	
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
   
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisServiceFacade redisServiceFacade;


    @Override
    public int save(Category t) {
        Map<String,Object> map=new HashMap<>();
        map.put("classCode",t.getClassCode());
        int count = categoryMapper.findCount(map);
        if(count>0){
            throw  new  BussinessException("分类编码不能重复");
        }
        t.setCreateTime(new Date());
        if(t.getLevelNum().intValue()== CategoryLevelEnum.三级.getState().intValue()){
            Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_PRODUCT_NO+t.getClassCode()), 1);
        }
       return categoryMapper.insertSelective(t);
    }

    @Override
    public int update(Category t) {
        t.setEditTime(new Date());
        return categoryMapper.updateSelective(t);
    }

    @Override
    public Category findByPrimary(Integer id) {
        return categoryMapper.findByPrimary(id);
    }

    @Override
    public Category findSelective(Map<String, Object> map) {
        return categoryMapper.findSelective(map);
    }

    @Override
    public List<Category> listSelective(Map<String, Object> map) {
        return categoryMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return categoryMapper.findCount(map);
    }

    @Override
    public QueryResult<Category> findPage(Map<String, Object> map, int current, int pageSize) {
        PageHelper.startPage(current,pageSize);
        Page<Category> list = (Page<Category>)categoryMapper.listSelective(map);
        return new QueryResult(list);
    }

    @Override
    @Transactional
    public void deleteByIds(String ids) {
        String[] strings=ids.split(",");
        for(String strId:strings){
            Map<String,Object> map= new HashMap<>();
            map.put("parentClass",strId);
            int count = categoryMapper.findCount(map);
            if(count>0){
                throw  new  BussinessException("存在下级分类无法，请先删除下级分类");
            }else {
                categoryMapper.deleteByPrimaryKey(Integer.parseInt(strId));
            }
        }
    }

}