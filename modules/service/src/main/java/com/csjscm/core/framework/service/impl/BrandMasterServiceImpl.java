package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.BrandMasterMapper;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjc on 2018/8/31.
 */
@Service
public class BrandMasterServiceImpl implements BrandMasterService {

    @Autowired
    private BrandMasterMapper brandMasterMapper;

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
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(record.getBrandName())){
            throw  new BussinessException("品牌为空");
        }
        map.put("brandName", record.getBrandName());
        List<BrandMaster> list = brandMasterMapper.selectByBrand(map);
        if (null != list && !list.isEmpty()) {
            throw  new BussinessException("品牌已存在");
        }
        return brandMasterMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BrandMaster record) {
        Map<String, Object> response = new HashMap<>();
        response.put("brandName", record.getBrandName());
        List<BrandMaster> brandMasterList = brandMasterMapper.selectByBrand(response);
        if (null != brandMasterList && !brandMasterList.isEmpty()){
            throw  new BussinessException("修改品牌已存在");
        }
        return brandMasterMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return brandMasterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        String[] str = ids.split(",");
        for (String s : str) {
            brandMasterMapper.deleteByPrimaryKey(Integer.valueOf(s));
        }
    }

}
