package com.csjscm.core.framework.service.spu.impl;

import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.dao.BrandMasterMapper;
import com.csjscm.core.framework.dao.SpCategoryMapper;
import com.csjscm.core.framework.dao.SpSkuCoreMapper;
import com.csjscm.core.framework.dao.SpuMapper;
import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.model.*;
import com.csjscm.core.framework.service.spu.SpuService;
import com.csjscm.core.framework.service.spu.dto.SpSkuCoreDto;
import com.csjscm.core.framework.service.spu.dto.SpuAttrDetailDto;
import com.csjscm.core.framework.service.spu.dto.SpuAttrDto;
import com.csjscm.core.framework.service.spu.dto.SpuDto;
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
    @Autowired
    private SpCategoryMapper spCategoryMapper;
    @Autowired
    private BrandMasterMapper brandMasterMapper;

    @Override
    public QueryResult<SpuDto> querySpuList(int page, int rpp, SpuExample example) {
        PageHelper.startPage(page,rpp);
        List<SpuEx> spuExList=spuMapper.selectByExample(example);
        PageInfo<SpuEx> pageInfo=new PageInfo<>(spuExList);
        QueryResult<SpuDto> result=new QueryResult<>();
        List<SpuDto> list= Lists.newLinkedList();
        for(SpuEx spuEx:pageInfo.getList()){
            SpuDto vo=new SpuDto();
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
    public List<SpSkuCoreDto> querySkuListBySpu(String spuNo, int isvalidate) {
        Map<String,Object> map=Maps.newHashMap();
        map.put("stdProductNo",spuNo);
        map.put("isvalidate",isvalidate);
        List<SpSkuCore> spSkuCoreList= spSkuCoreMapper.selectByCondition(map);
        List<SpSkuCoreDto> result=Lists.newLinkedList();
        for(SpSkuCore spSkuCore:spSkuCoreList){
            SpSkuCoreDto vo=new SpSkuCoreDto();
            BeanutilsCopy.copyProperties(spSkuCore,vo);
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<SpSkuCore> selectByProductNoList() {
        return spSkuCoreMapper.selectByProductNoList();
    }

    @Override
    public List<Spu> selectBySpuNoList() {
        return spuMapper.selectBySpuNoList();
    }

    @Override
    public SpuDto querySpu(String spuNo) {
        Spu spu=spuMapper.selectByPrimaryKey(spuNo);
        SpuDto result=new SpuDto();
        if(spu==null){
            throw new BusinessException("找不到对应的spu");
        }
        BeanutilsCopy.copyProperties(spu,result);
        if(result.getBrandId()!=null){
            BrandMaster brandMaster=brandMasterMapper.selectByPrimaryKey(Integer.parseInt(result.getBrandId()));
            if(brandMaster!=null){
                result.setBrandName(brandMaster.getBrandName());
            }
        }
        return result;
    }

    @Override
    public String createSpu(SpuDto spuDto) {
        Spu spu=new Spu();
        BeanutilsCopy.copyProperties(spuDto,spu);
        spu.setStdProductNo(createSpuNo(spuDto.getCategorySpNo()));
        spuMapper.insertSelective(spu);
        return spu.getStdProductNo();
    }

    @Override
    public void updateSpu(SpuDto spuDto) {
        Spu spu=new Spu();
        BeanutilsCopy.copyProperties(spuDto,spu);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    @Transactional
    public void updateSpSkuList(String spuNo,List<SpSkuCoreDto> skuCoreVoList) {
        Spu spu=spuMapper.selectByPrimaryKey(spuNo);
        for(SpSkuCoreDto spSkuCoreDto :skuCoreVoList){
            SpSkuCore spSkuCore=new SpSkuCore();
            BeanutilsCopy.copyProperties(spSkuCoreDto,spSkuCore);
            spSkuCore.setStdProductNo(spuNo);
            spSkuCore.setCategorySpId(spu.getCategorySpId());
            spSkuCore.setCategorySpNo(spu.getCategorySpNo());
            spSkuCore.setLv1CategorySpId(spu.getLv1CategorySpId());
            spSkuCore.setLv1CategorySpNo(spu.getLv1CategorySpNo());
            spSkuCore.setLv2CategorySpId(spu.getLv2CategorySpId());
            spSkuCore.setLv2CategorySpNo(spu.getLv2CategorySpNo());
            if(StringUtils.isEmpty(spSkuCore.getProductNo())){
                //若不存在，则要生成产品编码
                spSkuCore.setProductNo(createProductNo(spSkuCore.getCategorySpNo()));
                spSkuCoreMapper.insertSelective(spSkuCore);
            }else{
                //若存在，则更新
                spSkuCoreMapper.updateByPrimaryKeySelective(spSkuCore);
            }
        }
    }

    @Override
    public List<SpuAttrDetailDto> queryAttrList(String spuNo) {
        Spu spu=spuMapper.selectByPrimaryKey(spuNo);
        SpCategory spCategory=spCategoryMapper.findByPrimary(spu.getCategorySpId());
        Map<String,SpuAttrDetailDto> map=Maps.newHashMap();
        if(spCategory.getUdf2()!=null){
            List<SpuAttrDetailDto> categoryAttrList= JSONObject.parseArray(spCategory.getUdf2(),SpuAttrDetailDto.class);
            for(SpuAttrDetailDto caAttr:categoryAttrList){
                map.put(caAttr.getName(),caAttr);
            }
        }
        if(spu.getCdf1()!=null){
            List<SpuAttrDetailDto> spuAttrList=JSONObject.parseArray(spu.getCdf1(),SpuAttrDetailDto.class);
            for(SpuAttrDetailDto spuAttr:spuAttrList){
                if(map.containsKey(spuAttr.getName())){
                    map.get(spuAttr.getName()).setCurrentValue(spuAttr.getCurrentValue());
                }else{
                    map.put(spuAttr.getName(),spuAttr);
                }
            }
        }
        return Lists.newArrayList(map.values());
    }

    @Override
    @Transactional
    public void saveSpuAttrList(String spuNo, List<SpuAttrDto> attrList) {
        Spu spu=new Spu();
        spu.setStdProductNo(spuNo);
        spu.setCdf1(JSONObject.toJSONString(attrList));
        spuMapper.updateByPrimaryKeySelective(spu);
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

    private String createSpuNo(String categorySpNo){
        Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_SP_SPU_NO + categorySpNo), 1);
        return String.format("%s%s%05d","SP", categorySpNo,increase);
    }

    private String createProductNo(String categorySpNo) {
        Long increase = redisServiceFacade.increase(new RedisDistributedCounterObject(Constant.REDIS_KEY_SP_PRODUCT_NO + categorySpNo), 1);
        return String.format("%s%06d",categorySpNo,increase);
    }
}
