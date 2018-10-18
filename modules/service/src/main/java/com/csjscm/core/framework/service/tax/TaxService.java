package com.csjscm.core.framework.service.tax;

import com.csjscm.core.framework.example.TaxCategoryExample;
import com.csjscm.core.framework.example.TaxVersionExample;
import com.csjscm.core.framework.model.TaxCategory;
import com.csjscm.core.framework.model.TaxVersion;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TaxService {
    void importTaxCategoryExcel(String userName, Integer versionId, MultipartFile file);

    QueryResult<TaxVersion> queryTaxVersion(int page, int rpp, TaxVersionExample example);

    TaxVersion queryTaxVersionById(Integer id);

    Integer addTaxVersion(TaxVersion taxVersion);

    Integer updateTaxVersion(TaxVersion taxVersion);

    void copyTaxVersion(Integer id, String userName);

    List<TaxCategory> queryTaxCategoryAll(Integer versionId);

    QueryResult<TaxCategory> queryTaxCategoryList(int page, int rpp, TaxCategoryExample example);
}
