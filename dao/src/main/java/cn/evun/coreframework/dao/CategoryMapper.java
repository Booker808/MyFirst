package cn.evun.coreframework.dao;

import cn.evun.coreframework.model.Category;
import cn.evun.coreframework.model.CategoryExample;
import cn.evun.coreframework.model.CategoryKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int countByExample(CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int deleteByExample(CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int deleteByPrimaryKey(CategoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int insert(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int insertSelective(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    List<Category> selectByExample(CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    Category selectByPrimaryKey(CategoryKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByPrimaryKeySelective(Category record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    int updateByPrimaryKey(Category record);
}