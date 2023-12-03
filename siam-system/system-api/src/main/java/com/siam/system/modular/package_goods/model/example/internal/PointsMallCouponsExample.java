package com.siam.system.modular.package_goods.model.example.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointsMallCouponsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PointsMallCouponsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeIsNull() {
            addCriterion("preferential_type is null");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeIsNotNull() {
            addCriterion("preferential_type is not null");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeEqualTo(Integer value) {
            addCriterion("preferential_type =", value, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeNotEqualTo(Integer value) {
            addCriterion("preferential_type <>", value, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeGreaterThan(Integer value) {
            addCriterion("preferential_type >", value, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("preferential_type >=", value, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeLessThan(Integer value) {
            addCriterion("preferential_type <", value, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeLessThanOrEqualTo(Integer value) {
            addCriterion("preferential_type <=", value, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeIn(List<Integer> values) {
            addCriterion("preferential_type in", values, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeNotIn(List<Integer> values) {
            addCriterion("preferential_type not in", values, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeBetween(Integer value1, Integer value2) {
            addCriterion("preferential_type between", value1, value2, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andPreferentialTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("preferential_type not between", value1, value2, "preferentialType");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountIsNull() {
            addCriterion("discount_amount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountIsNotNull() {
            addCriterion("discount_amount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountEqualTo(BigDecimal value) {
            addCriterion("discount_amount =", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountNotEqualTo(BigDecimal value) {
            addCriterion("discount_amount <>", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountGreaterThan(BigDecimal value) {
            addCriterion("discount_amount >", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discount_amount >=", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountLessThan(BigDecimal value) {
            addCriterion("discount_amount <", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discount_amount <=", value, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountIn(List<BigDecimal> values) {
            addCriterion("discount_amount in", values, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountNotIn(List<BigDecimal> values) {
            addCriterion("discount_amount not in", values, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount_amount between", value1, value2, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andDiscountAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount_amount not between", value1, value2, "discountAmount");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceIsNull() {
            addCriterion("limited_price is null");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceIsNotNull() {
            addCriterion("limited_price is not null");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceEqualTo(BigDecimal value) {
            addCriterion("limited_price =", value, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceNotEqualTo(BigDecimal value) {
            addCriterion("limited_price <>", value, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceGreaterThan(BigDecimal value) {
            addCriterion("limited_price >", value, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("limited_price >=", value, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceLessThan(BigDecimal value) {
            addCriterion("limited_price <", value, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("limited_price <=", value, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceIn(List<BigDecimal> values) {
            addCriterion("limited_price in", values, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceNotIn(List<BigDecimal> values) {
            addCriterion("limited_price not in", values, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("limited_price between", value1, value2, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andLimitedPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("limited_price not between", value1, value2, "limitedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceIsNull() {
            addCriterion("reduced_price is null");
            return (Criteria) this;
        }

        public Criteria andReducedPriceIsNotNull() {
            addCriterion("reduced_price is not null");
            return (Criteria) this;
        }

        public Criteria andReducedPriceEqualTo(BigDecimal value) {
            addCriterion("reduced_price =", value, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceNotEqualTo(BigDecimal value) {
            addCriterion("reduced_price <>", value, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceGreaterThan(BigDecimal value) {
            addCriterion("reduced_price >", value, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("reduced_price >=", value, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceLessThan(BigDecimal value) {
            addCriterion("reduced_price <", value, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("reduced_price <=", value, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceIn(List<BigDecimal> values) {
            addCriterion("reduced_price in", values, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceNotIn(List<BigDecimal> values) {
            addCriterion("reduced_price not in", values, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reduced_price between", value1, value2, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andReducedPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reduced_price not between", value1, value2, "reducedPrice");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andValidTypeIsNull() {
            addCriterion("valid_type is null");
            return (Criteria) this;
        }

        public Criteria andValidTypeIsNotNull() {
            addCriterion("valid_type is not null");
            return (Criteria) this;
        }

        public Criteria andValidTypeEqualTo(Integer value) {
            addCriterion("valid_type =", value, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeNotEqualTo(Integer value) {
            addCriterion("valid_type <>", value, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeGreaterThan(Integer value) {
            addCriterion("valid_type >", value, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid_type >=", value, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeLessThan(Integer value) {
            addCriterion("valid_type <", value, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeLessThanOrEqualTo(Integer value) {
            addCriterion("valid_type <=", value, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeIn(List<Integer> values) {
            addCriterion("valid_type in", values, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeNotIn(List<Integer> values) {
            addCriterion("valid_type not in", values, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeBetween(Integer value1, Integer value2) {
            addCriterion("valid_type between", value1, value2, "validType");
            return (Criteria) this;
        }

        public Criteria andValidTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("valid_type not between", value1, value2, "validType");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeIsNull() {
            addCriterion("valid_start_time is null");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeIsNotNull() {
            addCriterion("valid_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeEqualTo(Date value) {
            addCriterion("valid_start_time =", value, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeNotEqualTo(Date value) {
            addCriterion("valid_start_time <>", value, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeGreaterThan(Date value) {
            addCriterion("valid_start_time >", value, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("valid_start_time >=", value, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeLessThan(Date value) {
            addCriterion("valid_start_time <", value, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("valid_start_time <=", value, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeIn(List<Date> values) {
            addCriterion("valid_start_time in", values, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeNotIn(List<Date> values) {
            addCriterion("valid_start_time not in", values, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeBetween(Date value1, Date value2) {
            addCriterion("valid_start_time between", value1, value2, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("valid_start_time not between", value1, value2, "validStartTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeIsNull() {
            addCriterion("valid_end_time is null");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeIsNotNull() {
            addCriterion("valid_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeEqualTo(Date value) {
            addCriterion("valid_end_time =", value, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeNotEqualTo(Date value) {
            addCriterion("valid_end_time <>", value, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeGreaterThan(Date value) {
            addCriterion("valid_end_time >", value, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("valid_end_time >=", value, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeLessThan(Date value) {
            addCriterion("valid_end_time <", value, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("valid_end_time <=", value, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeIn(List<Date> values) {
            addCriterion("valid_end_time in", values, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeNotIn(List<Date> values) {
            addCriterion("valid_end_time not in", values, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeBetween(Date value1, Date value2) {
            addCriterion("valid_end_time between", value1, value2, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("valid_end_time not between", value1, value2, "validEndTime");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNull() {
            addCriterion("valid_days is null");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNotNull() {
            addCriterion("valid_days is not null");
            return (Criteria) this;
        }

        public Criteria andValidDaysEqualTo(Integer value) {
            addCriterion("valid_days =", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotEqualTo(Integer value) {
            addCriterion("valid_days <>", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThan(Integer value) {
            addCriterion("valid_days >", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid_days >=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThan(Integer value) {
            addCriterion("valid_days <", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThanOrEqualTo(Integer value) {
            addCriterion("valid_days <=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysIn(List<Integer> values) {
            addCriterion("valid_days in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotIn(List<Integer> values) {
            addCriterion("valid_days not in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysBetween(Integer value1, Integer value2) {
            addCriterion("valid_days between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("valid_days not between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(Integer value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(Integer value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(Integer value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(Integer value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(Integer value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<Integer> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<Integer> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(Integer value1, Integer value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("source not between", value1, value2, "source");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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