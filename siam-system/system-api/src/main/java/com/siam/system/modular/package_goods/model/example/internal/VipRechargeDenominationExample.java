package com.siam.system.modular.package_goods.model.example.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VipRechargeDenominationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VipRechargeDenominationExample() {
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

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andIsSaleIsNull() {
            addCriterion("is_sale is null");
            return (Criteria) this;
        }

        public Criteria andIsSaleIsNotNull() {
            addCriterion("is_sale is not null");
            return (Criteria) this;
        }

        public Criteria andIsSaleEqualTo(Boolean value) {
            addCriterion("is_sale =", value, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleNotEqualTo(Boolean value) {
            addCriterion("is_sale <>", value, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleGreaterThan(Boolean value) {
            addCriterion("is_sale >", value, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_sale >=", value, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleLessThan(Boolean value) {
            addCriterion("is_sale <", value, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_sale <=", value, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleIn(List<Boolean> values) {
            addCriterion("is_sale in", values, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleNotIn(List<Boolean> values) {
            addCriterion("is_sale not in", values, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_sale between", value1, value2, "isSale");
            return (Criteria) this;
        }

        public Criteria andIsSaleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_sale not between", value1, value2, "isSale");
            return (Criteria) this;
        }

        public Criteria andSalePriceIsNull() {
            addCriterion("sale_price is null");
            return (Criteria) this;
        }

        public Criteria andSalePriceIsNotNull() {
            addCriterion("sale_price is not null");
            return (Criteria) this;
        }

        public Criteria andSalePriceEqualTo(BigDecimal value) {
            addCriterion("sale_price =", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotEqualTo(BigDecimal value) {
            addCriterion("sale_price <>", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceGreaterThan(BigDecimal value) {
            addCriterion("sale_price >", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sale_price >=", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceLessThan(BigDecimal value) {
            addCriterion("sale_price <", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sale_price <=", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceIn(List<BigDecimal> values) {
            addCriterion("sale_price in", values, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotIn(List<BigDecimal> values) {
            addCriterion("sale_price not in", values, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sale_price between", value1, value2, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sale_price not between", value1, value2, "salePrice");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionIsNull() {
            addCriterion("brief_description is null");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionIsNotNull() {
            addCriterion("brief_description is not null");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionEqualTo(String value) {
            addCriterion("brief_description =", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionNotEqualTo(String value) {
            addCriterion("brief_description <>", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionGreaterThan(String value) {
            addCriterion("brief_description >", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("brief_description >=", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionLessThan(String value) {
            addCriterion("brief_description <", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionLessThanOrEqualTo(String value) {
            addCriterion("brief_description <=", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionLike(String value) {
            addCriterion("brief_description like", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionNotLike(String value) {
            addCriterion("brief_description not like", value, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionIn(List<String> values) {
            addCriterion("brief_description in", values, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionNotIn(List<String> values) {
            addCriterion("brief_description not in", values, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionBetween(String value1, String value2) {
            addCriterion("brief_description between", value1, value2, "briefDescription");
            return (Criteria) this;
        }

        public Criteria andBriefDescriptionNotBetween(String value1, String value2) {
            addCriterion("brief_description not between", value1, value2, "briefDescription");
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

        public Criteria andIsGiveBalanceIsNull() {
            addCriterion("is_give_balance is null");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceIsNotNull() {
            addCriterion("is_give_balance is not null");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceEqualTo(Boolean value) {
            addCriterion("is_give_balance =", value, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceNotEqualTo(Boolean value) {
            addCriterion("is_give_balance <>", value, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceGreaterThan(Boolean value) {
            addCriterion("is_give_balance >", value, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_give_balance >=", value, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceLessThan(Boolean value) {
            addCriterion("is_give_balance <", value, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceLessThanOrEqualTo(Boolean value) {
            addCriterion("is_give_balance <=", value, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceIn(List<Boolean> values) {
            addCriterion("is_give_balance in", values, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceNotIn(List<Boolean> values) {
            addCriterion("is_give_balance not in", values, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceBetween(Boolean value1, Boolean value2) {
            addCriterion("is_give_balance between", value1, value2, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveBalanceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_give_balance not between", value1, value2, "isGiveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceIsNull() {
            addCriterion("give_balance is null");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceIsNotNull() {
            addCriterion("give_balance is not null");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceEqualTo(BigDecimal value) {
            addCriterion("give_balance =", value, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceNotEqualTo(BigDecimal value) {
            addCriterion("give_balance <>", value, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceGreaterThan(BigDecimal value) {
            addCriterion("give_balance >", value, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("give_balance >=", value, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceLessThan(BigDecimal value) {
            addCriterion("give_balance <", value, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("give_balance <=", value, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceIn(List<BigDecimal> values) {
            addCriterion("give_balance in", values, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceNotIn(List<BigDecimal> values) {
            addCriterion("give_balance not in", values, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("give_balance between", value1, value2, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andGiveBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("give_balance not between", value1, value2, "giveBalance");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsIsNull() {
            addCriterion("is_give_coupons is null");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsIsNotNull() {
            addCriterion("is_give_coupons is not null");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsEqualTo(Boolean value) {
            addCriterion("is_give_coupons =", value, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsNotEqualTo(Boolean value) {
            addCriterion("is_give_coupons <>", value, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsGreaterThan(Boolean value) {
            addCriterion("is_give_coupons >", value, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_give_coupons >=", value, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsLessThan(Boolean value) {
            addCriterion("is_give_coupons <", value, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsLessThanOrEqualTo(Boolean value) {
            addCriterion("is_give_coupons <=", value, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsIn(List<Boolean> values) {
            addCriterion("is_give_coupons in", values, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsNotIn(List<Boolean> values) {
            addCriterion("is_give_coupons not in", values, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsBetween(Boolean value1, Boolean value2) {
            addCriterion("is_give_coupons between", value1, value2, "isGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andIsGiveCouponsNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_give_coupons not between", value1, value2, "isGiveCoupons");
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