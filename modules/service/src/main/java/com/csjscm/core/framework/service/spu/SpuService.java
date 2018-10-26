package com.csjscm.core.framework.service.spu;

import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.model.SpSkuCore;
import com.csjscm.core.framework.vo.SpuVo;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.List;

public interface SpuService {
    QueryResult<SpuVo> querySpuList(int page, int rpp, SpuExample example);

    /**
     * 批量更新上架状态
     *
     * @param shelf
     * @param spuList
     */
    void updateShelfState(Integer shelf, List<String> spuList);

    /**
     * 根据spuNo获取对应正在架上的sku
     *
     * @param spuNo
     * @return
     */
    List<SpSkuCore> querySkuListBySpu(String spuNo, int isvalidate);

    List<SpSkuCore> selectByProductNoList();
}
