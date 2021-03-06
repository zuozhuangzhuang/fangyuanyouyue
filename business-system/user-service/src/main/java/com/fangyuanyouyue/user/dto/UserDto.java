package com.fangyuanyouyue.user.dto;

import com.fangyuanyouyue.user.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 用户信息
 */
public class UserDto {
    //UserInfo 用户基本信息表
    private Integer userId;//用户ID

    private String token;//用户token

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


    private Integer identityStatus;//实名认证状态 1申请 2通过 3拒绝

    //UserVip
    private Integer vipLevel;//会员等级

    private String vipLevelDesc;//会员等级描述

    private Integer vipType;//会员类型 1体验会员 2月会员 3年会员

    private Integer vipStatus;//会员状态 1已开通 2未开通

    //UserFans
    private Integer fansCount;//粉丝数量

    private Integer collectCount;//关注数量

    private Integer isFollow = 2;//是否关注 1是 2否

    //UserAddressDto
    private UserAddressDto defaultAddress;//用户默认收货地址


    //UserInfoExt 用户扩展表
    private Integer score;//用户积分

    private Integer credit;//信誉度

    private Integer authType;//认证状态 1已认证 2未认证

    private Integer extStatus;//实名登记状态 1已实名 2未实名

    /**
     * ↓↓↓↓↓↓↓注释掉不需要返回的属性↓↓↓↓↓↓↓
     */

    //IdentityAuthApply
//    private String identityImgCover;//身份证封面图

//    private String identityImgBack;//身份证背面

//    private String identityRejectDesc;//拒绝原因



    //UserThirdParty
//    private Integer thirdType;//类型 1微信 2QQ 3微博

//    private String unionId;//第三方唯一ID

//    private String appOpenId;//微信app openid

//    private String mpOpenId;//公众号openid

//    private String miniOpenId;//小程序openid

//    private String identity;//身份证号码

//    private String name;//真实姓名



    /**
     * ↑↑↑↑↑↑注释掉不需要返回的属性↑↑↑↑↑↑
     */

    public UserDto() {
    }

    public UserDto(String token,UserInfo userInfo, UserVip userVip, UserInfoExt userInfoExt,IdentityAuthApply identityAuthApply) {
        if(StringUtils.isNotEmpty(token)){
            this.token = token;
        }
        //UserInfo
        if(userInfo != null){
            this.userId = userInfo.getId();
            this.phone = userInfo.getPhone();
            this.email = userInfo.getEmail();
            this.userAddress = userInfo.getAddress();
            this.nickName = userInfo.getNickName();
            this.headImgUrl = userInfo.getHeadImgUrl();
            this.bgImgUrl = userInfo.getBgImgUrl();
            this.gender = userInfo.getGender();
            this.signature = userInfo.getSignature();
            this.contact = userInfo.getContact();
            this.level = userInfo.getLevel();
            this.levelDesc = userInfo.getLevelDesc();
        }
        //UserInfoExt
        if(userInfoExt != null){
            this.credit = userInfoExt.getCredit();
            this.score = userInfoExt.getScore();
            this.authType = userInfoExt.getAuthType();
            this.extStatus = userInfoExt.getStatus();
        }
        //IdentityAuthApply
        if(identityAuthApply != null){
//            this.identity = identityAuthApply.getIdentity();
//            this.name = identityAuthApply.getName();
//            this.identityImgCover = identityAuthApply.getIdentityImgCover();
//            this.identityImgBack = identityAuthApply.getIdentityImgBack();
//            this.identityRejectDesc = identityAuthApply.getRejectDesc();
            this.identityStatus = identityAuthApply.getStatus();
        }
        //UserThirdParty
//        if(userThirdParty != null){
//            this.thirdType = userThirdParty.getType();
//            this.unionId = userThirdParty.getUnionId();
//            this.appOpenId = userThirdParty.getAppOpenId();
//            this.mpOpenId = userThirdParty.getMpOpenId();
//            this.miniOpenId = userThirdParty.getMiniOpenId();
//        }
        //UserVip
        if(userVip != null){
            this.vipLevel = userVip.getVipLevel();
            this.vipLevelDesc = userVip.getLevelDesc();
            this.vipType = userVip.getVipType();
            this.vipStatus = userVip.getStatus();
        }
        //UserAddressInfo
//        if(userAddressInfos != null && userAddressInfos.size()>0){
//            this.userAddressDtos = UserAddressDto.toDtoList(userAddressInfos);
//        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Integer getExtStatus() {
        return extStatus;
    }

    public void setExtStatus(Integer extStatus) {
        this.extStatus = extStatus;
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

    public Integer getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(Integer identityStatus) {
        this.identityStatus = identityStatus;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public UserAddressDto getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(UserAddressDto defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }
}
