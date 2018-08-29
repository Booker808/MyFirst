package cn.evun.coreframework.service.demo.impl;

import cn.evun.coreframework.common.util.Md5Util;
import cn.evun.coreframework.dao.UnitConvertMapper;
import cn.evun.coreframework.model.UnitConvert;
import cn.evun.coreframework.service.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
