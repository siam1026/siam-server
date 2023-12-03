package com.siam.system.modular.package_goods.model.example.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrinterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PrinterExample() {
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

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(Integer value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(Integer value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(Integer value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(Integer value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(Integer value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<Integer> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<Integer> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(Integer value1, Integer value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
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

        public Criteria andNumberIsNull() {
            addCriterion("number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(String value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(String value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(String value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(String value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(String value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(String value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLike(String value) {
            addCriterion("number like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotLike(String value) {
            addCriterion("number not like", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<String> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<String> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(String value1, String value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(String value1, String value2) {
            addCriterion("number not between", value1, value2, "number");
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

        public Criteria andIsAutoPrintIsNull() {
            addCriterion("is_auto_print is null");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintIsNotNull() {
            addCriterion("is_auto_print is not null");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintEqualTo(Boolean value) {
            addCriterion("is_auto_print =", value, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintNotEqualTo(Boolean value) {
            addCriterion("is_auto_print <>", value, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintGreaterThan(Boolean value) {
            addCriterion("is_auto_print >", value, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_auto_print >=", value, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintLessThan(Boolean value) {
            addCriterion("is_auto_print <", value, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintLessThanOrEqualTo(Boolean value) {
            addCriterion("is_auto_print <=", value, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintIn(List<Boolean> values) {
            addCriterion("is_auto_print in", values, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintNotIn(List<Boolean> values) {
            addCriterion("is_auto_print not in", values, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintBetween(Boolean value1, Boolean value2) {
            addCriterion("is_auto_print between", value1, value2, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andIsAutoPrintNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_auto_print not between", value1, value2, "isAutoPrint");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberIsNull() {
            addCriterion("mobile_card_number is null");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberIsNotNull() {
            addCriterion("mobile_card_number is not null");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberEqualTo(String value) {
            addCriterion("mobile_card_number =", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberNotEqualTo(String value) {
            addCriterion("mobile_card_number <>", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberGreaterThan(String value) {
            addCriterion("mobile_card_number >", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_card_number >=", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberLessThan(String value) {
            addCriterion("mobile_card_number <", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberLessThanOrEqualTo(String value) {
            addCriterion("mobile_card_number <=", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberLike(String value) {
            addCriterion("mobile_card_number like", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberNotLike(String value) {
            addCriterion("mobile_card_number not like", value, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberIn(List<String> values) {
            addCriterion("mobile_card_number in", values, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberNotIn(List<String> values) {
            addCriterion("mobile_card_number not in", values, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberBetween(String value1, String value2) {
            addCriterion("mobile_card_number between", value1, value2, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andMobileCardNumberNotBetween(String value1, String value2) {
            addCriterion("mobile_card_number not between", value1, value2, "mobileCardNumber");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusIsNull() {
            addCriterion("cloud_registration_status is null");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusIsNotNull() {
            addCriterion("cloud_registration_status is not null");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusEqualTo(String value) {
            addCriterion("cloud_registration_status =", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusNotEqualTo(String value) {
            addCriterion("cloud_registration_status <>", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusGreaterThan(String value) {
            addCriterion("cloud_registration_status >", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_registration_status >=", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusLessThan(String value) {
            addCriterion("cloud_registration_status <", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusLessThanOrEqualTo(String value) {
            addCriterion("cloud_registration_status <=", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusLike(String value) {
            addCriterion("cloud_registration_status like", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusNotLike(String value) {
            addCriterion("cloud_registration_status not like", value, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusIn(List<String> values) {
            addCriterion("cloud_registration_status in", values, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusNotIn(List<String> values) {
            addCriterion("cloud_registration_status not in", values, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusBetween(String value1, String value2) {
            addCriterion("cloud_registration_status between", value1, value2, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andCloudRegistrationStatusNotBetween(String value1, String value2) {
            addCriterion("cloud_registration_status not between", value1, value2, "cloudRegistrationStatus");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andBrandIsNull() {
            addCriterion("brand is null");
            return (Criteria) this;
        }

        public Criteria andBrandIsNotNull() {
            addCriterion("brand is not null");
            return (Criteria) this;
        }

        public Criteria andBrandEqualTo(Integer value) {
            addCriterion("brand =", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotEqualTo(Integer value) {
            addCriterion("brand <>", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThan(Integer value) {
            addCriterion("brand >", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThanOrEqualTo(Integer value) {
            addCriterion("brand >=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThan(Integer value) {
            addCriterion("brand <", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThanOrEqualTo(Integer value) {
            addCriterion("brand <=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandIn(List<Integer> values) {
            addCriterion("brand in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotIn(List<Integer> values) {
            addCriterion("brand not in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandBetween(Integer value1, Integer value2) {
            addCriterion("brand between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotBetween(Integer value1, Integer value2) {
            addCriterion("brand not between", value1, value2, "brand");
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