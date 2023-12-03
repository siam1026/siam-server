package com.siam.system.modular.package_goods.model.example.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VipRechargeRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VipRechargeRecordExample() {
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

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Integer value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Integer value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Integer value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Integer value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Integer value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Integer> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Integer> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Integer value1, Integer value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Integer value1, Integer value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(Integer value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(Integer value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(Integer value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(Integer value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(Integer value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<Integer> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<Integer> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(Integer value1, Integer value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(Integer value1, Integer value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andDenominationIdIsNull() {
            addCriterion("denomination_id is null");
            return (Criteria) this;
        }

        public Criteria andDenominationIdIsNotNull() {
            addCriterion("denomination_id is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationIdEqualTo(Integer value) {
            addCriterion("denomination_id =", value, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdNotEqualTo(Integer value) {
            addCriterion("denomination_id <>", value, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdGreaterThan(Integer value) {
            addCriterion("denomination_id >", value, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("denomination_id >=", value, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdLessThan(Integer value) {
            addCriterion("denomination_id <", value, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdLessThanOrEqualTo(Integer value) {
            addCriterion("denomination_id <=", value, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdIn(List<Integer> values) {
            addCriterion("denomination_id in", values, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdNotIn(List<Integer> values) {
            addCriterion("denomination_id not in", values, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdBetween(Integer value1, Integer value2) {
            addCriterion("denomination_id between", value1, value2, "denominationId");
            return (Criteria) this;
        }

        public Criteria andDenominationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("denomination_id not between", value1, value2, "denominationId");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andDenominationNameIsNull() {
            addCriterion("denomination_name is null");
            return (Criteria) this;
        }

        public Criteria andDenominationNameIsNotNull() {
            addCriterion("denomination_name is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationNameEqualTo(String value) {
            addCriterion("denomination_name =", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameNotEqualTo(String value) {
            addCriterion("denomination_name <>", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameGreaterThan(String value) {
            addCriterion("denomination_name >", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameGreaterThanOrEqualTo(String value) {
            addCriterion("denomination_name >=", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameLessThan(String value) {
            addCriterion("denomination_name <", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameLessThanOrEqualTo(String value) {
            addCriterion("denomination_name <=", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameLike(String value) {
            addCriterion("denomination_name like", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameNotLike(String value) {
            addCriterion("denomination_name not like", value, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameIn(List<String> values) {
            addCriterion("denomination_name in", values, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameNotIn(List<String> values) {
            addCriterion("denomination_name not in", values, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameBetween(String value1, String value2) {
            addCriterion("denomination_name between", value1, value2, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationNameNotBetween(String value1, String value2) {
            addCriterion("denomination_name not between", value1, value2, "denominationName");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceIsNull() {
            addCriterion("denomination_price is null");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceIsNotNull() {
            addCriterion("denomination_price is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceEqualTo(BigDecimal value) {
            addCriterion("denomination_price =", value, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceNotEqualTo(BigDecimal value) {
            addCriterion("denomination_price <>", value, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceGreaterThan(BigDecimal value) {
            addCriterion("denomination_price >", value, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("denomination_price >=", value, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceLessThan(BigDecimal value) {
            addCriterion("denomination_price <", value, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("denomination_price <=", value, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceIn(List<BigDecimal> values) {
            addCriterion("denomination_price in", values, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceNotIn(List<BigDecimal> values) {
            addCriterion("denomination_price not in", values, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("denomination_price between", value1, value2, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("denomination_price not between", value1, value2, "denominationPrice");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleIsNull() {
            addCriterion("denomination_is_sale is null");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleIsNotNull() {
            addCriterion("denomination_is_sale is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleEqualTo(Boolean value) {
            addCriterion("denomination_is_sale =", value, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleNotEqualTo(Boolean value) {
            addCriterion("denomination_is_sale <>", value, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleGreaterThan(Boolean value) {
            addCriterion("denomination_is_sale >", value, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("denomination_is_sale >=", value, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleLessThan(Boolean value) {
            addCriterion("denomination_is_sale <", value, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleLessThanOrEqualTo(Boolean value) {
            addCriterion("denomination_is_sale <=", value, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleIn(List<Boolean> values) {
            addCriterion("denomination_is_sale in", values, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleNotIn(List<Boolean> values) {
            addCriterion("denomination_is_sale not in", values, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleBetween(Boolean value1, Boolean value2) {
            addCriterion("denomination_is_sale between", value1, value2, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationIsSaleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("denomination_is_sale not between", value1, value2, "denominationIsSale");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceIsNull() {
            addCriterion("denomination_sale_price is null");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceIsNotNull() {
            addCriterion("denomination_sale_price is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceEqualTo(BigDecimal value) {
            addCriterion("denomination_sale_price =", value, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceNotEqualTo(BigDecimal value) {
            addCriterion("denomination_sale_price <>", value, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceGreaterThan(BigDecimal value) {
            addCriterion("denomination_sale_price >", value, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("denomination_sale_price >=", value, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceLessThan(BigDecimal value) {
            addCriterion("denomination_sale_price <", value, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("denomination_sale_price <=", value, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceIn(List<BigDecimal> values) {
            addCriterion("denomination_sale_price in", values, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceNotIn(List<BigDecimal> values) {
            addCriterion("denomination_sale_price not in", values, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("denomination_sale_price between", value1, value2, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationSalePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("denomination_sale_price not between", value1, value2, "denominationSalePrice");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceIsNull() {
            addCriterion("denomination_is_give_balance is null");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceIsNotNull() {
            addCriterion("denomination_is_give_balance is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceEqualTo(Boolean value) {
            addCriterion("denomination_is_give_balance =", value, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceNotEqualTo(Boolean value) {
            addCriterion("denomination_is_give_balance <>", value, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceGreaterThan(Boolean value) {
            addCriterion("denomination_is_give_balance >", value, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("denomination_is_give_balance >=", value, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceLessThan(Boolean value) {
            addCriterion("denomination_is_give_balance <", value, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceLessThanOrEqualTo(Boolean value) {
            addCriterion("denomination_is_give_balance <=", value, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceIn(List<Boolean> values) {
            addCriterion("denomination_is_give_balance in", values, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceNotIn(List<Boolean> values) {
            addCriterion("denomination_is_give_balance not in", values, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceBetween(Boolean value1, Boolean value2) {
            addCriterion("denomination_is_give_balance between", value1, value2, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveBalanceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("denomination_is_give_balance not between", value1, value2, "denominationIsGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceIsNull() {
            addCriterion("denomination_give_balance is null");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceIsNotNull() {
            addCriterion("denomination_give_balance is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceEqualTo(BigDecimal value) {
            addCriterion("denomination_give_balance =", value, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceNotEqualTo(BigDecimal value) {
            addCriterion("denomination_give_balance <>", value, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceGreaterThan(BigDecimal value) {
            addCriterion("denomination_give_balance >", value, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("denomination_give_balance >=", value, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceLessThan(BigDecimal value) {
            addCriterion("denomination_give_balance <", value, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("denomination_give_balance <=", value, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceIn(List<BigDecimal> values) {
            addCriterion("denomination_give_balance in", values, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceNotIn(List<BigDecimal> values) {
            addCriterion("denomination_give_balance not in", values, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("denomination_give_balance between", value1, value2, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("denomination_give_balance not between", value1, value2, "denominationGiveBalance");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsIsNull() {
            addCriterion("denomination_is_give_coupons is null");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsIsNotNull() {
            addCriterion("denomination_is_give_coupons is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsEqualTo(Boolean value) {
            addCriterion("denomination_is_give_coupons =", value, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsNotEqualTo(Boolean value) {
            addCriterion("denomination_is_give_coupons <>", value, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsGreaterThan(Boolean value) {
            addCriterion("denomination_is_give_coupons >", value, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsGreaterThanOrEqualTo(Boolean value) {
            addCriterion("denomination_is_give_coupons >=", value, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsLessThan(Boolean value) {
            addCriterion("denomination_is_give_coupons <", value, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsLessThanOrEqualTo(Boolean value) {
            addCriterion("denomination_is_give_coupons <=", value, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsIn(List<Boolean> values) {
            addCriterion("denomination_is_give_coupons in", values, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsNotIn(List<Boolean> values) {
            addCriterion("denomination_is_give_coupons not in", values, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsBetween(Boolean value1, Boolean value2) {
            addCriterion("denomination_is_give_coupons between", value1, value2, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationIsGiveCouponsNotBetween(Boolean value1, Boolean value2) {
            addCriterion("denomination_is_give_coupons not between", value1, value2, "denominationIsGiveCoupons");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionIsNull() {
            addCriterion("denomination_give_coupons_description is null");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionIsNotNull() {
            addCriterion("denomination_give_coupons_description is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionEqualTo(String value) {
            addCriterion("denomination_give_coupons_description =", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionNotEqualTo(String value) {
            addCriterion("denomination_give_coupons_description <>", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionGreaterThan(String value) {
            addCriterion("denomination_give_coupons_description >", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("denomination_give_coupons_description >=", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionLessThan(String value) {
            addCriterion("denomination_give_coupons_description <", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionLessThanOrEqualTo(String value) {
            addCriterion("denomination_give_coupons_description <=", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionLike(String value) {
            addCriterion("denomination_give_coupons_description like", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionNotLike(String value) {
            addCriterion("denomination_give_coupons_description not like", value, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionIn(List<String> values) {
            addCriterion("denomination_give_coupons_description in", values, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionNotIn(List<String> values) {
            addCriterion("denomination_give_coupons_description not in", values, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionBetween(String value1, String value2) {
            addCriterion("denomination_give_coupons_description between", value1, value2, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsDescriptionNotBetween(String value1, String value2) {
            addCriterion("denomination_give_coupons_description not between", value1, value2, "denominationGiveCouponsDescription");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonIsNull() {
            addCriterion("denomination_give_coupons_json is null");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonIsNotNull() {
            addCriterion("denomination_give_coupons_json is not null");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonEqualTo(String value) {
            addCriterion("denomination_give_coupons_json =", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonNotEqualTo(String value) {
            addCriterion("denomination_give_coupons_json <>", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonGreaterThan(String value) {
            addCriterion("denomination_give_coupons_json >", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonGreaterThanOrEqualTo(String value) {
            addCriterion("denomination_give_coupons_json >=", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonLessThan(String value) {
            addCriterion("denomination_give_coupons_json <", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonLessThanOrEqualTo(String value) {
            addCriterion("denomination_give_coupons_json <=", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonLike(String value) {
            addCriterion("denomination_give_coupons_json like", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonNotLike(String value) {
            addCriterion("denomination_give_coupons_json not like", value, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonIn(List<String> values) {
            addCriterion("denomination_give_coupons_json in", values, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonNotIn(List<String> values) {
            addCriterion("denomination_give_coupons_json not in", values, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonBetween(String value1, String value2) {
            addCriterion("denomination_give_coupons_json between", value1, value2, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andDenominationGiveCouponsJsonNotBetween(String value1, String value2) {
            addCriterion("denomination_give_coupons_json not between", value1, value2, "denominationGiveCouponsJson");
            return (Criteria) this;
        }

        public Criteria andTradeIdIsNull() {
            addCriterion("trade_id is null");
            return (Criteria) this;
        }

        public Criteria andTradeIdIsNotNull() {
            addCriterion("trade_id is not null");
            return (Criteria) this;
        }

        public Criteria andTradeIdEqualTo(Integer value) {
            addCriterion("trade_id =", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotEqualTo(Integer value) {
            addCriterion("trade_id <>", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdGreaterThan(Integer value) {
            addCriterion("trade_id >", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trade_id >=", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLessThan(Integer value) {
            addCriterion("trade_id <", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdLessThanOrEqualTo(Integer value) {
            addCriterion("trade_id <=", value, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdIn(List<Integer> values) {
            addCriterion("trade_id in", values, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotIn(List<Integer> values) {
            addCriterion("trade_id not in", values, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdBetween(Integer value1, Integer value2) {
            addCriterion("trade_id between", value1, value2, "tradeId");
            return (Criteria) this;
        }

        public Criteria andTradeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trade_id not between", value1, value2, "tradeId");
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