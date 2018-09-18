package com.csjscm.core.framework.service.enterprise;

import com.csjscm.core.framework.service.enterprise.dto.EnterpriseInfoDto;

public interface EnterpriseInfoService {
    String createEnterpriseNo();

    boolean insertEnterpriseInfo(EnterpriseInfoDto enterpriseInfoDto);
}
