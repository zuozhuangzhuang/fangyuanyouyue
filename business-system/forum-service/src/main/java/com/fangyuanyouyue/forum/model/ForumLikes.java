package com.fangyuanyouyue.forum.model;

import java.util.Date;

/**
 *论坛帖子点赞表
 */
public class ForumLikes {
    private Integer id;//唯一自增ID

    private Integer userId;//发布用户id

    private Integer forumId;//帖子id

    private Date addTime;//添加时间

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

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}