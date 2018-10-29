package com.csjscm.core.framework.service.spu;

import com.csjscm.core.framework.example.SpuExample;
import com.csjscm.core.framework.model.SpSkuCore;
import com.csjscm.core.framework.model.Spu;
import com.csjscm.core.framework.service.spu.dto.SpSkuCoreDto;
import com.csjscm.core.framework.service.spu.dto.SpuDto;
import com.csjscm.sweet.framework.core.mvc.model.QueryResult;

import java.util.List;

public interface SpuService {
    QueryResult<SpuDto> querySpuList(int page, int rpp, SpuExample example);

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
    List<SpSkuCoreDto> querySkuListBySpu(String spuNo, int isvalidate);

    List<SpSkuCore> selectByProductNoList();

    List<Spu> selectBySpuNoList();

    SpuDto querySpu(String spuNo);

    /**
     * 创建spu
     *
     * @param spuDto
     * @return 生成的spu编码
     */
    String createSpu(SpuDto spuDto);

    void updateSpu(SpuDto spuDto);

    /**
     * 批量操作商城sku
     *
     * @param spuNo
     * @param skuCoreVoList
     */
    void updateSpSkuList(String spuNo,List<SpSkuCoreDto> skuCoreVoList);
}
