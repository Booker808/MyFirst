package com.csjscm.core.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.util.BeanValidator;
import com.csjscm.core.framework.common.util.BeanutilsCopy;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.*;
import com.csjscm.core.framework.model.BrandCategory;
import com.csjscm.core.framework.model.BrandMaster;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.SpCategory;
import com.csjscm.core.framework.service.BrandMasterService;
import com.csjscm.core.framework.vo.BrandMasterModel;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SpCategoryMapper spCategoryMapper;
    @Autowired
    private BrandCategoryMapper brandCategoryMapper;

    @Override
    public QueryResult<BrandMaster> queryBrandMasterList(Map<String, Object> map, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        Page<BrandMaster> page = (Page<BrandMaster>) brandMasterMapper.selectByBrand(map);
        return new QueryResult(page);
    }

    @Override
    public List<BrandMaster> selectByBrandName(String brandName) {
        List<BrandMaster> brandList = brandMasterMapper.selectByBrandName(brandName);
        List list = new ArrayList();
        if (null != brandList && !brandList.isEmpty()) {
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
        if (null != brandList && !brandList.isEmpty()) {
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
    @Transactional
    public int insertSelective(BrandMasterModel record) {
        BrandMaster brandMaster = new BrandMaster();
        BeanutilsCopy.copyProperties(record, brandMaster);
        brandMaster.setCreateTime(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("brandName", brandMaster.getBrandName());
        List<BrandMaster> list = brandMasterMapper.selectByBrand(map);
        if (list.size() > 0) {
            throw new BussinessException("品牌已存在");
        }
        int i = brandMasterMapper.insertSelective(brandMaster);
        saveBrandSpCategory(brandMaster.getId(), record.getBrandCategory(), record.getBrandSpCategory());
        reloadBrandList();
        return i;
    }

    @Transactional
    public void saveBrandSpCategory(Integer brandId, String brandCategoryStr, String brandSpCategoryStr) {
        if (StringUtils.isNotBlank(brandCategoryStr)) {
            String[] brandCategorys = brandCategoryStr.split(",");
            for (String s : brandCategorys) {
                int id = Integer.parseInt(s);
                Category byPrimary = categoryMapper.findByPrimary(id);
                Category byPrimary1 = categoryMapper.findByPrimary(byPrimary.getParentClass());
                Category byPrimary2 = categoryMapper.findByPrimary(byPrimary1.getParentClass());
                BrandCategory brandCategory = new BrandCategory();
                brandCategory.setBrandId(brandId);
                brandCategory.setLv1CategoryId(byPrimary2.getId());
                brandCategory.setLv2CategoryId(byPrimary1.getId());
                brandCategory.setLv3CategoryId(byPrimary.getId());
                brandCategory.setType(1);
                brandCategoryMapper.insertSelective(brandCategory);
            }
        }
        if (StringUtils.isNotBlank(brandSpCategoryStr)) {
            String[] brandSpCategorys = brandSpCategoryStr.split(",");
            for (String s : brandSpCategorys) {
                int id = Integer.parseInt(s);
                SpCategory byPrimary = spCategoryMapper.findByPrimary(id);
                SpCategory byPrimary1 = spCategoryMapper.findByPrimary(byPrimary.getParentClass());
                SpCategory byPrimary2 = spCategoryMapper.findByPrimary(byPrimary1.getParentClass());
                BrandCategory brandCategory = new BrandCategory();
                brandCategory.setBrandId(brandId);
                brandCategory.setLv1CategoryId(byPrimary2.getId());
                brandCategory.setLv2CategoryId(byPrimary1.getId());
                brandCategory.setLv3CategoryId(byPrimary.getId());
                brandCategory.setType(2);
                brandCategoryMapper.insertSelective(brandCategory);
            }
        }
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(BrandMasterModel brand) {
        if (brand.getId() == null) {
            throw new BussinessException("id不能为空");
        }
        BrandMaster brandMaster = new BrandMaster();
        BeanutilsCopy.copyProperties(brand, brandMaster);

        Map<String, Object> response = new HashMap<>();
        response.put("brandName", brand.getBrandName());
        List<BrandMaster> brandMasterList = brandMasterMapper.selectByBrand(response);
        if (brandMasterList.size() > 0) {
            BrandMaster brandMaster1 = brandMasterList.get(0);
            if (brandMaster1.getId().intValue() != brand.getId().intValue()) {
                throw new BussinessException("修改品牌已存在");
            }
        }
        int i = brandMasterMapper.updateByPrimaryKeySelective(brandMaster);
        brandCategoryMapper.deleteByBrandId(brand.getId());
        saveBrandSpCategory(brand.getId(), brand.getBrandCategory(), brand.getBrandSpCategory());
        reloadBrandList();
        return i;
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("brandId", id);
        int count = skuCoreMapper.findCount(map);
        if (count > 0) {
            throw new BussinessException("该品牌已关联商品，无法删除");
        }
        int i = brandMasterMapper.deleteByPrimaryKey(id);
        brandCategoryMapper.deleteByBrandId(id);
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
        redisServiceFacade.set(Constant.REDIS_KEY_JSONSTR_BRAND, JSONArray.parseArray(JSON.toJSONString(brandList)));
    }

    @Override
    public BrandMaster findSelective(Map<String, Object> map) {
        return brandMasterMapper.findSelective(map);
    }

    @Override
    public List<BrandMaster> listSelective(Map<String, Object> map) {
        return brandMasterMapper.listSelective(map);
    }

    @Override
    public List<BrandMaster> findBrandByLv3CategoryId(Integer lv3CategoryId) {
        return brandMasterMapper.findBrandByLv3CategoryId(lv3CategoryId);
    }

    @Override
    public List<BrandMaster> findBrandListByIds(List<Integer> brandIds) {
        List<BrandMaster> brands = new ArrayList<>();
        for (Integer brandId : brandIds) {
            Map<String, Object> map = new HashMap<>();
            map.put("brandId", brandId);
            brands.add(brandMasterMapper.findSelective(map));
        }
        return brands;
    }
}
