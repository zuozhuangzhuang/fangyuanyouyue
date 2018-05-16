package com.fangyuanyouyue.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户详细信息")
public class User {
    @ApiModelProperty(name = "start", value = "用户ID", dataType = "Integer")
    private Long id;
    @ApiModelProperty(name = "start", value = "邮箱", dataType = "String")
    private String email;
    @ApiModelProperty(name = "start", value = "昵称", dataType = "String")
    private String nickName;
    @ApiModelProperty(name = "start", value = "密码", dataType = "String")
    private String passWord;
    @ApiModelProperty(name = "start", value = "注册时间", dataType = "String")
    private String regTime;
    @ApiModelProperty(name = "start", value = "用户名", dataType = "String")
    private String userName;

    public User() {
    }

    public User(String nickName) {
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime == null ? null : regTime.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}