package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.BrandMasterMapper;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
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
    public Map<String, Object> insertSelective(BrandMaster record) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(record.getBrandName())){
            map.put("message", "品牌名称为空");
            return map;
        }
        map.put("brandName", record.getBrandName());
        List<BrandMaster> list = brandMasterMapper.selectByBrand(map);
        if (null != list && !list.isEmpty()) {
            map.clear();
            map.put("message", "品牌已存在");
            return map;
        }
        brandMasterMapper.insertSelective(record);
        return null;
    }

    @Override
    public Map<String, Object> updateByPrimaryKeySelective(BrandMaster record) {
        Map<String, Object> response = new HashMap<>();
        List<String> listMsg = new ArrayList<>();
        String message = "";
        if (null == record.getId()){
            message = "品牌ID为空";
            listMsg.add(message);
        }
        if (StringUtils.isBlank(record.getBrandName())){
            message = "品牌名称为空";
            listMsg.add(message);
        }
        if (null != listMsg && !listMsg.isEmpty()){
            response.put("message", listMsg);
            return response;
        }
        response.put("brandName", record.getBrandName());
        List<BrandMaster> brandMasterList = brandMasterMapper.selectByBrand(response);
        if (null != brandMasterList && !brandMasterList.isEmpty()){
            response.clear();
            response.put("message", "修改品牌已存在");
            return response;
        }
        brandMasterMapper.updateByPrimaryKeySelective(record);
        return null;
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
