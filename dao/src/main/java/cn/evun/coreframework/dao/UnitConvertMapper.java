package cn.evun.coreframework.dao;

import cn.evun.coreframework.model.UnitConvert;
import cn.evun.coreframework.model.UnitConvertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UnitConvertMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int countByExample(UnitConvertExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int deleteByExample(UnitConvertExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int insert(UnitConvert record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int insertSelective(UnitConvert record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    List<UnitConvert> selectByExample(UnitConvertExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    UnitConvert selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByExampleSelective(@Param("record") UnitConvert record, @Param("example") UnitConvertExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByExample(@Param("record") UnitConvert record, @Param("example") UnitConvertExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByPrimaryKeySelective(UnitConvert record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit_convert
     *
     * @mbggenerated Thu Aug 30 16:21:57 CST 2018
     */
    int updateByPrimaryKey(UnitConvert record);
}