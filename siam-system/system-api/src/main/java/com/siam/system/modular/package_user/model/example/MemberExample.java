package com.siam.system.modular.package_user.model.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberExample() {
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

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltIsNull() {
            addCriterion("password_salt is null");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltIsNotNull() {
            addCriterion("password_salt is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltEqualTo(String value) {
            addCriterion("password_salt =", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltNotEqualTo(String value) {
            addCriterion("password_salt <>", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltGreaterThan(String value) {
            addCriterion("password_salt >", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltGreaterThanOrEqualTo(String value) {
            addCriterion("password_salt >=", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltLessThan(String value) {
            addCriterion("password_salt <", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltLessThanOrEqualTo(String value) {
            addCriterion("password_salt <=", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltLike(String value) {
            addCriterion("password_salt like", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltNotLike(String value) {
            addCriterion("password_salt not like", value, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltIn(List<String> values) {
            addCriterion("password_salt in", values, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltNotIn(List<String> values) {
            addCriterion("password_salt not in", values, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltBetween(String value1, String value2) {
            addCriterion("password_salt between", value1, value2, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andPasswordSaltNotBetween(String value1, String value2) {
            addCriterion("password_salt not between", value1, value2, "passwordSalt");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickname is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickname =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickname <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickname >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickname >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickname <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickname <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickname like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickname not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickname in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickname not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickname not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andLoginCountIsNull() {
            addCriterion("login_count is null");
            return (Criteria) this;
        }

        public Criteria andLoginCountIsNotNull() {
            addCriterion("login_count is not null");
            return (Criteria) this;
        }

        public Criteria andLoginCountEqualTo(Integer value) {
            addCriterion("login_count =", value, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountNotEqualTo(Integer value) {
            addCriterion("login_count <>", value, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountGreaterThan(Integer value) {
            addCriterion("login_count >", value, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("login_count >=", value, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountLessThan(Integer value) {
            addCriterion("login_count <", value, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountLessThanOrEqualTo(Integer value) {
            addCriterion("login_count <=", value, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountIn(List<Integer> values) {
            addCriterion("login_count in", values, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountNotIn(List<Integer> values) {
            addCriterion("login_count not in", values, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountBetween(Integer value1, Integer value2) {
            addCriterion("login_count between", value1, value2, "loginCount");
            return (Criteria) this;
        }

        public Criteria andLoginCountNotBetween(Integer value1, Integer value2) {
            addCriterion("login_count not between", value1, value2, "loginCount");
            return (Criteria) this;
        }

        public Criteria andInviteCodeIsNull() {
            addCriterion("invite_code is null");
            return (Criteria) this;
        }

        public Criteria andInviteCodeIsNotNull() {
            addCriterion("invite_code is not null");
            return (Criteria) this;
        }

        public Criteria andInviteCodeEqualTo(String value) {
            addCriterion("invite_code =", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotEqualTo(String value) {
            addCriterion("invite_code <>", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeGreaterThan(String value) {
            addCriterion("invite_code >", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("invite_code >=", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeLessThan(String value) {
            addCriterion("invite_code <", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeLessThanOrEqualTo(String value) {
            addCriterion("invite_code <=", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeLike(String value) {
            addCriterion("invite_code like", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotLike(String value) {
            addCriterion("invite_code not like", value, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeIn(List<String> values) {
            addCriterion("invite_code in", values, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotIn(List<String> values) {
            addCriterion("invite_code not in", values, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeBetween(String value1, String value2) {
            addCriterion("invite_code between", value1, value2, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andInviteCodeNotBetween(String value1, String value2) {
            addCriterion("invite_code not between", value1, value2, "inviteCode");
            return (Criteria) this;
        }

        public Criteria andHeadImgIsNull() {
            addCriterion("head_img is null");
            return (Criteria) this;
        }

        public Criteria andHeadImgIsNotNull() {
            addCriterion("head_img is not null");
            return (Criteria) this;
        }

        public Criteria andHeadImgEqualTo(String value) {
            addCriterion("head_img =", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotEqualTo(String value) {
            addCriterion("head_img <>", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgGreaterThan(String value) {
            addCriterion("head_img >", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgGreaterThanOrEqualTo(String value) {
            addCriterion("head_img >=", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgLessThan(String value) {
            addCriterion("head_img <", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgLessThanOrEqualTo(String value) {
            addCriterion("head_img <=", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgLike(String value) {
            addCriterion("head_img like", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotLike(String value) {
            addCriterion("head_img not like", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgIn(List<String> values) {
            addCriterion("head_img in", values, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotIn(List<String> values) {
            addCriterion("head_img not in", values, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgBetween(String value1, String value2) {
            addCriterion("head_img between", value1, value2, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotBetween(String value1, String value2) {
            addCriterion("head_img not between", value1, value2, "headImg");
            return (Criteria) this;
        }

        public Criteria andRolesIsNull() {
            addCriterion("roles is null");
            return (Criteria) this;
        }

        public Criteria andRolesIsNotNull() {
            addCriterion("roles is not null");
            return (Criteria) this;
        }

        public Criteria andRolesEqualTo(String value) {
            addCriterion("roles =", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotEqualTo(String value) {
            addCriterion("roles <>", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesGreaterThan(String value) {
            addCriterion("roles >", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesGreaterThanOrEqualTo(String value) {
            addCriterion("roles >=", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesLessThan(String value) {
            addCriterion("roles <", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesLessThanOrEqualTo(String value) {
            addCriterion("roles <=", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesLike(String value) {
            addCriterion("roles like", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotLike(String value) {
            addCriterion("roles not like", value, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesIn(List<String> values) {
            addCriterion("roles in", values, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotIn(List<String> values) {
            addCriterion("roles not in", values, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesBetween(String value1, String value2) {
            addCriterion("roles between", value1, value2, "roles");
            return (Criteria) this;
        }

        public Criteria andRolesNotBetween(String value1, String value2) {
            addCriterion("roles not between", value1, value2, "roles");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andIsDisabledIsNull() {
            addCriterion("is_disabled is null");
            return (Criteria) this;
        }

        public Criteria andIsDisabledIsNotNull() {
            addCriterion("is_disabled is not null");
            return (Criteria) this;
        }

        public Criteria andIsDisabledEqualTo(Boolean value) {
            addCriterion("is_disabled =", value, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledNotEqualTo(Boolean value) {
            addCriterion("is_disabled <>", value, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledGreaterThan(Boolean value) {
            addCriterion("is_disabled >", value, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_disabled >=", value, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledLessThan(Boolean value) {
            addCriterion("is_disabled <", value, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledLessThanOrEqualTo(Boolean value) {
            addCriterion("is_disabled <=", value, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledIn(List<Boolean> values) {
            addCriterion("is_disabled in", values, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledNotIn(List<Boolean> values) {
            addCriterion("is_disabled not in", values, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledBetween(Boolean value1, Boolean value2) {
            addCriterion("is_disabled between", value1, value2, "isDisabled");
            return (Criteria) this;
        }

        public Criteria andIsDisabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_disabled not between", value1, value2, "isDisabled");
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

        public Criteria andOpenIdIsNull() {
            addCriterion("open_id is null");
            return (Criteria) this;
        }

        public Criteria andOpenIdIsNotNull() {
            addCriterion("open_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpenIdEqualTo(String value) {
            addCriterion("open_id =", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotEqualTo(String value) {
            addCriterion("open_id <>", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThan(String value) {
            addCriterion("open_id >", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThanOrEqualTo(String value) {
            addCriterion("open_id >=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThan(String value) {
            addCriterion("open_id <", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThanOrEqualTo(String value) {
            addCriterion("open_id <=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLike(String value) {
            addCriterion("open_id like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotLike(String value) {
            addCriterion("open_id not like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdIn(List<String> values) {
            addCriterion("open_id in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotIn(List<String> values) {
            addCriterion("open_id not in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdBetween(String value1, String value2) {
            addCriterion("open_id between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotBetween(String value1, String value2) {
            addCriterion("open_id not between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andIsBindWxIsNull() {
            addCriterion("is_bind_wx is null");
            return (Criteria) this;
        }

        public Criteria andIsBindWxIsNotNull() {
            addCriterion("is_bind_wx is not null");
            return (Criteria) this;
        }

        public Criteria andIsBindWxEqualTo(Boolean value) {
            addCriterion("is_bind_wx =", value, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxNotEqualTo(Boolean value) {
            addCriterion("is_bind_wx <>", value, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxGreaterThan(Boolean value) {
            addCriterion("is_bind_wx >", value, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_bind_wx >=", value, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxLessThan(Boolean value) {
            addCriterion("is_bind_wx <", value, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxLessThanOrEqualTo(Boolean value) {
            addCriterion("is_bind_wx <=", value, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxIn(List<Boolean> values) {
            addCriterion("is_bind_wx in", values, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxNotIn(List<Boolean> values) {
            addCriterion("is_bind_wx not in", values, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxBetween(Boolean value1, Boolean value2) {
            addCriterion("is_bind_wx between", value1, value2, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andIsBindWxNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_bind_wx not between", value1, value2, "isBindWx");
            return (Criteria) this;
        }

        public Criteria andPointsIsNull() {
            addCriterion("points is null");
            return (Criteria) this;
        }

        public Criteria andPointsIsNotNull() {
            addCriterion("points is not null");
            return (Criteria) this;
        }

        public Criteria andPointsEqualTo(BigDecimal value) {
            addCriterion("points =", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotEqualTo(BigDecimal value) {
            addCriterion("points <>", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThan(BigDecimal value) {
            addCriterion("points >", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points >=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThan(BigDecimal value) {
            addCriterion("points <", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points <=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsIn(List<BigDecimal> values) {
            addCriterion("points in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotIn(List<BigDecimal> values) {
            addCriterion("points not in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points not between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andVipStatusIsNull() {
            addCriterion("vip_status is null");
            return (Criteria) this;
        }

        public Criteria andVipStatusIsNotNull() {
            addCriterion("vip_status is not null");
            return (Criteria) this;
        }

        public Criteria andVipStatusEqualTo(Integer value) {
            addCriterion("vip_status =", value, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusNotEqualTo(Integer value) {
            addCriterion("vip_status <>", value, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusGreaterThan(Integer value) {
            addCriterion("vip_status >", value, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("vip_status >=", value, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusLessThan(Integer value) {
            addCriterion("vip_status <", value, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusLessThanOrEqualTo(Integer value) {
            addCriterion("vip_status <=", value, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusIn(List<Integer> values) {
            addCriterion("vip_status in", values, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusNotIn(List<Integer> values) {
            addCriterion("vip_status not in", values, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusBetween(Integer value1, Integer value2) {
            addCriterion("vip_status between", value1, value2, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("vip_status not between", value1, value2, "vipStatus");
            return (Criteria) this;
        }

        public Criteria andVipTypeIsNull() {
            addCriterion("vip_type is null");
            return (Criteria) this;
        }

        public Criteria andVipTypeIsNotNull() {
            addCriterion("vip_type is not null");
            return (Criteria) this;
        }

        public Criteria andVipTypeEqualTo(Integer value) {
            addCriterion("vip_type =", value, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeNotEqualTo(Integer value) {
            addCriterion("vip_type <>", value, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeGreaterThan(Integer value) {
            addCriterion("vip_type >", value, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("vip_type >=", value, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeLessThan(Integer value) {
            addCriterion("vip_type <", value, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeLessThanOrEqualTo(Integer value) {
            addCriterion("vip_type <=", value, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeIn(List<Integer> values) {
            addCriterion("vip_type in", values, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeNotIn(List<Integer> values) {
            addCriterion("vip_type not in", values, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeBetween(Integer value1, Integer value2) {
            addCriterion("vip_type between", value1, value2, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("vip_type not between", value1, value2, "vipType");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeIsNull() {
            addCriterion("vip_start_time is null");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeIsNotNull() {
            addCriterion("vip_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeEqualTo(Date value) {
            addCriterion("vip_start_time =", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeNotEqualTo(Date value) {
            addCriterion("vip_start_time <>", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeGreaterThan(Date value) {
            addCriterion("vip_start_time >", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("vip_start_time >=", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeLessThan(Date value) {
            addCriterion("vip_start_time <", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("vip_start_time <=", value, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeIn(List<Date> values) {
            addCriterion("vip_start_time in", values, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeNotIn(List<Date> values) {
            addCriterion("vip_start_time not in", values, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeBetween(Date value1, Date value2) {
            addCriterion("vip_start_time between", value1, value2, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("vip_start_time not between", value1, value2, "vipStartTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeIsNull() {
            addCriterion("vip_end_time is null");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeIsNotNull() {
            addCriterion("vip_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeEqualTo(Date value) {
            addCriterion("vip_end_time =", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeNotEqualTo(Date value) {
            addCriterion("vip_end_time <>", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeGreaterThan(Date value) {
            addCriterion("vip_end_time >", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("vip_end_time >=", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeLessThan(Date value) {
            addCriterion("vip_end_time <", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("vip_end_time <=", value, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeIn(List<Date> values) {
            addCriterion("vip_end_time in", values, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeNotIn(List<Date> values) {
            addCriterion("vip_end_time not in", values, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeBetween(Date value1, Date value2) {
            addCriterion("vip_end_time between", value1, value2, "vipEndTime");
            return (Criteria) this;
        }

        public Criteria andVipEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("vip_end_time not between", value1, value2, "vipEndTime");
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

        public Criteria andVipNoIsNull() {
            addCriterion("vip_no is null");
            return (Criteria) this;
        }

        public Criteria andVipNoIsNotNull() {
            addCriterion("vip_no is not null");
            return (Criteria) this;
        }

        public Criteria andVipNoEqualTo(String value) {
            addCriterion("vip_no =", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoNotEqualTo(String value) {
            addCriterion("vip_no <>", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoGreaterThan(String value) {
            addCriterion("vip_no >", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoGreaterThanOrEqualTo(String value) {
            addCriterion("vip_no >=", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoLessThan(String value) {
            addCriterion("vip_no <", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoLessThanOrEqualTo(String value) {
            addCriterion("vip_no <=", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoLike(String value) {
            addCriterion("vip_no like", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoNotLike(String value) {
            addCriterion("vip_no not like", value, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoIn(List<String> values) {
            addCriterion("vip_no in", values, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoNotIn(List<String> values) {
            addCriterion("vip_no not in", values, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoBetween(String value1, String value2) {
            addCriterion("vip_no between", value1, value2, "vipNo");
            return (Criteria) this;
        }

        public Criteria andVipNoNotBetween(String value1, String value2) {
            addCriterion("vip_no not between", value1, value2, "vipNo");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleIsNull() {
            addCriterion("is_new_people is null");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleIsNotNull() {
            addCriterion("is_new_people is not null");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleEqualTo(Boolean value) {
            addCriterion("is_new_people =", value, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleNotEqualTo(Boolean value) {
            addCriterion("is_new_people <>", value, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleGreaterThan(Boolean value) {
            addCriterion("is_new_people >", value, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_new_people >=", value, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleLessThan(Boolean value) {
            addCriterion("is_new_people <", value, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_new_people <=", value, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleIn(List<Boolean> values) {
            addCriterion("is_new_people in", values, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleNotIn(List<Boolean> values) {
            addCriterion("is_new_people not in", values, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_new_people between", value1, value2, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsNewPeopleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_new_people not between", value1, value2, "isNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleIsNull() {
            addCriterion("is_remind_new_people is null");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleIsNotNull() {
            addCriterion("is_remind_new_people is not null");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleEqualTo(Boolean value) {
            addCriterion("is_remind_new_people =", value, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleNotEqualTo(Boolean value) {
            addCriterion("is_remind_new_people <>", value, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleGreaterThan(Boolean value) {
            addCriterion("is_remind_new_people >", value, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_remind_new_people >=", value, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleLessThan(Boolean value) {
            addCriterion("is_remind_new_people <", value, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleLessThanOrEqualTo(Boolean value) {
            addCriterion("is_remind_new_people <=", value, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleIn(List<Boolean> values) {
            addCriterion("is_remind_new_people in", values, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleNotIn(List<Boolean> values) {
            addCriterion("is_remind_new_people not in", values, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleBetween(Boolean value1, Boolean value2) {
            addCriterion("is_remind_new_people between", value1, value2, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andIsRemindNewPeopleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_remind_new_people not between", value1, value2, "isRemindNewPeople");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeIsNull() {
            addCriterion("last_use_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeIsNotNull() {
            addCriterion("last_use_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeEqualTo(Date value) {
            addCriterion("last_use_time =", value, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeNotEqualTo(Date value) {
            addCriterion("last_use_time <>", value, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeGreaterThan(Date value) {
            addCriterion("last_use_time >", value, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_use_time >=", value, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeLessThan(Date value) {
            addCriterion("last_use_time <", value, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_use_time <=", value, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeIn(List<Date> values) {
            addCriterion("last_use_time in", values, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeNotIn(List<Date> values) {
            addCriterion("last_use_time not in", values, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeBetween(Date value1, Date value2) {
            addCriterion("last_use_time between", value1, value2, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_use_time not between", value1, value2, "lastUseTime");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressIsNull() {
            addCriterion("last_use_address is null");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressIsNotNull() {
            addCriterion("last_use_address is not null");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressEqualTo(String value) {
            addCriterion("last_use_address =", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressNotEqualTo(String value) {
            addCriterion("last_use_address <>", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressGreaterThan(String value) {
            addCriterion("last_use_address >", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressGreaterThanOrEqualTo(String value) {
            addCriterion("last_use_address >=", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressLessThan(String value) {
            addCriterion("last_use_address <", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressLessThanOrEqualTo(String value) {
            addCriterion("last_use_address <=", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressLike(String value) {
            addCriterion("last_use_address like", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressNotLike(String value) {
            addCriterion("last_use_address not like", value, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressIn(List<String> values) {
            addCriterion("last_use_address in", values, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressNotIn(List<String> values) {
            addCriterion("last_use_address not in", values, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressBetween(String value1, String value2) {
            addCriterion("last_use_address between", value1, value2, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andLastUseAddressNotBetween(String value1, String value2) {
            addCriterion("last_use_address not between", value1, value2, "lastUseAddress");
            return (Criteria) this;
        }

        public Criteria andRegisterWayIsNull() {
            addCriterion("register_way is null");
            return (Criteria) this;
        }

        public Criteria andRegisterWayIsNotNull() {
            addCriterion("register_way is not null");
            return (Criteria) this;
        }

        public Criteria andRegisterWayEqualTo(Integer value) {
            addCriterion("register_way =", value, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayNotEqualTo(Integer value) {
            addCriterion("register_way <>", value, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayGreaterThan(Integer value) {
            addCriterion("register_way >", value, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayGreaterThanOrEqualTo(Integer value) {
            addCriterion("register_way >=", value, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayLessThan(Integer value) {
            addCriterion("register_way <", value, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayLessThanOrEqualTo(Integer value) {
            addCriterion("register_way <=", value, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayIn(List<Integer> values) {
            addCriterion("register_way in", values, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayNotIn(List<Integer> values) {
            addCriterion("register_way not in", values, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayBetween(Integer value1, Integer value2) {
            addCriterion("register_way between", value1, value2, "registerWay");
            return (Criteria) this;
        }

        public Criteria andRegisterWayNotBetween(Integer value1, Integer value2) {
            addCriterion("register_way not between", value1, value2, "registerWay");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdIsNull() {
            addCriterion("wx_public_platform_open_id is null");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdIsNotNull() {
            addCriterion("wx_public_platform_open_id is not null");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdEqualTo(String value) {
            addCriterion("wx_public_platform_open_id =", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdNotEqualTo(String value) {
            addCriterion("wx_public_platform_open_id <>", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdGreaterThan(String value) {
            addCriterion("wx_public_platform_open_id >", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdGreaterThanOrEqualTo(String value) {
            addCriterion("wx_public_platform_open_id >=", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdLessThan(String value) {
            addCriterion("wx_public_platform_open_id <", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdLessThanOrEqualTo(String value) {
            addCriterion("wx_public_platform_open_id <=", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdLike(String value) {
            addCriterion("wx_public_platform_open_id like", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdNotLike(String value) {
            addCriterion("wx_public_platform_open_id not like", value, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdIn(List<String> values) {
            addCriterion("wx_public_platform_open_id in", values, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdNotIn(List<String> values) {
            addCriterion("wx_public_platform_open_id not in", values, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdBetween(String value1, String value2) {
            addCriterion("wx_public_platform_open_id between", value1, value2, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andWxPublicPlatformOpenIdNotBetween(String value1, String value2) {
            addCriterion("wx_public_platform_open_id not between", value1, value2, "wxPublicPlatformOpenId");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyIsNull() {
            addCriterion("is_request_wx_notify is null");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyIsNotNull() {
            addCriterion("is_request_wx_notify is not null");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyEqualTo(Boolean value) {
            addCriterion("is_request_wx_notify =", value, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyNotEqualTo(Boolean value) {
            addCriterion("is_request_wx_notify <>", value, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyGreaterThan(Boolean value) {
            addCriterion("is_request_wx_notify >", value, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_request_wx_notify >=", value, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyLessThan(Boolean value) {
            addCriterion("is_request_wx_notify <", value, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyLessThanOrEqualTo(Boolean value) {
            addCriterion("is_request_wx_notify <=", value, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyIn(List<Boolean> values) {
            addCriterion("is_request_wx_notify in", values, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyNotIn(List<Boolean> values) {
            addCriterion("is_request_wx_notify not in", values, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyBetween(Boolean value1, Boolean value2) {
            addCriterion("is_request_wx_notify between", value1, value2, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andIsRequestWxNotifyNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_request_wx_notify not between", value1, value2, "isRequestWxNotify");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeIsNull() {
            addCriterion("last_request_wx_notify_time is null");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeIsNotNull() {
            addCriterion("last_request_wx_notify_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeEqualTo(Date value) {
            addCriterion("last_request_wx_notify_time =", value, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeNotEqualTo(Date value) {
            addCriterion("last_request_wx_notify_time <>", value, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeGreaterThan(Date value) {
            addCriterion("last_request_wx_notify_time >", value, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_request_wx_notify_time >=", value, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeLessThan(Date value) {
            addCriterion("last_request_wx_notify_time <", value, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_request_wx_notify_time <=", value, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeIn(List<Date> values) {
            addCriterion("last_request_wx_notify_time in", values, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeNotIn(List<Date> values) {
            addCriterion("last_request_wx_notify_time not in", values, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeBetween(Date value1, Date value2) {
            addCriterion("last_request_wx_notify_time between", value1, value2, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andLastRequestWxNotifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_request_wx_notify_time not between", value1, value2, "lastRequestWxNotifyTime");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountIsNull() {
            addCriterion("invite_reward_amount is null");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountIsNotNull() {
            addCriterion("invite_reward_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountEqualTo(BigDecimal value) {
            addCriterion("invite_reward_amount =", value, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountNotEqualTo(BigDecimal value) {
            addCriterion("invite_reward_amount <>", value, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountGreaterThan(BigDecimal value) {
            addCriterion("invite_reward_amount >", value, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invite_reward_amount >=", value, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountLessThan(BigDecimal value) {
            addCriterion("invite_reward_amount <", value, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invite_reward_amount <=", value, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountIn(List<BigDecimal> values) {
            addCriterion("invite_reward_amount in", values, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountNotIn(List<BigDecimal> values) {
            addCriterion("invite_reward_amount not in", values, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invite_reward_amount between", value1, value2, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andInviteRewardAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invite_reward_amount not between", value1, value2, "inviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNull() {
            addCriterion("real_name is null");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNotNull() {
            addCriterion("real_name is not null");
            return (Criteria) this;
        }

        public Criteria andRealNameEqualTo(String value) {
            addCriterion("real_name =", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotEqualTo(String value) {
            addCriterion("real_name <>", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThan(String value) {
            addCriterion("real_name >", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThanOrEqualTo(String value) {
            addCriterion("real_name >=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThan(String value) {
            addCriterion("real_name <", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThanOrEqualTo(String value) {
            addCriterion("real_name <=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLike(String value) {
            addCriterion("real_name like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotLike(String value) {
            addCriterion("real_name not like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameIn(List<String> values) {
            addCriterion("real_name in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotIn(List<String> values) {
            addCriterion("real_name not in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameBetween(String value1, String value2) {
            addCriterion("real_name between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotBetween(String value1, String value2) {
            addCriterion("real_name not between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceIsNull() {
            addCriterion("total_balance is null");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceIsNotNull() {
            addCriterion("total_balance is not null");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceEqualTo(BigDecimal value) {
            addCriterion("total_balance =", value, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceNotEqualTo(BigDecimal value) {
            addCriterion("total_balance <>", value, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceGreaterThan(BigDecimal value) {
            addCriterion("total_balance >", value, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_balance >=", value, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceLessThan(BigDecimal value) {
            addCriterion("total_balance <", value, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_balance <=", value, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceIn(List<BigDecimal> values) {
            addCriterion("total_balance in", values, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceNotIn(List<BigDecimal> values) {
            addCriterion("total_balance not in", values, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_balance between", value1, value2, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_balance not between", value1, value2, "totalBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceIsNull() {
            addCriterion("total_consume_balance is null");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceIsNotNull() {
            addCriterion("total_consume_balance is not null");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceEqualTo(BigDecimal value) {
            addCriterion("total_consume_balance =", value, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceNotEqualTo(BigDecimal value) {
            addCriterion("total_consume_balance <>", value, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceGreaterThan(BigDecimal value) {
            addCriterion("total_consume_balance >", value, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_consume_balance >=", value, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceLessThan(BigDecimal value) {
            addCriterion("total_consume_balance <", value, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_consume_balance <=", value, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceIn(List<BigDecimal> values) {
            addCriterion("total_consume_balance in", values, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceNotIn(List<BigDecimal> values) {
            addCriterion("total_consume_balance not in", values, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_consume_balance between", value1, value2, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalConsumeBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_consume_balance not between", value1, value2, "totalConsumeBalance");
            return (Criteria) this;
        }

        public Criteria andTotalPointsIsNull() {
            addCriterion("total_points is null");
            return (Criteria) this;
        }

        public Criteria andTotalPointsIsNotNull() {
            addCriterion("total_points is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPointsEqualTo(BigDecimal value) {
            addCriterion("total_points =", value, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsNotEqualTo(BigDecimal value) {
            addCriterion("total_points <>", value, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsGreaterThan(BigDecimal value) {
            addCriterion("total_points >", value, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_points >=", value, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsLessThan(BigDecimal value) {
            addCriterion("total_points <", value, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_points <=", value, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsIn(List<BigDecimal> values) {
            addCriterion("total_points in", values, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsNotIn(List<BigDecimal> values) {
            addCriterion("total_points not in", values, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_points between", value1, value2, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalPointsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_points not between", value1, value2, "totalPoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsIsNull() {
            addCriterion("total_consume_points is null");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsIsNotNull() {
            addCriterion("total_consume_points is not null");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsEqualTo(BigDecimal value) {
            addCriterion("total_consume_points =", value, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsNotEqualTo(BigDecimal value) {
            addCriterion("total_consume_points <>", value, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsGreaterThan(BigDecimal value) {
            addCriterion("total_consume_points >", value, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_consume_points >=", value, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsLessThan(BigDecimal value) {
            addCriterion("total_consume_points <", value, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_consume_points <=", value, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsIn(List<BigDecimal> values) {
            addCriterion("total_consume_points in", values, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsNotIn(List<BigDecimal> values) {
            addCriterion("total_consume_points not in", values, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_consume_points between", value1, value2, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalConsumePointsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_consume_points not between", value1, value2, "totalConsumePoints");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountIsNull() {
            addCriterion("total_withdraw_invite_reward_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountIsNotNull() {
            addCriterion("total_withdraw_invite_reward_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountEqualTo(BigDecimal value) {
            addCriterion("total_withdraw_invite_reward_amount =", value, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountNotEqualTo(BigDecimal value) {
            addCriterion("total_withdraw_invite_reward_amount <>", value, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountGreaterThan(BigDecimal value) {
            addCriterion("total_withdraw_invite_reward_amount >", value, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_withdraw_invite_reward_amount >=", value, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountLessThan(BigDecimal value) {
            addCriterion("total_withdraw_invite_reward_amount <", value, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_withdraw_invite_reward_amount <=", value, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountIn(List<BigDecimal> values) {
            addCriterion("total_withdraw_invite_reward_amount in", values, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountNotIn(List<BigDecimal> values) {
            addCriterion("total_withdraw_invite_reward_amount not in", values, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_withdraw_invite_reward_amount between", value1, value2, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andTotalWithdrawInviteRewardAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_withdraw_invite_reward_amount not between", value1, value2, "totalWithdrawInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordIsNull() {
            addCriterion("payment_password is null");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordIsNotNull() {
            addCriterion("payment_password is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordEqualTo(String value) {
            addCriterion("payment_password =", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordNotEqualTo(String value) {
            addCriterion("payment_password <>", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordGreaterThan(String value) {
            addCriterion("payment_password >", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("payment_password >=", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordLessThan(String value) {
            addCriterion("payment_password <", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordLessThanOrEqualTo(String value) {
            addCriterion("payment_password <=", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordLike(String value) {
            addCriterion("payment_password like", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordNotLike(String value) {
            addCriterion("payment_password not like", value, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordIn(List<String> values) {
            addCriterion("payment_password in", values, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordNotIn(List<String> values) {
            addCriterion("payment_password not in", values, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordBetween(String value1, String value2) {
            addCriterion("payment_password between", value1, value2, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordNotBetween(String value1, String value2) {
            addCriterion("payment_password not between", value1, value2, "paymentPassword");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltIsNull() {
            addCriterion("payment_password_salt is null");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltIsNotNull() {
            addCriterion("payment_password_salt is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltEqualTo(String value) {
            addCriterion("payment_password_salt =", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltNotEqualTo(String value) {
            addCriterion("payment_password_salt <>", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltGreaterThan(String value) {
            addCriterion("payment_password_salt >", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltGreaterThanOrEqualTo(String value) {
            addCriterion("payment_password_salt >=", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltLessThan(String value) {
            addCriterion("payment_password_salt <", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltLessThanOrEqualTo(String value) {
            addCriterion("payment_password_salt <=", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltLike(String value) {
            addCriterion("payment_password_salt like", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltNotLike(String value) {
            addCriterion("payment_password_salt not like", value, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltIn(List<String> values) {
            addCriterion("payment_password_salt in", values, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltNotIn(List<String> values) {
            addCriterion("payment_password_salt not in", values, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltBetween(String value1, String value2) {
            addCriterion("payment_password_salt between", value1, value2, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andPaymentPasswordSaltNotBetween(String value1, String value2) {
            addCriterion("payment_password_salt not between", value1, value2, "paymentPasswordSalt");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeIsNull() {
            addCriterion("invite_suncode is null");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeIsNotNull() {
            addCriterion("invite_suncode is not null");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeEqualTo(String value) {
            addCriterion("invite_suncode =", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeNotEqualTo(String value) {
            addCriterion("invite_suncode <>", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeGreaterThan(String value) {
            addCriterion("invite_suncode >", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeGreaterThanOrEqualTo(String value) {
            addCriterion("invite_suncode >=", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeLessThan(String value) {
            addCriterion("invite_suncode <", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeLessThanOrEqualTo(String value) {
            addCriterion("invite_suncode <=", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeLike(String value) {
            addCriterion("invite_suncode like", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeNotLike(String value) {
            addCriterion("invite_suncode not like", value, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeIn(List<String> values) {
            addCriterion("invite_suncode in", values, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeNotIn(List<String> values) {
            addCriterion("invite_suncode not in", values, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeBetween(String value1, String value2) {
            addCriterion("invite_suncode between", value1, value2, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andInviteSuncodeNotBetween(String value1, String value2) {
            addCriterion("invite_suncode not between", value1, value2, "inviteSuncode");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsIsNull() {
            addCriterion("unreceived_points is null");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsIsNotNull() {
            addCriterion("unreceived_points is not null");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsEqualTo(BigDecimal value) {
            addCriterion("unreceived_points =", value, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsNotEqualTo(BigDecimal value) {
            addCriterion("unreceived_points <>", value, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsGreaterThan(BigDecimal value) {
            addCriterion("unreceived_points >", value, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unreceived_points >=", value, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsLessThan(BigDecimal value) {
            addCriterion("unreceived_points <", value, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unreceived_points <=", value, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsIn(List<BigDecimal> values) {
            addCriterion("unreceived_points in", values, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsNotIn(List<BigDecimal> values) {
            addCriterion("unreceived_points not in", values, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unreceived_points between", value1, value2, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedPointsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unreceived_points not between", value1, value2, "unreceivedPoints");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountIsNull() {
            addCriterion("unreceived_invite_reward_amount is null");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountIsNotNull() {
            addCriterion("unreceived_invite_reward_amount is not null");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountEqualTo(BigDecimal value) {
            addCriterion("unreceived_invite_reward_amount =", value, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountNotEqualTo(BigDecimal value) {
            addCriterion("unreceived_invite_reward_amount <>", value, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountGreaterThan(BigDecimal value) {
            addCriterion("unreceived_invite_reward_amount >", value, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unreceived_invite_reward_amount >=", value, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountLessThan(BigDecimal value) {
            addCriterion("unreceived_invite_reward_amount <", value, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unreceived_invite_reward_amount <=", value, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountIn(List<BigDecimal> values) {
            addCriterion("unreceived_invite_reward_amount in", values, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountNotIn(List<BigDecimal> values) {
            addCriterion("unreceived_invite_reward_amount not in", values, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unreceived_invite_reward_amount between", value1, value2, "unreceivedInviteRewardAmount");
            return (Criteria) this;
        }

        public Criteria andUnreceivedInviteRewardAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unreceived_invite_reward_amount not between", value1, value2, "unreceivedInviteRewardAmount");
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

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("last_login_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("last_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(Date value) {
            addCriterion("last_login_time =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(Date value) {
            addCriterion("last_login_time <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(Date value) {
            addCriterion("last_login_time >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_login_time >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(Date value) {
            addCriterion("last_login_time <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_login_time <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<Date> values) {
            addCriterion("last_login_time in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<Date> values) {
            addCriterion("last_login_time not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(Date value1, Date value2) {
            addCriterion("last_login_time between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_login_time not between", value1, value2, "lastLoginTime");
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