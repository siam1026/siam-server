package com.siam.system.modular.package_order.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderExample() {
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

        public Criteria andGoodsTotalQuantityIsNull() {
            addCriterion("goods_total_quantity is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityIsNotNull() {
            addCriterion("goods_total_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityEqualTo(Integer value) {
            addCriterion("goods_total_quantity =", value, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityNotEqualTo(Integer value) {
            addCriterion("goods_total_quantity <>", value, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityGreaterThan(Integer value) {
            addCriterion("goods_total_quantity >", value, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_total_quantity >=", value, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityLessThan(Integer value) {
            addCriterion("goods_total_quantity <", value, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("goods_total_quantity <=", value, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityIn(List<Integer> values) {
            addCriterion("goods_total_quantity in", values, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityNotIn(List<Integer> values) {
            addCriterion("goods_total_quantity not in", values, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityBetween(Integer value1, Integer value2) {
            addCriterion("goods_total_quantity between", value1, value2, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_total_quantity not between", value1, value2, "goodsTotalQuantity");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceIsNull() {
            addCriterion("goods_total_price is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceIsNotNull() {
            addCriterion("goods_total_price is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceEqualTo(BigDecimal value) {
            addCriterion("goods_total_price =", value, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("goods_total_price <>", value, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("goods_total_price >", value, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("goods_total_price >=", value, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceLessThan(BigDecimal value) {
            addCriterion("goods_total_price <", value, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("goods_total_price <=", value, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceIn(List<BigDecimal> values) {
            addCriterion("goods_total_price in", values, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("goods_total_price not in", values, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goods_total_price between", value1, value2, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andGoodsTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goods_total_price not between", value1, value2, "goodsTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPackingChargesIsNull() {
            addCriterion("packing_charges is null");
            return (Criteria) this;
        }

        public Criteria andPackingChargesIsNotNull() {
            addCriterion("packing_charges is not null");
            return (Criteria) this;
        }

        public Criteria andPackingChargesEqualTo(BigDecimal value) {
            addCriterion("packing_charges =", value, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesNotEqualTo(BigDecimal value) {
            addCriterion("packing_charges <>", value, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesGreaterThan(BigDecimal value) {
            addCriterion("packing_charges >", value, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("packing_charges >=", value, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesLessThan(BigDecimal value) {
            addCriterion("packing_charges <", value, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("packing_charges <=", value, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesIn(List<BigDecimal> values) {
            addCriterion("packing_charges in", values, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesNotIn(List<BigDecimal> values) {
            addCriterion("packing_charges not in", values, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("packing_charges between", value1, value2, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andPackingChargesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("packing_charges not between", value1, value2, "packingCharges");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeIsNull() {
            addCriterion("delivery_fee is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeIsNotNull() {
            addCriterion("delivery_fee is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeEqualTo(BigDecimal value) {
            addCriterion("delivery_fee =", value, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeNotEqualTo(BigDecimal value) {
            addCriterion("delivery_fee <>", value, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeGreaterThan(BigDecimal value) {
            addCriterion("delivery_fee >", value, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_fee >=", value, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeLessThan(BigDecimal value) {
            addCriterion("delivery_fee <", value, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("delivery_fee <=", value, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeIn(List<BigDecimal> values) {
            addCriterion("delivery_fee in", values, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeNotIn(List<BigDecimal> values) {
            addCriterion("delivery_fee not in", values, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_fee between", value1, value2, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andDeliveryFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("delivery_fee not between", value1, value2, "deliveryFee");
            return (Criteria) this;
        }

        public Criteria andActualPriceIsNull() {
            addCriterion("actual_price is null");
            return (Criteria) this;
        }

        public Criteria andActualPriceIsNotNull() {
            addCriterion("actual_price is not null");
            return (Criteria) this;
        }

        public Criteria andActualPriceEqualTo(BigDecimal value) {
            addCriterion("actual_price =", value, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceNotEqualTo(BigDecimal value) {
            addCriterion("actual_price <>", value, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceGreaterThan(BigDecimal value) {
            addCriterion("actual_price >", value, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_price >=", value, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceLessThan(BigDecimal value) {
            addCriterion("actual_price <", value, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_price <=", value, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceIn(List<BigDecimal> values) {
            addCriterion("actual_price in", values, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceNotIn(List<BigDecimal> values) {
            addCriterion("actual_price not in", values, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_price between", value1, value2, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andActualPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_price not between", value1, value2, "actualPrice");
            return (Criteria) this;
        }

        public Criteria andShoppingWayIsNull() {
            addCriterion("shopping_way is null");
            return (Criteria) this;
        }

        public Criteria andShoppingWayIsNotNull() {
            addCriterion("shopping_way is not null");
            return (Criteria) this;
        }

        public Criteria andShoppingWayEqualTo(Integer value) {
            addCriterion("shopping_way =", value, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayNotEqualTo(Integer value) {
            addCriterion("shopping_way <>", value, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayGreaterThan(Integer value) {
            addCriterion("shopping_way >", value, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayGreaterThanOrEqualTo(Integer value) {
            addCriterion("shopping_way >=", value, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayLessThan(Integer value) {
            addCriterion("shopping_way <", value, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayLessThanOrEqualTo(Integer value) {
            addCriterion("shopping_way <=", value, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayIn(List<Integer> values) {
            addCriterion("shopping_way in", values, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayNotIn(List<Integer> values) {
            addCriterion("shopping_way not in", values, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayBetween(Integer value1, Integer value2) {
            addCriterion("shopping_way between", value1, value2, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andShoppingWayNotBetween(Integer value1, Integer value2) {
            addCriterion("shopping_way not between", value1, value2, "shoppingWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdIsNull() {
            addCriterion("delivery_address_id is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdIsNotNull() {
            addCriterion("delivery_address_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdEqualTo(Integer value) {
            addCriterion("delivery_address_id =", value, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdNotEqualTo(Integer value) {
            addCriterion("delivery_address_id <>", value, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdGreaterThan(Integer value) {
            addCriterion("delivery_address_id >", value, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("delivery_address_id >=", value, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdLessThan(Integer value) {
            addCriterion("delivery_address_id <", value, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdLessThanOrEqualTo(Integer value) {
            addCriterion("delivery_address_id <=", value, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdIn(List<Integer> values) {
            addCriterion("delivery_address_id in", values, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdNotIn(List<Integer> values) {
            addCriterion("delivery_address_id not in", values, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdBetween(Integer value1, Integer value2) {
            addCriterion("delivery_address_id between", value1, value2, "deliveryAddressId");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIdNotBetween(Integer value1, Integer value2) {
            addCriterion("delivery_address_id not between", value1, value2, "deliveryAddressId");
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

        public Criteria andContactProvinceIsNull() {
            addCriterion("contact_province is null");
            return (Criteria) this;
        }

        public Criteria andContactProvinceIsNotNull() {
            addCriterion("contact_province is not null");
            return (Criteria) this;
        }

        public Criteria andContactProvinceEqualTo(String value) {
            addCriterion("contact_province =", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceNotEqualTo(String value) {
            addCriterion("contact_province <>", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceGreaterThan(String value) {
            addCriterion("contact_province >", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("contact_province >=", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceLessThan(String value) {
            addCriterion("contact_province <", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceLessThanOrEqualTo(String value) {
            addCriterion("contact_province <=", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceLike(String value) {
            addCriterion("contact_province like", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceNotLike(String value) {
            addCriterion("contact_province not like", value, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceIn(List<String> values) {
            addCriterion("contact_province in", values, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceNotIn(List<String> values) {
            addCriterion("contact_province not in", values, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceBetween(String value1, String value2) {
            addCriterion("contact_province between", value1, value2, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactProvinceNotBetween(String value1, String value2) {
            addCriterion("contact_province not between", value1, value2, "contactProvince");
            return (Criteria) this;
        }

        public Criteria andContactCityIsNull() {
            addCriterion("contact_city is null");
            return (Criteria) this;
        }

        public Criteria andContactCityIsNotNull() {
            addCriterion("contact_city is not null");
            return (Criteria) this;
        }

        public Criteria andContactCityEqualTo(String value) {
            addCriterion("contact_city =", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityNotEqualTo(String value) {
            addCriterion("contact_city <>", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityGreaterThan(String value) {
            addCriterion("contact_city >", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityGreaterThanOrEqualTo(String value) {
            addCriterion("contact_city >=", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityLessThan(String value) {
            addCriterion("contact_city <", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityLessThanOrEqualTo(String value) {
            addCriterion("contact_city <=", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityLike(String value) {
            addCriterion("contact_city like", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityNotLike(String value) {
            addCriterion("contact_city not like", value, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityIn(List<String> values) {
            addCriterion("contact_city in", values, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityNotIn(List<String> values) {
            addCriterion("contact_city not in", values, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityBetween(String value1, String value2) {
            addCriterion("contact_city between", value1, value2, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactCityNotBetween(String value1, String value2) {
            addCriterion("contact_city not between", value1, value2, "contactCity");
            return (Criteria) this;
        }

        public Criteria andContactAreaIsNull() {
            addCriterion("contact_area is null");
            return (Criteria) this;
        }

        public Criteria andContactAreaIsNotNull() {
            addCriterion("contact_area is not null");
            return (Criteria) this;
        }

        public Criteria andContactAreaEqualTo(String value) {
            addCriterion("contact_area =", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaNotEqualTo(String value) {
            addCriterion("contact_area <>", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaGreaterThan(String value) {
            addCriterion("contact_area >", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaGreaterThanOrEqualTo(String value) {
            addCriterion("contact_area >=", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaLessThan(String value) {
            addCriterion("contact_area <", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaLessThanOrEqualTo(String value) {
            addCriterion("contact_area <=", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaLike(String value) {
            addCriterion("contact_area like", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaNotLike(String value) {
            addCriterion("contact_area not like", value, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaIn(List<String> values) {
            addCriterion("contact_area in", values, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaNotIn(List<String> values) {
            addCriterion("contact_area not in", values, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaBetween(String value1, String value2) {
            addCriterion("contact_area between", value1, value2, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactAreaNotBetween(String value1, String value2) {
            addCriterion("contact_area not between", value1, value2, "contactArea");
            return (Criteria) this;
        }

        public Criteria andContactStreetIsNull() {
            addCriterion("contact_street is null");
            return (Criteria) this;
        }

        public Criteria andContactStreetIsNotNull() {
            addCriterion("contact_street is not null");
            return (Criteria) this;
        }

        public Criteria andContactStreetEqualTo(String value) {
            addCriterion("contact_street =", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetNotEqualTo(String value) {
            addCriterion("contact_street <>", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetGreaterThan(String value) {
            addCriterion("contact_street >", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetGreaterThanOrEqualTo(String value) {
            addCriterion("contact_street >=", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetLessThan(String value) {
            addCriterion("contact_street <", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetLessThanOrEqualTo(String value) {
            addCriterion("contact_street <=", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetLike(String value) {
            addCriterion("contact_street like", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetNotLike(String value) {
            addCriterion("contact_street not like", value, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetIn(List<String> values) {
            addCriterion("contact_street in", values, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetNotIn(List<String> values) {
            addCriterion("contact_street not in", values, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetBetween(String value1, String value2) {
            addCriterion("contact_street between", value1, value2, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactStreetNotBetween(String value1, String value2) {
            addCriterion("contact_street not between", value1, value2, "contactStreet");
            return (Criteria) this;
        }

        public Criteria andContactSexIsNull() {
            addCriterion("contact_sex is null");
            return (Criteria) this;
        }

        public Criteria andContactSexIsNotNull() {
            addCriterion("contact_sex is not null");
            return (Criteria) this;
        }

        public Criteria andContactSexEqualTo(Integer value) {
            addCriterion("contact_sex =", value, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexNotEqualTo(Integer value) {
            addCriterion("contact_sex <>", value, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexGreaterThan(Integer value) {
            addCriterion("contact_sex >", value, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("contact_sex >=", value, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexLessThan(Integer value) {
            addCriterion("contact_sex <", value, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexLessThanOrEqualTo(Integer value) {
            addCriterion("contact_sex <=", value, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexIn(List<Integer> values) {
            addCriterion("contact_sex in", values, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexNotIn(List<Integer> values) {
            addCriterion("contact_sex not in", values, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexBetween(Integer value1, Integer value2) {
            addCriterion("contact_sex between", value1, value2, "contactSex");
            return (Criteria) this;
        }

        public Criteria andContactSexNotBetween(Integer value1, Integer value2) {
            addCriterion("contact_sex not between", value1, value2, "contactSex");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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

        public Criteria andOrderLogisticsIdIsNull() {
            addCriterion("order_logistics_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdIsNotNull() {
            addCriterion("order_logistics_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdEqualTo(Integer value) {
            addCriterion("order_logistics_id =", value, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdNotEqualTo(Integer value) {
            addCriterion("order_logistics_id <>", value, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdGreaterThan(Integer value) {
            addCriterion("order_logistics_id >", value, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_logistics_id >=", value, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdLessThan(Integer value) {
            addCriterion("order_logistics_id <", value, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_logistics_id <=", value, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdIn(List<Integer> values) {
            addCriterion("order_logistics_id in", values, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdNotIn(List<Integer> values) {
            addCriterion("order_logistics_id not in", values, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdBetween(Integer value1, Integer value2) {
            addCriterion("order_logistics_id between", value1, value2, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andOrderLogisticsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_logistics_id not between", value1, value2, "orderLogisticsId");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceIsNull() {
            addCriterion("is_invoice is null");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceIsNotNull() {
            addCriterion("is_invoice is not null");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceEqualTo(Boolean value) {
            addCriterion("is_invoice =", value, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceNotEqualTo(Boolean value) {
            addCriterion("is_invoice <>", value, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceGreaterThan(Boolean value) {
            addCriterion("is_invoice >", value, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_invoice >=", value, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceLessThan(Boolean value) {
            addCriterion("is_invoice <", value, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceLessThanOrEqualTo(Boolean value) {
            addCriterion("is_invoice <=", value, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceIn(List<Boolean> values) {
            addCriterion("is_invoice in", values, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceNotIn(List<Boolean> values) {
            addCriterion("is_invoice not in", values, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceBetween(Boolean value1, Boolean value2) {
            addCriterion("is_invoice between", value1, value2, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andIsInvoiceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_invoice not between", value1, value2, "isInvoice");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIsNull() {
            addCriterion("invoice_id is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIsNotNull() {
            addCriterion("invoice_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdEqualTo(Integer value) {
            addCriterion("invoice_id =", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotEqualTo(Integer value) {
            addCriterion("invoice_id <>", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdGreaterThan(Integer value) {
            addCriterion("invoice_id >", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("invoice_id >=", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdLessThan(Integer value) {
            addCriterion("invoice_id <", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdLessThanOrEqualTo(Integer value) {
            addCriterion("invoice_id <=", value, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdIn(List<Integer> values) {
            addCriterion("invoice_id in", values, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotIn(List<Integer> values) {
            addCriterion("invoice_id not in", values, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdBetween(Integer value1, Integer value2) {
            addCriterion("invoice_id between", value1, value2, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andInvoiceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("invoice_id not between", value1, value2, "invoiceId");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
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

        public Criteria andShopNameIsNull() {
            addCriterion("shop_name is null");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNotNull() {
            addCriterion("shop_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopNameEqualTo(String value) {
            addCriterion("shop_name =", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotEqualTo(String value) {
            addCriterion("shop_name <>", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThan(String value) {
            addCriterion("shop_name >", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_name >=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThan(String value) {
            addCriterion("shop_name <", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThanOrEqualTo(String value) {
            addCriterion("shop_name <=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLike(String value) {
            addCriterion("shop_name like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotLike(String value) {
            addCriterion("shop_name not like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameIn(List<String> values) {
            addCriterion("shop_name in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotIn(List<String> values) {
            addCriterion("shop_name not in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameBetween(String value1, String value2) {
            addCriterion("shop_name between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotBetween(String value1, String value2) {
            addCriterion("shop_name not between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNull() {
            addCriterion("shop_address is null");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNotNull() {
            addCriterion("shop_address is not null");
            return (Criteria) this;
        }

        public Criteria andShopAddressEqualTo(String value) {
            addCriterion("shop_address =", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotEqualTo(String value) {
            addCriterion("shop_address <>", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThan(String value) {
            addCriterion("shop_address >", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThanOrEqualTo(String value) {
            addCriterion("shop_address >=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThan(String value) {
            addCriterion("shop_address <", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThanOrEqualTo(String value) {
            addCriterion("shop_address <=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLike(String value) {
            addCriterion("shop_address like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotLike(String value) {
            addCriterion("shop_address not like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressIn(List<String> values) {
            addCriterion("shop_address in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotIn(List<String> values) {
            addCriterion("shop_address not in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressBetween(String value1, String value2) {
            addCriterion("shop_address between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotBetween(String value1, String value2) {
            addCriterion("shop_address not between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIsNull() {
            addCriterion("cancel_reason is null");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIsNotNull() {
            addCriterion("cancel_reason is not null");
            return (Criteria) this;
        }

        public Criteria andCancelReasonEqualTo(Integer value) {
            addCriterion("cancel_reason =", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonNotEqualTo(Integer value) {
            addCriterion("cancel_reason <>", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonGreaterThan(Integer value) {
            addCriterion("cancel_reason >", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_reason >=", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonLessThan(Integer value) {
            addCriterion("cancel_reason <", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_reason <=", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIn(List<Integer> values) {
            addCriterion("cancel_reason in", values, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonNotIn(List<Integer> values) {
            addCriterion("cancel_reason not in", values, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonBetween(Integer value1, Integer value2) {
            addCriterion("cancel_reason between", value1, value2, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_reason not between", value1, value2, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineIsNull() {
            addCriterion("payment_deadline is null");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineIsNotNull() {
            addCriterion("payment_deadline is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineEqualTo(Date value) {
            addCriterion("payment_deadline =", value, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineNotEqualTo(Date value) {
            addCriterion("payment_deadline <>", value, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineGreaterThan(Date value) {
            addCriterion("payment_deadline >", value, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("payment_deadline >=", value, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineLessThan(Date value) {
            addCriterion("payment_deadline <", value, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("payment_deadline <=", value, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineIn(List<Date> values) {
            addCriterion("payment_deadline in", values, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineNotIn(List<Date> values) {
            addCriterion("payment_deadline not in", values, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineBetween(Date value1, Date value2) {
            addCriterion("payment_deadline between", value1, value2, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andPaymentDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("payment_deadline not between", value1, value2, "paymentDeadline");
            return (Criteria) this;
        }

        public Criteria andIsPrintedIsNull() {
            addCriterion("is_printed is null");
            return (Criteria) this;
        }

        public Criteria andIsPrintedIsNotNull() {
            addCriterion("is_printed is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrintedEqualTo(Boolean value) {
            addCriterion("is_printed =", value, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedNotEqualTo(Boolean value) {
            addCriterion("is_printed <>", value, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedGreaterThan(Boolean value) {
            addCriterion("is_printed >", value, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_printed >=", value, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedLessThan(Boolean value) {
            addCriterion("is_printed <", value, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_printed <=", value, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedIn(List<Boolean> values) {
            addCriterion("is_printed in", values, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedNotIn(List<Boolean> values) {
            addCriterion("is_printed not in", values, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_printed between", value1, value2, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andIsPrintedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_printed not between", value1, value2, "isPrinted");
            return (Criteria) this;
        }

        public Criteria andQueueNoIsNull() {
            addCriterion("queue_no is null");
            return (Criteria) this;
        }

        public Criteria andQueueNoIsNotNull() {
            addCriterion("queue_no is not null");
            return (Criteria) this;
        }

        public Criteria andQueueNoEqualTo(Integer value) {
            addCriterion("queue_no =", value, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoNotEqualTo(Integer value) {
            addCriterion("queue_no <>", value, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoGreaterThan(Integer value) {
            addCriterion("queue_no >", value, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("queue_no >=", value, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoLessThan(Integer value) {
            addCriterion("queue_no <", value, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoLessThanOrEqualTo(Integer value) {
            addCriterion("queue_no <=", value, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoIn(List<Integer> values) {
            addCriterion("queue_no in", values, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoNotIn(List<Integer> values) {
            addCriterion("queue_no not in", values, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoBetween(Integer value1, Integer value2) {
            addCriterion("queue_no between", value1, value2, "queueNo");
            return (Criteria) this;
        }

        public Criteria andQueueNoNotBetween(Integer value1, Integer value2) {
            addCriterion("queue_no not between", value1, value2, "queueNo");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdIsNull() {
            addCriterion("full_reduction_rule_id is null");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdIsNotNull() {
            addCriterion("full_reduction_rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdEqualTo(Integer value) {
            addCriterion("full_reduction_rule_id =", value, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdNotEqualTo(Integer value) {
            addCriterion("full_reduction_rule_id <>", value, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdGreaterThan(Integer value) {
            addCriterion("full_reduction_rule_id >", value, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("full_reduction_rule_id >=", value, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdLessThan(Integer value) {
            addCriterion("full_reduction_rule_id <", value, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdLessThanOrEqualTo(Integer value) {
            addCriterion("full_reduction_rule_id <=", value, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdIn(List<Integer> values) {
            addCriterion("full_reduction_rule_id in", values, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdNotIn(List<Integer> values) {
            addCriterion("full_reduction_rule_id not in", values, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdBetween(Integer value1, Integer value2) {
            addCriterion("full_reduction_rule_id between", value1, value2, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("full_reduction_rule_id not between", value1, value2, "fullReductionRuleId");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionIsNull() {
            addCriterion("full_reduction_rule_description is null");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionIsNotNull() {
            addCriterion("full_reduction_rule_description is not null");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionEqualTo(String value) {
            addCriterion("full_reduction_rule_description =", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionNotEqualTo(String value) {
            addCriterion("full_reduction_rule_description <>", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionGreaterThan(String value) {
            addCriterion("full_reduction_rule_description >", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("full_reduction_rule_description >=", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionLessThan(String value) {
            addCriterion("full_reduction_rule_description <", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionLessThanOrEqualTo(String value) {
            addCriterion("full_reduction_rule_description <=", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionLike(String value) {
            addCriterion("full_reduction_rule_description like", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionNotLike(String value) {
            addCriterion("full_reduction_rule_description not like", value, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionIn(List<String> values) {
            addCriterion("full_reduction_rule_description in", values, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionNotIn(List<String> values) {
            addCriterion("full_reduction_rule_description not in", values, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionBetween(String value1, String value2) {
            addCriterion("full_reduction_rule_description between", value1, value2, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andFullReductionRuleDescriptionNotBetween(String value1, String value2) {
            addCriterion("full_reduction_rule_description not between", value1, value2, "fullReductionRuleDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsIdIsNull() {
            addCriterion("coupons_id is null");
            return (Criteria) this;
        }

        public Criteria andCouponsIdIsNotNull() {
            addCriterion("coupons_id is not null");
            return (Criteria) this;
        }

        public Criteria andCouponsIdEqualTo(Integer value) {
            addCriterion("coupons_id =", value, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdNotEqualTo(Integer value) {
            addCriterion("coupons_id <>", value, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdGreaterThan(Integer value) {
            addCriterion("coupons_id >", value, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupons_id >=", value, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdLessThan(Integer value) {
            addCriterion("coupons_id <", value, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdLessThanOrEqualTo(Integer value) {
            addCriterion("coupons_id <=", value, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdIn(List<Integer> values) {
            addCriterion("coupons_id in", values, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdNotIn(List<Integer> values) {
            addCriterion("coupons_id not in", values, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdBetween(Integer value1, Integer value2) {
            addCriterion("coupons_id between", value1, value2, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("coupons_id not between", value1, value2, "couponsId");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionIsNull() {
            addCriterion("coupons_description is null");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionIsNotNull() {
            addCriterion("coupons_description is not null");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionEqualTo(String value) {
            addCriterion("coupons_description =", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionNotEqualTo(String value) {
            addCriterion("coupons_description <>", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionGreaterThan(String value) {
            addCriterion("coupons_description >", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("coupons_description >=", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionLessThan(String value) {
            addCriterion("coupons_description <", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionLessThanOrEqualTo(String value) {
            addCriterion("coupons_description <=", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionLike(String value) {
            addCriterion("coupons_description like", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionNotLike(String value) {
            addCriterion("coupons_description not like", value, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionIn(List<String> values) {
            addCriterion("coupons_description in", values, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionNotIn(List<String> values) {
            addCriterion("coupons_description not in", values, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionBetween(String value1, String value2) {
            addCriterion("coupons_description between", value1, value2, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsDescriptionNotBetween(String value1, String value2) {
            addCriterion("coupons_description not between", value1, value2, "couponsDescription");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdIsNull() {
            addCriterion("coupons_member_relation_id is null");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdIsNotNull() {
            addCriterion("coupons_member_relation_id is not null");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdEqualTo(Integer value) {
            addCriterion("coupons_member_relation_id =", value, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdNotEqualTo(Integer value) {
            addCriterion("coupons_member_relation_id <>", value, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdGreaterThan(Integer value) {
            addCriterion("coupons_member_relation_id >", value, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("coupons_member_relation_id >=", value, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdLessThan(Integer value) {
            addCriterion("coupons_member_relation_id <", value, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdLessThanOrEqualTo(Integer value) {
            addCriterion("coupons_member_relation_id <=", value, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdIn(List<Integer> values) {
            addCriterion("coupons_member_relation_id in", values, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdNotIn(List<Integer> values) {
            addCriterion("coupons_member_relation_id not in", values, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdBetween(Integer value1, Integer value2) {
            addCriterion("coupons_member_relation_id between", value1, value2, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andCouponsMemberRelationIdNotBetween(Integer value1, Integer value2) {
            addCriterion("coupons_member_relation_id not between", value1, value2, "couponsMemberRelationId");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryIsNull() {
            addCriterion("is_change_to_delivery is null");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryIsNotNull() {
            addCriterion("is_change_to_delivery is not null");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryEqualTo(Boolean value) {
            addCriterion("is_change_to_delivery =", value, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryNotEqualTo(Boolean value) {
            addCriterion("is_change_to_delivery <>", value, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryGreaterThan(Boolean value) {
            addCriterion("is_change_to_delivery >", value, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_change_to_delivery >=", value, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryLessThan(Boolean value) {
            addCriterion("is_change_to_delivery <", value, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryLessThanOrEqualTo(Boolean value) {
            addCriterion("is_change_to_delivery <=", value, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryIn(List<Boolean> values) {
            addCriterion("is_change_to_delivery in", values, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryNotIn(List<Boolean> values) {
            addCriterion("is_change_to_delivery not in", values, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryBetween(Boolean value1, Boolean value2) {
            addCriterion("is_change_to_delivery between", value1, value2, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andIsChangeToDeliveryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_change_to_delivery not between", value1, value2, "isChangeToDelivery");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoIsNull() {
            addCriterion("change_to_delivery_out_trade_no is null");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoIsNotNull() {
            addCriterion("change_to_delivery_out_trade_no is not null");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoEqualTo(String value) {
            addCriterion("change_to_delivery_out_trade_no =", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoNotEqualTo(String value) {
            addCriterion("change_to_delivery_out_trade_no <>", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoGreaterThan(String value) {
            addCriterion("change_to_delivery_out_trade_no >", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoGreaterThanOrEqualTo(String value) {
            addCriterion("change_to_delivery_out_trade_no >=", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoLessThan(String value) {
            addCriterion("change_to_delivery_out_trade_no <", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoLessThanOrEqualTo(String value) {
            addCriterion("change_to_delivery_out_trade_no <=", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoLike(String value) {
            addCriterion("change_to_delivery_out_trade_no like", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoNotLike(String value) {
            addCriterion("change_to_delivery_out_trade_no not like", value, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoIn(List<String> values) {
            addCriterion("change_to_delivery_out_trade_no in", values, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoNotIn(List<String> values) {
            addCriterion("change_to_delivery_out_trade_no not in", values, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoBetween(String value1, String value2) {
            addCriterion("change_to_delivery_out_trade_no between", value1, value2, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryOutTradeNoNotBetween(String value1, String value2) {
            addCriterion("change_to_delivery_out_trade_no not between", value1, value2, "changeToDeliveryOutTradeNo");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdIsNull() {
            addCriterion("change_to_delivery_trade_id is null");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdIsNotNull() {
            addCriterion("change_to_delivery_trade_id is not null");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdEqualTo(Integer value) {
            addCriterion("change_to_delivery_trade_id =", value, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdNotEqualTo(Integer value) {
            addCriterion("change_to_delivery_trade_id <>", value, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdGreaterThan(Integer value) {
            addCriterion("change_to_delivery_trade_id >", value, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("change_to_delivery_trade_id >=", value, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdLessThan(Integer value) {
            addCriterion("change_to_delivery_trade_id <", value, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdLessThanOrEqualTo(Integer value) {
            addCriterion("change_to_delivery_trade_id <=", value, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdIn(List<Integer> values) {
            addCriterion("change_to_delivery_trade_id in", values, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdNotIn(List<Integer> values) {
            addCriterion("change_to_delivery_trade_id not in", values, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdBetween(Integer value1, Integer value2) {
            addCriterion("change_to_delivery_trade_id between", value1, value2, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andChangeToDeliveryTradeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("change_to_delivery_trade_id not between", value1, value2, "changeToDeliveryTradeId");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioIsNull() {
            addCriterion("platform_extract_ratio is null");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioIsNotNull() {
            addCriterion("platform_extract_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioEqualTo(BigDecimal value) {
            addCriterion("platform_extract_ratio =", value, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioNotEqualTo(BigDecimal value) {
            addCriterion("platform_extract_ratio <>", value, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioGreaterThan(BigDecimal value) {
            addCriterion("platform_extract_ratio >", value, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_extract_ratio >=", value, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioLessThan(BigDecimal value) {
            addCriterion("platform_extract_ratio <", value, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioLessThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_extract_ratio <=", value, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioIn(List<BigDecimal> values) {
            addCriterion("platform_extract_ratio in", values, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioNotIn(List<BigDecimal> values) {
            addCriterion("platform_extract_ratio not in", values, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_extract_ratio between", value1, value2, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractRatioNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_extract_ratio not between", value1, value2, "platformExtractRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceIsNull() {
            addCriterion("platform_extract_price is null");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceIsNotNull() {
            addCriterion("platform_extract_price is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceEqualTo(BigDecimal value) {
            addCriterion("platform_extract_price =", value, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceNotEqualTo(BigDecimal value) {
            addCriterion("platform_extract_price <>", value, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceGreaterThan(BigDecimal value) {
            addCriterion("platform_extract_price >", value, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_extract_price >=", value, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceLessThan(BigDecimal value) {
            addCriterion("platform_extract_price <", value, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_extract_price <=", value, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceIn(List<BigDecimal> values) {
            addCriterion("platform_extract_price in", values, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceNotIn(List<BigDecimal> values) {
            addCriterion("platform_extract_price not in", values, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_extract_price between", value1, value2, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformExtractPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_extract_price not between", value1, value2, "platformExtractPrice");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeIsNull() {
            addCriterion("platform_delivery_fee is null");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeIsNotNull() {
            addCriterion("platform_delivery_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeEqualTo(BigDecimal value) {
            addCriterion("platform_delivery_fee =", value, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeNotEqualTo(BigDecimal value) {
            addCriterion("platform_delivery_fee <>", value, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeGreaterThan(BigDecimal value) {
            addCriterion("platform_delivery_fee >", value, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_delivery_fee >=", value, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeLessThan(BigDecimal value) {
            addCriterion("platform_delivery_fee <", value, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_delivery_fee <=", value, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeIn(List<BigDecimal> values) {
            addCriterion("platform_delivery_fee in", values, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeNotIn(List<BigDecimal> values) {
            addCriterion("platform_delivery_fee not in", values, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_delivery_fee between", value1, value2, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformDeliveryFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_delivery_fee not between", value1, value2, "platformDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeIsNull() {
            addCriterion("platform_income is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeIsNotNull() {
            addCriterion("platform_income is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeEqualTo(BigDecimal value) {
            addCriterion("platform_income =", value, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeNotEqualTo(BigDecimal value) {
            addCriterion("platform_income <>", value, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeGreaterThan(BigDecimal value) {
            addCriterion("platform_income >", value, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_income >=", value, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeLessThan(BigDecimal value) {
            addCriterion("platform_income <", value, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_income <=", value, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeIn(List<BigDecimal> values) {
            addCriterion("platform_income in", values, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeNotIn(List<BigDecimal> values) {
            addCriterion("platform_income not in", values, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_income between", value1, value2, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andPlatformIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_income not between", value1, value2, "platformIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeIsNull() {
            addCriterion("merchant_delivery_fee is null");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeIsNotNull() {
            addCriterion("merchant_delivery_fee is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeEqualTo(BigDecimal value) {
            addCriterion("merchant_delivery_fee =", value, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeNotEqualTo(BigDecimal value) {
            addCriterion("merchant_delivery_fee <>", value, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeGreaterThan(BigDecimal value) {
            addCriterion("merchant_delivery_fee >", value, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_delivery_fee >=", value, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeLessThan(BigDecimal value) {
            addCriterion("merchant_delivery_fee <", value, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_delivery_fee <=", value, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeIn(List<BigDecimal> values) {
            addCriterion("merchant_delivery_fee in", values, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeNotIn(List<BigDecimal> values) {
            addCriterion("merchant_delivery_fee not in", values, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_delivery_fee between", value1, value2, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andMerchantDeliveryFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_delivery_fee not between", value1, value2, "merchantDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeIsNull() {
            addCriterion("courier_income is null");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeIsNotNull() {
            addCriterion("courier_income is not null");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeEqualTo(BigDecimal value) {
            addCriterion("courier_income =", value, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeNotEqualTo(BigDecimal value) {
            addCriterion("courier_income <>", value, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeGreaterThan(BigDecimal value) {
            addCriterion("courier_income >", value, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("courier_income >=", value, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeLessThan(BigDecimal value) {
            addCriterion("courier_income <", value, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("courier_income <=", value, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeIn(List<BigDecimal> values) {
            addCriterion("courier_income in", values, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeNotIn(List<BigDecimal> values) {
            addCriterion("courier_income not in", values, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("courier_income between", value1, value2, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andCourierIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("courier_income not between", value1, value2, "courierIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeIsNull() {
            addCriterion("merchant_income is null");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeIsNotNull() {
            addCriterion("merchant_income is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeEqualTo(BigDecimal value) {
            addCriterion("merchant_income =", value, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeNotEqualTo(BigDecimal value) {
            addCriterion("merchant_income <>", value, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeGreaterThan(BigDecimal value) {
            addCriterion("merchant_income >", value, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_income >=", value, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeLessThan(BigDecimal value) {
            addCriterion("merchant_income <", value, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("merchant_income <=", value, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeIn(List<BigDecimal> values) {
            addCriterion("merchant_income in", values, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeNotIn(List<BigDecimal> values) {
            addCriterion("merchant_income not in", values, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_income between", value1, value2, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andMerchantIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("merchant_income not between", value1, value2, "merchantIncome");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeIsNull() {
            addCriterion("payment_success_time is null");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeIsNotNull() {
            addCriterion("payment_success_time is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeEqualTo(Date value) {
            addCriterion("payment_success_time =", value, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeNotEqualTo(Date value) {
            addCriterion("payment_success_time <>", value, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeGreaterThan(Date value) {
            addCriterion("payment_success_time >", value, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("payment_success_time >=", value, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeLessThan(Date value) {
            addCriterion("payment_success_time <", value, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeLessThanOrEqualTo(Date value) {
            addCriterion("payment_success_time <=", value, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeIn(List<Date> values) {
            addCriterion("payment_success_time in", values, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeNotIn(List<Date> values) {
            addCriterion("payment_success_time not in", values, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeBetween(Date value1, Date value2) {
            addCriterion("payment_success_time between", value1, value2, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andPaymentSuccessTimeNotBetween(Date value1, Date value2) {
            addCriterion("payment_success_time not between", value1, value2, "paymentSuccessTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeIsNull() {
            addCriterion("order_completion_time is null");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeIsNotNull() {
            addCriterion("order_completion_time is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeEqualTo(Date value) {
            addCriterion("order_completion_time =", value, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeNotEqualTo(Date value) {
            addCriterion("order_completion_time <>", value, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeGreaterThan(Date value) {
            addCriterion("order_completion_time >", value, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("order_completion_time >=", value, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeLessThan(Date value) {
            addCriterion("order_completion_time <", value, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeLessThanOrEqualTo(Date value) {
            addCriterion("order_completion_time <=", value, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeIn(List<Date> values) {
            addCriterion("order_completion_time in", values, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeNotIn(List<Date> values) {
            addCriterion("order_completion_time not in", values, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeBetween(Date value1, Date value2) {
            addCriterion("order_completion_time between", value1, value2, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andOrderCompletionTimeNotBetween(Date value1, Date value2) {
            addCriterion("order_completion_time not between", value1, value2, "orderCompletionTime");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonIsNull() {
            addCriterion("paid_order_cancel_reason is null");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonIsNotNull() {
            addCriterion("paid_order_cancel_reason is not null");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonEqualTo(Integer value) {
            addCriterion("paid_order_cancel_reason =", value, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonNotEqualTo(Integer value) {
            addCriterion("paid_order_cancel_reason <>", value, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonGreaterThan(Integer value) {
            addCriterion("paid_order_cancel_reason >", value, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("paid_order_cancel_reason >=", value, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonLessThan(Integer value) {
            addCriterion("paid_order_cancel_reason <", value, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonLessThanOrEqualTo(Integer value) {
            addCriterion("paid_order_cancel_reason <=", value, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonIn(List<Integer> values) {
            addCriterion("paid_order_cancel_reason in", values, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonNotIn(List<Integer> values) {
            addCriterion("paid_order_cancel_reason not in", values, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonBetween(Integer value1, Integer value2) {
            addCriterion("paid_order_cancel_reason between", value1, value2, "paidOrderCancelReason");
            return (Criteria) this;
        }

        public Criteria andPaidOrderCancelReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("paid_order_cancel_reason not between", value1, value2, "paidOrderCancelReason");
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

        public Criteria andCouponsDiscountPriceIsNull() {
            addCriterion("coupons_discount_price is null");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceIsNotNull() {
            addCriterion("coupons_discount_price is not null");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceEqualTo(BigDecimal value) {
            addCriterion("coupons_discount_price =", value, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceNotEqualTo(BigDecimal value) {
            addCriterion("coupons_discount_price <>", value, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceGreaterThan(BigDecimal value) {
            addCriterion("coupons_discount_price >", value, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("coupons_discount_price >=", value, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceLessThan(BigDecimal value) {
            addCriterion("coupons_discount_price <", value, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("coupons_discount_price <=", value, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceIn(List<BigDecimal> values) {
            addCriterion("coupons_discount_price in", values, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceNotIn(List<BigDecimal> values) {
            addCriterion("coupons_discount_price not in", values, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("coupons_discount_price between", value1, value2, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andCouponsDiscountPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("coupons_discount_price not between", value1, value2, "couponsDiscountPrice");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayIsNull() {
            addCriterion("delivery_way is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayIsNotNull() {
            addCriterion("delivery_way is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayEqualTo(Integer value) {
            addCriterion("delivery_way =", value, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayNotEqualTo(Integer value) {
            addCriterion("delivery_way <>", value, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayGreaterThan(Integer value) {
            addCriterion("delivery_way >", value, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayGreaterThanOrEqualTo(Integer value) {
            addCriterion("delivery_way >=", value, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayLessThan(Integer value) {
            addCriterion("delivery_way <", value, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayLessThanOrEqualTo(Integer value) {
            addCriterion("delivery_way <=", value, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayIn(List<Integer> values) {
            addCriterion("delivery_way in", values, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayNotIn(List<Integer> values) {
            addCriterion("delivery_way not in", values, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayBetween(Integer value1, Integer value2) {
            addCriterion("delivery_way between", value1, value2, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andDeliveryWayNotBetween(Integer value1, Integer value2) {
            addCriterion("delivery_way not between", value1, value2, "deliveryWay");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantIsNull() {
            addCriterion("is_pay_to_merchant is null");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantIsNotNull() {
            addCriterion("is_pay_to_merchant is not null");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantEqualTo(Boolean value) {
            addCriterion("is_pay_to_merchant =", value, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantNotEqualTo(Boolean value) {
            addCriterion("is_pay_to_merchant <>", value, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantGreaterThan(Boolean value) {
            addCriterion("is_pay_to_merchant >", value, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_pay_to_merchant >=", value, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantLessThan(Boolean value) {
            addCriterion("is_pay_to_merchant <", value, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantLessThanOrEqualTo(Boolean value) {
            addCriterion("is_pay_to_merchant <=", value, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantIn(List<Boolean> values) {
            addCriterion("is_pay_to_merchant in", values, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantNotIn(List<Boolean> values) {
            addCriterion("is_pay_to_merchant not in", values, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pay_to_merchant between", value1, value2, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andIsPayToMerchantNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pay_to_merchant not between", value1, value2, "isPayToMerchant");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeIsNull() {
            addCriterion("before_reduced_delivery_fee is null");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeIsNotNull() {
            addCriterion("before_reduced_delivery_fee is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeEqualTo(BigDecimal value) {
            addCriterion("before_reduced_delivery_fee =", value, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeNotEqualTo(BigDecimal value) {
            addCriterion("before_reduced_delivery_fee <>", value, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeGreaterThan(BigDecimal value) {
            addCriterion("before_reduced_delivery_fee >", value, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("before_reduced_delivery_fee >=", value, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeLessThan(BigDecimal value) {
            addCriterion("before_reduced_delivery_fee <", value, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("before_reduced_delivery_fee <=", value, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeIn(List<BigDecimal> values) {
            addCriterion("before_reduced_delivery_fee in", values, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeNotIn(List<BigDecimal> values) {
            addCriterion("before_reduced_delivery_fee not in", values, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_reduced_delivery_fee between", value1, value2, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andBeforeReducedDeliveryFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_reduced_delivery_fee not between", value1, value2, "beforeReducedDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andPaymentModeIsNull() {
            addCriterion("payment_mode is null");
            return (Criteria) this;
        }

        public Criteria andPaymentModeIsNotNull() {
            addCriterion("payment_mode is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentModeEqualTo(Integer value) {
            addCriterion("payment_mode =", value, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeNotEqualTo(Integer value) {
            addCriterion("payment_mode <>", value, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeGreaterThan(Integer value) {
            addCriterion("payment_mode >", value, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("payment_mode >=", value, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeLessThan(Integer value) {
            addCriterion("payment_mode <", value, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeLessThanOrEqualTo(Integer value) {
            addCriterion("payment_mode <=", value, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeIn(List<Integer> values) {
            addCriterion("payment_mode in", values, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeNotIn(List<Integer> values) {
            addCriterion("payment_mode not in", values, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBetween(Integer value1, Integer value2) {
            addCriterion("payment_mode between", value1, value2, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andPaymentModeNotBetween(Integer value1, Integer value2) {
            addCriterion("payment_mode not between", value1, value2, "paymentMode");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberIsNull() {
            addCriterion("contact_house_number is null");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberIsNotNull() {
            addCriterion("contact_house_number is not null");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberEqualTo(String value) {
            addCriterion("contact_house_number =", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberNotEqualTo(String value) {
            addCriterion("contact_house_number <>", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberGreaterThan(String value) {
            addCriterion("contact_house_number >", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberGreaterThanOrEqualTo(String value) {
            addCriterion("contact_house_number >=", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberLessThan(String value) {
            addCriterion("contact_house_number <", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberLessThanOrEqualTo(String value) {
            addCriterion("contact_house_number <=", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberLike(String value) {
            addCriterion("contact_house_number like", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberNotLike(String value) {
            addCriterion("contact_house_number not like", value, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberIn(List<String> values) {
            addCriterion("contact_house_number in", values, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberNotIn(List<String> values) {
            addCriterion("contact_house_number not in", values, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberBetween(String value1, String value2) {
            addCriterion("contact_house_number between", value1, value2, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactHouseNumberNotBetween(String value1, String value2) {
            addCriterion("contact_house_number not between", value1, value2, "contactHouseNumber");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeIsNull() {
            addCriterion("contact_longitude is null");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeIsNotNull() {
            addCriterion("contact_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeEqualTo(BigDecimal value) {
            addCriterion("contact_longitude =", value, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("contact_longitude <>", value, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeGreaterThan(BigDecimal value) {
            addCriterion("contact_longitude >", value, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("contact_longitude >=", value, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeLessThan(BigDecimal value) {
            addCriterion("contact_longitude <", value, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("contact_longitude <=", value, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeIn(List<BigDecimal> values) {
            addCriterion("contact_longitude in", values, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("contact_longitude not in", values, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contact_longitude between", value1, value2, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contact_longitude not between", value1, value2, "contactLongitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeIsNull() {
            addCriterion("contact_latitude is null");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeIsNotNull() {
            addCriterion("contact_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeEqualTo(BigDecimal value) {
            addCriterion("contact_latitude =", value, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("contact_latitude <>", value, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeGreaterThan(BigDecimal value) {
            addCriterion("contact_latitude >", value, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("contact_latitude >=", value, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeLessThan(BigDecimal value) {
            addCriterion("contact_latitude <", value, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("contact_latitude <=", value, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeIn(List<BigDecimal> values) {
            addCriterion("contact_latitude in", values, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("contact_latitude not in", values, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contact_latitude between", value1, value2, "contactLatitude");
            return (Criteria) this;
        }

        public Criteria andContactLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contact_latitude not between", value1, value2, "contactLatitude");
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