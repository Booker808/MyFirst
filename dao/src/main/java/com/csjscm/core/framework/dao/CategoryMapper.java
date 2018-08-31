package com.csjscm.core.framework.dao;

import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.model.CategoryKey;

public interface CategoryMapper {
    int deleteByPrimaryKey(CategoryKey key);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(CategoryKey key);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}