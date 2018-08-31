package com.csjscm.core.framework.service.demo.impl;

import com.csjscm.core.framework.common.util.Md5Util;
import com.csjscm.core.framework.dao.UnitConvertMapper;
import com.csjscm.core.framework.model.UnitConvert;
import com.csjscm.core.framework.service.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private UnitConvertMapper unitConvertMapper;

    @Override
    public String getMd5(String originStr) {
        UnitConvert unitConvert=new UnitConvert();
        unitConvert.setUnit("测试单位");
        unitConvertMapper.insertSelective(unitConvert);
        return Md5Util.md5(originStr);
    }
}
