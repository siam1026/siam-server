package com.siam.system.modular.package_goods.entity;

import java.util.Date;

public class WxPublicPlatformSubscribeUser {
    private Integer id;

    private String subscribe;

    private String openid;

    private String nickname;

    private String sex;

    private String language;

    private String city;

    private String province;

    private String country;

    private String headimgurl;

    private String subscribeTime;

    private String remark;

    private String groupid;

    private String tagidList;

    private String subscribeScene;

    private String qrScene;

    private String qrSceneStr;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe == null ? null : subscribe.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime == null ? null : subscribeTime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getTagidList() {
        return tagidList;
    }

    public void setTagidList(String tagidList) {
        this.tagidList = tagidList == null ? null : tagidList.trim();
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene == null ? null : subscribeScene.trim();
    }

    public String getQrScene() {
        return qrScene;
    }

    public void setQrScene(String qrScene) {
        this.qrScene = qrScene == null ? null : qrScene.trim();
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr == null ? null : qrSceneStr.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}