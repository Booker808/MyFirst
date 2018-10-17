package com.csjscm.core.framework.service.tax;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TaxService {
    void importTaxCategoryExcel(String userName, Integer versionId, MultipartFile file);
}
