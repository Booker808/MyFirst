package com.csjscm.core.framework.service.product.impl;

import com.csjscm.core.framework.dao.BrandCategoryMapper;
import com.csjscm.core.framework.model.BrandCategory;
import com.csjscm.core.framework.service.product.BrandCategoryService;
import com.csjscm.core.framework.vo.BrandCategoryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 品牌分类关联表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-29 14:49:28
 */
 
@Service
public class BrandCategoryServiceImpl implements BrandCategoryService {
	
    private static final Logger logger = LoggerFactory.getLogger(BrandCategoryServiceImpl.class);
   
    @Autowired
    private BrandCategoryMapper brandCategoryMapper;


    @Override
    public List<BrandCategory> listSelective(Map<String, Object> map) {
        return brandCategoryMapper.listSelective(map);
    }

    @Override
    public List<String> findCategoryNameList(Map<String, Object> map) {
        return brandCategoryMapper.findCategoryNameList(map);
    }

    @Override
    public List<BrandCategoryVo> findBrandCategoryVo(Integer brandId) {
        return brandCategoryMapper.findBrandCategoryVo(brandId);
    }
}