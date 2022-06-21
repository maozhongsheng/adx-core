package com.mk.adx.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataAllExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DataAllExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andReq_idIsNull() {
            addCriterion("req_id is null");
            return (Criteria) this;
        }

        public Criteria andReq_idIsNotNull() {
            addCriterion("req_id is not null");
            return (Criteria) this;
        }

        public Criteria andReq_idEqualTo(String value) {
            addCriterion("req_id =", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idNotEqualTo(String value) {
            addCriterion("req_id <>", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idGreaterThan(String value) {
            addCriterion("req_id >", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idGreaterThanOrEqualTo(String value) {
            addCriterion("req_id >=", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idLessThan(String value) {
            addCriterion("req_id <", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idLessThanOrEqualTo(String value) {
            addCriterion("req_id <=", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idLike(String value) {
            addCriterion("req_id like", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idNotLike(String value) {
            addCriterion("req_id not like", value, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idIn(List<String> values) {
            addCriterion("req_id in", values, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idNotIn(List<String> values) {
            addCriterion("req_id not in", values, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idBetween(String value1, String value2) {
            addCriterion("req_id between", value1, value2, "req_id");
            return (Criteria) this;
        }

        public Criteria andReq_idNotBetween(String value1, String value2) {
            addCriterion("req_id not between", value1, value2, "req_id");
            return (Criteria) this;
        }

        public Criteria andDate_hourIsNull() {
            addCriterion("date_hour is null");
            return (Criteria) this;
        }

        public Criteria andDate_hourIsNotNull() {
            addCriterion("date_hour is not null");
            return (Criteria) this;
        }

        public Criteria andDate_hourEqualTo(String value) {
            addCriterion("date_hour =", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourNotEqualTo(String value) {
            addCriterion("date_hour <>", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourGreaterThan(String value) {
            addCriterion("date_hour >", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourGreaterThanOrEqualTo(String value) {
            addCriterion("date_hour >=", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourLessThan(String value) {
            addCriterion("date_hour <", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourLessThanOrEqualTo(String value) {
            addCriterion("date_hour <=", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourLike(String value) {
            addCriterion("date_hour like", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourNotLike(String value) {
            addCriterion("date_hour not like", value, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourIn(List<String> values) {
            addCriterion("date_hour in", values, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourNotIn(List<String> values) {
            addCriterion("date_hour not in", values, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourBetween(String value1, String value2) {
            addCriterion("date_hour between", value1, value2, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDate_hourNotBetween(String value1, String value2) {
            addCriterion("date_hour not between", value1, value2, "date_hour");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("`date` is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("`date` is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterion("`date` =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterion("`date` <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterion("`date` >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterion("`date` >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterion("`date` <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterion("`date` <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterion("`date` in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterion("`date` not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterion("`date` between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterion("`date` not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andReq_sysIsNull() {
            addCriterion("req_sys is null");
            return (Criteria) this;
        }

        public Criteria andReq_sysIsNotNull() {
            addCriterion("req_sys is not null");
            return (Criteria) this;
        }

        public Criteria andReq_sysEqualTo(Long value) {
            addCriterion("req_sys =", value, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysNotEqualTo(Long value) {
            addCriterion("req_sys <>", value, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysGreaterThan(Long value) {
            addCriterion("req_sys >", value, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysGreaterThanOrEqualTo(Long value) {
            addCriterion("req_sys >=", value, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysLessThan(Long value) {
            addCriterion("req_sys <", value, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysLessThanOrEqualTo(Long value) {
            addCriterion("req_sys <=", value, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysIn(List<Long> values) {
            addCriterion("req_sys in", values, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysNotIn(List<Long> values) {
            addCriterion("req_sys not in", values, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysBetween(Long value1, Long value2) {
            addCriterion("req_sys between", value1, value2, "req_sys");
            return (Criteria) this;
        }

        public Criteria andReq_sysNotBetween(Long value1, Long value2) {
            addCriterion("req_sys not between", value1, value2, "req_sys");
            return (Criteria) this;
        }

        public Criteria andAd_idIsNull() {
            addCriterion("ad_id is null");
            return (Criteria) this;
        }

        public Criteria andAd_idIsNotNull() {
            addCriterion("ad_id is not null");
            return (Criteria) this;
        }

        public Criteria andAd_idEqualTo(Integer value) {
            addCriterion("ad_id =", value, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idNotEqualTo(Integer value) {
            addCriterion("ad_id <>", value, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idGreaterThan(Integer value) {
            addCriterion("ad_id >", value, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("ad_id >=", value, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idLessThan(Integer value) {
            addCriterion("ad_id <", value, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idLessThanOrEqualTo(Integer value) {
            addCriterion("ad_id <=", value, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idIn(List<Integer> values) {
            addCriterion("ad_id in", values, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idNotIn(List<Integer> values) {
            addCriterion("ad_id not in", values, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idBetween(Integer value1, Integer value2) {
            addCriterion("ad_id between", value1, value2, "ad_id");
            return (Criteria) this;
        }

        public Criteria andAd_idNotBetween(Integer value1, Integer value2) {
            addCriterion("ad_id not between", value1, value2, "ad_id");
            return (Criteria) this;
        }

        public Criteria andPos_idIsNull() {
            addCriterion("pos_id is null");
            return (Criteria) this;
        }

        public Criteria andPos_idIsNotNull() {
            addCriterion("pos_id is not null");
            return (Criteria) this;
        }

        public Criteria andPos_idEqualTo(Integer value) {
            addCriterion("pos_id =", value, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idNotEqualTo(Integer value) {
            addCriterion("pos_id <>", value, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idGreaterThan(Integer value) {
            addCriterion("pos_id >", value, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("pos_id >=", value, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idLessThan(Integer value) {
            addCriterion("pos_id <", value, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idLessThanOrEqualTo(Integer value) {
            addCriterion("pos_id <=", value, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idIn(List<Integer> values) {
            addCriterion("pos_id in", values, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idNotIn(List<Integer> values) {
            addCriterion("pos_id not in", values, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idBetween(Integer value1, Integer value2) {
            addCriterion("pos_id between", value1, value2, "pos_id");
            return (Criteria) this;
        }

        public Criteria andPos_idNotBetween(Integer value1, Integer value2) {
            addCriterion("pos_id not between", value1, value2, "pos_id");
            return (Criteria) this;
        }

        public Criteria andSlot_typeIsNull() {
            addCriterion("slot_type is null");
            return (Criteria) this;
        }

        public Criteria andSlot_typeIsNotNull() {
            addCriterion("slot_type is not null");
            return (Criteria) this;
        }

        public Criteria andSlot_typeEqualTo(Integer value) {
            addCriterion("slot_type =", value, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeNotEqualTo(Integer value) {
            addCriterion("slot_type <>", value, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeGreaterThan(Integer value) {
            addCriterion("slot_type >", value, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeGreaterThanOrEqualTo(Integer value) {
            addCriterion("slot_type >=", value, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeLessThan(Integer value) {
            addCriterion("slot_type <", value, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeLessThanOrEqualTo(Integer value) {
            addCriterion("slot_type <=", value, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeIn(List<Integer> values) {
            addCriterion("slot_type in", values, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeNotIn(List<Integer> values) {
            addCriterion("slot_type not in", values, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeBetween(Integer value1, Integer value2) {
            addCriterion("slot_type between", value1, value2, "slot_type");
            return (Criteria) this;
        }

        public Criteria andSlot_typeNotBetween(Integer value1, Integer value2) {
            addCriterion("slot_type not between", value1, value2, "slot_type");
            return (Criteria) this;
        }

        public Criteria andMedia_idIsNull() {
            addCriterion("media_id is null");
            return (Criteria) this;
        }

        public Criteria andMedia_idIsNotNull() {
            addCriterion("media_id is not null");
            return (Criteria) this;
        }

        public Criteria andMedia_idEqualTo(Integer value) {
            addCriterion("media_id =", value, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idNotEqualTo(Integer value) {
            addCriterion("media_id <>", value, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idGreaterThan(Integer value) {
            addCriterion("media_id >", value, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("media_id >=", value, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idLessThan(Integer value) {
            addCriterion("media_id <", value, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idLessThanOrEqualTo(Integer value) {
            addCriterion("media_id <=", value, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idIn(List<Integer> values) {
            addCriterion("media_id in", values, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idNotIn(List<Integer> values) {
            addCriterion("media_id not in", values, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idBetween(Integer value1, Integer value2) {
            addCriterion("media_id between", value1, value2, "media_id");
            return (Criteria) this;
        }

        public Criteria andMedia_idNotBetween(Integer value1, Integer value2) {
            addCriterion("media_id not between", value1, value2, "media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idIsNull() {
            addCriterion("dsp_id is null");
            return (Criteria) this;
        }

        public Criteria andDsp_idIsNotNull() {
            addCriterion("dsp_id is not null");
            return (Criteria) this;
        }

        public Criteria andDsp_idEqualTo(Integer value) {
            addCriterion("dsp_id =", value, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idNotEqualTo(Integer value) {
            addCriterion("dsp_id <>", value, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idGreaterThan(Integer value) {
            addCriterion("dsp_id >", value, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("dsp_id >=", value, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idLessThan(Integer value) {
            addCriterion("dsp_id <", value, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idLessThanOrEqualTo(Integer value) {
            addCriterion("dsp_id <=", value, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idIn(List<Integer> values) {
            addCriterion("dsp_id in", values, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idNotIn(List<Integer> values) {
            addCriterion("dsp_id not in", values, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idBetween(Integer value1, Integer value2) {
            addCriterion("dsp_id between", value1, value2, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_idNotBetween(Integer value1, Integer value2) {
            addCriterion("dsp_id not between", value1, value2, "dsp_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idIsNull() {
            addCriterion("dsp_media_id is null");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idIsNotNull() {
            addCriterion("dsp_media_id is not null");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idEqualTo(String value) {
            addCriterion("dsp_media_id =", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idNotEqualTo(String value) {
            addCriterion("dsp_media_id <>", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idGreaterThan(String value) {
            addCriterion("dsp_media_id >", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idGreaterThanOrEqualTo(String value) {
            addCriterion("dsp_media_id >=", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idLessThan(String value) {
            addCriterion("dsp_media_id <", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idLessThanOrEqualTo(String value) {
            addCriterion("dsp_media_id <=", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idLike(String value) {
            addCriterion("dsp_media_id like", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idNotLike(String value) {
            addCriterion("dsp_media_id not like", value, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idIn(List<String> values) {
            addCriterion("dsp_media_id in", values, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idNotIn(List<String> values) {
            addCriterion("dsp_media_id not in", values, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idBetween(String value1, String value2) {
            addCriterion("dsp_media_id between", value1, value2, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_media_idNotBetween(String value1, String value2) {
            addCriterion("dsp_media_id not between", value1, value2, "dsp_media_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idIsNull() {
            addCriterion("dsp_pos_id is null");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idIsNotNull() {
            addCriterion("dsp_pos_id is not null");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idEqualTo(String value) {
            addCriterion("dsp_pos_id =", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idNotEqualTo(String value) {
            addCriterion("dsp_pos_id <>", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idGreaterThan(String value) {
            addCriterion("dsp_pos_id >", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idGreaterThanOrEqualTo(String value) {
            addCriterion("dsp_pos_id >=", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idLessThan(String value) {
            addCriterion("dsp_pos_id <", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idLessThanOrEqualTo(String value) {
            addCriterion("dsp_pos_id <=", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idLike(String value) {
            addCriterion("dsp_pos_id like", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idNotLike(String value) {
            addCriterion("dsp_pos_id not like", value, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idIn(List<String> values) {
            addCriterion("dsp_pos_id in", values, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idNotIn(List<String> values) {
            addCriterion("dsp_pos_id not in", values, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idBetween(String value1, String value2) {
            addCriterion("dsp_pos_id between", value1, value2, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andDsp_pos_idNotBetween(String value1, String value2) {
            addCriterion("dsp_pos_id not between", value1, value2, "dsp_pos_id");
            return (Criteria) this;
        }

        public Criteria andPv_sIsNull() {
            addCriterion("pv_s is null");
            return (Criteria) this;
        }

        public Criteria andPv_sIsNotNull() {
            addCriterion("pv_s is not null");
            return (Criteria) this;
        }

        public Criteria andPv_sEqualTo(Integer value) {
            addCriterion("pv_s =", value, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sNotEqualTo(Integer value) {
            addCriterion("pv_s <>", value, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sGreaterThan(Integer value) {
            addCriterion("pv_s >", value, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("pv_s >=", value, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sLessThan(Integer value) {
            addCriterion("pv_s <", value, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sLessThanOrEqualTo(Integer value) {
            addCriterion("pv_s <=", value, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sIn(List<Integer> values) {
            addCriterion("pv_s in", values, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sNotIn(List<Integer> values) {
            addCriterion("pv_s not in", values, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sBetween(Integer value1, Integer value2) {
            addCriterion("pv_s between", value1, value2, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_sNotBetween(Integer value1, Integer value2) {
            addCriterion("pv_s not between", value1, value2, "pv_s");
            return (Criteria) this;
        }

        public Criteria andPv_timeIsNull() {
            addCriterion("pv_time is null");
            return (Criteria) this;
        }

        public Criteria andPv_timeIsNotNull() {
            addCriterion("pv_time is not null");
            return (Criteria) this;
        }

        public Criteria andPv_timeEqualTo(Long value) {
            addCriterion("pv_time =", value, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeNotEqualTo(Long value) {
            addCriterion("pv_time <>", value, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeGreaterThan(Long value) {
            addCriterion("pv_time >", value, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeGreaterThanOrEqualTo(Long value) {
            addCriterion("pv_time >=", value, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeLessThan(Long value) {
            addCriterion("pv_time <", value, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeLessThanOrEqualTo(Long value) {
            addCriterion("pv_time <=", value, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeIn(List<Long> values) {
            addCriterion("pv_time in", values, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeNotIn(List<Long> values) {
            addCriterion("pv_time not in", values, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeBetween(Long value1, Long value2) {
            addCriterion("pv_time between", value1, value2, "pv_time");
            return (Criteria) this;
        }

        public Criteria andPv_timeNotBetween(Long value1, Long value2) {
            addCriterion("pv_time not between", value1, value2, "pv_time");
            return (Criteria) this;
        }

        public Criteria andClikc_sIsNull() {
            addCriterion("clikc_s is null");
            return (Criteria) this;
        }

        public Criteria andClikc_sIsNotNull() {
            addCriterion("clikc_s is not null");
            return (Criteria) this;
        }

        public Criteria andClikc_sEqualTo(Integer value) {
            addCriterion("clikc_s =", value, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sNotEqualTo(Integer value) {
            addCriterion("clikc_s <>", value, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sGreaterThan(Integer value) {
            addCriterion("clikc_s >", value, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("clikc_s >=", value, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sLessThan(Integer value) {
            addCriterion("clikc_s <", value, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sLessThanOrEqualTo(Integer value) {
            addCriterion("clikc_s <=", value, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sIn(List<Integer> values) {
            addCriterion("clikc_s in", values, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sNotIn(List<Integer> values) {
            addCriterion("clikc_s not in", values, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sBetween(Integer value1, Integer value2) {
            addCriterion("clikc_s between", value1, value2, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_sNotBetween(Integer value1, Integer value2) {
            addCriterion("clikc_s not between", value1, value2, "clikc_s");
            return (Criteria) this;
        }

        public Criteria andClikc_timeIsNull() {
            addCriterion("clikc_time is null");
            return (Criteria) this;
        }

        public Criteria andClikc_timeIsNotNull() {
            addCriterion("clikc_time is not null");
            return (Criteria) this;
        }

        public Criteria andClikc_timeEqualTo(Long value) {
            addCriterion("clikc_time =", value, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeNotEqualTo(Long value) {
            addCriterion("clikc_time <>", value, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeGreaterThan(Long value) {
            addCriterion("clikc_time >", value, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeGreaterThanOrEqualTo(Long value) {
            addCriterion("clikc_time >=", value, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeLessThan(Long value) {
            addCriterion("clikc_time <", value, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeLessThanOrEqualTo(Long value) {
            addCriterion("clikc_time <=", value, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeIn(List<Long> values) {
            addCriterion("clikc_time in", values, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeNotIn(List<Long> values) {
            addCriterion("clikc_time not in", values, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeBetween(Long value1, Long value2) {
            addCriterion("clikc_time between", value1, value2, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andClikc_timeNotBetween(Long value1, Long value2) {
            addCriterion("clikc_time not between", value1, value2, "clikc_time");
            return (Criteria) this;
        }

        public Criteria andV_start_sIsNull() {
            addCriterion("v_start_s is null");
            return (Criteria) this;
        }

        public Criteria andV_start_sIsNotNull() {
            addCriterion("v_start_s is not null");
            return (Criteria) this;
        }

        public Criteria andV_start_sEqualTo(Integer value) {
            addCriterion("v_start_s =", value, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sNotEqualTo(Integer value) {
            addCriterion("v_start_s <>", value, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sGreaterThan(Integer value) {
            addCriterion("v_start_s >", value, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("v_start_s >=", value, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sLessThan(Integer value) {
            addCriterion("v_start_s <", value, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sLessThanOrEqualTo(Integer value) {
            addCriterion("v_start_s <=", value, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sIn(List<Integer> values) {
            addCriterion("v_start_s in", values, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sNotIn(List<Integer> values) {
            addCriterion("v_start_s not in", values, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sBetween(Integer value1, Integer value2) {
            addCriterion("v_start_s between", value1, value2, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_start_sNotBetween(Integer value1, Integer value2) {
            addCriterion("v_start_s not between", value1, value2, "v_start_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sIsNull() {
            addCriterion("v_end_s is null");
            return (Criteria) this;
        }

        public Criteria andV_end_sIsNotNull() {
            addCriterion("v_end_s is not null");
            return (Criteria) this;
        }

        public Criteria andV_end_sEqualTo(Integer value) {
            addCriterion("v_end_s =", value, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sNotEqualTo(Integer value) {
            addCriterion("v_end_s <>", value, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sGreaterThan(Integer value) {
            addCriterion("v_end_s >", value, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("v_end_s >=", value, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sLessThan(Integer value) {
            addCriterion("v_end_s <", value, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sLessThanOrEqualTo(Integer value) {
            addCriterion("v_end_s <=", value, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sIn(List<Integer> values) {
            addCriterion("v_end_s in", values, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sNotIn(List<Integer> values) {
            addCriterion("v_end_s not in", values, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sBetween(Integer value1, Integer value2) {
            addCriterion("v_end_s between", value1, value2, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andV_end_sNotBetween(Integer value1, Integer value2) {
            addCriterion("v_end_s not between", value1, value2, "v_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sIsNull() {
            addCriterion("download_start_s is null");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sIsNotNull() {
            addCriterion("download_start_s is not null");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sEqualTo(Integer value) {
            addCriterion("download_start_s =", value, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sNotEqualTo(Integer value) {
            addCriterion("download_start_s <>", value, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sGreaterThan(Integer value) {
            addCriterion("download_start_s >", value, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("download_start_s >=", value, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sLessThan(Integer value) {
            addCriterion("download_start_s <", value, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sLessThanOrEqualTo(Integer value) {
            addCriterion("download_start_s <=", value, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sIn(List<Integer> values) {
            addCriterion("download_start_s in", values, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sNotIn(List<Integer> values) {
            addCriterion("download_start_s not in", values, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sBetween(Integer value1, Integer value2) {
            addCriterion("download_start_s between", value1, value2, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_start_sNotBetween(Integer value1, Integer value2) {
            addCriterion("download_start_s not between", value1, value2, "download_start_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sIsNull() {
            addCriterion("download_end_s is null");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sIsNotNull() {
            addCriterion("download_end_s is not null");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sEqualTo(Integer value) {
            addCriterion("download_end_s =", value, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sNotEqualTo(Integer value) {
            addCriterion("download_end_s <>", value, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sGreaterThan(Integer value) {
            addCriterion("download_end_s >", value, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("download_end_s >=", value, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sLessThan(Integer value) {
            addCriterion("download_end_s <", value, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sLessThanOrEqualTo(Integer value) {
            addCriterion("download_end_s <=", value, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sIn(List<Integer> values) {
            addCriterion("download_end_s in", values, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sNotIn(List<Integer> values) {
            addCriterion("download_end_s not in", values, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sBetween(Integer value1, Integer value2) {
            addCriterion("download_end_s between", value1, value2, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andDownload_end_sNotBetween(Integer value1, Integer value2) {
            addCriterion("download_end_s not between", value1, value2, "download_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sIsNull() {
            addCriterion("install_start_s is null");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sIsNotNull() {
            addCriterion("install_start_s is not null");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sEqualTo(Integer value) {
            addCriterion("install_start_s =", value, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sNotEqualTo(Integer value) {
            addCriterion("install_start_s <>", value, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sGreaterThan(Integer value) {
            addCriterion("install_start_s >", value, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("install_start_s >=", value, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sLessThan(Integer value) {
            addCriterion("install_start_s <", value, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sLessThanOrEqualTo(Integer value) {
            addCriterion("install_start_s <=", value, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sIn(List<Integer> values) {
            addCriterion("install_start_s in", values, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sNotIn(List<Integer> values) {
            addCriterion("install_start_s not in", values, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sBetween(Integer value1, Integer value2) {
            addCriterion("install_start_s between", value1, value2, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_start_sNotBetween(Integer value1, Integer value2) {
            addCriterion("install_start_s not between", value1, value2, "install_start_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sIsNull() {
            addCriterion("install_end_s is null");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sIsNotNull() {
            addCriterion("install_end_s is not null");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sEqualTo(Integer value) {
            addCriterion("install_end_s =", value, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sNotEqualTo(Integer value) {
            addCriterion("install_end_s <>", value, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sGreaterThan(Integer value) {
            addCriterion("install_end_s >", value, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("install_end_s >=", value, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sLessThan(Integer value) {
            addCriterion("install_end_s <", value, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sLessThanOrEqualTo(Integer value) {
            addCriterion("install_end_s <=", value, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sIn(List<Integer> values) {
            addCriterion("install_end_s in", values, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sNotIn(List<Integer> values) {
            addCriterion("install_end_s not in", values, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sBetween(Integer value1, Integer value2) {
            addCriterion("install_end_s between", value1, value2, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andInstall_end_sNotBetween(Integer value1, Integer value2) {
            addCriterion("install_end_s not between", value1, value2, "install_end_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sIsNull() {
            addCriterion("deeplink_s is null");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sIsNotNull() {
            addCriterion("deeplink_s is not null");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sEqualTo(Integer value) {
            addCriterion("deeplink_s =", value, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sNotEqualTo(Integer value) {
            addCriterion("deeplink_s <>", value, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sGreaterThan(Integer value) {
            addCriterion("deeplink_s >", value, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("deeplink_s >=", value, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sLessThan(Integer value) {
            addCriterion("deeplink_s <", value, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sLessThanOrEqualTo(Integer value) {
            addCriterion("deeplink_s <=", value, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sIn(List<Integer> values) {
            addCriterion("deeplink_s in", values, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sNotIn(List<Integer> values) {
            addCriterion("deeplink_s not in", values, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sBetween(Integer value1, Integer value2) {
            addCriterion("deeplink_s between", value1, value2, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andDeeplink_sNotBetween(Integer value1, Integer value2) {
            addCriterion("deeplink_s not between", value1, value2, "deeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sIsNull() {
            addCriterion("ideeplink_s is null");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sIsNotNull() {
            addCriterion("ideeplink_s is not null");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sEqualTo(Integer value) {
            addCriterion("ideeplink_s =", value, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sNotEqualTo(Integer value) {
            addCriterion("ideeplink_s <>", value, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sGreaterThan(Integer value) {
            addCriterion("ideeplink_s >", value, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("ideeplink_s >=", value, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sLessThan(Integer value) {
            addCriterion("ideeplink_s <", value, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sLessThanOrEqualTo(Integer value) {
            addCriterion("ideeplink_s <=", value, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sIn(List<Integer> values) {
            addCriterion("ideeplink_s in", values, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sNotIn(List<Integer> values) {
            addCriterion("ideeplink_s not in", values, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sBetween(Integer value1, Integer value2) {
            addCriterion("ideeplink_s between", value1, value2, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIdeeplink_sNotBetween(Integer value1, Integer value2) {
            addCriterion("ideeplink_s not between", value1, value2, "ideeplink_s");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andRes_sIsNull() {
            addCriterion("res_s is null");
            return (Criteria) this;
        }

        public Criteria andRes_sIsNotNull() {
            addCriterion("res_s is not null");
            return (Criteria) this;
        }

        public Criteria andRes_sEqualTo(Integer value) {
            addCriterion("res_s =", value, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sNotEqualTo(Integer value) {
            addCriterion("res_s <>", value, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sGreaterThan(Integer value) {
            addCriterion("res_s >", value, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sGreaterThanOrEqualTo(Integer value) {
            addCriterion("res_s >=", value, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sLessThan(Integer value) {
            addCriterion("res_s <", value, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sLessThanOrEqualTo(Integer value) {
            addCriterion("res_s <=", value, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sIn(List<Integer> values) {
            addCriterion("res_s in", values, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sNotIn(List<Integer> values) {
            addCriterion("res_s not in", values, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sBetween(Integer value1, Integer value2) {
            addCriterion("res_s between", value1, value2, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sNotBetween(Integer value1, Integer value2) {
            addCriterion("res_s not between", value1, value2, "res_s");
            return (Criteria) this;
        }

        public Criteria andRes_sysIsNull() {
            addCriterion("res_sys is null");
            return (Criteria) this;
        }

        public Criteria andRes_sysIsNotNull() {
            addCriterion("res_sys is not null");
            return (Criteria) this;
        }

        public Criteria andRes_sysEqualTo(Long value) {
            addCriterion("res_sys =", value, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysNotEqualTo(Long value) {
            addCriterion("res_sys <>", value, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysGreaterThan(Long value) {
            addCriterion("res_sys >", value, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysGreaterThanOrEqualTo(Long value) {
            addCriterion("res_sys >=", value, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysLessThan(Long value) {
            addCriterion("res_sys <", value, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysLessThanOrEqualTo(Long value) {
            addCriterion("res_sys <=", value, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysIn(List<Long> values) {
            addCriterion("res_sys in", values, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysNotIn(List<Long> values) {
            addCriterion("res_sys not in", values, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysBetween(Long value1, Long value2) {
            addCriterion("res_sys between", value1, value2, "res_sys");
            return (Criteria) this;
        }

        public Criteria andRes_sysNotBetween(Long value1, Long value2) {
            addCriterion("res_sys not between", value1, value2, "res_sys");
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