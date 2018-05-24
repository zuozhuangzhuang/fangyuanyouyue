package com.fangyuanyouyue.user.param;

import io.swagger.annotations.ApiModel;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;

//@ApiModel(value = "用户相关参数")
public class UserParam extends BaseParam {
	private String phone;//手机号，账号
	private String loginPwd;//登录密码
	private String cliendId;//识别号
	private String code;//
	
	private String nickName;//用户昵称
	private String gender;//用户性别
	private Integer userId;//用户ID
	private String token;//用户token

	private String cardNo;//身份证号
	private String realName;//真实姓名
	private String content;//申请实名认证理由
	private String type;//
	
	private String userName;//
	private String address;//详细地址
	private String province;//省
	private String city;//市
	private String area;//区
	
	private Integer addressId;//地址ID
	
	private String version;//版本
	
	private Integer catalogId;//
	private String title;//标题
	private BigDecimal price;//价格
	private MultipartFile[] imgUrl;//图片路径
	private String imgWidth;//图片宽
	private String imgHeight;//图片高
	private Long startTime;//竞拍开始时间
	private Long endTime;//竞拍结束时间
	private BigDecimal addPrice;//加价幅度
	
	private String search;//查询字段
	
	private Integer goodsId;//商品ID
	
	private Integer orderId;//订单ID

	private Integer commentId;//留言ID
	private Integer replyId;//回复ID
	
	private String payPwd;//支付密码
	
	private MultipartFile file1;//图片文件1
	private MultipartFile file2;
	private MultipartFile file3;
	private MultipartFile file4;
	private MultipartFile file5;
	private MultipartFile file6;
	private String pic1;//图片路径1
	private String pic2;
	private String pic3;
	private String pic4;
	private String pic5;
	private String pic6;
	
	private MultipartFile headImg;//头像
	
	private Integer companyId;//物流公司ID
	private String number;//物流公司编号
	
	private String description;//商品描述/商品详情
	
	private String newPwd;//新密码
	
	private String birth;//生日
	
	private String qqCliend;//qq三方识别号
	private String wechatCliend;//微信三方识别号
	
	private String thirdNo;//三方订单号
	private String unionId;//微信识别码
	
	private String headUrl;//头像路径
	
	private BigDecimal withdrawPrice;
	private String account;
	private BigDecimal decisionPrice;//一口价
	private String isSpecial;//是否特价商品  0是 1否
	
	private String goodsType;
	private String sortType;
	private String classify;
	private BigDecimal postage;//邮费
	
	private String autograph;
	
	private String goods;
	private String orderIds;

	private String contact;
	private String platform;
	
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public BigDecimal getPostage() {
		return postage;
	}
	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getIsSpecial() {
		return isSpecial;
	}
	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}
	public BigDecimal getDecisionPrice() {
		return decisionPrice;
	}
	public void setDecisionPrice(BigDecimal decisionPrice) {
		this.decisionPrice = decisionPrice;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public BigDecimal getWithdrawPrice() {
		return withdrawPrice;
	}
	public void setWithdrawPrice(BigDecimal withdrawPrice) {
		this.withdrawPrice = withdrawPrice;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getThirdNo() {
		return thirdNo;
	}
	public void setThirdNo(String thirdNo) {
		this.thirdNo = thirdNo;
	}
	public String getQqCliend() {
		return qqCliend;
	}
	public void setQqCliend(String qqCliend) {
		this.qqCliend = qqCliend;
	}
	public String getWechatCliend() {
		return wechatCliend;
	}
	public void setWechatCliend(String wechatCliend) {
		this.wechatCliend = wechatCliend;
	}
	public MultipartFile getHeadImg() {
		return headImg;
	}
	public void setHeadImg(MultipartFile headImg) {
		this.headImg = headImg;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public MultipartFile getFile6() {
		return file6;
	}
	public void setFile6(MultipartFile file6) {
		this.file6 = file6;
	}
	public MultipartFile getFile1() {
		return file1;
	}
	public void setFile1(MultipartFile file1) {
		this.file1 = file1;
	}
	public MultipartFile getFile2() {
		return file2;
	}
	public void setFile2(MultipartFile file2) {
		this.file2 = file2;
	}
	public MultipartFile getFile3() {
		return file3;
	}
	public void setFile3(MultipartFile file3) {
		this.file3 = file3;
	}
	public MultipartFile getFile4() {
		return file4;
	}
	public void setFile4(MultipartFile file4) {
		this.file4 = file4;
	}
	public MultipartFile getFile5() {
		return file5;
	}
	public void setFile5(MultipartFile file5) {
		this.file5 = file5;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getAddPrice() {
		return addPrice;
	}
	public void setAddPrice(BigDecimal addPrice) {
		this.addPrice = addPrice;
	}
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public MultipartFile[] getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(MultipartFile[] imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}
	public String getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCliendId() {
		return cliendId;
	}
	public void setCliendId(String cliendId) {
		this.cliendId = cliendId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public String getPic4() {
		return pic4;
	}
	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}
	public String getPic5() {
		return pic5;
	}
	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}
	public String getPic6() {
		return pic6;
	}
	public void setPic6(String pic6) {
		this.pic6 = pic6;
	}
	
	@Override
	public String toString() {
		return "ClientUserParam [phone=" + phone + ", loginPwd=" + loginPwd
				+ ", cliendId=" + cliendId + ", code=" + code + ", nickName="
				+ nickName + ", gender=" + gender + ", userId=" + userId
				+ ", token=" + token + ", cardNo=" + cardNo + ", realName="
				+ realName + ", content=" + content + ", type=" + type
				+ ", userName=" + userName + ", address=" + address
				+ ", province=" + province + ", city=" + city + ", area="
				+ area + ", addressId=" + addressId + ", version=" + version
				+ ", catalogId=" + catalogId + ", title=" + title + ", price="
				+ price + ", imgUrl=" + Arrays.toString(imgUrl) + ", imgWidth="
				+ imgWidth + ", imgHeight=" + imgHeight + ", startTime="
				+ startTime + ", endTime=" + endTime + ", addPrice=" + addPrice
				+ ", search=" + search + ", goodsId=" + goodsId + ", orderId="
				+ orderId + ", commentId=" + commentId + ", replyId=" + replyId
				+ ", payPwd=" + payPwd + ", file1=" + file1 + ", file2="
				+ file2 + ", file3=" + file3 + ", file4=" + file4 + ", file5="
				+ file5 + ", file6=" + file6 + ", pic1=" + pic1 + ", pic2="
				+ pic2 + ", pic3=" + pic3 + ", pic4=" + pic4 + ", pic5=" + pic5
				+ ", pic6=" + pic6 + ", headImg=" + headImg + ", companyId="
				+ companyId + ", number=" + number + ", description="
				+ description + ", newPwd=" + newPwd + ", birth=" + birth
				+ ", qqCliend=" + qqCliend + ", wechatCliend=" + wechatCliend
				+ ", thirdNo=" + thirdNo + ", unionId=" + unionId
				+ ", headUrl=" + headUrl + ", withdrawPrice=" + withdrawPrice
				+ ", account=" + account + ", decisionPrice=" + decisionPrice
				+ ", isSpecial=" + isSpecial + ", goodsType=" + goodsType
				+ ", sortType=" + sortType + ", classify=" + classify
				+ ", postage=" + postage + ", autograph=" + autograph
				+ ", goods=" + goods + ", orderIds=" + orderIds + ", contact="
				+ contact + ", platform=" + platform + "]";
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
}
