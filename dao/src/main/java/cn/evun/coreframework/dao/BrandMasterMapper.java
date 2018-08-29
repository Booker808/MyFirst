package cn.evun.coreframework.dao;

import cn.evun.coreframework.model.BrandMaster;
import cn.evun.coreframework.model.BrandMasterExample;
import cn.evun.coreframework.model.BrandMasterKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrandMasterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int countByExample(BrandMasterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int deleteByExample(BrandMasterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int deleteByPrimaryKey(BrandMasterKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int insert(BrandMaster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int insertSelective(BrandMaster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    List<BrandMaster> selectByExample(BrandMasterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    BrandMaster selectByPrimaryKey(BrandMasterKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByExampleSelective(@Param("record") BrandMaster record, @Param("example") BrandMasterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByExample(@Param("record") BrandMaster record, @Param("example") BrandMasterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByPrimaryKeySelective(BrandMaster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table brand_master
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByPrimaryKey(BrandMaster record);
}