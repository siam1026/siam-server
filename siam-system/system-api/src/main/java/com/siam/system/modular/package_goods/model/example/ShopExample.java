package com.siam.system.modular.package_goods.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShopExample() {
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

        public Criteria andMerchantIdIsNull() {
            addCriterion("merchant_id is null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIsNotNull() {
            addCriterion("merchant_id is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdEqualTo(Integer value) {
            addCriterion("merchant_id =", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotEqualTo(Integer value) {
            addCriterion("merchant_id <>", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThan(Integer value) {
            addCriterion("merchant_id >", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("merchant_id >=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThan(Integer value) {
            addCriterion("merchant_id <", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThanOrEqualTo(Integer value) {
            addCriterion("merchant_id <=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIn(List<Integer> values) {
            addCriterion("merchant_id in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotIn(List<Integer> values) {
            addCriterion("merchant_id not in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdBetween(Integer value1, Integer value2) {
            addCriterion("merchant_id between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("merchant_id not between", value1, value2, "merchantId");
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andStreetIsNull() {
            addCriterion("street is null");
            return (Criteria) this;
        }

        public Criteria andStreetIsNotNull() {
            addCriterion("street is not null");
            return (Criteria) this;
        }

        public Criteria andStreetEqualTo(String value) {
            addCriterion("street =", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotEqualTo(String value) {
            addCriterion("street <>", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThan(String value) {
            addCriterion("street >", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThanOrEqualTo(String value) {
            addCriterion("street >=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThan(String value) {
            addCriterion("street <", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThanOrEqualTo(String value) {
            addCriterion("street <=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLike(String value) {
            addCriterion("street like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotLike(String value) {
            addCriterion("street not like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetIn(List<String> values) {
            addCriterion("street in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotIn(List<String> values) {
            addCriterion("street not in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetBetween(String value1, String value2) {
            addCriterion("street between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotBetween(String value1, String value2) {
            addCriterion("street not between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andIsOperatingIsNull() {
            addCriterion("is_operating is null");
            return (Criteria) this;
        }

        public Criteria andIsOperatingIsNotNull() {
            addCriterion("is_operating is not null");
            return (Criteria) this;
        }

        public Criteria andIsOperatingEqualTo(Boolean value) {
            addCriterion("is_operating =", value, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingNotEqualTo(Boolean value) {
            addCriterion("is_operating <>", value, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingGreaterThan(Boolean value) {
            addCriterion("is_operating >", value, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_operating >=", value, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingLessThan(Boolean value) {
            addCriterion("is_operating <", value, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingLessThanOrEqualTo(Boolean value) {
            addCriterion("is_operating <=", value, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingIn(List<Boolean> values) {
            addCriterion("is_operating in", values, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingNotIn(List<Boolean> values) {
            addCriterion("is_operating not in", values, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingBetween(Boolean value1, Boolean value2) {
            addCriterion("is_operating between", value1, value2, "isOperating");
            return (Criteria) this;
        }

        public Criteria andIsOperatingNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_operating not between", value1, value2, "isOperating");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("start_time like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("start_time not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryIsNull() {
            addCriterion("manage_primary is null");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryIsNotNull() {
            addCriterion("manage_primary is not null");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryEqualTo(String value) {
            addCriterion("manage_primary =", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryNotEqualTo(String value) {
            addCriterion("manage_primary <>", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryGreaterThan(String value) {
            addCriterion("manage_primary >", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryGreaterThanOrEqualTo(String value) {
            addCriterion("manage_primary >=", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryLessThan(String value) {
            addCriterion("manage_primary <", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryLessThanOrEqualTo(String value) {
            addCriterion("manage_primary <=", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryLike(String value) {
            addCriterion("manage_primary like", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryNotLike(String value) {
            addCriterion("manage_primary not like", value, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryIn(List<String> values) {
            addCriterion("manage_primary in", values, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryNotIn(List<String> values) {
            addCriterion("manage_primary not in", values, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryBetween(String value1, String value2) {
            addCriterion("manage_primary between", value1, value2, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManagePrimaryNotBetween(String value1, String value2) {
            addCriterion("manage_primary not between", value1, value2, "managePrimary");
            return (Criteria) this;
        }

        public Criteria andManageMinorIsNull() {
            addCriterion("manage_minor is null");
            return (Criteria) this;
        }

        public Criteria andManageMinorIsNotNull() {
            addCriterion("manage_minor is not null");
            return (Criteria) this;
        }

        public Criteria andManageMinorEqualTo(String value) {
            addCriterion("manage_minor =", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorNotEqualTo(String value) {
            addCriterion("manage_minor <>", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorGreaterThan(String value) {
            addCriterion("manage_minor >", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorGreaterThanOrEqualTo(String value) {
            addCriterion("manage_minor >=", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorLessThan(String value) {
            addCriterion("manage_minor <", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorLessThanOrEqualTo(String value) {
            addCriterion("manage_minor <=", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorLike(String value) {
            addCriterion("manage_minor like", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorNotLike(String value) {
            addCriterion("manage_minor not like", value, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorIn(List<String> values) {
            addCriterion("manage_minor in", values, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorNotIn(List<String> values) {
            addCriterion("manage_minor not in", values, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorBetween(String value1, String value2) {
            addCriterion("manage_minor between", value1, value2, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andManageMinorNotBetween(String value1, String value2) {
            addCriterion("manage_minor not between", value1, value2, "manageMinor");
            return (Criteria) this;
        }

        public Criteria andShopImgIsNull() {
            addCriterion("shop_img is null");
            return (Criteria) this;
        }

        public Criteria andShopImgIsNotNull() {
            addCriterion("shop_img is not null");
            return (Criteria) this;
        }

        public Criteria andShopImgEqualTo(String value) {
            addCriterion("shop_img =", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgNotEqualTo(String value) {
            addCriterion("shop_img <>", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgGreaterThan(String value) {
            addCriterion("shop_img >", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgGreaterThanOrEqualTo(String value) {
            addCriterion("shop_img >=", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgLessThan(String value) {
            addCriterion("shop_img <", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgLessThanOrEqualTo(String value) {
            addCriterion("shop_img <=", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgLike(String value) {
            addCriterion("shop_img like", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgNotLike(String value) {
            addCriterion("shop_img not like", value, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgIn(List<String> values) {
            addCriterion("shop_img in", values, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgNotIn(List<String> values) {
            addCriterion("shop_img not in", values, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgBetween(String value1, String value2) {
            addCriterion("shop_img between", value1, value2, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopImgNotBetween(String value1, String value2) {
            addCriterion("shop_img not between", value1, value2, "shopImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgIsNull() {
            addCriterion("shop_within_img is null");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgIsNotNull() {
            addCriterion("shop_within_img is not null");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgEqualTo(String value) {
            addCriterion("shop_within_img =", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgNotEqualTo(String value) {
            addCriterion("shop_within_img <>", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgGreaterThan(String value) {
            addCriterion("shop_within_img >", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgGreaterThanOrEqualTo(String value) {
            addCriterion("shop_within_img >=", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgLessThan(String value) {
            addCriterion("shop_within_img <", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgLessThanOrEqualTo(String value) {
            addCriterion("shop_within_img <=", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgLike(String value) {
            addCriterion("shop_within_img like", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgNotLike(String value) {
            addCriterion("shop_within_img not like", value, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgIn(List<String> values) {
            addCriterion("shop_within_img in", values, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgNotIn(List<String> values) {
            addCriterion("shop_within_img not in", values, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgBetween(String value1, String value2) {
            addCriterion("shop_within_img between", value1, value2, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopWithinImgNotBetween(String value1, String value2) {
            addCriterion("shop_within_img not between", value1, value2, "shopWithinImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgIsNull() {
            addCriterion("shop_logo_img is null");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgIsNotNull() {
            addCriterion("shop_logo_img is not null");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgEqualTo(String value) {
            addCriterion("shop_logo_img =", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgNotEqualTo(String value) {
            addCriterion("shop_logo_img <>", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgGreaterThan(String value) {
            addCriterion("shop_logo_img >", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgGreaterThanOrEqualTo(String value) {
            addCriterion("shop_logo_img >=", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgLessThan(String value) {
            addCriterion("shop_logo_img <", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgLessThanOrEqualTo(String value) {
            addCriterion("shop_logo_img <=", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgLike(String value) {
            addCriterion("shop_logo_img like", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgNotLike(String value) {
            addCriterion("shop_logo_img not like", value, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgIn(List<String> values) {
            addCriterion("shop_logo_img in", values, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgNotIn(List<String> values) {
            addCriterion("shop_logo_img not in", values, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgBetween(String value1, String value2) {
            addCriterion("shop_logo_img between", value1, value2, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andShopLogoImgNotBetween(String value1, String value2) {
            addCriterion("shop_logo_img not between", value1, value2, "shopLogoImg");
            return (Criteria) this;
        }

        public Criteria andCertificateType1IsNull() {
            addCriterion("certificate_type1 is null");
            return (Criteria) this;
        }

        public Criteria andCertificateType1IsNotNull() {
            addCriterion("certificate_type1 is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateType1EqualTo(String value) {
            addCriterion("certificate_type1 =", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1NotEqualTo(String value) {
            addCriterion("certificate_type1 <>", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1GreaterThan(String value) {
            addCriterion("certificate_type1 >", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1GreaterThanOrEqualTo(String value) {
            addCriterion("certificate_type1 >=", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1LessThan(String value) {
            addCriterion("certificate_type1 <", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1LessThanOrEqualTo(String value) {
            addCriterion("certificate_type1 <=", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1Like(String value) {
            addCriterion("certificate_type1 like", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1NotLike(String value) {
            addCriterion("certificate_type1 not like", value, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1In(List<String> values) {
            addCriterion("certificate_type1 in", values, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1NotIn(List<String> values) {
            addCriterion("certificate_type1 not in", values, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1Between(String value1, String value2) {
            addCriterion("certificate_type1 between", value1, value2, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateType1NotBetween(String value1, String value2) {
            addCriterion("certificate_type1 not between", value1, value2, "certificateType1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1IsNull() {
            addCriterion("certificate_img1 is null");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1IsNotNull() {
            addCriterion("certificate_img1 is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1EqualTo(String value) {
            addCriterion("certificate_img1 =", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1NotEqualTo(String value) {
            addCriterion("certificate_img1 <>", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1GreaterThan(String value) {
            addCriterion("certificate_img1 >", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1GreaterThanOrEqualTo(String value) {
            addCriterion("certificate_img1 >=", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1LessThan(String value) {
            addCriterion("certificate_img1 <", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1LessThanOrEqualTo(String value) {
            addCriterion("certificate_img1 <=", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1Like(String value) {
            addCriterion("certificate_img1 like", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1NotLike(String value) {
            addCriterion("certificate_img1 not like", value, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1In(List<String> values) {
            addCriterion("certificate_img1 in", values, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1NotIn(List<String> values) {
            addCriterion("certificate_img1 not in", values, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1Between(String value1, String value2) {
            addCriterion("certificate_img1 between", value1, value2, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateImg1NotBetween(String value1, String value2) {
            addCriterion("certificate_img1 not between", value1, value2, "certificateImg1");
            return (Criteria) this;
        }

        public Criteria andCertificateType2IsNull() {
            addCriterion("certificate_type2 is null");
            return (Criteria) this;
        }

        public Criteria andCertificateType2IsNotNull() {
            addCriterion("certificate_type2 is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateType2EqualTo(String value) {
            addCriterion("certificate_type2 =", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2NotEqualTo(String value) {
            addCriterion("certificate_type2 <>", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2GreaterThan(String value) {
            addCriterion("certificate_type2 >", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2GreaterThanOrEqualTo(String value) {
            addCriterion("certificate_type2 >=", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2LessThan(String value) {
            addCriterion("certificate_type2 <", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2LessThanOrEqualTo(String value) {
            addCriterion("certificate_type2 <=", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2Like(String value) {
            addCriterion("certificate_type2 like", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2NotLike(String value) {
            addCriterion("certificate_type2 not like", value, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2In(List<String> values) {
            addCriterion("certificate_type2 in", values, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2NotIn(List<String> values) {
            addCriterion("certificate_type2 not in", values, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2Between(String value1, String value2) {
            addCriterion("certificate_type2 between", value1, value2, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateType2NotBetween(String value1, String value2) {
            addCriterion("certificate_type2 not between", value1, value2, "certificateType2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2IsNull() {
            addCriterion("certificate_img2 is null");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2IsNotNull() {
            addCriterion("certificate_img2 is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2EqualTo(String value) {
            addCriterion("certificate_img2 =", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2NotEqualTo(String value) {
            addCriterion("certificate_img2 <>", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2GreaterThan(String value) {
            addCriterion("certificate_img2 >", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2GreaterThanOrEqualTo(String value) {
            addCriterion("certificate_img2 >=", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2LessThan(String value) {
            addCriterion("certificate_img2 <", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2LessThanOrEqualTo(String value) {
            addCriterion("certificate_img2 <=", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2Like(String value) {
            addCriterion("certificate_img2 like", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2NotLike(String value) {
            addCriterion("certificate_img2 not like", value, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2In(List<String> values) {
            addCriterion("certificate_img2 in", values, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2NotIn(List<String> values) {
            addCriterion("certificate_img2 not in", values, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2Between(String value1, String value2) {
            addCriterion("certificate_img2 between", value1, value2, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andCertificateImg2NotBetween(String value1, String value2) {
            addCriterion("certificate_img2 not between", value1, value2, "certificateImg2");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeIsNull() {
            addCriterion("special_type is null");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeIsNotNull() {
            addCriterion("special_type is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeEqualTo(String value) {
            addCriterion("special_type =", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeNotEqualTo(String value) {
            addCriterion("special_type <>", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeGreaterThan(String value) {
            addCriterion("special_type >", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeGreaterThanOrEqualTo(String value) {
            addCriterion("special_type >=", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeLessThan(String value) {
            addCriterion("special_type <", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeLessThanOrEqualTo(String value) {
            addCriterion("special_type <=", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeLike(String value) {
            addCriterion("special_type like", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeNotLike(String value) {
            addCriterion("special_type not like", value, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeIn(List<String> values) {
            addCriterion("special_type in", values, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeNotIn(List<String> values) {
            addCriterion("special_type not in", values, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeBetween(String value1, String value2) {
            addCriterion("special_type between", value1, value2, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialTypeNotBetween(String value1, String value2) {
            addCriterion("special_type not between", value1, value2, "specialType");
            return (Criteria) this;
        }

        public Criteria andSpecialImgIsNull() {
            addCriterion("special_img is null");
            return (Criteria) this;
        }

        public Criteria andSpecialImgIsNotNull() {
            addCriterion("special_img is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialImgEqualTo(String value) {
            addCriterion("special_img =", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgNotEqualTo(String value) {
            addCriterion("special_img <>", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgGreaterThan(String value) {
            addCriterion("special_img >", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgGreaterThanOrEqualTo(String value) {
            addCriterion("special_img >=", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgLessThan(String value) {
            addCriterion("special_img <", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgLessThanOrEqualTo(String value) {
            addCriterion("special_img <=", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgLike(String value) {
            addCriterion("special_img like", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgNotLike(String value) {
            addCriterion("special_img not like", value, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgIn(List<String> values) {
            addCriterion("special_img in", values, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgNotIn(List<String> values) {
            addCriterion("special_img not in", values, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgBetween(String value1, String value2) {
            addCriterion("special_img between", value1, value2, "specialImg");
            return (Criteria) this;
        }

        public Criteria andSpecialImgNotBetween(String value1, String value2) {
            addCriterion("special_img not between", value1, value2, "specialImg");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNull() {
            addCriterion("audit_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNotNull() {
            addCriterion("audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusEqualTo(Integer value) {
            addCriterion("audit_status =", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotEqualTo(Integer value) {
            addCriterion("audit_status <>", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThan(Integer value) {
            addCriterion("audit_status >", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_status >=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThan(Integer value) {
            addCriterion("audit_status <", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("audit_status <=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIn(List<Integer> values) {
            addCriterion("audit_status in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotIn(List<Integer> values) {
            addCriterion("audit_status not in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusBetween(Integer value1, Integer value2) {
            addCriterion("audit_status between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_status not between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditReasonIsNull() {
            addCriterion("audit_reason is null");
            return (Criteria) this;
        }

        public Criteria andAuditReasonIsNotNull() {
            addCriterion("audit_reason is not null");
            return (Criteria) this;
        }

        public Criteria andAuditReasonEqualTo(String value) {
            addCriterion("audit_reason =", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonNotEqualTo(String value) {
            addCriterion("audit_reason <>", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonGreaterThan(String value) {
            addCriterion("audit_reason >", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonGreaterThanOrEqualTo(String value) {
            addCriterion("audit_reason >=", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonLessThan(String value) {
            addCriterion("audit_reason <", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonLessThanOrEqualTo(String value) {
            addCriterion("audit_reason <=", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonLike(String value) {
            addCriterion("audit_reason like", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonNotLike(String value) {
            addCriterion("audit_reason not like", value, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonIn(List<String> values) {
            addCriterion("audit_reason in", values, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonNotIn(List<String> values) {
            addCriterion("audit_reason not in", values, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonBetween(String value1, String value2) {
            addCriterion("audit_reason between", value1, value2, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditReasonNotBetween(String value1, String value2) {
            addCriterion("audit_reason not between", value1, value2, "auditReason");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNull() {
            addCriterion("audit_time is null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNotNull() {
            addCriterion("audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeEqualTo(Date value) {
            addCriterion("audit_time =", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotEqualTo(Date value) {
            addCriterion("audit_time <>", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThan(Date value) {
            addCriterion("audit_time >", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("audit_time >=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThan(Date value) {
            addCriterion("audit_time <", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThanOrEqualTo(Date value) {
            addCriterion("audit_time <=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIn(List<Date> values) {
            addCriterion("audit_time in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotIn(List<Date> values) {
            addCriterion("audit_time not in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeBetween(Date value1, Date value2) {
            addCriterion("audit_time between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotBetween(Date value1, Date value2) {
            addCriterion("audit_time not between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneIsNull() {
            addCriterion("take_out_phone is null");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneIsNotNull() {
            addCriterion("take_out_phone is not null");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneEqualTo(String value) {
            addCriterion("take_out_phone =", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneNotEqualTo(String value) {
            addCriterion("take_out_phone <>", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneGreaterThan(String value) {
            addCriterion("take_out_phone >", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("take_out_phone >=", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneLessThan(String value) {
            addCriterion("take_out_phone <", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneLessThanOrEqualTo(String value) {
            addCriterion("take_out_phone <=", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneLike(String value) {
            addCriterion("take_out_phone like", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneNotLike(String value) {
            addCriterion("take_out_phone not like", value, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneIn(List<String> values) {
            addCriterion("take_out_phone in", values, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneNotIn(List<String> values) {
            addCriterion("take_out_phone not in", values, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneBetween(String value1, String value2) {
            addCriterion("take_out_phone between", value1, value2, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andTakeOutPhoneNotBetween(String value1, String value2) {
            addCriterion("take_out_phone not between", value1, value2, "takeOutPhone");
            return (Criteria) this;
        }

        public Criteria andContactRealnameIsNull() {
            addCriterion("contact_realname is null");
            return (Criteria) this;
        }

        public Criteria andContactRealnameIsNotNull() {
            addCriterion("contact_realname is not null");
            return (Criteria) this;
        }

        public Criteria andContactRealnameEqualTo(String value) {
            addCriterion("contact_realname =", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameNotEqualTo(String value) {
            addCriterion("contact_realname <>", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameGreaterThan(String value) {
            addCriterion("contact_realname >", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("contact_realname >=", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameLessThan(String value) {
            addCriterion("contact_realname <", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameLessThanOrEqualTo(String value) {
            addCriterion("contact_realname <=", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameLike(String value) {
            addCriterion("contact_realname like", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameNotLike(String value) {
            addCriterion("contact_realname not like", value, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameIn(List<String> values) {
            addCriterion("contact_realname in", values, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameNotIn(List<String> values) {
            addCriterion("contact_realname not in", values, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameBetween(String value1, String value2) {
            addCriterion("contact_realname between", value1, value2, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactRealnameNotBetween(String value1, String value2) {
            addCriterion("contact_realname not between", value1, value2, "contactRealname");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNull() {
            addCriterion("contact_phone is null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNotNull() {
            addCriterion("contact_phone is not null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneEqualTo(String value) {
            addCriterion("contact_phone =", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotEqualTo(String value) {
            addCriterion("contact_phone <>", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThan(String value) {
            addCriterion("contact_phone >", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("contact_phone >=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThan(String value) {
            addCriterion("contact_phone <", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("contact_phone <=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLike(String value) {
            addCriterion("contact_phone like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotLike(String value) {
            addCriterion("contact_phone not like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIn(List<String> values) {
            addCriterion("contact_phone in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotIn(List<String> values) {
            addCriterion("contact_phone not in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneBetween(String value1, String value2) {
            addCriterion("contact_phone between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotBetween(String value1, String value2) {
            addCriterion("contact_phone not between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIsNull() {
            addCriterion("announcement is null");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIsNotNull() {
            addCriterion("announcement is not null");
            return (Criteria) this;
        }

        public Criteria andAnnouncementEqualTo(String value) {
            addCriterion("announcement =", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementNotEqualTo(String value) {
            addCriterion("announcement <>", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementGreaterThan(String value) {
            addCriterion("announcement >", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementGreaterThanOrEqualTo(String value) {
            addCriterion("announcement >=", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementLessThan(String value) {
            addCriterion("announcement <", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementLessThanOrEqualTo(String value) {
            addCriterion("announcement <=", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementLike(String value) {
            addCriterion("announcement like", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementNotLike(String value) {
            addCriterion("announcement not like", value, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIn(List<String> values) {
            addCriterion("announcement in", values, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementNotIn(List<String> values) {
            addCriterion("announcement not in", values, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementBetween(String value1, String value2) {
            addCriterion("announcement between", value1, value2, "announcement");
            return (Criteria) this;
        }

        public Criteria andAnnouncementNotBetween(String value1, String value2) {
            addCriterion("announcement not between", value1, value2, "announcement");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceIsNull() {
            addCriterion("start_delivery_price is null");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceIsNotNull() {
            addCriterion("start_delivery_price is not null");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceEqualTo(BigDecimal value) {
            addCriterion("start_delivery_price =", value, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceNotEqualTo(BigDecimal value) {
            addCriterion("start_delivery_price <>", value, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceGreaterThan(BigDecimal value) {
            addCriterion("start_delivery_price >", value, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("start_delivery_price >=", value, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceLessThan(BigDecimal value) {
            addCriterion("start_delivery_price <", value, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("start_delivery_price <=", value, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceIn(List<BigDecimal> values) {
            addCriterion("start_delivery_price in", values, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceNotIn(List<BigDecimal> values) {
            addCriterion("start_delivery_price not in", values, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("start_delivery_price between", value1, value2, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andStartDeliveryPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("start_delivery_price not between", value1, value2, "startDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceIsNull() {
            addCriterion("delivery_starting_price is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceIsNotNull() {
            addCriterion("delivery_starting_price is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_price =", value, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceNotEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_price <>", value, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceGreaterThan(BigDecimal value) {
            addCriterion("delivery_starting_price >", value, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_price >=", value, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceLessThan(BigDecimal value) {
            addCriterion("delivery_starting_price <", value, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_price <=", value, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceIn(List<BigDecimal> values) {
            addCriterion("delivery_starting_price in", values, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceNotIn(List<BigDecimal> values) {
            addCriterion("delivery_starting_price not in", values, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_starting_price between", value1, value2, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_starting_price not between", value1, value2, "deliveryStartingPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceIsNull() {
            addCriterion("delivery_kilometer_price is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceIsNotNull() {
            addCriterion("delivery_kilometer_price is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceEqualTo(BigDecimal value) {
            addCriterion("delivery_kilometer_price =", value, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceNotEqualTo(BigDecimal value) {
            addCriterion("delivery_kilometer_price <>", value, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceGreaterThan(BigDecimal value) {
            addCriterion("delivery_kilometer_price >", value, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_kilometer_price >=", value, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceLessThan(BigDecimal value) {
            addCriterion("delivery_kilometer_price <", value, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_kilometer_price <=", value, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceIn(List<BigDecimal> values) {
            addCriterion("delivery_kilometer_price in", values, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceNotIn(List<BigDecimal> values) {
            addCriterion("delivery_kilometer_price not in", values, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_kilometer_price between", value1, value2, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryKilometerPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_kilometer_price not between", value1, value2, "deliveryKilometerPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitIsNull() {
            addCriterion("delivery_distance_limit is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitIsNotNull() {
            addCriterion("delivery_distance_limit is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitEqualTo(BigDecimal value) {
            addCriterion("delivery_distance_limit =", value, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitNotEqualTo(BigDecimal value) {
            addCriterion("delivery_distance_limit <>", value, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitGreaterThan(BigDecimal value) {
            addCriterion("delivery_distance_limit >", value, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_distance_limit >=", value, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitLessThan(BigDecimal value) {
            addCriterion("delivery_distance_limit <", value, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_distance_limit <=", value, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitIn(List<BigDecimal> values) {
            addCriterion("delivery_distance_limit in", values, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitNotIn(List<BigDecimal> values) {
            addCriterion("delivery_distance_limit not in", values, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_distance_limit between", value1, value2, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andDeliveryDistanceLimitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_distance_limit not between", value1, value2, "deliveryDistanceLimit");
            return (Criteria) this;
        }

        public Criteria andServiceRatingIsNull() {
            addCriterion("service_rating is null");
            return (Criteria) this;
        }

        public Criteria andServiceRatingIsNotNull() {
            addCriterion("service_rating is not null");
            return (Criteria) this;
        }

        public Criteria andServiceRatingEqualTo(BigDecimal value) {
            addCriterion("service_rating =", value, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingNotEqualTo(BigDecimal value) {
            addCriterion("service_rating <>", value, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingGreaterThan(BigDecimal value) {
            addCriterion("service_rating >", value, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("service_rating >=", value, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingLessThan(BigDecimal value) {
            addCriterion("service_rating <", value, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingLessThanOrEqualTo(BigDecimal value) {
            addCriterion("service_rating <=", value, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingIn(List<BigDecimal> values) {
            addCriterion("service_rating in", values, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingNotIn(List<BigDecimal> values) {
            addCriterion("service_rating not in", values, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_rating between", value1, value2, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andServiceRatingNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("service_rating not between", value1, value2, "serviceRating");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseIsNull() {
            addCriterion("business_license is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseIsNotNull() {
            addCriterion("business_license is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEqualTo(String value) {
            addCriterion("business_license =", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotEqualTo(String value) {
            addCriterion("business_license <>", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseGreaterThan(String value) {
            addCriterion("business_license >", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseGreaterThanOrEqualTo(String value) {
            addCriterion("business_license >=", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseLessThan(String value) {
            addCriterion("business_license <", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseLessThanOrEqualTo(String value) {
            addCriterion("business_license <=", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseLike(String value) {
            addCriterion("business_license like", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotLike(String value) {
            addCriterion("business_license not like", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseIn(List<String> values) {
            addCriterion("business_license in", values, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotIn(List<String> values) {
            addCriterion("business_license not in", values, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseBetween(String value1, String value2) {
            addCriterion("business_license between", value1, value2, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotBetween(String value1, String value2) {
            addCriterion("business_license not between", value1, value2, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideIsNull() {
            addCriterion("id_card_front_side is null");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideIsNotNull() {
            addCriterion("id_card_front_side is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideEqualTo(String value) {
            addCriterion("id_card_front_side =", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideNotEqualTo(String value) {
            addCriterion("id_card_front_side <>", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideGreaterThan(String value) {
            addCriterion("id_card_front_side >", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_front_side >=", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideLessThan(String value) {
            addCriterion("id_card_front_side <", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideLessThanOrEqualTo(String value) {
            addCriterion("id_card_front_side <=", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideLike(String value) {
            addCriterion("id_card_front_side like", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideNotLike(String value) {
            addCriterion("id_card_front_side not like", value, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideIn(List<String> values) {
            addCriterion("id_card_front_side in", values, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideNotIn(List<String> values) {
            addCriterion("id_card_front_side not in", values, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideBetween(String value1, String value2) {
            addCriterion("id_card_front_side between", value1, value2, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontSideNotBetween(String value1, String value2) {
            addCriterion("id_card_front_side not between", value1, value2, "idCardFrontSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideIsNull() {
            addCriterion("id_card_back_side is null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideIsNotNull() {
            addCriterion("id_card_back_side is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideEqualTo(String value) {
            addCriterion("id_card_back_side =", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideNotEqualTo(String value) {
            addCriterion("id_card_back_side <>", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideGreaterThan(String value) {
            addCriterion("id_card_back_side >", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_back_side >=", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideLessThan(String value) {
            addCriterion("id_card_back_side <", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideLessThanOrEqualTo(String value) {
            addCriterion("id_card_back_side <=", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideLike(String value) {
            addCriterion("id_card_back_side like", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideNotLike(String value) {
            addCriterion("id_card_back_side not like", value, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideIn(List<String> values) {
            addCriterion("id_card_back_side in", values, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideNotIn(List<String> values) {
            addCriterion("id_card_back_side not in", values, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideBetween(String value1, String value2) {
            addCriterion("id_card_back_side between", value1, value2, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andIdCardBackSideNotBetween(String value1, String value2) {
            addCriterion("id_card_back_side not between", value1, value2, "idCardBackSide");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceIsNull() {
            addCriterion("reduced_delivery_price is null");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceIsNotNull() {
            addCriterion("reduced_delivery_price is not null");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceEqualTo(BigDecimal value) {
            addCriterion("reduced_delivery_price =", value, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceNotEqualTo(BigDecimal value) {
            addCriterion("reduced_delivery_price <>", value, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceGreaterThan(BigDecimal value) {
            addCriterion("reduced_delivery_price >", value, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("reduced_delivery_price >=", value, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceLessThan(BigDecimal value) {
            addCriterion("reduced_delivery_price <", value, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("reduced_delivery_price <=", value, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceIn(List<BigDecimal> values) {
            addCriterion("reduced_delivery_price in", values, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceNotIn(List<BigDecimal> values) {
            addCriterion("reduced_delivery_price not in", values, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reduced_delivery_price between", value1, value2, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andReducedDeliveryPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reduced_delivery_price not between", value1, value2, "reducedDeliveryPrice");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioIsNull() {
            addCriterion("is_open_order_audio is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioIsNotNull() {
            addCriterion("is_open_order_audio is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioEqualTo(Boolean value) {
            addCriterion("is_open_order_audio =", value, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioNotEqualTo(Boolean value) {
            addCriterion("is_open_order_audio <>", value, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioGreaterThan(Boolean value) {
            addCriterion("is_open_order_audio >", value, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_open_order_audio >=", value, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioLessThan(Boolean value) {
            addCriterion("is_open_order_audio <", value, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioLessThanOrEqualTo(Boolean value) {
            addCriterion("is_open_order_audio <=", value, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioIn(List<Boolean> values) {
            addCriterion("is_open_order_audio in", values, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioNotIn(List<Boolean> values) {
            addCriterion("is_open_order_audio not in", values, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioBetween(Boolean value1, Boolean value2) {
            addCriterion("is_open_order_audio between", value1, value2, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenOrderAudioNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_open_order_audio not between", value1, value2, "isOpenOrderAudio");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintIsNull() {
            addCriterion("is_open_local_print is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintIsNotNull() {
            addCriterion("is_open_local_print is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintEqualTo(Boolean value) {
            addCriterion("is_open_local_print =", value, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintNotEqualTo(Boolean value) {
            addCriterion("is_open_local_print <>", value, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintGreaterThan(Boolean value) {
            addCriterion("is_open_local_print >", value, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_open_local_print >=", value, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintLessThan(Boolean value) {
            addCriterion("is_open_local_print <", value, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintLessThanOrEqualTo(Boolean value) {
            addCriterion("is_open_local_print <=", value, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintIn(List<Boolean> values) {
            addCriterion("is_open_local_print in", values, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintNotIn(List<Boolean> values) {
            addCriterion("is_open_local_print not in", values, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintBetween(Boolean value1, Boolean value2) {
            addCriterion("is_open_local_print between", value1, value2, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenLocalPrintNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_open_local_print not between", value1, value2, "isOpenLocalPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintIsNull() {
            addCriterion("is_open_cloud_print is null");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintIsNotNull() {
            addCriterion("is_open_cloud_print is not null");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintEqualTo(Boolean value) {
            addCriterion("is_open_cloud_print =", value, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintNotEqualTo(Boolean value) {
            addCriterion("is_open_cloud_print <>", value, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintGreaterThan(Boolean value) {
            addCriterion("is_open_cloud_print >", value, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_open_cloud_print >=", value, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintLessThan(Boolean value) {
            addCriterion("is_open_cloud_print <", value, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintLessThanOrEqualTo(Boolean value) {
            addCriterion("is_open_cloud_print <=", value, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintIn(List<Boolean> values) {
            addCriterion("is_open_cloud_print in", values, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintNotIn(List<Boolean> values) {
            addCriterion("is_open_cloud_print not in", values, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintBetween(Boolean value1, Boolean value2) {
            addCriterion("is_open_cloud_print between", value1, value2, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andIsOpenCloudPrintNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_open_cloud_print not between", value1, value2, "isOpenCloudPrint");
            return (Criteria) this;
        }

        public Criteria andFirstPosterIsNull() {
            addCriterion("first_poster is null");
            return (Criteria) this;
        }

        public Criteria andFirstPosterIsNotNull() {
            addCriterion("first_poster is not null");
            return (Criteria) this;
        }

        public Criteria andFirstPosterEqualTo(String value) {
            addCriterion("first_poster =", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterNotEqualTo(String value) {
            addCriterion("first_poster <>", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterGreaterThan(String value) {
            addCriterion("first_poster >", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterGreaterThanOrEqualTo(String value) {
            addCriterion("first_poster >=", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterLessThan(String value) {
            addCriterion("first_poster <", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterLessThanOrEqualTo(String value) {
            addCriterion("first_poster <=", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterLike(String value) {
            addCriterion("first_poster like", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterNotLike(String value) {
            addCriterion("first_poster not like", value, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterIn(List<String> values) {
            addCriterion("first_poster in", values, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterNotIn(List<String> values) {
            addCriterion("first_poster not in", values, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterBetween(String value1, String value2) {
            addCriterion("first_poster between", value1, value2, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andFirstPosterNotBetween(String value1, String value2) {
            addCriterion("first_poster not between", value1, value2, "firstPoster");
            return (Criteria) this;
        }

        public Criteria andHouseNumberIsNull() {
            addCriterion("house_number is null");
            return (Criteria) this;
        }

        public Criteria andHouseNumberIsNotNull() {
            addCriterion("house_number is not null");
            return (Criteria) this;
        }

        public Criteria andHouseNumberEqualTo(String value) {
            addCriterion("house_number =", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberNotEqualTo(String value) {
            addCriterion("house_number <>", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberGreaterThan(String value) {
            addCriterion("house_number >", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberGreaterThanOrEqualTo(String value) {
            addCriterion("house_number >=", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberLessThan(String value) {
            addCriterion("house_number <", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberLessThanOrEqualTo(String value) {
            addCriterion("house_number <=", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberLike(String value) {
            addCriterion("house_number like", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberNotLike(String value) {
            addCriterion("house_number not like", value, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberIn(List<String> values) {
            addCriterion("house_number in", values, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberNotIn(List<String> values) {
            addCriterion("house_number not in", values, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberBetween(String value1, String value2) {
            addCriterion("house_number between", value1, value2, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andHouseNumberNotBetween(String value1, String value2) {
            addCriterion("house_number not between", value1, value2, "houseNumber");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(BigDecimal value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(BigDecimal value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(BigDecimal value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<BigDecimal> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(BigDecimal value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(BigDecimal value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(BigDecimal value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<BigDecimal> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
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