package com.csjscm.core.framework.service.product;

import com.csjscm.core.framework.model.BrandCategory;
import com.csjscm.core.framework.vo.BrandCategoryVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联表Service
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-10-29 14:49:28
 */

public interface BrandCategoryService {
    /**
     * 按条件查询list
     *
     */
    List<BrandCategory> listSelective(Map<String, Object> map);

    List<String> findCategoryNameList(Map<String, Object> map);

    List<BrandCategoryVo> findBrandCategoryVo(Integer brandId);
}
