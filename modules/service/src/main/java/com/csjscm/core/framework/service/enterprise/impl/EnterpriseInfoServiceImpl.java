package com.csjscm.core.framework.service.enterprise.impl;

import com.csjscm.core.framework.common.constant.Constant;
import com.csjscm.core.framework.dao.EnterpriseInfoMapper;
import com.csjscm.core.framework.service.enterprise.EnterpriseInfoService;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoDto;
import com.csjscm.sweet.framework.redis.RedisServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {
    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;

    @Override
    public String createEnterpriseNo() {
        //暂定 企业编码生成规则
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date())+"0001";
        Long newno=Long.parseLong(format);
        String maxEntNumber = enterpriseInfoMapper.findMaxEntNumber();
        if(maxEntNumber==null|| StringUtils.isEmpty(maxEntNumber)){
            return Constant.ENTNUMBER_INDEX+format;
        }else{
            maxEntNumber=maxEntNumber.replace(Constant.ENTNUMBER_INDEX,"").trim();
        }
        Long oldno=Long.parseLong(maxEntNumber);
        String  entNumber=Constant.ENTNUMBER_INDEX+format;
        if(oldno>=newno){
            Long i = oldno + 1;
            entNumber=Constant.ENTNUMBER_INDEX+i;
        }
        return entNumber;
    }

    @Override
    public boolean insertEnterpriseInfo(EnterpriseInfoDto enterpriseInfoDto) {
        return false;
    }
}
