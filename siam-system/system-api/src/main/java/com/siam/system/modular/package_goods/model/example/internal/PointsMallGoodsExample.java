package com.siam.system.modular.package_goods.model.example.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointsMallGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PointsMallGoodsExample() {
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

        public Criteria andMainImageIsNull() {
            addCriterion("main_image is null");
            return (Criteria) this;
        }

        public Criteria andMainImageIsNotNull() {
            addCriterion("main_image is not null");
            return (Criteria) this;
        }

        public Criteria andMainImageEqualTo(String value) {
            addCriterion("main_image =", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotEqualTo(String value) {
            addCriterion("main_image <>", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageGreaterThan(String value) {
            addCriterion("main_image >", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageGreaterThanOrEqualTo(String value) {
            addCriterion("main_image >=", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageLessThan(String value) {
            addCriterion("main_image <", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageLessThanOrEqualTo(String value) {
            addCriterion("main_image <=", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageLike(String value) {
            addCriterion("main_image like", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotLike(String value) {
            addCriterion("main_image not like", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageIn(List<String> values) {
            addCriterion("main_image in", values, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotIn(List<String> values) {
            addCriterion("main_image not in", values, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageBetween(String value1, String value2) {
            addCriterion("main_image between", value1, value2, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotBetween(String value1, String value2) {
            addCriterion("main_image not between", value1, value2, "mainImage");
            return (Criteria) this;
        }

        public Criteria andSubImagesIsNull() {
            addCriterion("sub_images is null");
            return (Criteria) this;
        }

        public Criteria andSubImagesIsNotNull() {
            addCriterion("sub_images is not null");
            return (Criteria) this;
        }

        public Criteria andSubImagesEqualTo(String value) {
            addCriterion("sub_images =", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesNotEqualTo(String value) {
            addCriterion("sub_images <>", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesGreaterThan(String value) {
            addCriterion("sub_images >", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesGreaterThanOrEqualTo(String value) {
            addCriterion("sub_images >=", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesLessThan(String value) {
            addCriterion("sub_images <", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesLessThanOrEqualTo(String value) {
            addCriterion("sub_images <=", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesLike(String value) {
            addCriterion("sub_images like", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesNotLike(String value) {
            addCriterion("sub_images not like", value, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesIn(List<String> values) {
            addCriterion("sub_images in", values, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesNotIn(List<String> values) {
            addCriterion("sub_images not in", values, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesBetween(String value1, String value2) {
            addCriterion("sub_images between", value1, value2, "subImages");
            return (Criteria) this;
        }

        public Criteria andSubImagesNotBetween(String value1, String value2) {
            addCriterion("sub_images not between", value1, value2, "subImages");
            return (Criteria) this;
        }

        public Criteria andDetailIsNull() {
            addCriterion("detail is null");
            return (Criteria) this;
        }

        public Criteria andDetailIsNotNull() {
            addCriterion("detail is not null");
            return (Criteria) this;
        }

        public Criteria andDetailEqualTo(String value) {
            addCriterion("detail =", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotEqualTo(String value) {
            addCriterion("detail <>", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThan(String value) {
            addCriterion("detail >", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThanOrEqualTo(String value) {
            addCriterion("detail >=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThan(String value) {
            addCriterion("detail <", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThanOrEqualTo(String value) {
            addCriterion("detail <=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLike(String value) {
            addCriterion("detail like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotLike(String value) {
            addCriterion("detail not like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailIn(List<String> values) {
            addCriterion("detail in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotIn(List<String> values) {
            addCriterion("detail not in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailBetween(String value1, String value2) {
            addCriterion("detail between", value1, value2, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotBetween(String value1, String value2) {
            addCriterion("detail not between", value1, value2, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailImagesIsNull() {
            addCriterion("detail_images is null");
            return (Criteria) this;
        }

        public Criteria andDetailImagesIsNotNull() {
            addCriterion("detail_images is not null");
            return (Criteria) this;
        }

        public Criteria andDetailImagesEqualTo(String value) {
            addCriterion("detail_images =", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesNotEqualTo(String value) {
            addCriterion("detail_images <>", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesGreaterThan(String value) {
            addCriterion("detail_images >", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesGreaterThanOrEqualTo(String value) {
            addCriterion("detail_images >=", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesLessThan(String value) {
            addCriterion("detail_images <", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesLessThanOrEqualTo(String value) {
            addCriterion("detail_images <=", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesLike(String value) {
            addCriterion("detail_images like", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesNotLike(String value) {
            addCriterion("detail_images not like", value, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesIn(List<String> values) {
            addCriterion("detail_images in", values, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesNotIn(List<String> values) {
            addCriterion("detail_images not in", values, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesBetween(String value1, String value2) {
            addCriterion("detail_images between", value1, value2, "detailImages");
            return (Criteria) this;
        }

        public Criteria andDetailImagesNotBetween(String value1, String value2) {
            addCriterion("detail_images not between", value1, value2, "detailImages");
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

        public Criteria andStockIsNull() {
            addCriterion("stock is null");
            return (Criteria) this;
        }

        public Criteria andStockIsNotNull() {
            addCriterion("stock is not null");
            return (Criteria) this;
        }

        public Criteria andStockEqualTo(Integer value) {
            addCriterion("stock =", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockNotEqualTo(Integer value) {
            addCriterion("stock <>", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockGreaterThan(Integer value) {
            addCriterion("stock >", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock >=", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockLessThan(Integer value) {
            addCriterion("stock <", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockLessThanOrEqualTo(Integer value) {
            addCriterion("stock <=", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockIn(List<Integer> values) {
            addCriterion("stock in", values, "stock");
            return (Criteria) this;
        }

        public Criteria andStockNotIn(List<Integer> values) {
            addCriterion("stock not in", values, "stock");
            return (Criteria) this;
        }

        public Criteria andStockBetween(Integer value1, Integer value2) {
            addCriterion("stock between", value1, value2, "stock");
            return (Criteria) this;
        }

        public Criteria andStockNotBetween(Integer value1, Integer value2) {
            addCriterion("stock not between", value1, value2, "stock");
            return (Criteria) this;
        }

        public Criteria andIsHotIsNull() {
            addCriterion("is_hot is null");
            return (Criteria) this;
        }

        public Criteria andIsHotIsNotNull() {
            addCriterion("is_hot is not null");
            return (Criteria) this;
        }

        public Criteria andIsHotEqualTo(Boolean value) {
            addCriterion("is_hot =", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotNotEqualTo(Boolean value) {
            addCriterion("is_hot <>", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotGreaterThan(Boolean value) {
            addCriterion("is_hot >", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_hot >=", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotLessThan(Boolean value) {
            addCriterion("is_hot <", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotLessThanOrEqualTo(Boolean value) {
            addCriterion("is_hot <=", value, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotIn(List<Boolean> values) {
            addCriterion("is_hot in", values, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotNotIn(List<Boolean> values) {
            addCriterion("is_hot not in", values, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_hot between", value1, value2, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsHotNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_hot not between", value1, value2, "isHot");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNull() {
            addCriterion("is_new is null");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNotNull() {
            addCriterion("is_new is not null");
            return (Criteria) this;
        }

        public Criteria andIsNewEqualTo(Boolean value) {
            addCriterion("is_new =", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotEqualTo(Boolean value) {
            addCriterion("is_new <>", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThan(Boolean value) {
            addCriterion("is_new >", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_new >=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThan(Boolean value) {
            addCriterion("is_new <", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThanOrEqualTo(Boolean value) {
            addCriterion("is_new <=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewIn(List<Boolean> values) {
            addCriterion("is_new in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotIn(List<Boolean> values) {
            addCriterion("is_new not in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewBetween(Boolean value1, Boolean value2) {
            addCriterion("is_new between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_new not between", value1, value2, "isNew");
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

        public Criteria andMonthlySalesIsNull() {
            addCriterion("monthly_sales is null");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesIsNotNull() {
            addCriterion("monthly_sales is not null");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesEqualTo(Integer value) {
            addCriterion("monthly_sales =", value, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesNotEqualTo(Integer value) {
            addCriterion("monthly_sales <>", value, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesGreaterThan(Integer value) {
            addCriterion("monthly_sales >", value, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesGreaterThanOrEqualTo(Integer value) {
            addCriterion("monthly_sales >=", value, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesLessThan(Integer value) {
            addCriterion("monthly_sales <", value, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesLessThanOrEqualTo(Integer value) {
            addCriterion("monthly_sales <=", value, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesIn(List<Integer> values) {
            addCriterion("monthly_sales in", values, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesNotIn(List<Integer> values) {
            addCriterion("monthly_sales not in", values, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesBetween(Integer value1, Integer value2) {
            addCriterion("monthly_sales between", value1, value2, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andMonthlySalesNotBetween(Integer value1, Integer value2) {
            addCriterion("monthly_sales not between", value1, value2, "monthlySales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesIsNull() {
            addCriterion("total_sales is null");
            return (Criteria) this;
        }

        public Criteria andTotalSalesIsNotNull() {
            addCriterion("total_sales is not null");
            return (Criteria) this;
        }

        public Criteria andTotalSalesEqualTo(Integer value) {
            addCriterion("total_sales =", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesNotEqualTo(Integer value) {
            addCriterion("total_sales <>", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesGreaterThan(Integer value) {
            addCriterion("total_sales >", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_sales >=", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesLessThan(Integer value) {
            addCriterion("total_sales <", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesLessThanOrEqualTo(Integer value) {
            addCriterion("total_sales <=", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesIn(List<Integer> values) {
            addCriterion("total_sales in", values, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesNotIn(List<Integer> values) {
            addCriterion("total_sales not in", values, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesBetween(Integer value1, Integer value2) {
            addCriterion("total_sales between", value1, value2, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesNotBetween(Integer value1, Integer value2) {
            addCriterion("total_sales not between", value1, value2, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsIsNull() {
            addCriterion("total_comments is null");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsIsNotNull() {
            addCriterion("total_comments is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsEqualTo(Integer value) {
            addCriterion("total_comments =", value, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsNotEqualTo(Integer value) {
            addCriterion("total_comments <>", value, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsGreaterThan(Integer value) {
            addCriterion("total_comments >", value, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_comments >=", value, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsLessThan(Integer value) {
            addCriterion("total_comments <", value, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsLessThanOrEqualTo(Integer value) {
            addCriterion("total_comments <=", value, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsIn(List<Integer> values) {
            addCriterion("total_comments in", values, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsNotIn(List<Integer> values) {
            addCriterion("total_comments not in", values, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsBetween(Integer value1, Integer value2) {
            addCriterion("total_comments between", value1, value2, "totalComments");
            return (Criteria) this;
        }

        public Criteria andTotalCommentsNotBetween(Integer value1, Integer value2) {
            addCriterion("total_comments not between", value1, value2, "totalComments");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameIsNull() {
            addCriterion("preferential_name is null");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameIsNotNull() {
            addCriterion("preferential_name is not null");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameEqualTo(String value) {
            addCriterion("preferential_name =", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameNotEqualTo(String value) {
            addCriterion("preferential_name <>", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameGreaterThan(String value) {
            addCriterion("preferential_name >", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameGreaterThanOrEqualTo(String value) {
            addCriterion("preferential_name >=", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameLessThan(String value) {
            addCriterion("preferential_name <", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameLessThanOrEqualTo(String value) {
            addCriterion("preferential_name <=", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameLike(String value) {
            addCriterion("preferential_name like", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameNotLike(String value) {
            addCriterion("preferential_name not like", value, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameIn(List<String> values) {
            addCriterion("preferential_name in", values, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameNotIn(List<String> values) {
            addCriterion("preferential_name not in", values, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameBetween(String value1, String value2) {
            addCriterion("preferential_name between", value1, value2, "preferentialName");
            return (Criteria) this;
        }

        public Criteria andPreferentialNameNotBetween(String value1, String value2) {
            addCriterion("preferential_name not between", value1, value2, "preferentialName");
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

        public Criteria andProductTimeIsNull() {
            addCriterion("product_time is null");
            return (Criteria) this;
        }

        public Criteria andProductTimeIsNotNull() {
            addCriterion("product_time is not null");
            return (Criteria) this;
        }

        public Criteria andProductTimeEqualTo(BigDecimal value) {
            addCriterion("product_time =", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotEqualTo(BigDecimal value) {
            addCriterion("product_time <>", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeGreaterThan(BigDecimal value) {
            addCriterion("product_time >", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("product_time >=", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeLessThan(BigDecimal value) {
            addCriterion("product_time <", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("product_time <=", value, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeIn(List<BigDecimal> values) {
            addCriterion("product_time in", values, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotIn(List<BigDecimal> values) {
            addCriterion("product_time not in", values, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("product_time between", value1, value2, "productTime");
            return (Criteria) this;
        }

        public Criteria andProductTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("product_time not between", value1, value2, "productTime");
            return (Criteria) this;
        }

        public Criteria andExchangePointsIsNull() {
            addCriterion("exchange_points is null");
            return (Criteria) this;
        }

        public Criteria andExchangePointsIsNotNull() {
            addCriterion("exchange_points is not null");
            return (Criteria) this;
        }

        public Criteria andExchangePointsEqualTo(Integer value) {
            addCriterion("exchange_points =", value, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsNotEqualTo(Integer value) {
            addCriterion("exchange_points <>", value, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsGreaterThan(Integer value) {
            addCriterion("exchange_points >", value, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsGreaterThanOrEqualTo(Integer value) {
            addCriterion("exchange_points >=", value, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsLessThan(Integer value) {
            addCriterion("exchange_points <", value, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsLessThanOrEqualTo(Integer value) {
            addCriterion("exchange_points <=", value, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsIn(List<Integer> values) {
            addCriterion("exchange_points in", values, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsNotIn(List<Integer> values) {
            addCriterion("exchange_points not in", values, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsBetween(Integer value1, Integer value2) {
            addCriterion("exchange_points between", value1, value2, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andExchangePointsNotBetween(Integer value1, Integer value2) {
            addCriterion("exchange_points not between", value1, value2, "exchangePoints");
            return (Criteria) this;
        }

        public Criteria andSortNumberIsNull() {
            addCriterion("sort_number is null");
            return (Criteria) this;
        }

        public Criteria andSortNumberIsNotNull() {
            addCriterion("sort_number is not null");
            return (Criteria) this;
        }

        public Criteria andSortNumberEqualTo(Integer value) {
            addCriterion("sort_number =", value, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberNotEqualTo(Integer value) {
            addCriterion("sort_number <>", value, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberGreaterThan(Integer value) {
            addCriterion("sort_number >", value, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_number >=", value, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberLessThan(Integer value) {
            addCriterion("sort_number <", value, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberLessThanOrEqualTo(Integer value) {
            addCriterion("sort_number <=", value, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberIn(List<Integer> values) {
            addCriterion("sort_number in", values, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberNotIn(List<Integer> values) {
            addCriterion("sort_number not in", values, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberBetween(Integer value1, Integer value2) {
            addCriterion("sort_number between", value1, value2, "sortNumber");
            return (Criteria) this;
        }

        public Criteria andSortNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_number not between", value1, value2, "sortNumber");
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