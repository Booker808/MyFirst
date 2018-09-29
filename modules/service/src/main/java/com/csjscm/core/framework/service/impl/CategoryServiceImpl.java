package com.csjscm.core.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.common.enums.CategoryLevelEnum;
import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.dao.CategoryMapper;
import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.model.Category;
import com.csjscm.core.framework.service.CategoryService;
import com.csjscm.core.framework.vo.CategoryJsonModel;
import com.csjscm.sweet.framework.core.mvc.BusinessException;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 商品分类表ServiceImpl
 *
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisServiceFacade redisServiceFacade;
    @Autowired
    private SkuCoreMapper skuCoreMapper;


    @Override
    public int save(Category t) {
        if(t.getClassCode().length()!= t.getLevelNum()){
            throw new BusinessException("分类编码长度与级别不符");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("classCode", t.getClassCode());
        int count = categoryMapper.findCount(map);
        if (count > 0) {
            throw new BussinessException("分类编码不能重复");
        }
        t.setCreateTime(new Date());
        if (t.getLevelNum().intValue() == CategoryLevelEnum.三级.getState().intValue()) {
            redisServiceFacade.set(Constant.REDIS_KEY_PRODUCT_NO + t.getClassCode(), 0);
        }
        int i = categoryMapper.insertSelective(t);
        getJsonCategory();
        return i;
    }

    @Override
    public int update(Category t) {
        Category old = categoryMapper.findByPrimary(t.getId());
        //校验编码是否重复
        Map<String, Object> map = new HashMap<>();
        map.put("classCode", t.getClassCode());
        Category category1 = categoryMapper.findSelective(map);
        if (category1 != null && t.getId().intValue() != category1.getId().intValue()) {
            throw new BussinessException("分类编码不能重复");
        }
        //查看编码是否可以修改
        if (old.getLevelNum().intValue() == CategoryLevelEnum.三级.getState().intValue()) {
            if (!t.getClassCode().equals(old.getClassCode())) {
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("categoryNo", old.getClassCode());
                int count = skuCoreMapper.findCount(productMap);
                if (count > 0) {
                    throw new BussinessException("该分类下存在商品，无法修改编码");
                }
                redisServiceFacade.set(Constant.REDIS_KEY_PRODUCT_NO + t.getClassCode(), 0);
                redisServiceFacade.delete(Constant.REDIS_KEY_PRODUCT_NO + old.getClassCode());
            }
        }
        t.setEditTime(new Date());
        int i = categoryMapper.updateSelective(t);
        getJsonCategory();
        return i;
    }

    @Override
    public Category findByPrimary(Integer id) {
        return categoryMapper.findByPrimary(id);
    }

    @Override
    public Category findSelective(Map<String, Object> map) {
        return categoryMapper.findSelective(map);
    }

    @Override
    public List<Category> listSelective(Map<String, Object> map) {
        return categoryMapper.listSelective(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return categoryMapper.findCount(map);
    }

    @Override
    public QueryResult<Category> findPage(Map<String, Object> map, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        Page<Category> list = (Page<Category>) categoryMapper.listSelective(map);
        return new QueryResult(list);
    }

    @Override
    @Transactional
    public void deleteByIds(String ids) {
        String[] strings = ids.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (String strId : strings) {
            Map<String, Object> map = new HashMap<>();
            map.put("parentClass", strId);
            int count = categoryMapper.findCount(map);
            if (count > 0) {
                throw new BussinessException("存在下级分类无法删除，请先删除下级分类");
            }
            Category primary = categoryMapper.findByPrimary(Integer.parseInt(strId));
            if (primary.getLevelNum().intValue() == CategoryLevelEnum.三级.getState().intValue()) {
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("categoryNo", primary.getClassCode());
                int count1 = skuCoreMapper.findCount(productMap);
                if (count1 > 0) {
                    throw new BussinessException("该分类下存在商品，无法删除分类");
                }
                stringBuffer.append(Constant.REDIS_KEY_PRODUCT_NO).append(primary.getClassCode()).append(",");
            }
            categoryMapper.deleteByPrimaryKey(Integer.parseInt(strId));
        }
        String s = stringBuffer.toString();
        if (StringUtils.isNotBlank(s)) {
            String[] split = s.substring(0, s.length() - 1).split(",");
            if (split.length > 0) {
                redisServiceFacade.delete(split);
            }
        }
        getJsonCategory();
    }

    @Override
    @Transactional
    public void updateState(List<Integer> ids, Integer state) {
        List<Integer> nextIds = new ArrayList<>();
        for (Integer id : ids) {
            Category category = categoryMapper.findByPrimary(id);
            category.setState(state);
            categoryMapper.updateSelective(category);
            Map<String, Object> map = new HashMap<>();
            map.put("parentClass", category.getId());
            List<Category> categories = categoryMapper.listSelective(map);
            for (Category c : categories) {
                nextIds.add(c.getId());
            }
        }
        if (nextIds.size() > 0) {
            updateState(nextIds, state);
        }
        getJsonCategory();
    }

    @Override
    public void updateUdf(Map<String, Object> map) {
        categoryMapper.updateUdf(map);
        getJsonCategory();
    }

    @Override
    public List<CategoryJsonModel> getJsonCategory() {
        List<CategoryJsonModel> list=new ArrayList<>();
        List<CategoryJsonModel> model = categoryMapper.findModelAll();
        for (CategoryJsonModel treeNode : model) {
            if (treeNode.getParentClass().equals("0")) {
                list.add(treeNode);
            }
            for (CategoryJsonModel it : model) {
                if (it.getParentClass().equals(String.valueOf(treeNode.getId()))) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<CategoryJsonModel>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        redisServiceFacade.set(Constant.REDIS_KEY_JSON_CATEGORY,  JSONArray.parseArray(JSON.toJSONString(list)));
        return list;
    }

}