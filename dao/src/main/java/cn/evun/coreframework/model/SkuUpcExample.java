package cn.evun.coreframework.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SkuUpcExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public SkuUpcExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProductNoIsNull() {
            addCriterion("product_no is null");
            return (Criteria) this;
        }

        public Criteria andProductNoIsNotNull() {
            addCriterion("product_no is not null");
            return (Criteria) this;
        }

        public Criteria andProductNoEqualTo(String value) {
            addCriterion("product_no =", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotEqualTo(String value) {
            addCriterion("product_no <>", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoGreaterThan(String value) {
            addCriterion("product_no >", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoGreaterThanOrEqualTo(String value) {
            addCriterion("product_no >=", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoLessThan(String value) {
            addCriterion("product_no <", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoLessThanOrEqualTo(String value) {
            addCriterion("product_no <=", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoLike(String value) {
            addCriterion("product_no like", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotLike(String value) {
            addCriterion("product_no not like", value, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoIn(List<String> values) {
            addCriterion("product_no in", values, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotIn(List<String> values) {
            addCriterion("product_no not in", values, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoBetween(String value1, String value2) {
            addCriterion("product_no between", value1, value2, "productNo");
            return (Criteria) this;
        }

        public Criteria andProductNoNotBetween(String value1, String value2) {
            addCriterion("product_no not between", value1, value2, "productNo");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeIsNull() {
            addCriterion("identifying_code is null");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeIsNotNull() {
            addCriterion("identifying_code is not null");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeEqualTo(String value) {
            addCriterion("identifying_code =", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeNotEqualTo(String value) {
            addCriterion("identifying_code <>", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeGreaterThan(String value) {
            addCriterion("identifying_code >", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeGreaterThanOrEqualTo(String value) {
            addCriterion("identifying_code >=", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeLessThan(String value) {
            addCriterion("identifying_code <", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeLessThanOrEqualTo(String value) {
            addCriterion("identifying_code <=", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeLike(String value) {
            addCriterion("identifying_code like", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeNotLike(String value) {
            addCriterion("identifying_code not like", value, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeIn(List<String> values) {
            addCriterion("identifying_code in", values, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeNotIn(List<String> values) {
            addCriterion("identifying_code not in", values, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeBetween(String value1, String value2) {
            addCriterion("identifying_code between", value1, value2, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andIdentifyingCodeNotBetween(String value1, String value2) {
            addCriterion("identifying_code not between", value1, value2, "identifyingCode");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("create_user_id like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("create_user_id not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andEditUserIdIsNull() {
            addCriterion("edit_user_id is null");
            return (Criteria) this;
        }

        public Criteria andEditUserIdIsNotNull() {
            addCriterion("edit_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andEditUserIdEqualTo(String value) {
            addCriterion("edit_user_id =", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdNotEqualTo(String value) {
            addCriterion("edit_user_id <>", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdGreaterThan(String value) {
            addCriterion("edit_user_id >", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("edit_user_id >=", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdLessThan(String value) {
            addCriterion("edit_user_id <", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdLessThanOrEqualTo(String value) {
            addCriterion("edit_user_id <=", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdLike(String value) {
            addCriterion("edit_user_id like", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdNotLike(String value) {
            addCriterion("edit_user_id not like", value, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdIn(List<String> values) {
            addCriterion("edit_user_id in", values, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdNotIn(List<String> values) {
            addCriterion("edit_user_id not in", values, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdBetween(String value1, String value2) {
            addCriterion("edit_user_id between", value1, value2, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditUserIdNotBetween(String value1, String value2) {
            addCriterion("edit_user_id not between", value1, value2, "editUserId");
            return (Criteria) this;
        }

        public Criteria andEditTimeIsNull() {
            addCriterion("edit_time is null");
            return (Criteria) this;
        }

        public Criteria andEditTimeIsNotNull() {
            addCriterion("edit_time is not null");
            return (Criteria) this;
        }

        public Criteria andEditTimeEqualTo(Date value) {
            addCriterion("edit_time =", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotEqualTo(Date value) {
            addCriterion("edit_time <>", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeGreaterThan(Date value) {
            addCriterion("edit_time >", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("edit_time >=", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeLessThan(Date value) {
            addCriterion("edit_time <", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeLessThanOrEqualTo(Date value) {
            addCriterion("edit_time <=", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeIn(List<Date> values) {
            addCriterion("edit_time in", values, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotIn(List<Date> values) {
            addCriterion("edit_time not in", values, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeBetween(Date value1, Date value2) {
            addCriterion("edit_time between", value1, value2, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotBetween(Date value1, Date value2) {
            addCriterion("edit_time not between", value1, value2, "editTime");
            return (Criteria) this;
        }

        public Criteria andUdf1IsNull() {
            addCriterion("udf1 is null");
            return (Criteria) this;
        }

        public Criteria andUdf1IsNotNull() {
            addCriterion("udf1 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf1EqualTo(String value) {
            addCriterion("udf1 =", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1NotEqualTo(String value) {
            addCriterion("udf1 <>", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1GreaterThan(String value) {
            addCriterion("udf1 >", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1GreaterThanOrEqualTo(String value) {
            addCriterion("udf1 >=", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1LessThan(String value) {
            addCriterion("udf1 <", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1LessThanOrEqualTo(String value) {
            addCriterion("udf1 <=", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1Like(String value) {
            addCriterion("udf1 like", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1NotLike(String value) {
            addCriterion("udf1 not like", value, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1In(List<String> values) {
            addCriterion("udf1 in", values, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1NotIn(List<String> values) {
            addCriterion("udf1 not in", values, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1Between(String value1, String value2) {
            addCriterion("udf1 between", value1, value2, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf1NotBetween(String value1, String value2) {
            addCriterion("udf1 not between", value1, value2, "udf1");
            return (Criteria) this;
        }

        public Criteria andUdf2IsNull() {
            addCriterion("udf2 is null");
            return (Criteria) this;
        }

        public Criteria andUdf2IsNotNull() {
            addCriterion("udf2 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf2EqualTo(String value) {
            addCriterion("udf2 =", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2NotEqualTo(String value) {
            addCriterion("udf2 <>", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2GreaterThan(String value) {
            addCriterion("udf2 >", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2GreaterThanOrEqualTo(String value) {
            addCriterion("udf2 >=", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2LessThan(String value) {
            addCriterion("udf2 <", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2LessThanOrEqualTo(String value) {
            addCriterion("udf2 <=", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2Like(String value) {
            addCriterion("udf2 like", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2NotLike(String value) {
            addCriterion("udf2 not like", value, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2In(List<String> values) {
            addCriterion("udf2 in", values, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2NotIn(List<String> values) {
            addCriterion("udf2 not in", values, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2Between(String value1, String value2) {
            addCriterion("udf2 between", value1, value2, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf2NotBetween(String value1, String value2) {
            addCriterion("udf2 not between", value1, value2, "udf2");
            return (Criteria) this;
        }

        public Criteria andUdf3IsNull() {
            addCriterion("udf3 is null");
            return (Criteria) this;
        }

        public Criteria andUdf3IsNotNull() {
            addCriterion("udf3 is not null");
            return (Criteria) this;
        }

        public Criteria andUdf3EqualTo(String value) {
            addCriterion("udf3 =", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3NotEqualTo(String value) {
            addCriterion("udf3 <>", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3GreaterThan(String value) {
            addCriterion("udf3 >", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3GreaterThanOrEqualTo(String value) {
            addCriterion("udf3 >=", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3LessThan(String value) {
            addCriterion("udf3 <", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3LessThanOrEqualTo(String value) {
            addCriterion("udf3 <=", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3Like(String value) {
            addCriterion("udf3 like", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3NotLike(String value) {
            addCriterion("udf3 not like", value, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3In(List<String> values) {
            addCriterion("udf3 in", values, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3NotIn(List<String> values) {
            addCriterion("udf3 not in", values, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3Between(String value1, String value2) {
            addCriterion("udf3 between", value1, value2, "udf3");
            return (Criteria) this;
        }

        public Criteria andUdf3NotBetween(String value1, String value2) {
            addCriterion("udf3 not between", value1, value2, "udf3");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sku_upc
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 29 15:22:26 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sku_upc
     *
     * @mbggenerated Wed Aug 29 15:22:26 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}