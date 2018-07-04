package com.fangyuanyouyue.user.dto;

/**
 * 用户信息
 */
public class UserDto {
    //UserInfo 用户基本信息表
    private Integer userId;//用户id

    private String phone;//手机号码

    private String email;//电子邮件

    private String userAddress;//用户所在地

    private String nickName;//昵称

    private String headImgUrl;//头像图片地址

    private String bgImgUrl;//背景图片地址

    private Integer gender;//性别，1男 2女 0不确定

    private String signature;//个性签名

    private String contact;//联系电话

    private Integer level;//用户等级

    private String levelDesc;//等级描述



    //UserInfoExt 用户扩展表
    private String identity;//身份证号码

    private String name;//真实姓名

    private Integer extStatus;//实名登记状态 1已实名 2未实名

    /* ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */
    //IdentityAuthApply
    private String identityImgCover;//身份证封面图

    private String identityImgBack;//身份证背面

    private String identityRejectDesc;//拒绝原因

    private Integer identityStatus;//实名认证状态 1申请 2通过 3拒绝



    //UserThirdParty
    private Integer thirdType;//类型 1微信 2QQ 3微博

    private String unionId;//第三方唯一ID

    private String appOpenId;//微信app openid

    private String mpOpenId;//公众号openid

    private String miniOpenId;//小程序openid

    private String thirdNickName;//第三方账号昵称

    private String thirdHeadImgUrl;//第三方账号头像地址





    //UserVip
    private Integer vipLevel;//会员等级

    private String vipLevelDesc;//会员等级描述

    private Integer vipType;//会员类型 1体验会员 2月会员 3年会员

    private Integer vipStatus;//会员状态1已开通 2未开通



    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getBgImgUrl() {
        return bgImgUrl;
    }

    public void setBgImgUrl(String bgImgUrl) {
        this.bgImgUrl = bgImgUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExtStatus() {
        return extStatus;
    }

    public void setExtStatus(Integer extStatus) {
        this.extStatus = extStatus;
    }

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppOpenId() {
        return appOpenId;
    }

    public void setAppOpenId(String appOpenId) {
        this.appOpenId = appOpenId;
    }

    public String getMpOpenId() {
        return mpOpenId;
    }

    public void setMpOpenId(String mpOpenId) {
        this.mpOpenId = mpOpenId;
    }

    public String getMiniOpenId() {
        return miniOpenId;
    }

    public void setMiniOpenId(String miniOpenId) {
        this.miniOpenId = miniOpenId;
    }

    public String getThirdNickName() {
        return thirdNickName;
    }

    public void setThirdNickName(String thirdNickName) {
        this.thirdNickName = thirdNickName;
    }

    public String getThirdHeadImgUrl() {
        return thirdHeadImgUrl;
    }

    public void setThirdHeadImgUrl(String thirdHeadImgUrl) {
        this.thirdHeadImgUrl = thirdHeadImgUrl;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipLevelDesc() {
        return vipLevelDesc;
    }

    public void setVipLevelDesc(String vipLevelDesc) {
        this.vipLevelDesc = vipLevelDesc;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Integer getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getIdentityImgCover() {
        return identityImgCover;
    }

    public void setIdentityImgCover(String identityImgCover) {
        this.identityImgCover = identityImgCover;
    }

    public String getIdentityImgBack() {
        return identityImgBack;
    }

    public void setIdentityImgBack(String identityImgBack) {
        this.identityImgBack = identityImgBack;
    }

    public String getIdentityRejectDesc() {
        return identityRejectDesc;
    }

    public void setIdentityRejectDesc(String identityRejectDesc) {
        this.identityRejectDesc = identityRejectDesc;
    }

    public Integer getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(Integer identityStatus) {
        this.identityStatus = identityStatus;
    }
}
