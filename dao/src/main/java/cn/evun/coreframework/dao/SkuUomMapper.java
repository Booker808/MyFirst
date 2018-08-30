package cn.evun.coreframework.dao;

import cn.evun.coreframework.model.SkuUom;
import cn.evun.coreframework.model.SkuUomExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SkuUomMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int countByExample(SkuUomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int deleteByExample(SkuUomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int insert(SkuUom record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int insertSelective(SkuUom record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    List<SkuUom> selectByExample(SkuUomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    SkuUom selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByExampleSelective(@Param("record") SkuUom record, @Param("example") SkuUomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByExample(@Param("record") SkuUom record, @Param("example") SkuUomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByPrimaryKeySelective(SkuUom record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_uom
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByPrimaryKey(SkuUom record);
}