package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.dao.BrandMasterMapper;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
                Map map = new HashMap();
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
        return brandMasterMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BrandMaster record) {
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
