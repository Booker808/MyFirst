package com.csjscm.core.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.BrandMasterMapper;
import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zjc on 2018/8/31.
 */
@Service
public class BrandMasterServiceImpl implements BrandMasterService {

    @Autowired
    private BrandMasterMapper brandMasterMapper;
    @Autowired
    private SkuCoreMapper skuCoreMapper;
    @Autowired
    private RedisServiceFacade redisServiceFacade;

    @Override
    public QueryResult<BrandMaster> queryBrandMasterList(Map<String, Object> map, int current, int pageSize) {
        PageHelper.startPage(current,pageSize);
        Page<BrandMaster> page = (Page<BrandMaster>) brandMasterMapper.selectByBrand(map);
        return new QueryResult(page);
    }

    @Override
    public List<BrandMaster> selectByBrandName(String brandName) {
        List<BrandMaster> brandList = brandMasterMapper.selectByBrandName(brandName);
        List list = new ArrayList();
        if(null != brandList && !brandList.isEmpty()){
            for (BrandMaster brandMaster : brandList) {
                Map<String, Object> map = new HashMap();
                map.put("id", brandMaster.getId());
                map.put("brand_name", brandMaster.getBrandName());
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public List<BrandMaster> selectByBrandNameSky() {
        List<BrandMaster> brandList = brandMasterMapper.selectByBrandNameSky();
        List list = new ArrayList();
        if(null != brandList && !brandList.isEmpty()){
            for (BrandMaster brandMaster : brandList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", brandMaster.getId());
                map.put("brand_name", brandMaster.getBrandName());
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public BrandMaster selectByPrimaryKey(Integer id) {
        return brandMasterMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertSelective(BrandMaster record) {
        record.setCreateTime(new Date());
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(record.getBrandName())){
            throw  new BussinessException("品牌为空");
        }
        map.put("brandName", record.getBrandName());
        List<BrandMaster> list = brandMasterMapper.selectByBrand(map);
        if (null != list && !list.isEmpty()) {
            throw  new BussinessException("品牌已存在");
        }
        int i = brandMasterMapper.insertSelective(record);
        reloadBrandList();
        return i;
    }

    @Override
    public int updateByPrimaryKeySelective(BrandMaster record) {
        if(record.getId()==null){
            throw  new BussinessException("id不能为空");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("brandName", record.getBrandName());
        List<BrandMaster> brandMasterList = brandMasterMapper.selectByBrand(response);
        if (brandMasterList.size()>0){
            BrandMaster brandMaster = brandMasterList.get(0);
            if(brandMaster.getId().intValue()!=record.getId().intValue()){
                throw  new BussinessException("修改品牌已存在");
            }
        }
        int i = brandMasterMapper.updateByPrimaryKeySelective(record);
        reloadBrandList();
        return i;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        Map<String,Object> map=new HashMap<>();
        map.put("brandId",id);
        int count = skuCoreMapper.findCount(map);
        if(count>0){
            throw  new  BussinessException("该品牌已关联商品，无法删除");
        }
        int i = brandMasterMapper.deleteByPrimaryKey(id);
        reloadBrandList();
        return i;
    }

    @Override
    public void deleteByIds(String ids) {
        String[] str = ids.split(",");
        for (String s : str) {
            brandMasterMapper.deleteByPrimaryKey(Integer.valueOf(s));
        }
        reloadBrandList();
    }

    @Override
    public void reloadBrandList() {
        List<BrandMaster> brandList = selectByBrandNameSky();
        redisServiceFacade.set(Constant.REDIS_KEY_JSONSTR_BRAND,  JSONArray.parseArray(JSON.toJSONString(brandList)));
    }

}
