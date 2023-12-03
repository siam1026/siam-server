package com.siam.system.modular.package_order.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRefundExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderRefundExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
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

        public Criteria andRefundWayIsNull() {
            addCriterion("refund_way is null");
            return (Criteria) this;
        }

        public Criteria andRefundWayIsNotNull() {
            addCriterion("refund_way is not null");
            return (Criteria) this;
        }

        public Criteria andRefundWayEqualTo(Integer value) {
            addCriterion("refund_way =", value, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayNotEqualTo(Integer value) {
            addCriterion("refund_way <>", value, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayGreaterThan(Integer value) {
            addCriterion("refund_way >", value, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayGreaterThanOrEqualTo(Integer value) {
            addCriterion("refund_way >=", value, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayLessThan(Integer value) {
            addCriterion("refund_way <", value, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayLessThanOrEqualTo(Integer value) {
            addCriterion("refund_way <=", value, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayIn(List<Integer> values) {
            addCriterion("refund_way in", values, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayNotIn(List<Integer> values) {
            addCriterion("refund_way not in", values, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayBetween(Integer value1, Integer value2) {
            addCriterion("refund_way between", value1, value2, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundWayNotBetween(Integer value1, Integer value2) {
            addCriterion("refund_way not between", value1, value2, "refundWay");
            return (Criteria) this;
        }

        public Criteria andRefundReasonIsNull() {
            addCriterion("refund_reason is null");
            return (Criteria) this;
        }

        public Criteria andRefundReasonIsNotNull() {
            addCriterion("refund_reason is not null");
            return (Criteria) this;
        }

        public Criteria andRefundReasonEqualTo(Integer value) {
            addCriterion("refund_reason =", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonNotEqualTo(Integer value) {
            addCriterion("refund_reason <>", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonGreaterThan(Integer value) {
            addCriterion("refund_reason >", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("refund_reason >=", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonLessThan(Integer value) {
            addCriterion("refund_reason <", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonLessThanOrEqualTo(Integer value) {
            addCriterion("refund_reason <=", value, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonIn(List<Integer> values) {
            addCriterion("refund_reason in", values, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonNotIn(List<Integer> values) {
            addCriterion("refund_reason not in", values, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonBetween(Integer value1, Integer value2) {
            addCriterion("refund_reason between", value1, value2, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("refund_reason not between", value1, value2, "refundReason");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionIsNull() {
            addCriterion("refund_reason_description is null");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionIsNotNull() {
            addCriterion("refund_reason_description is not null");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionEqualTo(String value) {
            addCriterion("refund_reason_description =", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionNotEqualTo(String value) {
            addCriterion("refund_reason_description <>", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionGreaterThan(String value) {
            addCriterion("refund_reason_description >", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("refund_reason_description >=", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionLessThan(String value) {
            addCriterion("refund_reason_description <", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionLessThanOrEqualTo(String value) {
            addCriterion("refund_reason_description <=", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionLike(String value) {
            addCriterion("refund_reason_description like", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionNotLike(String value) {
            addCriterion("refund_reason_description not like", value, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionIn(List<String> values) {
            addCriterion("refund_reason_description in", values, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionNotIn(List<String> values) {
            addCriterion("refund_reason_description not in", values, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionBetween(String value1, String value2) {
            addCriterion("refund_reason_description between", value1, value2, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andRefundReasonDescriptionNotBetween(String value1, String value2) {
            addCriterion("refund_reason_description not between", value1, value2, "refundReasonDescription");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesIsNull() {
            addCriterion("evidence_images is null");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesIsNotNull() {
            addCriterion("evidence_images is not null");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesEqualTo(String value) {
            addCriterion("evidence_images =", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesNotEqualTo(String value) {
            addCriterion("evidence_images <>", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesGreaterThan(String value) {
            addCriterion("evidence_images >", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesGreaterThanOrEqualTo(String value) {
            addCriterion("evidence_images >=", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesLessThan(String value) {
            addCriterion("evidence_images <", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesLessThanOrEqualTo(String value) {
            addCriterion("evidence_images <=", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesLike(String value) {
            addCriterion("evidence_images like", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesNotLike(String value) {
            addCriterion("evidence_images not like", value, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesIn(List<String> values) {
            addCriterion("evidence_images in", values, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesNotIn(List<String> values) {
            addCriterion("evidence_images not in", values, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesBetween(String value1, String value2) {
            addCriterion("evidence_images between", value1, value2, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andEvidenceImagesNotBetween(String value1, String value2) {
            addCriterion("evidence_images not between", value1, value2, "evidenceImages");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNull() {
            addCriterion("refund_amount is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNotNull() {
            addCriterion("refund_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountEqualTo(BigDecimal value) {
            addCriterion("refund_amount =", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotEqualTo(BigDecimal value) {
            addCriterion("refund_amount <>", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThan(BigDecimal value) {
            addCriterion("refund_amount >", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("refund_amount >=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThan(BigDecimal value) {
            addCriterion("refund_amount <", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("refund_amount <=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIn(List<BigDecimal> values) {
            addCriterion("refund_amount in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotIn(List<BigDecimal> values) {
            addCriterion("refund_amount not in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("refund_amount between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("refund_amount not between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountIsNull() {
            addCriterion("refund_account is null");
            return (Criteria) this;
        }

        public Criteria andRefundAccountIsNotNull() {
            addCriterion("refund_account is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAccountEqualTo(Integer value) {
            addCriterion("refund_account =", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountNotEqualTo(Integer value) {
            addCriterion("refund_account <>", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountGreaterThan(Integer value) {
            addCriterion("refund_account >", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountGreaterThanOrEqualTo(Integer value) {
            addCriterion("refund_account >=", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountLessThan(Integer value) {
            addCriterion("refund_account <", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountLessThanOrEqualTo(Integer value) {
            addCriterion("refund_account <=", value, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountIn(List<Integer> values) {
            addCriterion("refund_account in", values, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountNotIn(List<Integer> values) {
            addCriterion("refund_account not in", values, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountBetween(Integer value1, Integer value2) {
            addCriterion("refund_account between", value1, value2, "refundAccount");
            return (Criteria) this;
        }

        public Criteria andRefundAccountNotBetween(Integer value1, Integer value2) {
            addCriterion("refund_account not between", value1, value2, "refundAccount");
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

        public Criteria andIsRefundDeliveryFeeIsNull() {
            addCriterion("is_refund_delivery_fee is null");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeIsNotNull() {
            addCriterion("is_refund_delivery_fee is not null");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeEqualTo(Boolean value) {
            addCriterion("is_refund_delivery_fee =", value, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeNotEqualTo(Boolean value) {
            addCriterion("is_refund_delivery_fee <>", value, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeGreaterThan(Boolean value) {
            addCriterion("is_refund_delivery_fee >", value, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_refund_delivery_fee >=", value, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeLessThan(Boolean value) {
            addCriterion("is_refund_delivery_fee <", value, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeLessThanOrEqualTo(Boolean value) {
            addCriterion("is_refund_delivery_fee <=", value, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeIn(List<Boolean> values) {
            addCriterion("is_refund_delivery_fee in", values, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeNotIn(List<Boolean> values) {
            addCriterion("is_refund_delivery_fee not in", values, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeBetween(Boolean value1, Boolean value2) {
            addCriterion("is_refund_delivery_fee between", value1, value2, "isRefundDeliveryFee");
            return (Criteria) this;
        }

        public Criteria andIsRefundDeliveryFeeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_refund_delivery_fee not between", value1, value2, "isRefundDeliveryFee");
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

        public Criteria andIsUsedCouponsIsNull() {
            addCriterion("is_used_coupons is null");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsIsNotNull() {
            addCriterion("is_used_coupons is not null");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsEqualTo(Boolean value) {
            addCriterion("is_used_coupons =", value, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsNotEqualTo(Boolean value) {
            addCriterion("is_used_coupons <>", value, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsGreaterThan(Boolean value) {
            addCriterion("is_used_coupons >", value, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_used_coupons >=", value, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsLessThan(Boolean value) {
            addCriterion("is_used_coupons <", value, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsLessThanOrEqualTo(Boolean value) {
            addCriterion("is_used_coupons <=", value, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsIn(List<Boolean> values) {
            addCriterion("is_used_coupons in", values, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsNotIn(List<Boolean> values) {
            addCriterion("is_used_coupons not in", values, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsBetween(Boolean value1, Boolean value2) {
            addCriterion("is_used_coupons between", value1, value2, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedCouponsNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_used_coupons not between", value1, value2, "isUsedCoupons");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleIsNull() {
            addCriterion("is_used_full_reduction_rule is null");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleIsNotNull() {
            addCriterion("is_used_full_reduction_rule is not null");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleEqualTo(Boolean value) {
            addCriterion("is_used_full_reduction_rule =", value, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleNotEqualTo(Boolean value) {
            addCriterion("is_used_full_reduction_rule <>", value, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleGreaterThan(Boolean value) {
            addCriterion("is_used_full_reduction_rule >", value, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_used_full_reduction_rule >=", value, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleLessThan(Boolean value) {
            addCriterion("is_used_full_reduction_rule <", value, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_used_full_reduction_rule <=", value, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleIn(List<Boolean> values) {
            addCriterion("is_used_full_reduction_rule in", values, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleNotIn(List<Boolean> values) {
            addCriterion("is_used_full_reduction_rule not in", values, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_used_full_reduction_rule between", value1, value2, "isUsedFullReductionRule");
            return (Criteria) this;
        }

        public Criteria andIsUsedFullReductionRuleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_used_full_reduction_rule not between", value1, value2, "isUsedFullReductionRule");
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