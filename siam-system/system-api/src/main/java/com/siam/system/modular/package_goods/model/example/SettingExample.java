package com.siam.system.modular.package_goods.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettingExample() {
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

        public Criteria andPurchaseRewardPointsIsNull() {
            addCriterion("purchase_reward_points is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsIsNotNull() {
            addCriterion("purchase_reward_points is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsEqualTo(BigDecimal value) {
            addCriterion("purchase_reward_points =", value, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsNotEqualTo(BigDecimal value) {
            addCriterion("purchase_reward_points <>", value, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsGreaterThan(BigDecimal value) {
            addCriterion("purchase_reward_points >", value, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_reward_points >=", value, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsLessThan(BigDecimal value) {
            addCriterion("purchase_reward_points <", value, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_reward_points <=", value, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsIn(List<BigDecimal> values) {
            addCriterion("purchase_reward_points in", values, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsNotIn(List<BigDecimal> values) {
            addCriterion("purchase_reward_points not in", values, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_reward_points between", value1, value2, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andPurchaseRewardPointsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_reward_points not between", value1, value2, "purchaseRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsIsNull() {
            addCriterion("registration_reward_points is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsIsNotNull() {
            addCriterion("registration_reward_points is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsEqualTo(BigDecimal value) {
            addCriterion("registration_reward_points =", value, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsNotEqualTo(BigDecimal value) {
            addCriterion("registration_reward_points <>", value, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsGreaterThan(BigDecimal value) {
            addCriterion("registration_reward_points >", value, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("registration_reward_points >=", value, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsLessThan(BigDecimal value) {
            addCriterion("registration_reward_points <", value, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("registration_reward_points <=", value, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsIn(List<BigDecimal> values) {
            addCriterion("registration_reward_points in", values, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsNotIn(List<BigDecimal> values) {
            addCriterion("registration_reward_points not in", values, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("registration_reward_points between", value1, value2, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardPointsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("registration_reward_points not between", value1, value2, "registrationRewardPoints");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdIsNull() {
            addCriterion("new_member_coupons_id is null");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdIsNotNull() {
            addCriterion("new_member_coupons_id is not null");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdEqualTo(Integer value) {
            addCriterion("new_member_coupons_id =", value, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdNotEqualTo(Integer value) {
            addCriterion("new_member_coupons_id <>", value, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdGreaterThan(Integer value) {
            addCriterion("new_member_coupons_id >", value, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_member_coupons_id >=", value, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdLessThan(Integer value) {
            addCriterion("new_member_coupons_id <", value, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdLessThanOrEqualTo(Integer value) {
            addCriterion("new_member_coupons_id <=", value, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdIn(List<Integer> values) {
            addCriterion("new_member_coupons_id in", values, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdNotIn(List<Integer> values) {
            addCriterion("new_member_coupons_id not in", values, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdBetween(Integer value1, Integer value2) {
            addCriterion("new_member_coupons_id between", value1, value2, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andNewMemberCouponsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("new_member_coupons_id not between", value1, value2, "newMemberCouponsId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdIsNull() {
            addCriterion("default_shop_id is null");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdIsNotNull() {
            addCriterion("default_shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdEqualTo(Integer value) {
            addCriterion("default_shop_id =", value, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdNotEqualTo(Integer value) {
            addCriterion("default_shop_id <>", value, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdGreaterThan(Integer value) {
            addCriterion("default_shop_id >", value, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("default_shop_id >=", value, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdLessThan(Integer value) {
            addCriterion("default_shop_id <", value, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdLessThanOrEqualTo(Integer value) {
            addCriterion("default_shop_id <=", value, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdIn(List<Integer> values) {
            addCriterion("default_shop_id in", values, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdNotIn(List<Integer> values) {
            addCriterion("default_shop_id not in", values, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdBetween(Integer value1, Integer value2) {
            addCriterion("default_shop_id between", value1, value2, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andDefaultShopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("default_shop_id not between", value1, value2, "defaultShopId");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeIsNull() {
            addCriterion("merchant_withdraw_fee is null");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeIsNotNull() {
            addCriterion("merchant_withdraw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeEqualTo(BigDecimal value) {
            addCriterion("merchant_withdraw_fee =", value, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeNotEqualTo(BigDecimal value) {
            addCriterion("merchant_withdraw_fee <>", value, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeGreaterThan(BigDecimal value) {
            addCriterion("merchant_withdraw_fee >", value, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_withdraw_fee >=", value, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeLessThan(BigDecimal value) {
            addCriterion("merchant_withdraw_fee <", value, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_withdraw_fee <=", value, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeIn(List<BigDecimal> values) {
            addCriterion("merchant_withdraw_fee in", values, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeNotIn(List<BigDecimal> values) {
            addCriterion("merchant_withdraw_fee not in", values, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_withdraw_fee between", value1, value2, "merchantWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMerchantWithdrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_withdraw_fee not between", value1, value2, "merchantWithdrawFee");
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

        public Criteria andDeliveryStartingDistanceIsNull() {
            addCriterion("delivery_starting_distance is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceIsNotNull() {
            addCriterion("delivery_starting_distance is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_distance =", value, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceNotEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_distance <>", value, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceGreaterThan(BigDecimal value) {
            addCriterion("delivery_starting_distance >", value, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_distance >=", value, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceLessThan(BigDecimal value) {
            addCriterion("delivery_starting_distance <", value, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_starting_distance <=", value, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceIn(List<BigDecimal> values) {
            addCriterion("delivery_starting_distance in", values, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceNotIn(List<BigDecimal> values) {
            addCriterion("delivery_starting_distance not in", values, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_starting_distance between", value1, value2, "deliveryStartingDistance");
            return (Criteria) this;
        }

        public Criteria andDeliveryStartingDistanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_starting_distance not between", value1, value2, "deliveryStartingDistance");
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

        public Criteria andOrderSystemExtractionRatioIsNull() {
            addCriterion("order_system_extraction_ratio is null");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioIsNotNull() {
            addCriterion("order_system_extraction_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioEqualTo(BigDecimal value) {
            addCriterion("order_system_extraction_ratio =", value, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioNotEqualTo(BigDecimal value) {
            addCriterion("order_system_extraction_ratio <>", value, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioGreaterThan(BigDecimal value) {
            addCriterion("order_system_extraction_ratio >", value, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_system_extraction_ratio >=", value, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioLessThan(BigDecimal value) {
            addCriterion("order_system_extraction_ratio <", value, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_system_extraction_ratio <=", value, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioIn(List<BigDecimal> values) {
            addCriterion("order_system_extraction_ratio in", values, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioNotIn(List<BigDecimal> values) {
            addCriterion("order_system_extraction_ratio not in", values, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_system_extraction_ratio between", value1, value2, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andOrderSystemExtractionRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_system_extraction_ratio not between", value1, value2, "orderSystemExtractionRatio");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeIsNull() {
            addCriterion("merchant_meal_preparation_time is null");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeIsNotNull() {
            addCriterion("merchant_meal_preparation_time is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeEqualTo(BigDecimal value) {
            addCriterion("merchant_meal_preparation_time =", value, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeNotEqualTo(BigDecimal value) {
            addCriterion("merchant_meal_preparation_time <>", value, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeGreaterThan(BigDecimal value) {
            addCriterion("merchant_meal_preparation_time >", value, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_meal_preparation_time >=", value, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeLessThan(BigDecimal value) {
            addCriterion("merchant_meal_preparation_time <", value, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_meal_preparation_time <=", value, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeIn(List<BigDecimal> values) {
            addCriterion("merchant_meal_preparation_time in", values, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeNotIn(List<BigDecimal> values) {
            addCriterion("merchant_meal_preparation_time not in", values, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_meal_preparation_time between", value1, value2, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMerchantMealPreparationTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_meal_preparation_time not between", value1, value2, "merchantMealPreparationTime");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeIsNull() {
            addCriterion("member_withdraw_fee is null");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeIsNotNull() {
            addCriterion("member_withdraw_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_fee =", value, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeNotEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_fee <>", value, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeGreaterThan(BigDecimal value) {
            addCriterion("member_withdraw_fee >", value, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_fee >=", value, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeLessThan(BigDecimal value) {
            addCriterion("member_withdraw_fee <", value, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_fee <=", value, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeIn(List<BigDecimal> values) {
            addCriterion("member_withdraw_fee in", values, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeNotIn(List<BigDecimal> values) {
            addCriterion("member_withdraw_fee not in", values, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_withdraw_fee between", value1, value2, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_withdraw_fee not between", value1, value2, "memberWithdrawFee");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountIsNull() {
            addCriterion("registration_reward_invite_reward_amount is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountIsNotNull() {
            addCriterion("registration_reward_invite_reward_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountEqualTo(BigDecimal value) {
            addCriterion("registration_reward_invite_reward_amount =", value, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountNotEqualTo(BigDecimal value) {
            addCriterion("registration_reward_invite_reward_amount <>", value, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountGreaterThan(BigDecimal value) {
            addCriterion("registration_reward_invite_reward_amount >", value, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("registration_reward_invite_reward_amount >=", value, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountLessThan(BigDecimal value) {
            addCriterion("registration_reward_invite_reward_amount <", value, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("registration_reward_invite_reward_amount <=", value, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountIn(List<BigDecimal> values) {
            addCriterion("registration_reward_invite_reward_amount in", values, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountNotIn(List<BigDecimal> values) {
            addCriterion("registration_reward_invite_reward_amount not in", values, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("registration_reward_invite_reward_amount between", value1, value2, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRegistrationRewardInviteRewardAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("registration_reward_invite_reward_amount not between", value1, value2, "registrationRewardInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountIsNull() {
            addCriterion("member_withdraw_meet_amount is null");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountIsNotNull() {
            addCriterion("member_withdraw_meet_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_meet_amount =", value, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountNotEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_meet_amount <>", value, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountGreaterThan(BigDecimal value) {
            addCriterion("member_withdraw_meet_amount >", value, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_meet_amount >=", value, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountLessThan(BigDecimal value) {
            addCriterion("member_withdraw_meet_amount <", value, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_meet_amount <=", value, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountIn(List<BigDecimal> values) {
            addCriterion("member_withdraw_meet_amount in", values, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountNotIn(List<BigDecimal> values) {
            addCriterion("member_withdraw_meet_amount not in", values, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_withdraw_meet_amount between", value1, value2, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawMeetAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_withdraw_meet_amount not between", value1, value2, "memberWithdrawMeetAmount");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdIsNull() {
            addCriterion("member_withdraw_audit_threshold is null");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdIsNotNull() {
            addCriterion("member_withdraw_audit_threshold is not null");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_audit_threshold =", value, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdNotEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_audit_threshold <>", value, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdGreaterThan(BigDecimal value) {
            addCriterion("member_withdraw_audit_threshold >", value, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_audit_threshold >=", value, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdLessThan(BigDecimal value) {
            addCriterion("member_withdraw_audit_threshold <", value, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("member_withdraw_audit_threshold <=", value, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdIn(List<BigDecimal> values) {
            addCriterion("member_withdraw_audit_threshold in", values, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdNotIn(List<BigDecimal> values) {
            addCriterion("member_withdraw_audit_threshold not in", values, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_withdraw_audit_threshold between", value1, value2, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andMemberWithdrawAuditThresholdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("member_withdraw_audit_threshold not between", value1, value2, "memberWithdrawAuditThreshold");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneIsNull() {
            addCriterion("customer_service_phone is null");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneIsNotNull() {
            addCriterion("customer_service_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneEqualTo(String value) {
            addCriterion("customer_service_phone =", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneNotEqualTo(String value) {
            addCriterion("customer_service_phone <>", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneGreaterThan(String value) {
            addCriterion("customer_service_phone >", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("customer_service_phone >=", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneLessThan(String value) {
            addCriterion("customer_service_phone <", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneLessThanOrEqualTo(String value) {
            addCriterion("customer_service_phone <=", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneLike(String value) {
            addCriterion("customer_service_phone like", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneNotLike(String value) {
            addCriterion("customer_service_phone not like", value, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneIn(List<String> values) {
            addCriterion("customer_service_phone in", values, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneNotIn(List<String> values) {
            addCriterion("customer_service_phone not in", values, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneBetween(String value1, String value2) {
            addCriterion("customer_service_phone between", value1, value2, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServicePhoneNotBetween(String value1, String value2) {
            addCriterion("customer_service_phone not between", value1, value2, "customerServicePhone");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatIsNull() {
            addCriterion("customer_service_wechat is null");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatIsNotNull() {
            addCriterion("customer_service_wechat is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatEqualTo(String value) {
            addCriterion("customer_service_wechat =", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatNotEqualTo(String value) {
            addCriterion("customer_service_wechat <>", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatGreaterThan(String value) {
            addCriterion("customer_service_wechat >", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatGreaterThanOrEqualTo(String value) {
            addCriterion("customer_service_wechat >=", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatLessThan(String value) {
            addCriterion("customer_service_wechat <", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatLessThanOrEqualTo(String value) {
            addCriterion("customer_service_wechat <=", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatLike(String value) {
            addCriterion("customer_service_wechat like", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatNotLike(String value) {
            addCriterion("customer_service_wechat not like", value, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatIn(List<String> values) {
            addCriterion("customer_service_wechat in", values, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatNotIn(List<String> values) {
            addCriterion("customer_service_wechat not in", values, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatBetween(String value1, String value2) {
            addCriterion("customer_service_wechat between", value1, value2, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatNotBetween(String value1, String value2) {
            addCriterion("customer_service_wechat not between", value1, value2, "customerServiceWechat");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeIsNull() {
            addCriterion("customer_service_wechat_qrcode is null");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeIsNotNull() {
            addCriterion("customer_service_wechat_qrcode is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeEqualTo(String value) {
            addCriterion("customer_service_wechat_qrcode =", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeNotEqualTo(String value) {
            addCriterion("customer_service_wechat_qrcode <>", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeGreaterThan(String value) {
            addCriterion("customer_service_wechat_qrcode >", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeGreaterThanOrEqualTo(String value) {
            addCriterion("customer_service_wechat_qrcode >=", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeLessThan(String value) {
            addCriterion("customer_service_wechat_qrcode <", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeLessThanOrEqualTo(String value) {
            addCriterion("customer_service_wechat_qrcode <=", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeLike(String value) {
            addCriterion("customer_service_wechat_qrcode like", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeNotLike(String value) {
            addCriterion("customer_service_wechat_qrcode not like", value, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeIn(List<String> values) {
            addCriterion("customer_service_wechat_qrcode in", values, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeNotIn(List<String> values) {
            addCriterion("customer_service_wechat_qrcode not in", values, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeBetween(String value1, String value2) {
            addCriterion("customer_service_wechat_qrcode between", value1, value2, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andCustomerServiceWechatQrcodeNotBetween(String value1, String value2) {
            addCriterion("customer_service_wechat_qrcode not between", value1, value2, "customerServiceWechatQrcode");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountIsNull() {
            addCriterion("freight_insurance_paid_amount is null");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountIsNotNull() {
            addCriterion("freight_insurance_paid_amount is not null");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountEqualTo(BigDecimal value) {
            addCriterion("freight_insurance_paid_amount =", value, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountNotEqualTo(BigDecimal value) {
            addCriterion("freight_insurance_paid_amount <>", value, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountGreaterThan(BigDecimal value) {
            addCriterion("freight_insurance_paid_amount >", value, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("freight_insurance_paid_amount >=", value, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountLessThan(BigDecimal value) {
            addCriterion("freight_insurance_paid_amount <", value, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("freight_insurance_paid_amount <=", value, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountIn(List<BigDecimal> values) {
            addCriterion("freight_insurance_paid_amount in", values, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountNotIn(List<BigDecimal> values) {
            addCriterion("freight_insurance_paid_amount not in", values, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("freight_insurance_paid_amount between", value1, value2, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andFreightInsurancePaidAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("freight_insurance_paid_amount not between", value1, value2, "freightInsurancePaidAmount");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionIsNull() {
            addCriterion("invitee_consume_commission is null");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionIsNotNull() {
            addCriterion("invitee_consume_commission is not null");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionEqualTo(BigDecimal value) {
            addCriterion("invitee_consume_commission =", value, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionNotEqualTo(BigDecimal value) {
            addCriterion("invitee_consume_commission <>", value, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionGreaterThan(BigDecimal value) {
            addCriterion("invitee_consume_commission >", value, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invitee_consume_commission >=", value, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionLessThan(BigDecimal value) {
            addCriterion("invitee_consume_commission <", value, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invitee_consume_commission <=", value, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionIn(List<BigDecimal> values) {
            addCriterion("invitee_consume_commission in", values, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionNotIn(List<BigDecimal> values) {
            addCriterion("invitee_consume_commission not in", values, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invitee_consume_commission between", value1, value2, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andInviteeConsumeCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invitee_consume_commission not between", value1, value2, "inviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionIsNull() {
            addCriterion("caseone_own_commission is null");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionIsNotNull() {
            addCriterion("caseone_own_commission is not null");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionEqualTo(BigDecimal value) {
            addCriterion("caseone_own_commission =", value, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionNotEqualTo(BigDecimal value) {
            addCriterion("caseone_own_commission <>", value, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionGreaterThan(BigDecimal value) {
            addCriterion("caseone_own_commission >", value, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("caseone_own_commission >=", value, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionLessThan(BigDecimal value) {
            addCriterion("caseone_own_commission <", value, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("caseone_own_commission <=", value, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionIn(List<BigDecimal> values) {
            addCriterion("caseone_own_commission in", values, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionNotIn(List<BigDecimal> values) {
            addCriterion("caseone_own_commission not in", values, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("caseone_own_commission between", value1, value2, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCaseoneOwnCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("caseone_own_commission not between", value1, value2, "caseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionIsNull() {
            addCriterion("casetwo_own_commission is null");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionIsNotNull() {
            addCriterion("casetwo_own_commission is not null");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionEqualTo(BigDecimal value) {
            addCriterion("casetwo_own_commission =", value, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionNotEqualTo(BigDecimal value) {
            addCriterion("casetwo_own_commission <>", value, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionGreaterThan(BigDecimal value) {
            addCriterion("casetwo_own_commission >", value, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("casetwo_own_commission >=", value, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionLessThan(BigDecimal value) {
            addCriterion("casetwo_own_commission <", value, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("casetwo_own_commission <=", value, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionIn(List<BigDecimal> values) {
            addCriterion("casetwo_own_commission in", values, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionNotIn(List<BigDecimal> values) {
            addCriterion("casetwo_own_commission not in", values, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casetwo_own_commission between", value1, value2, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoOwnCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casetwo_own_commission not between", value1, value2, "casetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionIsNull() {
            addCriterion("casetwo_first_level_inviter_commission is null");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionIsNotNull() {
            addCriterion("casetwo_first_level_inviter_commission is not null");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionEqualTo(BigDecimal value) {
            addCriterion("casetwo_first_level_inviter_commission =", value, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionNotEqualTo(BigDecimal value) {
            addCriterion("casetwo_first_level_inviter_commission <>", value, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionGreaterThan(BigDecimal value) {
            addCriterion("casetwo_first_level_inviter_commission >", value, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("casetwo_first_level_inviter_commission >=", value, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionLessThan(BigDecimal value) {
            addCriterion("casetwo_first_level_inviter_commission <", value, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("casetwo_first_level_inviter_commission <=", value, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionIn(List<BigDecimal> values) {
            addCriterion("casetwo_first_level_inviter_commission in", values, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionNotIn(List<BigDecimal> values) {
            addCriterion("casetwo_first_level_inviter_commission not in", values, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casetwo_first_level_inviter_commission between", value1, value2, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasetwoFirstLevelInviterCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casetwo_first_level_inviter_commission not between", value1, value2, "casetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionIsNull() {
            addCriterion("casethree_own_commission is null");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionIsNotNull() {
            addCriterion("casethree_own_commission is not null");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionEqualTo(BigDecimal value) {
            addCriterion("casethree_own_commission =", value, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionNotEqualTo(BigDecimal value) {
            addCriterion("casethree_own_commission <>", value, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionGreaterThan(BigDecimal value) {
            addCriterion("casethree_own_commission >", value, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("casethree_own_commission >=", value, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionLessThan(BigDecimal value) {
            addCriterion("casethree_own_commission <", value, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("casethree_own_commission <=", value, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionIn(List<BigDecimal> values) {
            addCriterion("casethree_own_commission in", values, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionNotIn(List<BigDecimal> values) {
            addCriterion("casethree_own_commission not in", values, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casethree_own_commission between", value1, value2, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeOwnCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casethree_own_commission not between", value1, value2, "casethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionIsNull() {
            addCriterion("casethree_first_level_inviter_commission is null");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionIsNotNull() {
            addCriterion("casethree_first_level_inviter_commission is not null");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionEqualTo(BigDecimal value) {
            addCriterion("casethree_first_level_inviter_commission =", value, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionNotEqualTo(BigDecimal value) {
            addCriterion("casethree_first_level_inviter_commission <>", value, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionGreaterThan(BigDecimal value) {
            addCriterion("casethree_first_level_inviter_commission >", value, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("casethree_first_level_inviter_commission >=", value, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionLessThan(BigDecimal value) {
            addCriterion("casethree_first_level_inviter_commission <", value, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("casethree_first_level_inviter_commission <=", value, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionIn(List<BigDecimal> values) {
            addCriterion("casethree_first_level_inviter_commission in", values, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionNotIn(List<BigDecimal> values) {
            addCriterion("casethree_first_level_inviter_commission not in", values, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casethree_first_level_inviter_commission between", value1, value2, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeFirstLevelInviterCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casethree_first_level_inviter_commission not between", value1, value2, "casethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionIsNull() {
            addCriterion("casethree_second_level_inviter_commission is null");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionIsNotNull() {
            addCriterion("casethree_second_level_inviter_commission is not null");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionEqualTo(BigDecimal value) {
            addCriterion("casethree_second_level_inviter_commission =", value, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionNotEqualTo(BigDecimal value) {
            addCriterion("casethree_second_level_inviter_commission <>", value, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionGreaterThan(BigDecimal value) {
            addCriterion("casethree_second_level_inviter_commission >", value, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("casethree_second_level_inviter_commission >=", value, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionLessThan(BigDecimal value) {
            addCriterion("casethree_second_level_inviter_commission <", value, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("casethree_second_level_inviter_commission <=", value, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionIn(List<BigDecimal> values) {
            addCriterion("casethree_second_level_inviter_commission in", values, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionNotIn(List<BigDecimal> values) {
            addCriterion("casethree_second_level_inviter_commission not in", values, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casethree_second_level_inviter_commission between", value1, value2, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andCasethreeSecondLevelInviterCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("casethree_second_level_inviter_commission not between", value1, value2, "casethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionIsNull() {
            addCriterion("points_mall_invitee_consume_commission is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionIsNotNull() {
            addCriterion("points_mall_invitee_consume_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionEqualTo(BigDecimal value) {
            addCriterion("points_mall_invitee_consume_commission =", value, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionNotEqualTo(BigDecimal value) {
            addCriterion("points_mall_invitee_consume_commission <>", value, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionGreaterThan(BigDecimal value) {
            addCriterion("points_mall_invitee_consume_commission >", value, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_invitee_consume_commission >=", value, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionLessThan(BigDecimal value) {
            addCriterion("points_mall_invitee_consume_commission <", value, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_invitee_consume_commission <=", value, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionIn(List<BigDecimal> values) {
            addCriterion("points_mall_invitee_consume_commission in", values, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionNotIn(List<BigDecimal> values) {
            addCriterion("points_mall_invitee_consume_commission not in", values, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_invitee_consume_commission between", value1, value2, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallInviteeConsumeCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_invitee_consume_commission not between", value1, value2, "pointsMallInviteeConsumeCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionIsNull() {
            addCriterion("points_mall_caseone_own_commission is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionIsNotNull() {
            addCriterion("points_mall_caseone_own_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionEqualTo(BigDecimal value) {
            addCriterion("points_mall_caseone_own_commission =", value, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionNotEqualTo(BigDecimal value) {
            addCriterion("points_mall_caseone_own_commission <>", value, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionGreaterThan(BigDecimal value) {
            addCriterion("points_mall_caseone_own_commission >", value, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_caseone_own_commission >=", value, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionLessThan(BigDecimal value) {
            addCriterion("points_mall_caseone_own_commission <", value, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_caseone_own_commission <=", value, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionIn(List<BigDecimal> values) {
            addCriterion("points_mall_caseone_own_commission in", values, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionNotIn(List<BigDecimal> values) {
            addCriterion("points_mall_caseone_own_commission not in", values, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_caseone_own_commission between", value1, value2, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCaseoneOwnCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_caseone_own_commission not between", value1, value2, "pointsMallCaseoneOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionIsNull() {
            addCriterion("points_mall_casetwo_own_commission is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionIsNotNull() {
            addCriterion("points_mall_casetwo_own_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_own_commission =", value, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionNotEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_own_commission <>", value, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionGreaterThan(BigDecimal value) {
            addCriterion("points_mall_casetwo_own_commission >", value, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_own_commission >=", value, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionLessThan(BigDecimal value) {
            addCriterion("points_mall_casetwo_own_commission <", value, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_own_commission <=", value, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionIn(List<BigDecimal> values) {
            addCriterion("points_mall_casetwo_own_commission in", values, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionNotIn(List<BigDecimal> values) {
            addCriterion("points_mall_casetwo_own_commission not in", values, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casetwo_own_commission between", value1, value2, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoOwnCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casetwo_own_commission not between", value1, value2, "pointsMallCasetwoOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionIsNull() {
            addCriterion("points_mall_casetwo_first_level_inviter_commission is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionIsNotNull() {
            addCriterion("points_mall_casetwo_first_level_inviter_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission =", value, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionNotEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission <>", value, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionGreaterThan(BigDecimal value) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission >", value, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission >=", value, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionLessThan(BigDecimal value) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission <", value, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission <=", value, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionIn(List<BigDecimal> values) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission in", values, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionNotIn(List<BigDecimal> values) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission not in", values, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission between", value1, value2, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasetwoFirstLevelInviterCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casetwo_first_level_inviter_commission not between", value1, value2, "pointsMallCasetwoFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionIsNull() {
            addCriterion("points_mall_casethree_own_commission is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionIsNotNull() {
            addCriterion("points_mall_casethree_own_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_own_commission =", value, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionNotEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_own_commission <>", value, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionGreaterThan(BigDecimal value) {
            addCriterion("points_mall_casethree_own_commission >", value, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_own_commission >=", value, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionLessThan(BigDecimal value) {
            addCriterion("points_mall_casethree_own_commission <", value, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_own_commission <=", value, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionIn(List<BigDecimal> values) {
            addCriterion("points_mall_casethree_own_commission in", values, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionNotIn(List<BigDecimal> values) {
            addCriterion("points_mall_casethree_own_commission not in", values, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casethree_own_commission between", value1, value2, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeOwnCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casethree_own_commission not between", value1, value2, "pointsMallCasethreeOwnCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionIsNull() {
            addCriterion("points_mall_casethree_first_level_inviter_commission is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionIsNotNull() {
            addCriterion("points_mall_casethree_first_level_inviter_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_first_level_inviter_commission =", value, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionNotEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_first_level_inviter_commission <>", value, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionGreaterThan(BigDecimal value) {
            addCriterion("points_mall_casethree_first_level_inviter_commission >", value, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_first_level_inviter_commission >=", value, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionLessThan(BigDecimal value) {
            addCriterion("points_mall_casethree_first_level_inviter_commission <", value, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_first_level_inviter_commission <=", value, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionIn(List<BigDecimal> values) {
            addCriterion("points_mall_casethree_first_level_inviter_commission in", values, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionNotIn(List<BigDecimal> values) {
            addCriterion("points_mall_casethree_first_level_inviter_commission not in", values, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casethree_first_level_inviter_commission between", value1, value2, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeFirstLevelInviterCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casethree_first_level_inviter_commission not between", value1, value2, "pointsMallCasethreeFirstLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionIsNull() {
            addCriterion("points_mall_casethree_second_level_inviter_commission is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionIsNotNull() {
            addCriterion("points_mall_casethree_second_level_inviter_commission is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_second_level_inviter_commission =", value, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionNotEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_second_level_inviter_commission <>", value, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionGreaterThan(BigDecimal value) {
            addCriterion("points_mall_casethree_second_level_inviter_commission >", value, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_second_level_inviter_commission >=", value, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionLessThan(BigDecimal value) {
            addCriterion("points_mall_casethree_second_level_inviter_commission <", value, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points_mall_casethree_second_level_inviter_commission <=", value, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionIn(List<BigDecimal> values) {
            addCriterion("points_mall_casethree_second_level_inviter_commission in", values, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionNotIn(List<BigDecimal> values) {
            addCriterion("points_mall_casethree_second_level_inviter_commission not in", values, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casethree_second_level_inviter_commission between", value1, value2, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andPointsMallCasethreeSecondLevelInviterCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points_mall_casethree_second_level_inviter_commission not between", value1, value2, "pointsMallCasethreeSecondLevelInviterCommission");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleIsNull() {
            addCriterion("invite_friends_activity_rule is null");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleIsNotNull() {
            addCriterion("invite_friends_activity_rule is not null");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleEqualTo(String value) {
            addCriterion("invite_friends_activity_rule =", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleNotEqualTo(String value) {
            addCriterion("invite_friends_activity_rule <>", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleGreaterThan(String value) {
            addCriterion("invite_friends_activity_rule >", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleGreaterThanOrEqualTo(String value) {
            addCriterion("invite_friends_activity_rule >=", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleLessThan(String value) {
            addCriterion("invite_friends_activity_rule <", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleLessThanOrEqualTo(String value) {
            addCriterion("invite_friends_activity_rule <=", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleLike(String value) {
            addCriterion("invite_friends_activity_rule like", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleNotLike(String value) {
            addCriterion("invite_friends_activity_rule not like", value, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleIn(List<String> values) {
            addCriterion("invite_friends_activity_rule in", values, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleNotIn(List<String> values) {
            addCriterion("invite_friends_activity_rule not in", values, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleBetween(String value1, String value2) {
            addCriterion("invite_friends_activity_rule between", value1, value2, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andInviteFriendsActivityRuleNotBetween(String value1, String value2) {
            addCriterion("invite_friends_activity_rule not between", value1, value2, "inviteFriendsActivityRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleIsNull() {
            addCriterion("commission_rule is null");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleIsNotNull() {
            addCriterion("commission_rule is not null");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleEqualTo(String value) {
            addCriterion("commission_rule =", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleNotEqualTo(String value) {
            addCriterion("commission_rule <>", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleGreaterThan(String value) {
            addCriterion("commission_rule >", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleGreaterThanOrEqualTo(String value) {
            addCriterion("commission_rule >=", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleLessThan(String value) {
            addCriterion("commission_rule <", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleLessThanOrEqualTo(String value) {
            addCriterion("commission_rule <=", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleLike(String value) {
            addCriterion("commission_rule like", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleNotLike(String value) {
            addCriterion("commission_rule not like", value, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleIn(List<String> values) {
            addCriterion("commission_rule in", values, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleNotIn(List<String> values) {
            addCriterion("commission_rule not in", values, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleBetween(String value1, String value2) {
            addCriterion("commission_rule between", value1, value2, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andCommissionRuleNotBetween(String value1, String value2) {
            addCriterion("commission_rule not between", value1, value2, "commissionRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleIsNull() {
            addCriterion("vip_rule is null");
            return (Criteria) this;
        }

        public Criteria andVipRuleIsNotNull() {
            addCriterion("vip_rule is not null");
            return (Criteria) this;
        }

        public Criteria andVipRuleEqualTo(String value) {
            addCriterion("vip_rule =", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleNotEqualTo(String value) {
            addCriterion("vip_rule <>", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleGreaterThan(String value) {
            addCriterion("vip_rule >", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleGreaterThanOrEqualTo(String value) {
            addCriterion("vip_rule >=", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleLessThan(String value) {
            addCriterion("vip_rule <", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleLessThanOrEqualTo(String value) {
            addCriterion("vip_rule <=", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleLike(String value) {
            addCriterion("vip_rule like", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleNotLike(String value) {
            addCriterion("vip_rule not like", value, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleIn(List<String> values) {
            addCriterion("vip_rule in", values, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleNotIn(List<String> values) {
            addCriterion("vip_rule not in", values, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleBetween(String value1, String value2) {
            addCriterion("vip_rule between", value1, value2, "vipRule");
            return (Criteria) this;
        }

        public Criteria andVipRuleNotBetween(String value1, String value2) {
            addCriterion("vip_rule not between", value1, value2, "vipRule");
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