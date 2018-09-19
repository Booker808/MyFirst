package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.example.EnterpriseInfoExample;
import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoDto;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.List;

public interface EnterpriseInfoService {
    String createEnterpriseNo();

    String insertEnterpriseInfo(EnterpriseInfoDto enterpriseInfoDto);

    List<String> queryEnterpriseName(String name);

    QueryResult<EnterpriseInfoDto> queryEnterpriseInfo(int page, int rpp, EnterpriseInfoExample enterpriseInfoExample);
}
