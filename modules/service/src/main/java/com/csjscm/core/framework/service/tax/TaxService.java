package com.csjscm.core.framework.service.tax;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TaxService {
    Map<String,Object> importtaxCategoryExcel(String userName,Integer versionId, MultipartFile file);
}
