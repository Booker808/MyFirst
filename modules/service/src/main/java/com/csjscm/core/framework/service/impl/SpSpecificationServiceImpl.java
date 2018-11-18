package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.common.enums.IsSpecUsedEnum;
import com.csjscm.core.framework.common.enums.SpecTypeEnum;
import com.csjscm.core.framework.dao.SpSpecificationMapper;
import com.csjscm.core.framework.dao.SpSpecificationOptionsMapper;
import com.csjscm.core.framework.model.SpSpecification;
import com.csjscm.core.framework.model.SpSpecificationOptions;
import com.csjscm.core.framework.service.SpSpecificationService;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类属性ServiceImpl
 *
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 13:14:18
 */
@Service
public class SpSpecificationServiceImpl implements SpSpecificationService {
    private static final Logger logger = LoggerFactory.getLogger(SpSpecificationServiceImpl.class);

    @Autowired
    private SpSpecificationMapper spSpecificationMapper;
    @Autowired
    private SpSpecificationOptionsMapper spSpecificationOptionsMapper;

    @Override
    public int save(SpSpecification t) {
        return 0;
    }

    @Override
    public int update(SpSpecification t) {
        return 0;
    }

    @Override
    public void insertSellSpecByLv3Id(SpSpecification spec, List<String> sellOptions) {
        if (spec == null) {
            return;
        }
        // 属性名称要是没有  就加进去   要是名称有了  值没有 就扩充
        // 设置type和status
        spec.setStatus(IsSpecUsedEnum.是.getState());
        spec.setType(SpecTypeEnum.销售.getState());
        insertSpecAndSpecOptions(spec, sellOptions, spec.getCategoryId());
    }

    @Override
    public void insertExtSpecByLv3Id(SpSpecification spec, List<String> extOptions) {
        if (spec == null) {
            return;
        }
        // 设置type和status
        spec.setStatus(IsSpecUsedEnum.是.getState());
        spec.setType(SpecTypeEnum.扩展.getState());
        // 判断属性是否存在以及属性值是否存在
        insertSpecAndSpecOptions(spec, extOptions, spec.getCategoryId());
    }

    private void insertSpecAndSpecOptions(SpSpecification spec, List<String> options, Integer categoryId) {
        List<SpSpecification> specList = spSpecificationMapper.listSelective(null);
        for (SpSpecification specTmp : specList) {
            if (specTmp.getSpecName() != spec.getSpecName()) {
                // 没有该属性名称就添加
                spSpecificationMapper.insert(spec);
                // 添加options
                for (String optionsTmp : options) {
                    SpSpecificationOptions newOptions = new SpSpecificationOptions();
                    newOptions.setSpecId(spec.getId());
                    newOptions.setOptionValue(optionsTmp);
                    spSpecificationOptionsMapper.insert(newOptions);
                }
            } else {
                // 如果本地存在该属性,查看该属性是否有值
                Map<String, Object> map = new HashMap<>();
                map.put("specId", spec.getId());
                List<SpSpecificationOptions> localOptions = spSpecificationOptionsMapper.listSelective(map);
                // 如果该属性的属性项不包含当前属性值
                for (SpSpecificationOptions specOption : localOptions) {
                    for (String optionsTmp : options) {
                        if (optionsTmp != specOption.getOptionValue()) {
                            SpSpecificationOptions newOptions = new SpSpecificationOptions();
                            newOptions.setOptionValue(optionsTmp);
                            newOptions.setSpecId(spec.getId());
                            spSpecificationOptionsMapper.insert(newOptions);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<SpSpecification> listSelectiveBySell(Integer categoryId) {
        // 根据分类id查询销售属性
        // 查询销售属性
        Map<String, Object> map = new HashMap<>();
        map.put("status", IsSpecUsedEnum.是.getState());
        map.put("type", SpecTypeEnum.销售.getState());
        List<SpSpecification> specList = spSpecificationMapper.listSelective(map);
        // 筛选 category
        List<SpSpecification> sellSpecList = new ArrayList<>();
        for (SpSpecification spec : specList) {
            if (spec.getCategoryId() == categoryId) {
                sellSpecList.add(spec);
            }
        }
        return sellSpecList;
    }

    @Override
    public List<SpSpecification> listSelectiveByExt(Integer categoryId) {
        // 根据分类id查询扩展属性
        // 查询扩展属性
        Map<String, Object> map = new HashMap<>();
        map.put("status", IsSpecUsedEnum.是.getState());
        map.put("type", SpecTypeEnum.扩展.getState());
        List<SpSpecification> specList = spSpecificationMapper.listSelective(map);
        // 筛选 category
        List<SpSpecification> extSpecList = new ArrayList<>();
        for (SpSpecification spec : specList) {
            if (spec.getCategoryId() == categoryId) {
                extSpecList.add(spec);
            }
        }
        return extSpecList;
    }

    @Override
    public SpSpecification findByPrimary(Integer specId) {
        return null;
    }

    @Override
    public SpSpecification findSelective(Map<String, Object> map) {
        return null;
    }

    @Override
    public List<SpSpecification> listSelective(Map<String, Object> map) {
        return null;
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return 0;
    }

    @Override
    public QueryResult<SpSpecification> findPage(Map<String, Object> map, int current, int pageSize) {
        return null;
    }

    @Override
    public void deleteByIds(String ids) {

    }

    @Override
    public void updateState(List<Integer> ids, Integer state) {

    }

    @Override
    public void updateUdf(SpSpecification t) {

    }
}
