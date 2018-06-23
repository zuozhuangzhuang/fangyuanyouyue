package com.fangyuanyouyue.user.model;

import java.util.Date;

public class UserExamine {
    private Integer id;//唯一自增ID

    private Integer userId;//用户id，user_info id

    private String oldNickname;//申请前昵称

    private String newNickname;//待审核昵称

    private Integer status;//审核状态 0申请中 1申请通过 2已拒绝

    private String content;//拒绝信息

    private Date addTime;//添加时间

    private Date updateTime;//更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOldNickname() {
        return oldNickname;
    }

    public void setOldNickname(String oldNickname) {
        this.oldNickname = oldNickname == null ? null : oldNickname.trim();
    }

    public String getNewNickname() {
        return newNickname;
    }

    public void setNewNickname(String newNickname) {
        this.newNickname = newNickname == null ? null : newNickname.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}