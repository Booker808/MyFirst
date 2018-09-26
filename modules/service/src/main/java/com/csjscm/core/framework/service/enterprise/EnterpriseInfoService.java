package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.example.EnterpriseInfoExample;
import com.csjscm.core.framework.model.EnterpriseInfo;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoAccessDto;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoDto;
import com.csjscm.core.framework.vo.EnterpriseInfoSPModel;
import io.swagger.models.auth.In;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.List;
import java.util.Map;

public interface EnterpriseInfoService {
    String createEnterpriseNo();

 //   boolean insertEnterpriseInfo(EnterpriseInfoDto enterpriseInfoDto);

    /**
     * 看看这个企业存不存在 存在返回EnterpriseInfo
     * @param name 企业名称
     * @param type 企业类型
     * @return
     */
    EnterpriseInfo checkPartnerName(String name, Integer type);

    /**
     * 保存商城过来的企业信息
     * @param enterpriseInfoSPModel
     * @return
     */
    String saveSPEnterpriseInfo(EnterpriseInfoSPModel enterpriseInfoSPModel);


    String insertEnterpriseInfo(EnterpriseInfoDto enterpriseInfoDto);

    List<String> queryEnterpriseName(String name);

    QueryResult<EnterpriseInfoDto> queryEnterpriseInfo(int page, int rpp, EnterpriseInfoExample enterpriseInfoExample);

    String updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    String updateEnterpriseDetail(EnterpriseInfoAccessDto enterpriseInfoAccessDto);

    List<EnterpriseInfo> listSelective(Map<String,Object> map);
}
