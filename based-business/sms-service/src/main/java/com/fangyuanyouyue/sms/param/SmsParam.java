package com.fangyuanyouyue.sms.param;

import io.swagger.annotations.ApiModelProperty;

public class SmsParam extends BaseParam{
    @ApiModelProperty(name = "userId", value = "发布用户id", dataType = "int",hidden = true)
    private Integer userId;//用户id

    @ApiModelProperty(name = "phone", value = "用户手机号", dataType = "String",hidden = true)
    private String phone;//用户手机号

    @ApiModelProperty(name = "unionId", value = "三方唯一识别号", dataType = "String",hidden = true)
    private String unionId;//三方唯一识别号

    @ApiModelProperty(name = "thirdType", value = "类型 1微信 2QQ 3微博", dataType = "int",hidden = true)
    private Integer thirdType;//类型 1微信 2QQ 3微博

    @ApiModelProperty(name = "code", value = "验证码", dataType = "String",hidden = true)
    private String code;//验证码


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SmsParam{" +
                "userId=" + userId +
                ", phone='" + phone + '\'' +
                ", unionId='" + unionId + '\'' +
                ", thirdType=" + thirdType +
                ", code='" + code + '\'' +
                '}';
    }
}
