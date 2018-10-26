package com.csjscm.core.framework.service.spu.impl;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.dao.SpSkuCoreMapper;
import com.csjscm.core.framework.dao.SpuMapper;
import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.model.SpSkuCore;
import com.csjscm.core.framework.model.SpuEx;
import com.csjscm.core.framework.service.spu.SpuService;
import com.csjscm.core.framework.vo.SpuVo;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisDistributedCounterObject;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpSkuCoreMapper spSkuCoreMapper;
    @Autowired
    private RedisServiceFacade redisServiceFacade;

    @Override
    public QueryResult<SpuVo> querySpuList(int page, int rpp, SpuExample example) {
        PageHelper.startPage(page,rpp);
        List<SpuEx> spuExList=spuMapper.selectByExample(example);
        PageInfo<SpuEx> pageInfo=new PageInfo<>(spuExList);
        QueryResult<SpuVo> result=new QueryResult<>();
        List<SpuVo> list= Lists.newLinkedList();
        for(SpuEx spuEx:pageInfo.getList()){
            SpuVo vo=new SpuVo();
            BeanutilsCopy.copyProperties(spuEx,vo);
            vo.setStock(spuMapper.selectStockBySpu(vo.getStdProductNo()));
            list.add(vo);
        }
        result.setTotal(pageInfo.getTotal());
        result.setItems(list);
        return result;
    }

    @Override
    @Transactional
    public void updateShelfState(Integer shelf, List<String> spuList) {
        Map<String,Object> map= Maps.newHashMap();
        map.put("shelf",shelf);
        map.put("spuList",spuList);
        //如果是上架，则需要更新上架时间
        if(0==shelf){
            map.put("shelfTime",new Date());
        }
        int count=spuMapper.updateShelfState(map);
        if(count!=spuList.size()){
            throw new BusinessException("上下架状态变更异常");
        }
        //如果下架，需要重新创建对应的sku
        if(1==shelf){
            recreateSpSkuCore(spuList);
        }
    }

    @Override
    public List<SpSkuCore> querySkuListBySpu(String spuNo, int isvalidate) {
        Map<String,Object> map=Maps.newHashMap();
        map.put("stdProductNo",spuNo);
        map.put("isvalidate",isvalidate);
        return spSkuCoreMapper.selectByCondition(map);
    }

    @Override
    public List<SpSkuCore> selectByProductNoList() {
        return spSkuCoreMapper.selectByProductNoList();
    }

    private void recreateSpSkuCore(List<String> spuList){
        Map<String,Object> map=Maps.newHashMap();
        map.put("spuList",spuList);
        map.put("isvalidate",0);
        List<SpSkuCore> oldSkuCoreList=spSkuCoreMapper.selectByCondition(map);

        for (SpSkuCore oldSkuCore:oldSkuCoreList) {
            oldSkuCore.setIsvalidate(1);
            oldSkuCore.setCreateTime(null);
            oldSkuCore.setEditTime(null);
            spSkuCoreMapper.updateByPrimaryKeySelective(oldSkuCore);
            oldSkuCore.setProductNo(createProductNo(oldSkuCore.getCategorySpNo()));
            oldSkuCore.setIsvalidate(0);
            spSkuCoreMapper.insertSelective(oldSkuCore);
        }
    }

    private String createProductNo(String categorySpNo) {
        Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_SP_PRODUCT_NO + categorySpNo), 1);
        return String.format("%s%06d",categorySpNo,increase);
    }
}
