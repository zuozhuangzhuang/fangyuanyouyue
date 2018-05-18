package com.fangyuanyouyue.user.model;

import java.math.BigDecimal;

public class User {
    private Integer id;

    private String phone;//手机号

    private String nickname;//昵称

    private String gender;//性别

    private Long addTime;//添加时间

    private BigDecimal balance;//余额

    private String status;//状态

    private String imgUrl;//头像地址

    private String loginPwd;//登录密码

    private String isHx;//是否注册环信0是  1否

    private String alipay;//支付宝账号

    private String wechat;//微信账号

    private String token;//用户标识

    private Integer userExtId;//用户实名认证表ID

    private String payPwd;//支付密码

    private String birth;//出生日期

    private String qqCliend;//三方qq识别号

    private String wechatCliend;//三方微信识别号

    private String isExt;//是否进行实名认证0未申请  1已申请

    private String hxPwd;//环信密码

    private String autograph;//签名

    private String address;//用户所在地

    private Integer fansCount;//粉丝数量基数(后台可编辑)

    private Integer userExpId;//经验值外键

    private String contact;//联系方式

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getIsHx() {
        return isHx;
    }

    public void setIsHx(String isHx) {
        this.isHx = isHx == null ? null : isHx.trim();
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getUserExtId() {
        return userExtId;
    }

    public void setUserExtId(Integer userExtId) {
        this.userExtId = userExtId;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd == null ? null : payPwd.trim();
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    public String getQqCliend() {
        return qqCliend;
    }

    public void setQqCliend(String qqCliend) {
        this.qqCliend = qqCliend == null ? null : qqCliend.trim();
    }

    public String getWechatCliend() {
        return wechatCliend;
    }

    public void setWechatCliend(String wechatCliend) {
        this.wechatCliend = wechatCliend == null ? null : wechatCliend.trim();
    }

    public String getIsExt() {
        return isExt;
    }

    public void setIsExt(String isExt) {
        this.isExt = isExt == null ? null : isExt.trim();
    }

    public String getHxPwd() {
        return hxPwd;
    }

    public void setHxPwd(String hxPwd) {
        this.hxPwd = hxPwd == null ? null : hxPwd.trim();
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph == null ? null : autograph.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getUserExpId() {
        return userExpId;
    }

    public void setUserExpId(Integer userExpId) {
        this.userExpId = userExpId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }
}