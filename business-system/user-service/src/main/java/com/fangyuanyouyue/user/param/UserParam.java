package com.fangyuanyouyue.user.param;

import java.util.Date;

//@ApiModel(value = "用户相关参数")
public class UserParam extends BaseParam {

	private String phone;//手机号码

	private String email;//电子邮件

	private String loginPwd;//登录密码,MD5小写

	private String nickName;//昵称

	private String headImgUrl;//头像图片地址

	private String bgImgUrl;//背景图片地址

	private Integer gender;//性别，0女 1男 2不确定

	private String signature;//个性签名

	private String contact;//联系电话

	private Integer regType;//注册来源 1app 2微信小程序

	private Integer regPlatform;//注册平台 1安卓 2IOS 3小程序

	private String levelDesc;//等级描述

	private Integer status;//状态 1正常 2冻结

	private Date updateTime;//更新时间

	private Integer userId;//用户ID

	private String newPwd;//新密码

	private String birth;//生日

	private String name;//真实姓名

	private String identity;//身份证号码

	private String identityImgCover;//身份证封面图

	private String identityImgBack;//身份证背面

	private String receiverName;//收货人

	private String receiverPhone;//收货人手机号码

	private String province;//省

	private String city;//市

	private String area;//区

	private String address;//详细地址

	private String postCode;//邮编

	private Integer addressId;//地址ID

	private String unionId;//第三方唯一ID

	private String appOpenId;//微信app openid

	private String mpOpenId;//公众号openid

	private String miniOpenId;//小程序openid

	private String thirdNickName;//第三方账号昵称

	private String thirdHeadImgUrl;//第三方账号头像地址

	private Integer lastLoginPlatform;//最后登录平台 1安卓 2IOS 3小程序

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

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
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

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public Integer getRegPlatform() {
		return regPlatform;
	}

	public void setRegPlatform(Integer regPlatform) {
		this.regPlatform = regPlatform;
	}

	public String getLevelDesc() {
		return levelDesc;
	}

	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
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

	public Integer getLastLoginPlatform() {
		return lastLoginPlatform;
	}

	public void setLastLoginPlatform(Integer lastLoginPlatform) {
		this.lastLoginPlatform = lastLoginPlatform;
	}

	@Override
	public String toString() {
		return "UserParam{" +
				"phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", loginPwd='" + loginPwd + '\'' +
				", nickName='" + nickName + '\'' +
				", headImgUrl='" + headImgUrl + '\'' +
				", bgImgUrl='" + bgImgUrl + '\'' +
				", gender=" + gender +
				", signature='" + signature + '\'' +
				", contact='" + contact + '\'' +
				", regType=" + regType +
				", regPlatform=" + regPlatform +
				", levelDesc='" + levelDesc + '\'' +
				", status=" + status +
				", updateTime=" + updateTime +
				", userId=" + userId +
				", newPwd='" + newPwd + '\'' +
				", birth='" + birth + '\'' +
				", name='" + name + '\'' +
				", identity='" + identity + '\'' +
				", identityImgCover='" + identityImgCover + '\'' +
				", identityImgBack='" + identityImgBack + '\'' +
				", receiverName='" + receiverName + '\'' +
				", receiverPhone='" + receiverPhone + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", area='" + area + '\'' +
				", address='" + address + '\'' +
				", postCode='" + postCode + '\'' +
				", addressId=" + addressId +
				", unionId='" + unionId + '\'' +
				", appOpenId='" + appOpenId + '\'' +
				", mpOpenId='" + mpOpenId + '\'' +
				", miniOpenId='" + miniOpenId + '\'' +
				", thirdNickName='" + thirdNickName + '\'' +
				", thirdHeadImgUrl='" + thirdHeadImgUrl + '\'' +
				", lastLoginPlatform=" + lastLoginPlatform +
				'}';
	}
}
