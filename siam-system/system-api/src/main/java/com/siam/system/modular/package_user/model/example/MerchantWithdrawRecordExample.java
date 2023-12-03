package com.siam.system.modular.package_user.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MerchantWithdrawRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MerchantWithdrawRecordExample() {
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

        public Criteria andWithdrawAmountIsNull() {
            addCriterion("withdraw_amount is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountIsNotNull() {
            addCriterion("withdraw_amount is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount =", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountNotEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount <>", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountGreaterThan(BigDecimal value) {
            addCriterion("withdraw_amount >", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount >=", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountLessThan(BigDecimal value) {
            addCriterion("withdraw_amount <", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw_amount <=", value, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountIn(List<BigDecimal> values) {
            addCriterion("withdraw_amount in", values, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountNotIn(List<BigDecimal> values) {
            addCriterion("withdraw_amount not in", values, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_amount between", value1, value2, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andWithdrawAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw_amount not between", value1, value2, "withdrawAmount");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeIsNull() {
            addCriterion("platform_fee is null");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeIsNotNull() {
            addCriterion("platform_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeEqualTo(BigDecimal value) {
            addCriterion("platform_fee =", value, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeNotEqualTo(BigDecimal value) {
            addCriterion("platform_fee <>", value, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeGreaterThan(BigDecimal value) {
            addCriterion("platform_fee >", value, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_fee >=", value, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeLessThan(BigDecimal value) {
            addCriterion("platform_fee <", value, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_fee <=", value, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeIn(List<BigDecimal> values) {
            addCriterion("platform_fee in", values, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeNotIn(List<BigDecimal> values) {
            addCriterion("platform_fee not in", values, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_fee between", value1, value2, "platformFee");
            return (Criteria) this;
        }

        public Criteria andPlatformFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_fee not between", value1, value2, "platformFee");
            return (Criteria) this;
        }

        public Criteria andActualAmountIsNull() {
            addCriterion("actual_amount is null");
            return (Criteria) this;
        }

        public Criteria andActualAmountIsNotNull() {
            addCriterion("actual_amount is not null");
            return (Criteria) this;
        }

        public Criteria andActualAmountEqualTo(BigDecimal value) {
            addCriterion("actual_amount =", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountNotEqualTo(BigDecimal value) {
            addCriterion("actual_amount <>", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountGreaterThan(BigDecimal value) {
            addCriterion("actual_amount >", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_amount >=", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountLessThan(BigDecimal value) {
            addCriterion("actual_amount <", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("actual_amount <=", value, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountIn(List<BigDecimal> values) {
            addCriterion("actual_amount in", values, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountNotIn(List<BigDecimal> values) {
            addCriterion("actual_amount not in", values, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_amount between", value1, value2, "actualAmount");
            return (Criteria) this;
        }

        public Criteria andActualAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actual_amount not between", value1, value2, "actualAmount");
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

        public Criteria andOpeningBankAddressIsNull() {
            addCriterion("opening_bank_address is null");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressIsNotNull() {
            addCriterion("opening_bank_address is not null");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressEqualTo(String value) {
            addCriterion("opening_bank_address =", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressNotEqualTo(String value) {
            addCriterion("opening_bank_address <>", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressGreaterThan(String value) {
            addCriterion("opening_bank_address >", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressGreaterThanOrEqualTo(String value) {
            addCriterion("opening_bank_address >=", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressLessThan(String value) {
            addCriterion("opening_bank_address <", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressLessThanOrEqualTo(String value) {
            addCriterion("opening_bank_address <=", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressLike(String value) {
            addCriterion("opening_bank_address like", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressNotLike(String value) {
            addCriterion("opening_bank_address not like", value, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressIn(List<String> values) {
            addCriterion("opening_bank_address in", values, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressNotIn(List<String> values) {
            addCriterion("opening_bank_address not in", values, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressBetween(String value1, String value2) {
            addCriterion("opening_bank_address between", value1, value2, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankAddressNotBetween(String value1, String value2) {
            addCriterion("opening_bank_address not between", value1, value2, "openingBankAddress");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameIsNull() {
            addCriterion("opening_bank_name is null");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameIsNotNull() {
            addCriterion("opening_bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameEqualTo(String value) {
            addCriterion("opening_bank_name =", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameNotEqualTo(String value) {
            addCriterion("opening_bank_name <>", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameGreaterThan(String value) {
            addCriterion("opening_bank_name >", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("opening_bank_name >=", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameLessThan(String value) {
            addCriterion("opening_bank_name <", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameLessThanOrEqualTo(String value) {
            addCriterion("opening_bank_name <=", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameLike(String value) {
            addCriterion("opening_bank_name like", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameNotLike(String value) {
            addCriterion("opening_bank_name not like", value, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameIn(List<String> values) {
            addCriterion("opening_bank_name in", values, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameNotIn(List<String> values) {
            addCriterion("opening_bank_name not in", values, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameBetween(String value1, String value2) {
            addCriterion("opening_bank_name between", value1, value2, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andOpeningBankNameNotBetween(String value1, String value2) {
            addCriterion("opening_bank_name not between", value1, value2, "openingBankName");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNull() {
            addCriterion("bank_card is null");
            return (Criteria) this;
        }

        public Criteria andBankCardIsNotNull() {
            addCriterion("bank_card is not null");
            return (Criteria) this;
        }

        public Criteria andBankCardEqualTo(String value) {
            addCriterion("bank_card =", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotEqualTo(String value) {
            addCriterion("bank_card <>", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThan(String value) {
            addCriterion("bank_card >", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardGreaterThanOrEqualTo(String value) {
            addCriterion("bank_card >=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThan(String value) {
            addCriterion("bank_card <", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLessThanOrEqualTo(String value) {
            addCriterion("bank_card <=", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardLike(String value) {
            addCriterion("bank_card like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotLike(String value) {
            addCriterion("bank_card not like", value, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardIn(List<String> values) {
            addCriterion("bank_card in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotIn(List<String> values) {
            addCriterion("bank_card not in", values, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardBetween(String value1, String value2) {
            addCriterion("bank_card between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andBankCardNotBetween(String value1, String value2) {
            addCriterion("bank_card not between", value1, value2, "bankCard");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountIsNull() {
            addCriterion("alipay_account is null");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountIsNotNull() {
            addCriterion("alipay_account is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountEqualTo(String value) {
            addCriterion("alipay_account =", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountNotEqualTo(String value) {
            addCriterion("alipay_account <>", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountGreaterThan(String value) {
            addCriterion("alipay_account >", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountGreaterThanOrEqualTo(String value) {
            addCriterion("alipay_account >=", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountLessThan(String value) {
            addCriterion("alipay_account <", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountLessThanOrEqualTo(String value) {
            addCriterion("alipay_account <=", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountLike(String value) {
            addCriterion("alipay_account like", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountNotLike(String value) {
            addCriterion("alipay_account not like", value, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountIn(List<String> values) {
            addCriterion("alipay_account in", values, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountNotIn(List<String> values) {
            addCriterion("alipay_account not in", values, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountBetween(String value1, String value2) {
            addCriterion("alipay_account between", value1, value2, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andAlipayAccountNotBetween(String value1, String value2) {
            addCriterion("alipay_account not between", value1, value2, "alipayAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountIsNull() {
            addCriterion("wechat_account is null");
            return (Criteria) this;
        }

        public Criteria andWechatAccountIsNotNull() {
            addCriterion("wechat_account is not null");
            return (Criteria) this;
        }

        public Criteria andWechatAccountEqualTo(String value) {
            addCriterion("wechat_account =", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountNotEqualTo(String value) {
            addCriterion("wechat_account <>", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountGreaterThan(String value) {
            addCriterion("wechat_account >", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountGreaterThanOrEqualTo(String value) {
            addCriterion("wechat_account >=", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountLessThan(String value) {
            addCriterion("wechat_account <", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountLessThanOrEqualTo(String value) {
            addCriterion("wechat_account <=", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountLike(String value) {
            addCriterion("wechat_account like", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountNotLike(String value) {
            addCriterion("wechat_account not like", value, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountIn(List<String> values) {
            addCriterion("wechat_account in", values, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountNotIn(List<String> values) {
            addCriterion("wechat_account not in", values, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountBetween(String value1, String value2) {
            addCriterion("wechat_account between", value1, value2, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andWechatAccountNotBetween(String value1, String value2) {
            addCriterion("wechat_account not between", value1, value2, "wechatAccount");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdIsNull() {
            addCriterion("merchant_trade_record_id is null");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdIsNotNull() {
            addCriterion("merchant_trade_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdEqualTo(Integer value) {
            addCriterion("merchant_trade_record_id =", value, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdNotEqualTo(Integer value) {
            addCriterion("merchant_trade_record_id <>", value, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdGreaterThan(Integer value) {
            addCriterion("merchant_trade_record_id >", value, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("merchant_trade_record_id >=", value, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdLessThan(Integer value) {
            addCriterion("merchant_trade_record_id <", value, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdLessThanOrEqualTo(Integer value) {
            addCriterion("merchant_trade_record_id <=", value, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdIn(List<Integer> values) {
            addCriterion("merchant_trade_record_id in", values, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdNotIn(List<Integer> values) {
            addCriterion("merchant_trade_record_id not in", values, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdBetween(Integer value1, Integer value2) {
            addCriterion("merchant_trade_record_id between", value1, value2, "merchantTradeRecordId");
            return (Criteria) this;
        }

        public Criteria andMerchantTradeRecordIdNotBetween(Integer value1, Integer value2) {
            addCriterion("merchant_trade_record_id not between", value1, value2, "merchantTradeRecordId");
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